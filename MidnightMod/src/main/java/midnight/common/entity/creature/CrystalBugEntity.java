package midnight.common.entity.creature;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import midnight.common.block.MnBlocks;
import midnight.common.registry.MnSounds;

public class CrystalBugEntity extends AmbientEntity {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(CrystalBugEntity.class, DataSerializers.BOOLEAN);
    private BlockPos spawnPosition;

    public CrystalBugEntity(EntityType<? extends CrystalBugEntity> entityType, World world) {
        super(entityType, world);
        experienceValue = 3;
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(IS_STANDING, false);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return getPositionVec().getY() > world.getSeaLevel();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 10;
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    public boolean isStanding() {
        return dataManager.get(IS_STANDING);
    }

    private void setStanding(boolean isStanding) {
        dataManager.set(IS_STANDING, isStanding);
    }

    @Override
    public void tick() {
        super.tick();
        if (isStanding()) {
            setMotion(Vector3d.ZERO);
        } else {
            setMotion(getMotion().mul(1.0, 0.4, 1.0));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        dataManager.set(IS_STANDING, compound.getBoolean("isStanding"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("isStanding", dataManager.get(IS_STANDING));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return false;
        }
        if (!world.isRemote && isStanding()) {
            setStanding(false);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this.getPositionVec());
        BlockPos blockpos1 = blockpos.offset(getHorizontalFacing());
        if (isStanding()) {
            if (canStayOnBlock(world.getBlockState(blockpos1))) {
                if (world.getClosestPlayer(this, 4d) != null) {
                    setStanding(false);
                    world.playEvent(null, 1025, blockpos, 0);
                }
            } else {
                setStanding(false);
                world.playEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (spawnPosition != null && (!world.isAirBlock(spawnPosition) || spawnPosition.getY() < 1)) {
                spawnPosition = null;
            }
            if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.distanceSq((double) ((int) getPosX()), (double) ((int) getPosY()), (double) ((int) getPosZ()), true) < 4d) {
                spawnPosition = new BlockPos((int) getPosX() + rand.nextInt(7) - rand.nextInt(7), (int) getPosY() + rand.nextInt(6) - 2, (int) getPosZ() + rand.nextInt(7) - rand.nextInt(7));
            }
            double d0 = (double) spawnPosition.getX() + 0.5d - getPosX();
            double d1 = (double) spawnPosition.getY() + 0.1d - getPosY();
            double d2 = (double) spawnPosition.getZ() + 0.5d - getPosZ();
            double addX = (Math.signum(d0) * 0.2d - getMotion().x) * 0.1d;
            double addY = (Math.signum(d1) * 0.4d - getMotion().y) * 0.1d;
            double addZ = (Math.signum(d2) * 0.2d - getMotion().z) * 0.1d;
            setMotion(getMotion().add(addX, addY, addZ));

            float f = (float) (MathHelper.atan2(getMotion().z, getMotion().x) * (180d / Math.PI)) - 90f;
            float f1 = MathHelper.wrapDegrees(f - rotationYaw);
            moveForward = 0.2f;
            rotationYaw += f1;
            if (rand.nextInt(100) == 0 && canStayOnBlock(world.getBlockState(blockpos1))) {
                setStanding(true);
                spawnPosition = blockpos1;
            }
        }
        if (!isStanding() && ticksExisted % 80 == 0) {
            this.playSound(MnSounds.CRYSTAL_BUG_FLYING, getRNG().nextFloat() * 0.6f, getRNG().nextFloat() * 0.15f);
        }
    }

    private boolean canStayOnBlock(BlockState state) {
        return state.getBlock() == MnBlocks.BLOOMCRYSTAL_ROCK;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().func_233815_a_(Attributes.MAX_HEALTH, 3.0D);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height / 3f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MnSounds.CRYSTAL_BUG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MnSounds.CRYSTAL_BUG_DEATH;
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }
}
