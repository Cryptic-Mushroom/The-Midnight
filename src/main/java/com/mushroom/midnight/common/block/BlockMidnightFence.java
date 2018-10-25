package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMidnightFence extends BlockFence {

    /**
     * Assumes nothing. You must provide a material, map color, and sound type for
     * this one to work.
     */
    public BlockMidnightFence(Material materialIn, MapColor mapColorIn, SoundType soundTypeIn) {

        super(materialIn, mapColorIn);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setSoundType(soundTypeIn);

    }

    /**
     * If you want to be simple, use this. Assumes you want wood
     * material and sound type, but leaves the map color up to you.
     */
    public BlockMidnightFence(MapColor mapColorIn) {

        this(Material.WOOD, mapColorIn, SoundType.WOOD);

    }

}
