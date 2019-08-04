package com.mushroom.midnight.common.entity.task;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;

public class HunterTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public HunterTargetGoal(CreatureEntity creature, Class<T> classTarget) {
        super(creature, classTarget, false);
    }

    @Override
    protected boolean isSuitableTarget(@Nullable LivingEntity target, EntityPredicate predicate) {
        return super.isSuitableTarget(target, predicate) && target != null && this.canSee(target);
    }

    private boolean canSee(LivingEntity target) {
        BlockPos position = target.getPosition().up();
        return target.world.canBlockSeeSky(position);
    }

    @Override
    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        BlockPos surface = this.goalOwner.world.getHeight(Heightmap.Type.MOTION_BLOCKING, this.goalOwner.getPosition());
        return new AxisAlignedBB(surface).grow(targetDistance, 6.0, targetDistance);
    }
}
