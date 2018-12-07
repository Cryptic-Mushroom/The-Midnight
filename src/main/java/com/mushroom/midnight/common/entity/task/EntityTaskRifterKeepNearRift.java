package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class EntityTaskRifterKeepNearRift extends EntityAIBase {
    protected static final double MAX_DISTANCE = 24.0;

    protected final EntityRifter owner;
    protected final double speed;

    public EntityTaskRifterKeepNearRift(EntityRifter owner, double speed) {
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
        if (Helper.isMidnightDimension(this.owner.world) || this.owner.getAttackTarget() instanceof EntityPlayer) {
            return false;
        }
        Optional<EntityRift> homeRift = this.owner.getHomeRift().deref(false);
        return homeRift.isPresent() && homeRift.get().getDistanceSq(this.owner) > MAX_DISTANCE * MAX_DISTANCE;
    }

    @Override
    public void startExecuting() {
        this.owner.setAttackTarget(null);
        this.owner.getNavigator().clearPath();

        Optional<EntityRift> derefRift = this.owner.getHomeRift().deref(true);
        if (!derefRift.isPresent()) {
            return;
        }

        EntityRift homeRift = derefRift.get();
        Vec3d target = new Vec3d(homeRift.getPosition());
        for (int i = 0; i < 16; i++) {
            Vec3d pathPos = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, 24, 4, target);
            if (pathPos != null && this.owner.getNavigator().tryMoveToXYZ(pathPos.x, pathPos.y, pathPos.z, this.speed)) {
                return;
            }
        }
    }

    @Override
    public void updateTask() {
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();
    }
}
