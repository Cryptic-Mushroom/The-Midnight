package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.util.FlyingTargetGenerator;
import com.mushroom.midnight.common.entity.util.IRandomTargetGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class HunterSwoopGoal extends Goal {
    private static final int SWOOP_COOLDOWN = 160;

    private static final int MAX_SWOOP_TICKS = 120;

    private static final int ATTACK_OFFSET_Y = 1;

    private final HunterEntity owner;
    private final double speed;

    private final IRandomTargetGenerator riseTargetGenerator;

    private int ticks;
    private boolean rising;

    public HunterSwoopGoal(HunterEntity owner, double speed) {
        this.owner = owner;
        this.speed = speed;

        this.riseTargetGenerator = new FlyingTargetGenerator(owner, 6, 10, 12, 14);
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.owner.getAttackTarget();
        return target != null && target.isAlive() && this.owner.swoopCooldown <= 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (!this.shouldExecute()) {
            return false;
        }
        Path path = this.owner.getNavigator().getPath();
        return path != null && (!this.rising || !path.isFinished()) && this.ticks < MAX_SWOOP_TICKS;
    }

    @Override
    public void startExecuting() {
        LivingEntity target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        this.recalculatePath(target);
    }

    @Override
    public void tick() {
        LivingEntity target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        if (this.ticks++ % 10 == 0) {
            this.recalculatePath(target);
        }

        if (this.canAttack(target)) {
            this.owner.attackEntityAsMob(target);

            this.rising = true;
            this.recalculatePath(target);
        }
    }

    private void recalculatePath(LivingEntity target) {
        this.owner.getNavigator().clearPath();

        Path path = this.produceTargetPath(target);
        if (path == null) {
            this.owner.setAttackTarget(null);
        }

        this.owner.getNavigator().setPath(path, this.speed);
    }

    @Nullable
    private Path produceTargetPath(LivingEntity target) {
        if (this.rising) {
            for (int i = 0; i < 16; i++) {
                Path path = this.produceRisePath(target);
                if (path != null) {
                    return path;
                }
            }
            return null;
        } else {
            return this.owner.getNavigator().getPathToPos(target.getPosition().up(ATTACK_OFFSET_Y), 0);
        }
    }

    @Nullable
    private Path produceRisePath(LivingEntity target) {
        BlockPos flightPos = this.riseTargetGenerator.generate(target.getPosition());
        if (flightPos == null) {
            return null;
        }
        return this.owner.getNavigator().getPathToPos(flightPos, 0);
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();

        this.owner.swoopCooldown = SWOOP_COOLDOWN;

        this.rising = false;
        this.ticks = 0;
    }

    private boolean canAttack(LivingEntity target) {
        BlockPos pos = this.owner.getPosition().down(ATTACK_OFFSET_Y);
        double distanceSq = target.getDistanceSq(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        double attackReach = this.getAttackReach(target);
        return distanceSq < attackReach * attackReach;
    }

    private double getAttackReach(LivingEntity target) {
        double meanWidth = (2.0 + target.getWidth()) / 2.0;
        return meanWidth + 0.5;
    }
}
 
