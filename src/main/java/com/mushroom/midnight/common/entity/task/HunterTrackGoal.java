package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.util.FlyingTargetGenerator;
import com.mushroom.midnight.common.entity.util.IRandomTargetGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class HunterTrackGoal extends Goal {
    private final HunterEntity owner;
    private final double speed;

    private final IRandomTargetGenerator trackTargetGenerator;

    public HunterTrackGoal(HunterEntity owner, double speed) {
        this.owner = owner;
        this.speed = speed;
        this.trackTargetGenerator = new FlyingTargetGenerator(owner, 6, 10, 14, 16);
    }

    @Override
    public boolean shouldExecute() {
        return this.owner.getNavigator().noPath() && this.owner.getAttackTarget() != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void startExecuting() {
        LivingEntity target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        Path path = null;

        int retries = 0;
        while (retries++ < 16 && path == null) {
            path = this.produceTrackPath(target);
        }

        if (path != null) {
            this.owner.getNavigator().setPath(path, this.speed);
        }
    }

    @Nullable
    private Path produceTrackPath(LivingEntity target) {
        BlockPos flightPos = this.trackTargetGenerator.generate(target.getPosition());
        if (flightPos == null) {
            return null;
        }

        return this.owner.getNavigator().getPathToPos(flightPos, 0);
    }
}
 
