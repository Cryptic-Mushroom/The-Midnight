package midnight.common.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.vector.Vector3d;

public class TargetIdleTracker {
    private final MobEntity owner;
    private final double idleBreakDistanceSq;

    private LivingEntity lastTarget;

    private int idleTime;
    private Vector3d lastTargetPos;

    public TargetIdleTracker(MobEntity owner, double idleBreakDistance) {
        this.owner = owner;
        this.idleBreakDistanceSq = idleBreakDistance * idleBreakDistance;
    }

    public void update() {
        LivingEntity target = this.owner.getAttackTarget();
        if (target != this.lastTarget) {
            this.reset();
        }

        if (target != null) {
            Vector3d position = target.getPositionVec();
            if (this.lastTargetPos == null || this.lastTargetPos.squareDistanceTo(position) > this.idleBreakDistanceSq) {
                this.idleTime = 0;
                this.lastTargetPos = position;
            } else {
                this.idleTime++;
            }
        }

        this.lastTarget = target;
    }

    public int getIdleTime() {
        return this.idleTime;
    }

    private void reset() {
        this.idleTime = 0;
        this.lastTargetPos = null;
    }
}
