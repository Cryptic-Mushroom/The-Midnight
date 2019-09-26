package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.BulbAnglerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulbAnglerModel extends EntityModel<BulbAnglerEntity> {
    private final ModelPartAnimator animator = new ModelPartAnimator();
    public RendererModel body;
    public RendererModel mouseUp;
    public RendererModel mouseDown;
    public RendererModel body2;
    public RendererModel FinL;
    public RendererModel FinR;
    public RendererModel shape12;
    public RendererModel light;
    public RendererModel tail;

    public BulbAnglerModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.tail = new RendererModel(this, 38, 0);
        this.tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tail.addBox(-0.5F, -2.0F, 0.0F, 1, 4, 4, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.3F, 2.0F);
        this.body.addBox(-3.0F, -3.5F, -5.0F, 6, 7, 6, 0.0F);
        this.light = new RendererModel(this, 24, 13);
        this.light.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.light.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(light, 0.6373942428283291F, 0.0F, 0.0F);
        this.body2 = new RendererModel(this, 24, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.body2.addBox(-2.0F, -2.5F, -3.0F, 4, 5, 3, 0.0F);
        this.FinR = new RendererModel(this, 48, 6);
        this.FinR.setRotationPoint(-3.0F, 1.0F, -3.0F);
        this.FinR.addBox(-1.0F, -1.5F, 0.0F, 1, 3, 3, 0.0F);
        this.mouseDown = new RendererModel(this, 0, 23);
        this.mouseDown.setRotationPoint(0.0F, 5.0F, -5.0F);
        this.mouseDown.addBox(-3.0F, -3.0F, -5.0F, 6, 1, 5, 0.0F);
        this.shape12 = new RendererModel(this, 24, 8);
        this.shape12.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.shape12.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(shape12, -0.6373942428283291F, 0.0F, 0.0F);
        this.mouseUp = new RendererModel(this, 0, 13);
        this.mouseUp.setRotationPoint(0.0F, 2.0F, -5.0F);
        this.mouseUp.addBox(-3.0F, -5.0F, -5.0F, 6, 5, 5, 0.0F);
        this.FinL = new RendererModel(this, 48, 0);
        this.FinL.setRotationPoint(3.0F, 1.0F, -3.0F);
        this.FinL.addBox(0.0F, -1.5F, 0.0F, 1, 3, 3, 0.0F);
        this.body2.addChild(this.tail);
        this.shape12.addChild(this.light);
        this.body.addChild(this.body2);
        this.body.addChild(this.FinR);
        this.body.addChild(this.mouseDown);
        this.mouseUp.addChild(this.shape12);
        this.body.addChild(this.mouseUp);
        this.body.addChild(this.FinL);
    }

    @Override
    public void render(BulbAnglerEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);

        this.body.render(scale);
    }

    @Override
    public void setRotationAngles(BulbAnglerEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);

        this.animator.bob(this.body, 0.125F, 0.25F, false, 0.0F, 0.0F, age, 1.0F);
        this.animator.swing(this.FinR, 0.125F, 0.125F, true, 0.0F, 0.5F, age, 1.0F);
        this.animator.swing(this.FinL, 0.125F, 0.125F, false, 0.0F, 0.5F, age, 1.0F);

        this.animator.swing(this.body2, 0.125F, 0.125F, true, 0.0F, 0.0F, age, 1.0F);
        this.animator.swing(this.tail, 0.125F, 0.15F, true, 0.0F, 0.0F, age, 1.0F);

    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
