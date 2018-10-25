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

public class BlockDeadWoodSlab extends BlockSlab {

    private boolean isDouble;

    public static final PropertyEnum<BlockDeadWoodSlab.EnumType> VARIANT = PropertyEnum.<BlockDeadWoodSlab.EnumType>create("variant", BlockDeadWoodSlab.EnumType.class);

    public BlockDeadWoodSlab(boolean isDouble) {

        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        IBlockState iblockstate = this.blockState.getBaseState();
        this.isDouble = isDouble;

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
            this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        }

    }

    @Override
    public String getTranslationKey(int meta) {

        return super.getTranslationKey() + "." + BlockDeadWoodSlab.EnumType.byMetadata(meta).getTranslationKey();

    }

    @Override
    public IProperty<?> getVariantProperty() {

        return VARIANT;

    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {

        return BlockDeadWoodSlab.EnumType.byMetadata(stack.getMetadata() & 7);

    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {

        for (BlockDeadWoodSlab.EnumType blockdeadwoodslab$enumtype : BlockDeadWoodSlab.EnumType.values()) {
            items.add(new ItemStack(this, 1, blockdeadwoodslab$enumtype.getMetadata()));
        }

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockDeadWoodSlab.EnumType.byMetadata(meta & 7));

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;

    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;
        i = i | ((BlockDeadWoodSlab.EnumType) state.getValue(VARIANT)).getMetadata();

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;

    }

    @Override
    protected BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] { HALF, VARIANT });

    }

    @Override
    public int damageDropped(IBlockState state) {

        return ((BlockDeadWoodSlab.EnumType) state.getValue(VARIANT)).getMetadata();

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {

        return Item.getItemFromBlock(ModBlocks.DEAD_WOOD_SLAB);

    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {

        return new ItemStack(ModBlocks.DEAD_WOOD_SLAB, 1, ((BlockDeadWoodSlab.EnumType) state.getValue(VARIANT)).getMetadata());

    }

    @Override
    public boolean isDouble() {

        return this.isDouble;

    }

    public static enum EnumType implements IStringSerializable {
        DEAD_WOOD(0, "dead_wood");

        private static final BlockDeadWoodSlab.EnumType[] META_LOOKUP = new BlockDeadWoodSlab.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String translationKey;

        private EnumType(int metaIn, String nameIn) {
            this(metaIn, nameIn, nameIn);
        }

        private EnumType(int metaIn, String nameIn, String unlocalizedNameIn) {
            this.meta = metaIn;
            this.name = nameIn;
            this.translationKey = unlocalizedNameIn;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static BlockDeadWoodSlab.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getTranslationKey() {
            return this.translationKey;
        }

        static {
            for (BlockDeadWoodSlab.EnumType blockdeadwoodslab$enumtype : values()) {
                META_LOOKUP[blockdeadwoodslab$enumtype.getMetadata()] = blockdeadwoodslab$enumtype;
            }
        }
    }

}
