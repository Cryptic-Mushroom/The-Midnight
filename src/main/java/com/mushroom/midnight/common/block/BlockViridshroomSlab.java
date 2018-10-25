package com.mushroom.midnight.common.block;

import java.util.Random;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class BlockViridshroomSlab extends BlockSlab {
	
	public static final PropertyEnum<BlockViridshroomSlab.EnumType> VARIANT = PropertyEnum.<BlockViridshroomSlab.EnumType>create("variant", BlockViridshroomSlab.EnumType.class);
	
	public BlockViridshroomSlab() {
		
		super(Material.WOOD);
		this.setSoundType(SoundType.WOOD);
		IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
            this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        }
		
	}

	@Override
	public String getTranslationKey(int meta) {
		
		return super.getTranslationKey() + "." + BlockViridshroomSlab.EnumType.byMetadata(meta).getTranslationKey();
		
	}

	@Override
	public IProperty<?> getVariantProperty() {
		
		return VARIANT;
		
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		
		return BlockViridshroomSlab.EnumType.byMetadata(stack.getMetadata() & 7);
		
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		
		for (BlockViridshroomSlab.EnumType blockviridshroomslab$enumtype : BlockViridshroomSlab.EnumType.values()) {
			items.add(new ItemStack(this, 1, blockviridshroomslab$enumtype.getMetadata()));
		}
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockViridshroomSlab.EnumType.byMetadata(meta & 7));
		
		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}
		
		return iblockstate;
		
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		int i = 0;
		i = i | ((BlockViridshroomSlab.EnumType)state.getValue(VARIANT)).getMetadata();
		
		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			i |= 8;
		}
		
		return i;
		
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
        return new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
        
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		
        return ((BlockViridshroomSlab.EnumType)state.getValue(VARIANT)).getMetadata();
        
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ModBlocks.VIRIDSHROOM_SLAB);
		
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		
		return new ItemStack(ModBlocks.VIRIDSHROOM_SLAB, 1, ((BlockViridshroomSlab.EnumType)state.getValue(VARIANT)).getMetadata());
		
	}
	
	public static enum EnumType implements IStringSerializable
	{
		VIRIDSHROOM(0, "viridshroom");

	    private static final BlockViridshroomSlab.EnumType[] META_LOOKUP = new BlockViridshroomSlab.EnumType[values().length];
	    private final int meta;
	    private final String name;
	    private final String translationKey;

	    private EnumType(int metaIn, String nameIn)
	    {
	    	this(metaIn, nameIn, nameIn);
	    }

	    private EnumType(int metaIn, String nameIn, String unlocalizedNameIn)
	    {
	    	this.meta = metaIn;
	    	this.name = nameIn;
	        this.translationKey = unlocalizedNameIn;
	    }

	    public int getMetadata()
	    {
	    	return this.meta;
	    }

	    @Override
	    public String toString()
	    {
	    	return this.name;
	    }

	    public static BlockViridshroomSlab.EnumType byMetadata(int meta)
	    {
	    	if (meta < 0 || meta >= META_LOOKUP.length)
	        {
	    		meta = 0;
	        }

	    	return META_LOOKUP[meta];
	    }

	    @Override
	    public String getName()
	    {
	    	return this.name;
	    }

	    public String getTranslationKey()
	    {
	    	return this.translationKey;
	    }

	    static
	    {
	    	for (BlockViridshroomSlab.EnumType blockviridshroomslab$enumtype : values())
	        {
	    		META_LOOKUP[blockviridshroomslab$enumtype.getMetadata()] = blockviridshroomslab$enumtype;
	        }
	    }
	}
	
}
