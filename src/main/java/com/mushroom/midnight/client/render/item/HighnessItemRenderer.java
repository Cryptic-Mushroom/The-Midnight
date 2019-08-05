package com.mushroom.midnight.client.render.item;

import com.mushroom.midnight.client.render.entity.NightStagRenderer;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HighnessItemRenderer extends ItemStackTileEntityRenderer {
    private NightStagEntity entity;

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderByItem(ItemStack stack) {
        // TODO improve this
        if (entity == null && Minecraft.getInstance().world != null) {
            entity = MidnightEntities.NIGHTSTAG.create(Minecraft.getInstance().world);
        }
        if (entity != null) {
            try {
                NightStagRenderer render = new NightStagRenderer(Minecraft.getInstance().getRenderManager());
                render.doRender(entity, 0d, 0d, 0d, 0f, 1f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
