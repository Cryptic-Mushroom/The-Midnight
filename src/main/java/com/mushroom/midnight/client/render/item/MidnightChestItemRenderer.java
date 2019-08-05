package com.mushroom.midnight.client.render.item;

import com.mushroom.midnight.common.tile.base.MidnightChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MidnightChestItemRenderer extends ItemStackTileEntityRenderer {
    private final MidnightChestTileEntity chest = new MidnightChestTileEntity();

    public MidnightChestItemRenderer(Block block) {
        chest.setChestModel(block);
    }

    @Override
    public void renderByItem(ItemStack stack) {
        TileEntityRendererDispatcher.instance.renderAsItem(this.chest);
    }
}
