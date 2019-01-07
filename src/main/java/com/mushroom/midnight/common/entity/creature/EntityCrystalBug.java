package com.mushroom.midnight.common.entity.creature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.mushroom.midnight.common.registry.ModLootTables.LOOT_TABLE_CRYSTAL_BUG;

public class EntityCrystalBug extends EntityAmbientCreature {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(EntityCrystalBug.class, DataSerializers.BOOLEAN);
    private BlockPos spawnPosition;

    public EntityCrystalBug(World world) {
        super(world);
        setSize(0.2f, 0.2f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(IS_STANDING, false);
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        // TODO ambient sound
        return isStanding() && rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    public boolean isStanding() {
        return dataManager.get(IS_STANDING);
    }

    private void setStanding(boolean isStanding) {
        dataManager.set(IS_STANDING, isStanding);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (isStanding()) {
            motionX = 0d;
            motionY = 0d;
            motionZ = 0d;
        } else {
            motionY *= 0.3d;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        dataManager.set(IS_STANDING, compound.getBoolean("isStanding"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("isStanding", dataManager.get(IS_STANDING));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isEntityInvulnerable(source)) {
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
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.offset(getHorizontalFacing());
        if (isStanding()) {
            if (world.getBlockState(blockpos1).isNormalCube()) {
                if (world.getNearestPlayerNotCreative(this, 4d) != null) {
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
            if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.distanceSq((double) ((int) posX), (double) ((int) posY), (double) ((int) posZ)) < 4d) {
                spawnPosition = new BlockPos((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
            }
            double d0 = (double) spawnPosition.getX() + 0.5d - posX;
            double d1 = (double) spawnPosition.getY() + 0.1d - posY;
            double d2 = (double) spawnPosition.getZ() + 0.5d - posZ;
            motionX += (Math.signum(d0) * 0.2d - motionX) * 0.1d;
            motionY += (Math.signum(d1) * 0.4d - motionY) * 0.1d;
            motionZ += (Math.signum(d2) * 0.2d - motionZ) * 0.1d;
            float f = (float) (MathHelper.atan2(motionZ, motionX) * (180d / Math.PI)) - 90f;
            float f1 = MathHelper.wrapDegrees(f - rotationYaw);
            moveForward = 0.2f;
            rotationYaw += f1;
            if (rand.nextInt(100) == 0 && world.getBlockState(blockpos1).isNormalCube()) {
                setStanding(true);
                spawnPosition = blockpos1;
            }
        }
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

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3d);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public float getEyeHeight() {
        return height / 3f;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE_CRYSTAL_BUG;
    }
}
