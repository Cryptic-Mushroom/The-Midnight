package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;

public class BlockNightstoneStairs extends BlockStairs implements IModelProvider {
	
    public BlockNightstoneStairs() {
		
		super(ModBlocks.NIGHTSTONE.getDefaultState());
		this.setCreativeTab(Midnight.MIDNIGHT_TAB);
		this.setSoundType(SoundType.WOOD);
		this.useNeighborBrightness = true;
		
	}
	
}
