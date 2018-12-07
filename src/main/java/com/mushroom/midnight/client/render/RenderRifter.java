package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelRifter;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRifter extends RenderLiving<EntityRifter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/rifter.png");

    public RenderRifter(RenderManager renderManager) {
        super(renderManager, new ModelRifter(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityRifter entity, float partialTick) {
        super.preRenderCallback(entity, partialTick);
        if (Helper.isMidnightDimension(entity.world)) {
            float scaleModifier = EntityRifter.HOME_SCALE_MODIFIER;
            GlStateManager.scale(scaleModifier, scaleModifier, scaleModifier);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRifter entity) {
        return TEXTURE;
    }
}
