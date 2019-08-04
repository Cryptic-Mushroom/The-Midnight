package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.registry.MidnightTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MidnightFurnaceTileEntity extends AbstractFurnaceTileEntity {

    protected MidnightFurnaceTileEntity(TileEntityType<?> entityType) {
        super(entityType, IRecipeType.SMELTING);
    }

    public MidnightFurnaceTileEntity() {
        this(MidnightTileEntities.MIDNIGHT_FURNACE);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.midnight.nightstone_furnace");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new FurnaceContainer(id, player, this, this.furnaceData); //new MidnightFurnaceContainer(playerIn.inventory, this);
    }
}
