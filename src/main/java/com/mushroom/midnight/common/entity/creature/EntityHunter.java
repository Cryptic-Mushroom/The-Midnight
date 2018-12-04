package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.NavigatorFlying;
import com.mushroom.midnight.common.entity.task.EntityTaskHunterIdle;
import com.mushroom.midnight.common.entity.task.EntityTaskHunterSwoop;
import com.mushroom.midnight.common.entity.task.EntityTaskHunterTarget;
import com.mushroom.midnight.common.entity.task.EntityTaskHunterTrack;
import com.mushroom.midnight.common.entity.util.ChainSolver;
import com.mushroom.midnight.common.network.MessageHunterAttack;
import com.mushroom.midnight.common.registry.ModLootTables;
import com.mushroom.midnight.common.util.MeanValueRecorder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.vecmath.Point3f;

public class EntityHunter extends EntityMob implements EntityFlying {
    public static final int ATTACK_ANIMATION_TICKS = 10;

    public static final int FLIGHT_HEIGHT = 40;

    public float roll;
    public float prevRoll;

    public int swoopCooldown;

    public boolean attacking;

    public int attackAnimation;
    public int prevAttackAnimation;

    private final MeanValueRecorder deltaYaw = new MeanValueRecorder(20);
    private final ChainSolver<EntityHunter> chainSolver = new ChainSolver<>(
            new Point3f(0.0F, 0.0F, 0.5875F),
            new Point3f[] {
                    new Point3f(0.0F, 0.0F, 0.775F),
                    new Point3f(0.0F, 0.0F, 1.65F),
                    new Point3f(0.0F, 0.0F, 2.525F)
            },
            0.5F,
            0.5F,
            (entity, matrix) -> {
                matrix.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                matrix.rotate(entity.roll, 0.0F, 0.0F, 1.0F);
                matrix.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
    );

    public EntityHunter(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.moveHelper = new MoveHelper(this);
        this.lookHelper = new LookHelper(this);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        NavigatorFlying navigator = new NavigatorFlying(this, world);
        navigator.setCanOpenDoors(false);
        navigator.setCanFloat(false);
        navigator.setCanEnterDoors(false);
        return navigator;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.12);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityTaskHunterSwoop(this, 1.3));
        this.tasks.addTask(2, new EntityTaskHunterTrack(this, 0.7));
        this.tasks.addTask(100, new EntityTaskHunterIdle(this, 0.6));

        this.targetTasks.addTask(1, new EntityTaskHunterTarget<>(this, EntityPlayer.class));
        this.targetTasks.addTask(2, new EntityTaskHunterTarget<>(this, EntityAnimal.class));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            if (this.swoopCooldown > 0) {
                this.swoopCooldown--;
            }
        } else {
            this.updateAttackAnimation();

            this.deltaYaw.record(this.rotationYaw - this.prevRotationYaw);
            float deltaYaw = this.deltaYaw.computeMean();

            this.prevRoll = this.roll;
            this.roll = MathHelper.clamp(-deltaYaw * 8.0F, -45.0F, 45.0F);

//            this.chainSolver.update(this);
        }
    }

    private void updateAttackAnimation() {
        this.prevAttackAnimation = this.attackAnimation;

        if (this.attacking) {
            if (this.attackAnimation < ATTACK_ANIMATION_TICKS) {
                this.attackAnimation += 1;
            } else {
                this.attackAnimation = 0;
                this.attacking = false;
            }
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean grounded, IBlockState state, BlockPos pos) {
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isServerWorld() || this.canPassengerSteer()) {
            double speed = this.getAIMoveSpeed() * 0.3;

            Vec3d lookVector = this.getLookVec();
            Vec3d moveVector = lookVector.normalize().scale(speed);

            this.motionX += moveVector.x;
            this.motionY += moveVector.y;
            this.motionZ += moveVector.z;

            this.motionX *= 0.91;
            this.motionY *= 0.91;
            this.motionZ *= 0.91;

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        this.updateLimbs();
    }

    private void updateLimbs() {
        this.prevLimbSwingAmount = this.limbSwingAmount;

        double deltaX = this.posX - this.prevPosX;
        double deltaY = this.posY - this.prevPosY;
        double deltaZ = this.posZ - this.prevPosZ;

        float distance = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        float moveAmount = Math.min(distance * 4.0F, 1.0F);

        this.limbSwingAmount += (moveAmount - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) entity;

                float theta = (float) Math.toRadians(this.rotationYaw);
                living.knockBack(this, 0.3F, MathHelper.sin(theta), -MathHelper.cos(theta));

                living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20));
                living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 6 * 20));

                Midnight.NETWORK.sendToAllTracking(new MessageHunterAttack(this), this);
            }

            return true;
        }

        return false;
    }

    public ChainSolver<EntityHunter> getChainSolver() {
        return this.chainSolver;
    }

    @Override
    public boolean getCanSpawnHere() {
        // TODO
        return false;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    private static class MoveHelper extends EntityMoveHelper {
        private static final float CLOSE_TURN_SPEED = 150.0F;
        private static final float FAR_TURN_SPEED = 6.5F;

        private static final float CLOSE_TURN_DISTANCE = 2.0F;
        private static final float FAR_TURN_DISTANCE = 7.0F;

        MoveHelper(EntityHunter parent) {
            super(parent);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                this.action = EntityMoveHelper.Action.WAIT;

                double deltaX = this.posX - this.entity.posX;
                double deltaZ = this.posZ - this.entity.posZ;
                double deltaY = this.posY - this.entity.posY;
                double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

                if (distanceSquared < 2.5) {
                    this.entity.setMoveForward(0.0F);
                    return;
                }

                float distance = MathHelper.sqrt(distanceSquared);
                float turnSpeed = this.computeTurnSpeed(distance);

                float targetYaw = (float) (Math.toDegrees(MathHelper.atan2(deltaZ, deltaX))) - 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, targetYaw, turnSpeed);

                double deltaHorizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

                float targetPitch = (float) -Math.toDegrees(Math.atan2(deltaY, deltaHorizontal));
                this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, targetPitch, turnSpeed);

                double flySpeed = this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue();
                double resultSpeed = this.speed * flySpeed;
                this.entity.setAIMoveSpeed((float) resultSpeed);
            } else {
                this.entity.setMoveForward(0.0F);
            }
        }

        private float computeTurnSpeed(float distance) {
            float lerpRange = FAR_TURN_DISTANCE - CLOSE_TURN_DISTANCE;
            float alpha = MathHelper.clamp((distance - CLOSE_TURN_DISTANCE) / lerpRange, 0.0F, 1.0F);
            float turnSpeed = CLOSE_TURN_SPEED + (FAR_TURN_SPEED - CLOSE_TURN_SPEED) * alpha;
            return turnSpeed * (float) this.speed;
        }
    }

    private static class LookHelper extends EntityLookHelper {
        private final EntityLiving parent;

        LookHelper(EntityLiving parent) {
            super(parent);
            this.parent = parent;
        }

        @Override
        public void onUpdateLook() {
            float deltaYaw = MathHelper.wrapDegrees(this.parent.rotationYawHead - this.parent.renderYawOffset);
            if (!this.parent.getNavigator().noPath()) {
                if (deltaYaw < -75.0F) {
                    this.parent.rotationYawHead = this.parent.renderYawOffset - 75.0F;
                }
                if (deltaYaw > 75.0F) {
                    this.parent.rotationYawHead = this.parent.renderYawOffset + 75.0F;
                }
            }
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return ModLootTables.LOOT_TABLE_HUNTER; }
}
