/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.item.FloatingPlantItem;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import java.util.function.Predicate;

import net.minecraft.block.AbstractBlock.Properties;

public class FloatingPlantBlock extends PlantBlock implements ICustomBlockItem {
    private final Predicate<Fluid> requiredFluid;

    protected FloatingPlantBlock(Properties props, Predicate<Fluid> requiredFluid) {
        super(props);
        this.requiredFluid = requiredFluid;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
        return requiredFluid.test(state.getFluidState().getType());
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos down = pos.below();
        return mayPlaceOn(world.getBlockState(down), world, down);
    }

    @Override
    public BlockItem newBlockItem(Item.Properties props) {
        return new FloatingPlantItem(this, props);
    }
}
