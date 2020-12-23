/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.block.fluid.MnFluids;
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

public interface IDarkWaterLoggable extends IBucketPickupHandler, ILiquidContainer {
    @Override
    default boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(BlockStateProperties.WATERLOGGED) && fluid == MnFluids.DARK_WATER;
    }

    @Override
    default boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, FluidState fstate) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fstate.getFluid() == MnFluids.DARK_WATER) {
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
            return MnFluids.DARK_WATER;
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
        boolean waterlogged = world.getFluidState(pos).getFluid() == MnFluids.DARK_WATER;
        return state.with(BlockStateProperties.WATERLOGGED, waterlogged);
    }

    default BlockState waterlog(BlockState state, BlockItemUseContext ctx) {
        return waterlog(state, ctx.getWorld(), ctx.getPos());
    }

    default FluidState fluidState(BlockState state) {
        return state.get(BlockStateProperties.WATERLOGGED)
               ? MnFluids.DARK_WATER.getDefaultState()
               : Fluids.EMPTY.getDefaultState();
    }
}
