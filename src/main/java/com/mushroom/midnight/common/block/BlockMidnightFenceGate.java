package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMidnightFenceGate extends BlockFenceGate {

    private final MapColor overrideMapColor;
    private final Material overrideMaterial;

    /**
     * Assumes nothing. You must provide a material, map color, and sound type for
     * this one to work.
     */
    public BlockMidnightFenceGate(Material materialIn, MapColor mapColorIn, SoundType soundTypeIn) {

        super(EnumType.OAK);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setSoundType(soundTypeIn);
        this.overrideMapColor = mapColorIn;
        this.overrideMaterial = materialIn;

    }
    
    /**
     * If you want to be simple, use this. Assumes you want wood
     * material and sound type, but leaves the map color up to you.
     */
    public BlockMidnightFenceGate(MapColor mapColorIn) {

        this(Material.WOOD, mapColorIn, SoundType.WOOD);

    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        return this.overrideMapColor;

    }

    @Override
    public Material getMaterial(IBlockState state) {
        
        return this.overrideMaterial;
        
    }

}
