package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class NovaSpikeEntity extends ThrowableEntity {
    private float damage = 4.0F;
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    private int ticksInGround;
    private int ticksInAir;

    public NovaSpikeEntity(World world) {
        super(MidnightEntities.NOVA_SPIKE, world);
    }

    public NovaSpikeEntity(EntityType<NovaSpikeEntity> entityType, World world) {
        super(entityType, world);
        this.setDamage(this.damage);
    }

    public NovaSpikeEntity(World world, double x, double y, double z) {
        super(MidnightEntities.NOVA_SPIKE, x, y, z, world);
        this.setDamage(this.damage);
        this.setPosition(x, y, z);
    }

    public NovaSpikeEntity(World world, LivingEntity thrower) {
        super(MidnightEntities.NOVA_SPIKE, thrower, world);
        this.setDamage(this.damage);
    }

    public NovaSpikeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.NOVA_SPIKE, world);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        LivingEntity thrower = this.getThrower();
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            int fireTimer = entity.getFireTimer();

            DamageSource damage;
            if (thrower == null) {
                damage = DamageSource.causeThrownDamage(this, this);
            } else {
                damage = DamageSource.causeThrownDamage(this, thrower);
            }

            if (entity.attackEntityFrom(damage, this.getDamage())) {
                this.remove();
            } else {
                entity.setFireTimer(fireTimer);
                this.setMotion(this.getMotion().scale(-0.1D));
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                this.ticksInAir = 0;
                if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                    this.remove();
                }
            }
        } else if (result.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) result;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());

            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            if (!blockstate.getCollisionShape(this.world, blockpos).isEmpty()) {
                this.inBlockState = blockstate;
                Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.posX, this.posY, this.posZ);
                this.setMotion(vec3d);
                Vec3d vec3d1 = vec3d.normalize().scale((double) 0.05F);
                this.posX -= vec3d1.x;
                this.posY -= vec3d1.y;
                this.posZ -= vec3d1.z;
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                this.inGround = true;
                blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
            }
        }
    }

    @Override
    public void tick() {
        Vec3d motion = this.getMotion();
        BlockState state = this.world.getBlockState(this.getPosition());

        if (this.inGround) {
            if (this.inBlockState != state && this.world.areCollisionShapesEmpty(this.getBoundingBox().grow(0.06D))) {
                this.inGround = false;
                this.setMotion(motion.mul(this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F));
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            } else if (!this.world.isRemote) {
                this.tryDespawn();
            }

            ++this.timeInGround;
        } else {
            super.tick();
            this.doBlockCollisions();
            this.world.addParticle(MidnightParticleTypes.SPORE, this.posX, this.posY, this.posZ, 0.0D, 0.05, 0.0D);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putShort("life", (short) this.ticksInGround);
        if (this.inBlockState != null) {
            compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compound.putByte("inGround", (byte) (this.inGround ? 1 : 0));
        compound.putFloat("damage", this.damage);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", Constants.NBT.TAG_COMPOUND)) {
            this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
        }

        this.inGround = compound.getByte("inGround") == 1;
        if (compound.contains("damage", Constants.NBT.TAG_ANY_NUMERIC)) {
            this.damage = compound.getFloat("damage");
        }
    }

    protected void tryDespawn() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 300) {
            this.remove();
        }
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    @Override
    protected void registerData() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
