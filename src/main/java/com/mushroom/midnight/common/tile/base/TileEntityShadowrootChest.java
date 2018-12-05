package com.mushroom.midnight.common.tile.base;

import net.minecraft.tileentity.TileEntityChest;

public class TileEntityShadowrootChest extends TileEntityChest {

    @Override
    public String getName() {
        return hasCustomName() ? customName : "tile.midnight.shadowroot_chest.name";
    }
}
