/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public interface MnWaterLoggable extends FluidDrainable, FluidFillable {
    @Override
    default boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(Properties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    default boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fstate) {
        if (!state.get(Properties.WATERLOGGED) && fstate.getFluid() == Fluids.WATER) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, true), 3);

                Fluid fluid = fstate.getFluid();
                world.getFluidTickScheduler().schedule(pos, fluid, fluid.getTickRate(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    default Fluid tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            world.setBlockState(pos, state.with(Properties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    default void tickFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            Fluid fluid = world.getFluidState(pos).getFluid();
            world.getFluidTickScheduler().schedule(pos, fluid, fluid.getTickRate(world));
        }
    }

    default BlockState waterlog(BlockState state, World world, BlockPos pos) {
        if (state == null) return null;
        boolean waterlogged = world.getFluidState(pos).getFluid() == Fluids.WATER;
        return state.with(Properties.WATERLOGGED, waterlogged);
    }

    default BlockState waterlog(BlockState state, ItemPlacementContext ctx) {
        return waterlog(state, ctx.getWorld(), ctx.getBlockPos());
    }

    default FluidState fluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED)
               ? Fluids.WATER.getDefaultState()
               : Fluids.EMPTY.getDefaultState();
    }
}
