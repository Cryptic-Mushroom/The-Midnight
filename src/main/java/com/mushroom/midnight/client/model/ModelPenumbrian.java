package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPenumbrian extends ModelBase {
    public ModelRenderer Chest;
    public ModelRenderer Abdomen;
    public ModelRenderer Head;
    public ModelRenderer Arm11;
    public ModelRenderer Arm21;
    public ModelRenderer Bottom;
    public ModelRenderer Arm12;
    public ModelRenderer Elbow1;
    public ModelRenderer Arm22;
    public ModelRenderer Elbow1_1;

    public ModelPenumbrian() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Head = new ModelRenderer(this, 50, 0);
        this.Head.setRotationPoint(2.1F, -6.0F, 2.0F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 7, 7, 7, 0.0F);
        this.Elbow1_1 = new ModelRenderer(this, 102, 46);
        this.Elbow1_1.setRotationPoint(0.1F, 0.8F, 4.1F);
        this.Elbow1_1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Elbow1_1, 0.31869712141416456F, -0.045553093477052F, 0.0F);
        this.Arm12 = new ModelRenderer(this, 77, 30);
        this.Arm12.setRotationPoint(4.0F, 0.2F, 6.2F);
        this.Arm12.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Arm12, -3.141592653589793F, -2.5953045977155678F, -1.5025539530419183F);
        this.Bottom = new ModelRenderer(this, 0, 45);
        this.Bottom.setRotationPoint(0.6F, 9.9F, 0.0F);
        this.Bottom.addBox(0.0F, 0.0F, 0.0F, 12, 4, 12, 0.0F);
        this.Arm21 = new ModelRenderer(this, 56, 44);
        this.Arm21.setRotationPoint(3.2F, 5.4F, 9.1F);
        this.Arm21.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Arm21, 0.9560913642424937F, 0.091106186954104F, 1.5025539530419183F);
        this.Arm22 = new ModelRenderer(this, 80, 43);
        this.Arm22.setRotationPoint(4.3F, 0.2F, 6.2F);
        this.Arm22.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Arm22, -3.141592653589793F, -2.5953045977155678F, -1.5025539530419183F);
        this.Elbow1 = new ModelRenderer(this, 102, 32);
        this.Elbow1.setRotationPoint(0.0F, 0.6F, 4.5F);
        this.Elbow1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Elbow1, 0.31869712141416456F, -0.045553093477052F, 0.0F);
        this.Arm11 = new ModelRenderer(this, 55, 30);
        this.Arm11.setRotationPoint(2.8F, 9.3F, 1.9F);
        this.Arm11.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Arm11, -2.276432943376204F, 0.045553093477052F, -1.5025539530419183F);
        this.Chest = new ModelRenderer(this, 0, 0);
        this.Chest.setRotationPoint(-5.8F, 3.1F, -5.8F);
        this.Chest.addBox(0.0F, 0.0F, 0.0F, 11, 4, 11, 0.0F);
        this.Abdomen = new ModelRenderer(this, 1, 18);
        this.Abdomen.setRotationPoint(-0.6F, 3.9F, -0.6F);
        this.Abdomen.addBox(0.0F, 0.0F, 0.0F, 14, 10, 12, 0.0F);
        this.Chest.addChild(this.Head);
        this.Arm21.addChild(this.Elbow1_1);
        this.Arm11.addChild(this.Arm12);
        this.Abdomen.addChild(this.Bottom);
        this.Chest.addChild(this.Arm21);
        this.Arm21.addChild(this.Arm22);
        this.Arm11.addChild(this.Elbow1);
        this.Chest.addChild(this.Arm11);
        this.Chest.addChild(this.Abdomen);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Chest.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
