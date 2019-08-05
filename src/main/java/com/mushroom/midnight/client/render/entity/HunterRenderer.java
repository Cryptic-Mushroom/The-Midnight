package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.HunterModel;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HunterRenderer extends MobRenderer<HunterEntity, HunterModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/hunter.png");

    public HunterRenderer(EntityRendererManager manager) {
        super(manager, new HunterModel(), 0.5F);
    }

    @Override
    protected void applyRotations(HunterEntity entity, float age, float rotationYaw, float partialTicks) {
        super.applyRotations(entity, age, rotationYaw, partialTicks);

        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float roll = entity.prevRoll + (entity.roll - entity.prevRoll) * partialTicks;

        GlStateManager.rotatef(roll, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotatef(-pitch, 1.0F, 0.0F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(HunterEntity entity) {
        return TEXTURE;
    }
}
