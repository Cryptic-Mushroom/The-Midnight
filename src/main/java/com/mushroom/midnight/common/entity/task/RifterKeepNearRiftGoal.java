package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class RifterKeepNearRiftGoal extends Goal {
    protected static final double MAX_DISTANCE = 24.0;

    protected final RifterEntity owner;
    protected final double speed;

    public RifterKeepNearRiftGoal(RifterEntity owner, double speed) {
        this.owner = owner;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return this.shouldReturn();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldReturn() && !this.owner.getNavigator().noPath();
    }

    private boolean shouldReturn() {
        if (MidnightUtil.isMidnightDimension(this.owner.world) || this.owner.getAttackTarget() instanceof PlayerEntity) {
            return false;
        }
        Optional<RiftEntity> homeRift = this.owner.getHomeRift().deref(false);
        return homeRift.isPresent() && homeRift.get().getDistanceSq(this.owner) > MAX_DISTANCE * MAX_DISTANCE;
    }

    @Override
    public void startExecuting() {
        this.owner.setAttackTarget(null);
        this.owner.getNavigator().clearPath();

        Optional<RiftEntity> derefRift = this.owner.getHomeRift().deref(true);
        if (!derefRift.isPresent()) {
            return;
        }

        RiftEntity homeRift = derefRift.get();
        Vec3d target = new Vec3d(homeRift.getPosition());
        for (int i = 0; i < 16; i++) {
            Vec3d pathPos = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, 24, 4, target);
            if (pathPos != null && this.owner.getNavigator().tryMoveToXYZ(pathPos.x, pathPos.y, pathPos.z, this.speed)) {
                return;
            }
        }
    }

    @Override
    public void tick() {
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();
    }
}
