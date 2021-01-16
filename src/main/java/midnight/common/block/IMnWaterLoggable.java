/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public interface IMnWaterLoggable extends IBucketPickupHandler, ILiquidContainer {
    @Override
    default boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    default boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, FluidState fstate) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fstate.getFluid() == Fluids.WATER) {
            if (!world.isRemote()) {
                world.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, true), 3);

                Fluid fluid = fstate.getFluid();
                world.getPendingFluidTicks().scheduleTick(pos, fluid, fluid.getTickRate(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    default Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(BlockStateProperties.WATERLOGGED)) {
            world.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    default void tickFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(BlockStateProperties.WATERLOGGED)) {
            Fluid fluid = world.getFluidState(pos).getFluid();
            world.getPendingFluidTicks().scheduleTick(pos, fluid, fluid.getTickRate(world));
        }
    }

    default BlockState waterlog(BlockState state, World world, BlockPos pos) {
        if (state == null) return null;
        boolean waterlogged = world.getFluidState(pos).getFluid() == Fluids.WATER;
        return state.with(BlockStateProperties.WATERLOGGED, waterlogged);
    }

    default BlockState waterlog(BlockState state, BlockItemUseContext ctx) {
        return waterlog(state, ctx.getWorld(), ctx.getPos());
    }

    default FluidState fluidState(BlockState state) {
        return state.get(BlockStateProperties.WATERLOGGED)
               ? Fluids.WATER.getDefaultState()
               : Fluids.EMPTY.getDefaultState();
    }
}
