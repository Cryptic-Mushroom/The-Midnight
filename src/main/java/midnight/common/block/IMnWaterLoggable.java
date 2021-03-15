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
    default boolean canPlaceLiquid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    default boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fstate) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fstate.getType() == Fluids.WATER) {
            if (!world.isClientSide()) {
                world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);

                Fluid fluid = fstate.getType();
                world.getLiquidTicks().scheduleTick(pos, fluid, fluid.getTickDelay(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    default Fluid takeLiquid(IWorld world, BlockPos pos, BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    default void tickFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            Fluid fluid = world.getFluidState(pos).getType();
            world.getLiquidTicks().scheduleTick(pos, fluid, fluid.getTickDelay(world));
        }
    }

    default BlockState waterlog(BlockState state, World world, BlockPos pos) {
        if (state == null) return null;
        boolean waterlogged = world.getFluidState(pos).getType() == Fluids.WATER;
        return state.setValue(BlockStateProperties.WATERLOGGED, waterlogged);
    }

    default BlockState waterlog(BlockState state, BlockItemUseContext ctx) {
        return waterlog(state, ctx.getLevel(), ctx.getClickedPos());
    }

    default FluidState fluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED)
               ? Fluids.WATER.defaultFluidState()
               : Fluids.EMPTY.defaultFluidState();
    }
}
