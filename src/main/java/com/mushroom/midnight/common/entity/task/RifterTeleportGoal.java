package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class RifterTeleportGoal extends Goal {
    private static final double MAX_DISTANCE_SQ = 24 * 24;

    private static final int MIN_IDLE_TIME = 40;

    private final RifterEntity owner;

    public RifterTeleportGoal(RifterEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean shouldExecute() {
        if (!MidnightConfig.general.allowRifterTeleport.get() || this.owner.getRNG().nextInt(10) != 0) {
            return false;
        }
        LivingEntity target = this.owner.getAttackTarget();
        if (target == null || this.owner.getTargetIdleTime() < MIN_IDLE_TIME) {
            return false;
        }
        double distanceSq = target.getDistanceSq(this.owner);
        return distanceSq < MAX_DISTANCE_SQ && !this.canBeSeen();
    }

    @Override
    public void startExecuting() {
        LivingEntity attackTarget = this.owner.getAttackTarget();
        if (attackTarget == null) {
            return;
        }

        BlockPos target = this.computeTeleportTarget(attackTarget);
        if (target != null) {
            this.owner.rotationYaw = attackTarget.rotationYaw;
            this.owner.setPositionAndUpdate(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
            this.owner.playAmbientSound();

            this.owner.getNavigator().clearPath();
        }
    }

    private BlockPos computeTeleportTarget(LivingEntity target) {
        BlockPos origin = target.getPosition();
        List<BlockPos> validPositions = new ArrayList<>();

        for (BlockPos pos : BlockPos.getAllInBoxMutable(origin.add(-2, -2, -2), origin.add(2, 2, 2))) {
            Vec3d vector = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            if (!this.canBeSeenBy(vector, target) && this.isTargetValid(pos)) {
                validPositions.add(pos.toImmutable());
            }
        }

        if (validPositions.isEmpty()) {
            return null;
        }

        return validPositions.get(this.owner.getRNG().nextInt(validPositions.size()));
    }

    private boolean isTargetValid(BlockPos target) {
        if (this.owner.world.checkBlockCollision(this.getEntityBoundAt(this.owner, target))) {
            return false;
        }
        return this.owner.getNavigator().getPathToPos(target, 0) != null;
    }

    private AxisAlignedBB getEntityBoundAt(Entity entity, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        float halfWidth = entity.getWidth() / 2.0F;
        return new AxisAlignedBB(
                x - halfWidth, y, z - halfWidth,
                x + halfWidth, y + entity.getHeight(), z + halfWidth
        );
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    private boolean canBeSeen() {
        List<PlayerEntity> players = this.owner.world.getEntitiesWithinAABB(PlayerEntity.class, this.owner.getBoundingBox().grow(24.0));
        for (PlayerEntity player : players) {
            if (this.canBeSeenBy(this.owner.getPositionVector(), player)) {
                return true;
            }
        }

        return false;
    }

    private boolean canBeSeenBy(Vec3d position, LivingEntity entity) {
        Vec3d playerLook = entity.getLook(1.0F);

        Vec3d deltaPos = entity.getPositionVector().subtract(position);
        deltaPos = deltaPos.normalize();

        return playerLook.dotProduct(deltaPos) < 0.0;
    }
}
