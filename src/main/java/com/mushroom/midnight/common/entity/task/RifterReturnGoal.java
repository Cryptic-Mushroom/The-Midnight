package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

public class RifterReturnGoal extends Goal {
    protected static final int RETURN_EARLY_TIME = 80;
    protected static final int INVALIDATE_TIME = 20;

    protected final RifterEntity owner;
    protected final double returnSpeed;

    protected int invalidCount = 0;
    protected Path path;

    public RifterReturnGoal(RifterEntity owner, double returnSpeed) {
        this.owner = owner;
        this.returnSpeed = returnSpeed;

        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        return this.shouldReturn();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldReturn() && invalidCount <= INVALIDATE_TIME;
    }

    public boolean shouldReturn() {
        if (MidnightUtil.isMidnightDimension(this.owner.world)) {
            return false;
        }
        Optional<RiftEntity> homeRift = this.owner.getHomeRift().deref(false);
        return homeRift.isPresent() && homeRift.get().getBridge().getTimeUntilClose() < RETURN_EARLY_TIME;
    }

    @Override
    public void startExecuting() {
        this.path = this.computeFollowPath();
        this.owner.getNavigator().setPath(this.path, this.returnSpeed);
    }

    @Override
    public void tick() {
        Path targetPath = this.computeFollowPath();
        if (targetPath == null) {
            this.path = null;
            return;
        }

        Path currentPath = this.owner.getNavigator().getPath();
        if (currentPath != targetPath) {
            this.owner.getNavigator().setPath(targetPath, this.returnSpeed);
        }
        invalidCount = this.path == null ? invalidCount++ : 0;
    }

    @Nullable
    private Path computeFollowPath() {
        if (this.isPathComplete(this.path)) {
            this.path = null;
            this.invalidCount = 0;
        }

        if (this.path == null) {
            this.owner.getNavigator().clearPath();

            Optional<RiftEntity> derefRift = this.owner.getHomeRift().deref(true);
            if (!derefRift.isPresent()) {
                return null;
            }

            RiftEntity homeRift = derefRift.get();
            BlockPos surface = WorldUtil.findSurfaceOrInput(homeRift.world, homeRift.getPosition(), 16);

            this.path = this.owner.getNavigator().getPathToPos(surface, 0);

            if (this.path == null) {
                this.path = this.computePathTowards(surface);
            }
        }
        return this.path;
    }

    @Nullable
    private Path computePathTowards(BlockPos surface) {
        Vec3d target = new Vec3d(surface);
        for (int i = 0; i < 16; i++) {
            Vec3d pathPos = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, 24, 4, target);
            if (pathPos == null) {
                continue;
            }
            Path path = this.owner.getNavigator().func_225466_a(pathPos.x, pathPos.y, pathPos.z, 0);
            if (path != null) {
                return path;
            }
        }
        return null;
    }

    private boolean isPathComplete(Path path) {
        if (path == null || path.isFinished()) {
            return true;
        }
        PathPoint finalPoint = path.getFinalPathPoint();
        return finalPoint == null || this.owner.getDistanceSq(finalPoint.x, finalPoint.y, finalPoint.z) < 1.5 * 1.5;
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();

        this.path = null;
        this.invalidCount = 0;
    }
}
