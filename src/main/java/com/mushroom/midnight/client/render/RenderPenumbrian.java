package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelPenumbrian;
import com.mushroom.midnight.common.entity.creature.EntityPenumbrian;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPenumbrian extends RenderLiving<EntityPenumbrian> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/penumbrian.png");

    public RenderPenumbrian(RenderManager rendermanager) {
        super(rendermanager, new ModelPenumbrian(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPenumbrian entity) {
        return TEXTURE;
    }
}
