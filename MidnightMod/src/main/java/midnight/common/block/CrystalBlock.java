/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 22
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class CrystalBlock extends PlantBlock implements IDarkWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected CrystalBlock(Properties props) {
        super(props);

        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        // Mud and soul sand are not solid, but appear to be so - include them as a special exception
        return state.isSideSolidFullSquare(world, pos, Direction.UP)
                   || state.isIn(MnBlocks.DECEITFUL_MUD)
                   || state.isIn(Blocks.SOUL_SAND);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return waterlog(super.getStateForPlacement(ctx), ctx);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        tickFluid(world, pos, state);
        return super.updatePostPlacement(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return fluidState(state);
    }
}
