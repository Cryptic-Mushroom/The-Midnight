package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Note from ZombieEnderman5: Map color assigned on a best-guess effort based on the texture color, without looking at the map color format. Done because <code>BlockFence</code> won't allow no map color to be passed in, but I think passing in <code>null</code> would crash the game. */
public class BlockDeadWoodFence extends BlockFence {

	public BlockDeadWoodFence() {
		
		super(Material.WOOD, MapColor.GRAY);
		this.setCreativeTab(Midnight.MIDNIGHT_TAB);
		this.setSoundType(SoundType.WOOD);
		
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		return super.getActualState(state, worldIn, pos);
		
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		
		return super.withRotation(state, rot);
		
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		
		return super.withMirror(state, mirrorIn);
		
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return super.createBlockState();
		
	}
	
}
