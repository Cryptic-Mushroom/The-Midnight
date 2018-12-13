package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.function.Supplier;

public class BlockMidnightDoubleSlab extends BlockMidnightSlab {
    private final Supplier<Block> singleSlabSupplier;

    public BlockMidnightDoubleSlab(Supplier<Block> singleSlabSupplier) {
        super(() -> singleSlabSupplier.get().getDefaultState());
        this.singleSlabSupplier = singleSlabSupplier;
        this.setCreativeTab(null);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    protected IBlockState buildDefaultState(IBlockState baseState) {
        return baseState;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.singleSlabSupplier.get());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return ((BlockMidnightSlab)singleSlabSupplier.get()).getParentstate().getBlock().canSustainPlant(state, world, pos, direction, plantable);
    }
}
