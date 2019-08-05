package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.StingerModel;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class StingerRenderer extends MobRenderer<StingerEntity, StingerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/stinger.png");

    public StingerRenderer(EntityRendererManager manager) {
        super(manager, new StingerModel(), 0.15f);
    }

    @Override
    public void doRender(StingerEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableCull();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableCull();
        GlStateManager.popMatrix();
    }

    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(StingerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(StingerEntity entity, float partialTicks) {
        float scale = 0.3f + (entity.getGrowingAge() * 0.1f);
        GlStateManager.scalef(scale, scale, scale);
    }

    @Override
    protected float getDeathMaxRotation(StingerEntity entity) {
        return 180f;
    }
}
