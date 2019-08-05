package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.NovaModel;
import com.mushroom.midnight.common.entity.creature.NovaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NovaRenderer extends MobRenderer<NovaEntity, NovaModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nova.png");

    public NovaRenderer(EntityRendererManager manager) {
        super(manager, new NovaModel(), 0.6f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NovaEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(NovaEntity entity, float partialTicks) {
        GlStateManager.translatef(0f, -0.25f + (MathHelper.sin((entity.ticksExisted + partialTicks) * 0.14f - 0.5f) / 8), 0f);
        GlStateManager.scalef(0.8f, 0.8f, 0.8f);
    }
}
