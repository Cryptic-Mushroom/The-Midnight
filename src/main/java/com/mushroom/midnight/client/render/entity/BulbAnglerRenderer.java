package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.BulbAnglerModel;
import com.mushroom.midnight.common.entity.creature.BulbAnglerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class BulbAnglerRenderer extends MobRenderer<BulbAnglerEntity, BulbAnglerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/bulb_angler.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/bulb_angler_emissive.png");

    public BulbAnglerRenderer(EntityRendererManager manager) {
        super(manager, new BulbAnglerModel(), 0f);
        this.addLayer(new EmissiveLayerRenderer<>(this, EMISSIVE_TEXTURE, BulbAnglerRenderer::computeBrightness, BulbAnglerRenderer::computeColor));
    }


    private static int computeColor(BulbAnglerEntity entity, float partialTicks) {
        return 0xebe579;
    }

    private static int computeBrightness(BulbAnglerEntity entity, float partialTicks) {
        return 0xf0;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(BulbAnglerEntity entity) {
        return TEXTURE;
    }


    protected void preRenderCallback(BulbAnglerEntity entity, float partialTicks) {
        GlStateManager.translatef(0f, 1.2f, 0.0f);
    }

    @Override
    protected void applyRotations(BulbAnglerEntity entity, float age, float yaw, float pitch) {
        super.applyRotations(entity, age, yaw, pitch);
        if (!entity.isInWater()) {
            GlStateManager.rotatef(90.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    @Override
    protected float getDeathMaxRotation(BulbAnglerEntity entity) {
        return 180f;
    }
}
