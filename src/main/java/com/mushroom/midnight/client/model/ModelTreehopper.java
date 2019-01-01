package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelTreehopper - xenoform
 * Created using Tabula 7.0.0
 */
public class ModelTreehopper extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Seg1;
    public ModelRenderer LbackHip;
    public ModelRenderer Neck;
    public ModelRenderer RbackHip;
    public ModelRenderer LfrontLeg;
    public ModelRenderer RfrontLeg;
    public ModelRenderer Seg2;
    public ModelRenderer Tail;
    public ModelRenderer LbackLeg;
    public ModelRenderer LbackForeleg;
    public ModelRenderer LbackEndLeg;
    public ModelRenderer Head;
    public ModelRenderer LeftWing;
    public ModelRenderer RightWing;
    public ModelRenderer Snout;
    public ModelRenderer Jaw;
    public ModelRenderer RbackLeg;
    public ModelRenderer RbackForeleg;
    public ModelRenderer RbackEndLeg;
    public ModelRenderer LfrontForeleg;
    public ModelRenderer LfrontEndleg;
    public ModelRenderer RfrontForeleg;
    public ModelRenderer RfrontEndleg;

    public ModelTreehopper() {
        this.textureWidth = 84;
        this.textureHeight = 67;
        this.Seg1 = new ModelRenderer(this, 0, 25);
        this.Seg1.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.Seg1.addBox(-1.5F, -2.5F, -4.0F, 3, 4, 6, 0.0F);
        this.LbackForeleg = new ModelRenderer(this, 48, 18);
        this.LbackForeleg.setRotationPoint(0.0F, 12.5F, -0.3F);
        this.LbackForeleg.addBox(-1.0F, -1.0F, -1.0F, 2, 17, 2, 0.0F);
        this.setRotateAngle(LbackForeleg, -2.844886680750757F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -0.5F, -4.0F);
        this.Head.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 6, 0.0F);
        this.Tail = new ModelRenderer(this, 8, 37);
        this.Tail.setRotationPoint(0.0F, -0.5F, 5.5F);
        this.Tail.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 11, 0.0F);
        this.setRotateAngle(Tail, 0.18203784098300857F, 0.0F, 0.0F);
        this.RightWing = new ModelRenderer(this, 50, 0);
        this.RightWing.setRotationPoint(-1.0F, -3.0F, 0.0F);
        this.RightWing.addBox(-4.0F, 0.0F, 0.0F, 4, 0, 22, 0.0F);
        this.setRotateAngle(RightWing, -0.04363323129985824F, 0.014835298641951801F, -0.4886921905584123F);
        this.Neck = new ModelRenderer(this, 48, 0);
        this.Neck.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.Neck.addBox(-3.5F, -3.0F, -4.0F, 7, 6, 4, 0.0F);
        this.LeftWing = new ModelRenderer(this, 50, 0);
        this.LeftWing.mirror = true;
        this.LeftWing.setRotationPoint(1.0F, -3.0F, 0.0F);
        this.LeftWing.addBox(0.0F, 0.0F, 0.0F, 4, 0, 22, 0.0F);
        this.setRotateAngle(LeftWing, -0.04363323129985824F, -0.014835298641951801F, 0.4886921905584123F);
        this.RfrontEndleg = new ModelRenderer(this, 64, 40);
        this.RfrontEndleg.setRotationPoint(0.0F, 0.0F, -9.5F);
        this.RfrontEndleg.addBox(-1.5F, -1.0F, -5.0F, 3, 2, 5, 0.0F);
        this.setRotateAngle(RfrontEndleg, -0.4553564018453205F, 0.0F, -0.045553093477052F);
        this.RbackHip = new ModelRenderer(this, 21, 23);
        this.RbackHip.setRotationPoint(-2.0F, 0.5F, -4.0F);
        this.RbackHip.addBox(-4.0F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
        this.setRotateAngle(RbackHip, 1.8212510744560826F, 0.0F, 0.0F);
        this.LbackLeg = new ModelRenderer(this, 34, 18);
        this.LbackLeg.mirror = true;
        this.LbackLeg.setRotationPoint(2.5F, 1.0F, 0.0F);
        this.LbackLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
        this.setRotateAngle(LbackLeg, 0.0F, 0.0F, -0.091106186954104F);
        this.LbackEndLeg = new ModelRenderer(this, 73, 23);
        this.LbackEndLeg.mirror = true;
        this.LbackEndLeg.setRotationPoint(0.0F, 15.6F, 0.3F);
        this.LbackEndLeg.addBox(-0.5F, -1.0F, -1.0F, 1, 7, 2, 0.0F);
        this.setRotateAngle(LbackEndLeg, 2.600540585471551F, 0.0F, 0.0F);
        this.RbackLeg = new ModelRenderer(this, 34, 35);
        this.RbackLeg.setRotationPoint(-2.5F, 1.0F, 0.0F);
        this.RbackLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
        this.setRotateAngle(RbackLeg, 0.0F, 0.0F, 0.091106186954104F);
        this.RbackForeleg = new ModelRenderer(this, 57, 18);
        this.RbackForeleg.mirror = true;
        this.RbackForeleg.setRotationPoint(0.0F, 12.5F, -0.5F);
        this.RbackForeleg.addBox(-1.0F, -1.0F, -1.0F, 2, 17, 2, 0.0F);
        this.setRotateAngle(RbackForeleg, -2.844886680750757F, 0.0F, 0.0F);
        this.LfrontEndleg = new ModelRenderer(this, 47, 40);
        this.LfrontEndleg.mirror = true;
        this.LfrontEndleg.setRotationPoint(0.0F, 0.0F, -9.5F);
        this.LfrontEndleg.addBox(-1.5F, -1.0F, -5.0F, 3, 2, 5, 0.0F);
        this.setRotateAngle(LfrontEndleg, -0.4553564018453205F, 0.0F, 0.045553093477052F);
        this.Seg2 = new ModelRenderer(this, 0, 36);
        this.Seg2.setRotationPoint(0.0F, 0.5F, 2.0F);
        this.Seg2.addBox(-1.5F, -2.0F, 0.0F, 3, 3, 6, 0.0F);
        this.LbackHip = new ModelRenderer(this, 21, 23);
        this.LbackHip.setRotationPoint(2.0F, 0.5F, -4.0F);
        this.LbackHip.addBox(0.0F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
        this.setRotateAngle(LbackHip, 1.8212510744560826F, 0.0F, 0.0F);
        this.LfrontForeleg = new ModelRenderer(this, 29, 53);
        this.LfrontForeleg.setRotationPoint(3.1F, 1.6F, 0.1F);
        this.LfrontForeleg.addBox(-1.0F, -1.0F, -10.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(LfrontForeleg, 0.22759093446006054F, -0.08726646259971647F, 0.0F);
        this.RfrontLeg = new ModelRenderer(this, 21, 28);
        this.RfrontLeg.setRotationPoint(-2.0F, 0.5F, -11.5F);
        this.RfrontLeg.addBox(-4.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(RfrontLeg, 0.22689280275926282F, 0.0F, 0.0F);
        this.RbackEndLeg = new ModelRenderer(this, 66, 23);
        this.RbackEndLeg.setRotationPoint(0.0F, 15.6F, 0.3F);
        this.RbackEndLeg.addBox(-0.5F, -1.0F, -1.0F, 1, 7, 2, 0.0F);
        this.setRotateAngle(RbackEndLeg, 2.600540585471551F, 0.0F, 0.0F);
        this.RfrontForeleg = new ModelRenderer(this, 1, 53);
        this.RfrontForeleg.mirror = true;
        this.RfrontForeleg.setRotationPoint(-3.1F, 1.6F, 0.1F);
        this.RfrontForeleg.addBox(-1.0F, -1.0F, -10.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(RfrontForeleg, 0.22759093446006054F, 0.08726646259971647F, 0.0F);
        this.Snout = new ModelRenderer(this, 25, 0);
        this.Snout.setRotationPoint(0.0F, -0.5F, -5.0F);
        this.Snout.addBox(-2.5F, -1.5F, -6.0F, 5, 3, 6, 0.0F);
        this.Body = new ModelRenderer(this, 0, 13);
        this.Body.setRotationPoint(0.0F, 17.0F, 8.0F);
        this.Body.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 6, 0.0F);
        this.LfrontLeg = new ModelRenderer(this, 21, 28);
        this.LfrontLeg.mirror = true;
        this.LfrontLeg.setRotationPoint(2.0F, 0.5F, -11.5F);
        this.LfrontLeg.addBox(1.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(LfrontLeg, 0.22689280275926282F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 25, 9);
        this.Jaw.setRotationPoint(0.0F, 1.5F, -5.0F);
        this.Jaw.addBox(-2.0F, -1.7F, -5.7F, 4, 3, 6, 0.0F);
        this.Body.addChild(this.Seg1);
        this.LbackLeg.addChild(this.LbackForeleg);
        this.Neck.addChild(this.Head);
        this.Seg2.addChild(this.Tail);
        this.Neck.addChild(this.RightWing);
        this.Body.addChild(this.Neck);
        this.Neck.addChild(this.LeftWing);
        this.RfrontForeleg.addChild(this.RfrontEndleg);
        this.Body.addChild(this.RbackHip);
        this.LbackHip.addChild(this.LbackLeg);
        this.LbackForeleg.addChild(this.LbackEndLeg);
        this.RbackHip.addChild(this.RbackLeg);
        this.RbackLeg.addChild(this.RbackForeleg);
        this.LfrontForeleg.addChild(this.LfrontEndleg);
        this.Seg1.addChild(this.Seg2);
        this.Body.addChild(this.LbackHip);
        this.LfrontLeg.addChild(this.LfrontForeleg);
        this.Body.addChild(this.RfrontLeg);
        this.RbackForeleg.addChild(this.RbackEndLeg);
        this.RfrontLeg.addChild(this.RfrontForeleg);
        this.Head.addChild(this.Snout);
        this.Body.addChild(this.LfrontLeg);
        this.Head.addChild(this.Jaw);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
