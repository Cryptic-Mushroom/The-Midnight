package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.DeceitfulSnapperModel;
import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DeceitfulSnapperRenderer extends MobRenderer<DeceitfulSnapperEntity, DeceitfulSnapperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/deceitful_snapper.png");

    public DeceitfulSnapperRenderer(EntityRendererManager manager) {
        super(manager, new DeceitfulSnapperModel(), 0f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(DeceitfulSnapperEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(DeceitfulSnapperEntity entity, float partialTicks) {
        GlStateManager.translatef(0f, 1.4f, -0.05f);
        GlStateManager.scalef(1.1f, 1.1f, 1.1f);
    }

    @Override
    protected float getDeathMaxRotation(DeceitfulSnapperEntity entity) {
        return 180f;
    }
}
