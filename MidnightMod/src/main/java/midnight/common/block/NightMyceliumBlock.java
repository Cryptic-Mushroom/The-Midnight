/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.block;

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
            world.setBlockState(pos, MnBlocks.NIGHTSTONE.getDefaultState());
        }
    }

    private static int getGrowStatusAt(ServerWorld world, BlockPos pos) {
        if (true) { // TODO world.getDimension().getType() == MnDimensions.midnight()
            BlockPos up = pos.up();
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
        if (upstate.getBlock() == Blocks.SNOW && upstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else {
            int opac = LightEngine.func_215613_a(world, state, pos, upstate, up, Direction.UP, upstate.getOpacity(world, up));
            return opac < world.getMaxLightLevel();
        }
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        return upState.isAir(world, up);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        BlockPos up = pos.up();
        BlockState plant = MnBlocks.GLOB_FUNGUS.getDefaultState();

        for (int i = 0; i < 128; ++i) {
            BlockPos placePos = up;
            int j = 0;

            while (true) {
                if (j >= i / 16) {
                    BlockState currState = world.getBlockState(placePos);
                    if (currState.getBlock() instanceof IGrowable && rand.nextInt(10) == 0) {
                        ((IGrowable) plant.getBlock()).grow(world, rand, placePos, currState);
                    }

                    if (!currState.isAir(world, placePos)) {
                        break;
                    }

                    BlockState newState = plant;
                    // TODO: Generate biome-specific plants here

                    if (newState.isValidPosition(world, placePos)) {
                        world.setBlockState(placePos, newState, 3);
                    }
                    break;
                }

                placePos = placePos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (world.getBlockState(placePos.down()).getBlock() != this || world.getBlockState(placePos).isOpaqueCube(world, placePos)) {
                    break;
                }

                ++j;
            }
        }
    }
}
