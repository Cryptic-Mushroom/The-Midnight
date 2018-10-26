package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

public class BlockTrenchstoneWall extends BlockWall {
	
    public BlockTrenchstoneWall() {
		
		super(ModBlocks.TRENCHSTONE);
		this.setCreativeTab(Midnight.MIDNIGHT_TAB);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 2);
		
	}
	
	@Override
	public String getLocalizedName() {
		
		return I18n.translateToLocal("tile.midnight.trenchstone_wall.name");
		
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		
		items.add(new ItemStack(this, 1, 0));
		
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		return 0;
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return this.getDefaultState().withProperty(VARIANT, BlockWall.EnumType.byMetadata(0));
		
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return 0;
		
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH, VARIANT});
		
	}
	
}
