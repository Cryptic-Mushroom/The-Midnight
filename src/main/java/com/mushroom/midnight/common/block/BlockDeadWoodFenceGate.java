package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockDeadWoodFenceGate extends BlockFenceGate {

	public BlockDeadWoodFenceGate() {
		
		super(EnumType.OAK);
		this.setCreativeTab(Midnight.MIDNIGHT_TAB);
		this.setSoundType(SoundType.WOOD);
		
	}

	/** Get the MapColor for this Block and the given BlockState.<br>
	 * Note from ZombieEnderman5: Didn't want these to all show up as oak on a map. */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		return MapColor.GRAY;
		
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
