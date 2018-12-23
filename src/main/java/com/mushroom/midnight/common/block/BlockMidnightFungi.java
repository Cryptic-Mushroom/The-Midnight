package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightFungi extends BlockMidnightPlant implements IGrowable {
    private final Supplier<Block> parentSupplier;

    public BlockMidnightFungi(Supplier<Block> parentSupplier) {
        super(PlantBehaviorType.BUSH, true);
        this.parentSupplier = parentSupplier;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == Blocks.MYCELIUM;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        if (parentSupplier.get().canPlaceBlockAt(world, pos)) {
            ((BlockMidnightDoubleFungi)parentSupplier.get()).placeAt(world, pos, 2);
        }
    }
}
