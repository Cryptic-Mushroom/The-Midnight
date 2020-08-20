package midnight.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import midnight.common.entity.SkulkEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkulkModel extends QuadrupedModel<SkulkEntity> {
    private ModelRenderer Tail;
    private ModelRenderer RightEar;
    private ModelRenderer LeftEar;
    private ModelRenderer Snout;

    public SkulkModel() {
        super(5, 0f, false, 4.0F, 4.0F, 2.0F, 2.0F, 0);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.headModel = new ModelRenderer(this, 0, 12);
        this.headModel.setRotationPoint(0.0F, 20.0F, -4.0F);
        this.headModel.addBox(-3.5F, -2.0F, -3.0F, 7, 4, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 2); // body
        this.body.setRotationPoint(0.0F, 20.0F, 4.0F);
        this.body.addBox(-2.5F, -1.5F, -7.0F, 5, 3, 7, 0.0F);
        this.legBackRight = new ModelRenderer(this, 0, 26); // leg1
        this.legBackRight.setRotationPoint(-1.4F, 22.0F, -2.1F);
        this.legBackRight.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.legBackLeft = new ModelRenderer(this, 0, 26); // leg2
        this.legBackLeft.setRotationPoint(-1.6F, 22.0F, 2.5F);
        this.legBackLeft.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.legFrontRight = new ModelRenderer(this, 0, 26); // leg3
        this.legFrontRight.setRotationPoint(1.4F, 22.0F, -2.1F);
        this.legFrontRight.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.legFrontLeft = new ModelRenderer(this, 0, 26); // leg4
        this.legFrontLeft.setRotationPoint(1.6F, 22.0F, 2.5F);
        this.legFrontLeft.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.LeftEar = new ModelRenderer(this, 1, 0);
        this.LeftEar.setRotationPoint(2.5F, -1.0F, -0.5F);
        this.LeftEar.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
        this.setRotateAngle(LeftEar, 0.17453292519943295F, -0.5235987755982988F, 0.5235987755982988F);
        this.RightEar = new ModelRenderer(this, 1, 0);
        this.RightEar.setRotationPoint(-2.5F, -1.0F, -0.5F);
        this.RightEar.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
        this.setRotateAngle(RightEar, 0.17453292519943295F, 0.5235987755982988F, -0.5235987755982988F);
        this.Snout = new ModelRenderer(this, 0, 22);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-1.5F, 0.0F, -4.0F, 3, 2, 1, 0.0F);
        this.Tail = new ModelRenderer(this, 8, 22);
        this.Tail.setRotationPoint(0.0F, 19.5F, 3.5F);
        this.Tail.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail, -0.4363323129985824F, 0.0F, 0.0F);
        this.headModel.addChild(this.LeftEar);
        this.headModel.addChild(this.RightEar);
        this.headModel.addChild(this.Snout);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.Tail));
    }

    @Override
    public void setRotationAngles(SkulkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.body.rotateAngleX = 0f;
        this.Tail.rotateAngleY = MathHelper.sin((float) (ageInTicks * Math.PI * 0.25f)) * 0.1f;
        this.LeftEar.rotateAngleX = this.RightEar.rotateAngleX = MathHelper.sin((float) (ageInTicks * Math.PI * 0.2f)) * 0.1f;

        if(entity.isSitting()) {
            this.body.setRotationPoint(0.0F, 23.0F, 4.0F);
            this.body.rotateAngleX = -((float) Math.PI / 4F);
            this.headModel.setRotationPoint(0.0F, 18.0F, 0.0F);
            this.Tail.setRotationPoint(0.0F, 21.0F, 3.5F);
            this.legBackRight.setRotationPoint(-1.4F, 23.0F, 0.1F);
            this.legBackRight.rotateAngleX = 5.811947F;
            this.legBackLeft.setRotationPoint(1.4F, 23.0F, 0.1F);
            this.legBackLeft.rotateAngleX = 5.811947F;
            this.legFrontRight.rotateAngleX = ((float) Math.PI * 1.5F);
            this.legFrontRight.setRotationPoint(-1.6F, 24.0F, 2.5F);
            this.legFrontLeft.rotateAngleX = ((float) Math.PI * 1.5F);
            this.legFrontLeft.setRotationPoint(1.6F, 24.0F, 2.5F);
        } else {
            this.body.setRotationPoint(0.0F, 20.0F, 4.0F);
            this.body.rotateAngleX = 0.0F;
            this.headModel.setRotationPoint(0.0F, 20.0F, -4.0F);
            this.Tail.setRotationPoint(0.0F, 19.5F, 3.5F);
            this.legBackRight.setRotationPoint(-1.4F, 22.0F, -2.1F);
            this.legBackLeft.setRotationPoint(1.4F, 22.0F, -2.1F);
            this.legFrontRight.setRotationPoint(-1.6F, 22.0F, 2.5F);
            this.legFrontLeft.setRotationPoint(1.6F, 22.0F, 2.5F);
        }
    }

    private void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
        ModelRenderer.rotateAngleX = x;
        ModelRenderer.rotateAngleY = y;
        ModelRenderer.rotateAngleZ = z;
    }
}
