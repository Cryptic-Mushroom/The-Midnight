package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class RifterFollowGoal extends Goal {
    protected final RifterEntity owner;
    protected final double followSpeed;

    protected BlockPos lastSeenPos;
    protected Path path;

    protected int invalidCounter;

    protected boolean navigatingFromMemory;

    public RifterFollowGoal(RifterEntity owner, double followSpeed) {
        this.owner = owner;
        this.followSpeed = followSpeed;

        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    protected abstract void handleInteract(LivingEntity target);

    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.owner.getAttackTarget();
        if (this.shouldFollow(target)) {
            this.path = this.owner.getNavigator().getPathToEntityLiving(target, 0);
            return this.path != null;
        }

        return false;
    }

    @Override
    public void startExecuting() {
        this.owner.getNavigator().setPath(this.path, this.followSpeed);
    }

    @Override
    public void tick() {
        LivingEntity target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        if (!this.navigatingFromMemory) {
            this.owner.getLookController().setLookPositionWithEntity(target, 20.0F, 20.0F);
        }

        Path targetPath = this.computeFollowPath(target);
        if (targetPath == null) {
            this.owner.setAttackTarget(null);
            return;
        }

        Path currentPath = this.owner.getNavigator().getPath();
        if (currentPath != targetPath) {
            this.owner.getNavigator().setPath(targetPath, this.followSpeed);
        }

        if (this.canInteract(target)) {
            this.handleInteract(target);
        }
    }

    @Nullable
    protected Path computeFollowPath(LivingEntity target) {
        if (this.path.isFinished()) {
            this.path = null;
        }

        double distanceSq = this.owner.getDistanceSq(target);
        double followRange = this.owner.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getValue();
        if (distanceSq < followRange * followRange) {
            this.updateFollowVisible(target);
        } else if (!this.navigatingFromMemory && this.lastSeenPos != null) {
            this.owner.getNavigator().clearPath();
            this.path = this.owner.getNavigator().getPathToPos(this.lastSeenPos, 0);
            this.navigatingFromMemory = true;
        }

        return this.path;
    }

    protected void updateFollowVisible(LivingEntity target) {
        if (this.shouldInvalidatePath(target) || this.navigatingFromMemory) {
            this.invalidCounter = 0;

            this.owner.getNavigator().clearPath();
            this.path = this.owner.getNavigator().getPathToEntityLiving(target, 0);
            if (this.path != null) {
                this.lastSeenPos = target.getPosition();
                this.navigatingFromMemory = false;
            } else {
                this.navigatingFromMemory = true;
            }
        }
    }

    protected boolean shouldInvalidatePath(LivingEntity target) {
        int invalidateTime = this.getPathInvalidateTime(target);
        return ++this.invalidCounter > invalidateTime;
    }

    protected int getPathInvalidateTime(LivingEntity target) {
        double distanceSq = this.owner.getDistanceSq(target);
        if (distanceSq < 4.0 * 4.0) {
            return 5;
        } else if (distanceSq < 8.0 * 8.0) {
            return 10;
        } else {
            return 20;
        }
    }

    protected boolean canInteract(LivingEntity target) {
        double distanceSq = this.owner.getDistanceSq(target);
        double interactReach = this.getInteractReach(target);
        return distanceSq < interactReach * interactReach;
    }

    protected double getInteractReach(LivingEntity target) {
        double averageWidth = (this.owner.getWidth() + target.getWidth()) / 2.0;
        return averageWidth + 0.5;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.path != null && this.shouldFollow(this.owner.getAttackTarget());
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();
        this.path = null;
        this.lastSeenPos = null;
        this.navigatingFromMemory = false;

        if (!this.shouldFollow(this.owner.getAttackTarget())) {
            this.owner.setAttackTarget(null);
        }
    }

    protected boolean shouldFollow(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;
            if (player.isSpectator() || player.isCreative()) {
                return false;
            }
        }
        return target.isAlive();
    }
}
