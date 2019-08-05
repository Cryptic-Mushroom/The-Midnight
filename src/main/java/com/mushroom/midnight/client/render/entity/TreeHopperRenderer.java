package com.mushroom.midnight.client.render.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.TreehopperModel;
import com.mushroom.midnight.common.entity.creature.TreeHopperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class TreeHopperRenderer extends MobRenderer<TreeHopperEntity, TreehopperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/treehopper.png");

    public TreeHopperRenderer(EntityRendererManager manager) {
        super(manager, new TreehopperModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(TreeHopperEntity entity) {
        return TEXTURE;
    }
}
