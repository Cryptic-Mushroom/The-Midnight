package com.mushroom.midnight.client.render.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ShadeSquirrelModel;
import com.mushroom.midnight.common.entity.creature.ShadeSquirrelEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ShadeSquirrelRenderer extends MobRenderer<ShadeSquirrelEntity, ShadeSquirrelModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/shade_squirrel.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/shade_squirrel_emissive.png");


    public ShadeSquirrelRenderer(EntityRendererManager manager) {
        super(manager, new ShadeSquirrelModel(), 0.4f);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new EmissiveLayerRenderer<>(this, EMISSIVE_TEXTURE, ShadeSquirrelRenderer::computeBrightness, ShadeSquirrelRenderer::computeColor));
    }

    private static int computeColor(ShadeSquirrelEntity entity, float partialTicks) {
        return 0xdccf70;
    }

    private static int computeBrightness(ShadeSquirrelEntity entity, float partialTicks) {
        return 0xf0;
    }



    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ShadeSquirrelEntity entity) {
        return TEXTURE;
    }
}
