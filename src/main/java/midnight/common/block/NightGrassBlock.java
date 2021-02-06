/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;

public class NightGrassBlock extends NightDirtBlock implements Fertilizable {
    public NightGrassBlock(Settings props) {
        super(props);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        int myStatus = getGrowStatusAt(world, pos);
        if (myStatus == -1) {
            world.setBlockState(pos, MnBlocks.NIGHT_DIRT.getDefaultState());
        } else {
            for (int i = 0; i < 4; i++) {
                BlockPos randomNearby = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                BlockState nearState = world.getBlockState(randomNearby);
                if (nearState.getBlock() == MnBlocks.NIGHT_DIRT && getGrowStatusAt(world, randomNearby) == 1) {
                    world.setBlockState(randomNearby, getDefaultState());
                }
            }
        }
    }

    private static int getGrowStatusAt(ServerWorld world, BlockPos pos) {
        if (true) { // TODO world.getDimension().getType() == MnDimensions.midnight()
            BlockPos up = pos.up();
            BlockState upstate = world.getBlockState(up);
            if (!doesLightGoThroughBlockAbove(world.getBlockState(pos), world, pos, upstate, up)) {
                return -1;
            } else if (upstate.getFluidState().isIn(FluidTags.WATER)) {
                return 0;
            } else {
                return 1;
            }
        }
        return -1;
    }

    private static boolean doesLightGoThroughBlockAbove(BlockState state, WorldView world, BlockPos pos, BlockState upstate, BlockPos up) {
        if (upstate.getBlock() == Blocks.SNOW && upstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else {
            int opac = ChunkLightProvider.getRealisticOpacity(world, state, pos, upstate, up, Direction.UP, upstate.getOpacity(world, up));
            return opac < world.getMaxLightLevel();
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        return upState.isAir();
    }

    @Override
    public boolean canGrow(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        BlockPos up = pos.up();
        BlockState plant = MnBlocks.NIGHT_GRASS.getDefaultState();

        for (int i = 0; i < 128; ++i) {
            BlockPos placePos = up;
            int j = 0;

            while (true) {
                if (j >= i / 16) {
                    BlockState currState = world.getBlockState(placePos);
                    if (currState.getBlock() == plant.getBlock() && rand.nextInt(10) == 0) {
                        ((Fertilizable) plant.getBlock()).grow(world, rand, placePos, currState);
                    }

                    if (!currState.isAir()) {
                        break;
                    }

                    BlockState newState = plant;
                    // TODO: Generate biome-specific plants here

                    if (newState.canPlaceAt(world, placePos)) {
                        world.setBlockState(placePos, newState, 3);
                    }
                    break;
                }

                placePos = placePos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (world.getBlockState(placePos.down()).getBlock() != this || world.getBlockState(placePos).isSolidBlock(world, placePos)) {
                    break;
                }

                ++j;
            }
        }
    }
}
