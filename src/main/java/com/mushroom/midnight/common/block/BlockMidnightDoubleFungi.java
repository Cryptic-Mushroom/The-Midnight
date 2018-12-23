package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightDoubleFungi extends BlockMidnightDoublePlant implements IGrowable {
    private final Supplier<WorldGenerator> generatorSupplier;

    public BlockMidnightDoubleFungi(Supplier<WorldGenerator> generatorSupplier) {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.STAGE, 0));
        this.generatorSupplier = generatorSupplier;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == Blocks.MYCELIUM;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return world.rand.nextFloat() < 0.75F;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        if (state.getValue(BlockSapling.STAGE) == 0) {
            world.setBlockState(pos, state.cycleProperty(BlockSapling.STAGE), 4);
        } else if (TerrainGen.saplingGrowTree(world, rand, pos)) {
            WorldGenerator generator = this.generatorSupplier.get();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, BlockSapling.STAGE);
    }
}
