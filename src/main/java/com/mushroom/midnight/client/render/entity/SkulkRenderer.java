package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.SkulkModel;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SkulkRenderer extends MobRenderer<SkulkEntity, SkulkModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/skulk.png");

    public SkulkRenderer(EntityRendererManager manager) {
        super(manager, new SkulkModel(), 0.4f);
    }

    @Override
    public void doRender(SkulkEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        boolean isTransparent = false;
        if (entity.isStealth()) {
            if (entity.getDistanceSq(Minecraft.getInstance().player) >= 8d) {
                return;
            }
            isTransparent = true;
            GlStateManager.Profile.TRANSPARENT_MODEL.func_187373_a();
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if (isTransparent) {
            GlStateManager.Profile.TRANSPARENT_MODEL.func_187374_b();
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(SkulkEntity entity) {
        return TEXTURE;
    }
}
