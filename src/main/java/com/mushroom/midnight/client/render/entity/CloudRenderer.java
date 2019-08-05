package com.mushroom.midnight.client.render.entity;

import com.mushroom.midnight.common.entity.CloudEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CloudRenderer extends EntityRenderer<CloudEntity> {
    public CloudRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    @Nullable
    public ResourceLocation getEntityTexture(CloudEntity entity) {
        return null;
    }
}
