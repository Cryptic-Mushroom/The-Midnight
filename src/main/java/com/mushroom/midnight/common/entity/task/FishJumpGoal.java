package com.mushroom.midnight.common.entity.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FishJumpGoal extends JumpGoal {
    private static final int[] field_220710_a = new int[]{0, 1, 4, 5, 6, 7};
    private final AbstractFishEntity fishEntity;
    private final int chance;
    private boolean field_220713_d;

    public FishJumpGoal(AbstractFishEntity fish, int chance) {
        this.fishEntity = fish;
        this.chance = chance;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.fishEntity.getRNG().nextInt(this.chance) != 0) {
            return false;
        } else {
            Direction direction = this.fishEntity.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = new BlockPos(this.fishEntity);

            for(int k : field_220710_a) {
                if (!this.func_220709_a(blockpos, i, j, k) || !this.func_220708_b(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean func_220709_a(BlockPos p_220709_1_, int p_220709_2_, int p_220709_3_, int p_220709_4_) {
        BlockPos blockpos = p_220709_1_.add(p_220709_2_ * p_220709_4_, 0, p_220709_3_ * p_220709_4_);
        return this.fishEntity.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.fishEntity.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean func_220708_b(BlockPos p_220708_1_, int p_220708_2_, int p_220708_3_, int p_220708_4_) {
        return this.fishEntity.world.getBlockState(p_220708_1_.add(p_220708_2_ * p_220708_4_, 1, p_220708_3_ * p_220708_4_)).isAir() && this.fishEntity.world.getBlockState(p_220708_1_.add(p_220708_2_ * p_220708_4_, 2, p_220708_3_ * p_220708_4_)).isAir();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        double d0 = this.fishEntity.getMotion().y;
        return (!(d0 * d0 < (double)0.03F) || this.fishEntity.rotationPitch == 0.0F || !(Math.abs(this.fishEntity.rotationPitch) < 10.0F) || !this.fishEntity.isInWater()) && !this.fishEntity.onGround;
    }

    public boolean isPreemptible() {
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        LivingEntity entity = this.fishEntity.getAttackTarget();
        Direction direction = this.fishEntity.getAdjustedHorizontalFacing();

        if(entity!= null && this.fishEntity.getDistanceSq(entity) < 12D){
            this.fishEntity.getLookController().setLookPositionWithEntity(entity, 60.0F, 30.0F);
            Vec3d vec3d = (new Vec3d(entity.posX - fishEntity.posX, entity.posY - fishEntity.posY, entity.posZ - fishEntity.posZ)).normalize();
            this.fishEntity.setMotion(this.fishEntity.getMotion().add(vec3d.x * 0.45D, 0.56D, vec3d.z * 0.45D));
        }else {
            this.fishEntity.setMotion(this.fishEntity.getMotion().add((double) direction.getXOffset() * 0.6D, 0.7D, (double) direction.getZOffset() * 0.6D));
        }
        this.fishEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.fishEntity.rotationPitch = 0.0F;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        boolean flag = this.field_220713_d;
        if (!flag) {
            IFluidState ifluidstate = this.fishEntity.world.getFluidState(new BlockPos(this.fishEntity));
            this.field_220713_d = ifluidstate.isTagged(FluidTags.WATER);
        }

        if (this.field_220713_d && !flag) {
            this.fishEntity.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
        }

        Vec3d vec3d = this.fishEntity.getMotion();
        if (vec3d.y * vec3d.y < (double)0.03F && this.fishEntity.rotationPitch != 0.0F) {
            this.fishEntity.rotationPitch = this.updateRotation(this.fishEntity.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.func_213296_b(vec3d));
            double d1 = Math.signum(-vec3d.y) * Math.acos(d0 / vec3d.length()) * (double)(180F / (float)Math.PI);
            this.fishEntity.rotationPitch = (float)d1;
        }
        LivingEntity entity = this.fishEntity.getAttackTarget();

        //when
        if(entity != null &&this.fishEntity.getDistanceSq(entity) <=  getAttackReachSqr(entity)){
            this.fishEntity.attackEntityAsMob(entity);
        }

    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double)(this.fishEntity.getWidth() * 1.5F * this.fishEntity.getWidth() * 1.5F + attackTarget.getWidth());
    }
}