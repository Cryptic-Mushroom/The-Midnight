/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.world.dimension.MnDimensions;
import midnight.core.dimension.DimensionUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class NightMyceliumBlock extends NightDirtBlock implements IGrowable {
    public NightMyceliumBlock(Properties props) {
        super(props);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        int myStatus = getGrowStatusAt(world, pos);
        if (myStatus == -1) {
            world.setBlockAndUpdate(pos, MnBlocks.NIGHTSTONE.defaultBlockState());
        }
    }

    private static int getGrowStatusAt(ServerWorld world, BlockPos pos) {
        if (DimensionUtil.isInDimension(world, MnDimensions.THE_MIDNIGHT)) {
            BlockPos up = pos.above();
            BlockState upstate = world.getBlockState(up);
            if (!doesLightGoThroughBlockAbove(world.getBlockState(pos), world, pos, upstate, up)) {
                return -1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    private static boolean doesLightGoThroughBlockAbove(BlockState state, IWorldReader world, BlockPos pos, BlockState upstate, BlockPos up) {
        if (upstate.getBlock() == Blocks.SNOW && upstate.getValue(SnowBlock.LAYERS) == 1) {
            return true;
        } else {
            int opac = LightEngine.getLightBlockInto(world, state, pos, upstate, up, Direction.UP, upstate.getLightBlock(world, up));
            return opac < world.getMaxLightLevel();
        }
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        BlockPos up = pos.above();
        BlockState upState = world.getBlockState(up);
        return upState.getBlock().isAir(upState, world, up);
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        BlockPos up = pos.above();
        BlockState plant = MnBlocks.GLOB_FUNGUS.defaultBlockState();

        for (int i = 0; i < 128; ++i) {
            BlockPos placePos = up;
            int j = 0;

            while (true) {
                if (j >= i / 16) {
                    BlockState currState = world.getBlockState(placePos);
                    if (currState.getBlock() instanceof IGrowable && rand.nextInt(10) == 0) {
                        ((IGrowable) plant.getBlock()).performBonemeal(world, rand, placePos, currState);
                    }

                    if (!currState.getBlock().isAir(currState, world, placePos)) {
                        break;
                    }

                    BlockState newState = plant;
                    // TODO: Generate biome-specific plants here

                    if (newState.canSurvive(world, placePos)) {
                        world.setBlock(placePos, newState, 3);
                    }
                    break;
                }

                placePos = placePos.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (world.getBlockState(placePos.below()).getBlock() != this || world.getBlockState(placePos).isSolidRender(world, placePos)) {
                    break;
                }

                ++j;
            }
        }
    }
}
