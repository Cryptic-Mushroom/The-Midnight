package midnight.client.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ModelPartAnimator {
    public void bob(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotationPointY = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void walk(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleX = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void swing(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleY = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void flap(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleZ = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void chainSwing(ModelRenderer[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for(int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleY = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public void chainWave(ModelRenderer[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for(int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleX = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public void chainFlap(ModelRenderer[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for(int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleZ = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public float computeChainRotation(float speed, float degree, boolean invert, float offset, int boxIndex, float swing, float swingAmount) {
        float theta = swing * speed + offset * boxIndex;
        float rotation = MathHelper.cos(theta) * swingAmount * degree;
        return invert ? -rotation : rotation;
    }

    public float computeChainOffset(ModelRenderer[] boxes, double rootOffset) {
        return (float) (rootOffset * Math.PI / (2 * boxes.length));
    }

    public float computeAnimation(float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        float theta = limbSwing * speed + offset;
        float scaledWeight = weight * limbSwingAmount;
        float rotation = (MathHelper.cos(theta) * degree * limbSwingAmount) + scaledWeight;
        return invert ? -rotation : rotation;
    }
}