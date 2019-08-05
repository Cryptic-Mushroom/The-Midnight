package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldItemRenderer extends ItemStackTileEntityRenderer {
    private final static ResourceLocation TEXTURE = new ResourceLocation("textures/block/purple_terracotta.png");
    private final ShieldModel shieldModel = new ShieldModel(); // TODO model rockshroom

    @Override
    public void renderByItem(ItemStack stack) {
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scalef(1f, -1f, -1f);
        this.shieldModel.render();
        GlStateManager.popMatrix();
    }
}
