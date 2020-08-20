package midnight.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import midnight.common.entity.DeceitfulSnapperEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeceitfulSnapperModel extends SegmentedModel<DeceitfulSnapperEntity> {
    public final ModelRenderer Body;
    public final ModelRenderer opened_mouth;
    public final ModelRenderer Mouth;
    public final ModelRenderer Tail1;
    public final ModelRenderer RightFin;
    public final ModelRenderer LeftFin;
    public final ModelRenderer Tail2;

    private final ModelRenderer[] tail;

    private final ModelPartAnimator animator = new ModelPartAnimator();

    public DeceitfulSnapperModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFin = new ModelRenderer(this, 0, 26);
        this.LeftFin.mirror = true;
        this.LeftFin.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(LeftFin, 0.0F, 0.0F, -0.7853981633974483F);
        this.RightFin = new ModelRenderer(this, 0, 26);
        this.RightFin.setRotationPoint(-2.0F, 1.0F, -1.0F);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(RightFin, 0.0F, 0.0F, 0.7853981633974483F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.opened_mouth = new ModelRenderer(this, 46, 28);
        this.opened_mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.opened_mouth.addBox(-2.0F, -2.0F, -3F, 4, 4, 0, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 10);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail1.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 4, 0.0F);
        this.Mouth = new ModelRenderer(this, 14, 0);
        this.Mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mouth.addBox(-2.0F, 2.0F, -3.0F, 4, 2, 3, 0.0F);
        this.Tail2 = new ModelRenderer(this, 0, 17);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tail2.addBox(0.0F, -3.0F, 0.0F, 0, 6, 6, 0.0F);
        this.Body.addChild(this.opened_mouth);
        this.Body.addChild(this.LeftFin);
        this.Body.addChild(this.RightFin);
        this.Body.addChild(this.Tail1);
        this.Tail1.addChild(this.Tail2);

        this.tail = new ModelRenderer[] {this.Body, this.Tail1, this.Tail2};
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.Body, this.Mouth);
    }

    @Override
    public void setRotationAngles(DeceitfulSnapperEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {
        this.animator.bob(this.Body, 0.125F, 0.25F, false, 0.0F, 0.0F, age, 1.0F);
        this.animator.flap(this.RightFin, 0.125F, 0.125F, false, 0.0F, 0.5F, age, 1.0F);
        this.animator.flap(this.LeftFin, 0.125F, 0.125F, true, 0.0F, 0.5F, age, 1.0F);

        this.animator.chainSwing(this.tail, 1.0F, 1.0F, false, 1.0, limbSwing, limbSwingAmount);

        this.opened_mouth.showModel = entity.ticksExisted % 200 < 150;
    }

    public void setRotateAngle(ModelRenderer part, float x, float y, float z) {
        part.rotateAngleX = x;
        part.rotateAngleY = y;
        part.rotateAngleZ = z;
    }
}