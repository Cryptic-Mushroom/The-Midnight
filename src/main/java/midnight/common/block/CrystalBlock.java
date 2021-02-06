/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class CrystalBlock extends PlantBlock implements MnWaterLoggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected CrystalBlock(Settings props) {
        super(props);

        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }


    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView world, BlockPos pos) {
        // Mud and soul sand are not solid, but appear to be so - include them as a special exception
        return state.isSideSolidFullSquare(world, pos, Direction.UP)
                   || state.isOf(MnBlocks.DECEITFUL_MUD)
                   || state.isOf(Blocks.SOUL_SAND);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return waterlog(super.getPlacementState(ctx), ctx);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction dir, BlockState adjState, WorldAccess world, BlockPos pos, BlockPos adjPos) {
        tickFluid(world, pos, state);
        return super.getStateForNeighborUpdate(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return fluidState(state);
    }
}
