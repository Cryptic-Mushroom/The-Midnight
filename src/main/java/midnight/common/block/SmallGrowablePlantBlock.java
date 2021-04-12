/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class SmallGrowablePlantBlock extends PlantBlock implements IGrowable {
    private final Supplier<TallPlantBlock> largePlant;

    public SmallGrowablePlantBlock(Properties props, Supplier<TallPlantBlock> largePlant) {
        super(props);
        this.largePlant = largePlant;
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        TallPlantBlock large = getDoubleBlock(world, rand, pos, state);
        if (large.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) {
            large.placeAt(world, pos, 2);
        }
    }

    protected TallPlantBlock getDoubleBlock(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        return largePlant.get();
    }
}
