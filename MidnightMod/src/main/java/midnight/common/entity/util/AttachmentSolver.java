package midnight.common.entity.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.vector.Vector3d;

public class AttachmentSolver {
    private final AttachmentPoint attachmentPoint = new AttachmentPoint();
    private final DragVector3d snappedAttachmentPoint = new DragVector3d(0, 0, 0);

    private final LivingEntity origin;

    public AttachmentSolver(LivingEntity origin) {
        this.origin = origin;
    }

    public Result solveAttachment(LivingEntity entity) {
        double globalX = this.attachmentPoint.getX() + this.origin.getPosX();
        double globalY = this.attachmentPoint.getY() + this.origin.getPosY();
        double globalZ = this.attachmentPoint.getZ() + this.origin.getPosZ();

        double deltaX = globalX - entity.getPosX();
        double deltaY = globalY - entity.getPosY();
        double deltaZ = globalZ - entity.getPosZ();
        entity.move(MoverType.SELF, new Vector3d(deltaX, deltaY, deltaZ));

        entity.setOnGround(true);
        entity.fallDistance = 0.0F;

        this.snappedAttachmentPoint.x = entity.getPosX();
        this.snappedAttachmentPoint.y = entity.getPosY();
        this.snappedAttachmentPoint.z = entity.getPosZ();

        return new Result(this.snappedAttachmentPoint);
    }

    public AttachmentPoint getAttachmentPoint() {
        return this.attachmentPoint;
    }

    public static class Result {
        private final DragVector3d snappedPoint;

        public Result(DragVector3d snappedPoint) {
            this.snappedPoint = snappedPoint;
        }

        public DragVector3d getSnappedPoint() {
            return this.snappedPoint;
        }
    }
}
