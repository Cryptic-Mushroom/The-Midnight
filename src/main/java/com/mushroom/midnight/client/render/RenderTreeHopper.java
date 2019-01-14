package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelTreehopper;
import com.mushroom.midnight.common.entity.creature.EntityTreeHopper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTreeHopper extends RenderLiving<EntityTreeHopper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/treehopper.png");

    public RenderTreeHopper(RenderManager rendermanager) {
        super(rendermanager, new ModelTreehopper(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTreeHopper entity) {
        return TEXTURE;
    }
}
