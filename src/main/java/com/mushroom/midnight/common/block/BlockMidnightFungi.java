package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMidnightFungi extends BlockMidnightPlant implements IGrowable {

    public BlockMidnightFungi() {
        super(PlantBehaviorType.BUSH, true);
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
        Block grownBlock = null;
        if (state.getBlock() == ModBlocks.NIGHTSHROOM) {
            grownBlock = ModBlocks.DOUBLE_NIGHTSHROOM;
        } else if (state.getBlock() == ModBlocks.DEWSHROOM) {
            grownBlock = ModBlocks.DOUBLE_DEWSHROOM;
        } else if (state.getBlock() == ModBlocks.VIRIDSHROOM) {
            grownBlock = ModBlocks.DOUBLE_VIRIDSHROOM;
        }
        if (grownBlock != null && grownBlock.canPlaceBlockAt(world, pos)) {
            ((BlockMidnightDoubleFungi)grownBlock).placeAt(world, pos, 2);
        }
    }
}
