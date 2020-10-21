/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ShroomShelfBlock extends MnPlantBlock {
    public static final DirectionProperty FACING = MnBlockStateProperties.FACING_EXCEPT_DOWN;
    private static final VoxelShape[] SHAPES = { // Order as specified in net.minecraft.util.Direction:
        null,                                    // DOWN
        makeCuboidShape(0, 0, 0, 16, 4, 16),     // UP
        makeCuboidShape(0, 6, 7, 16, 10, 16),    // NORTH
        makeCuboidShape(0, 6, 0, 16, 10, 9),     // SOUTH
        makeCuboidShape(7, 6, 0, 16, 10, 16),    // WEST
        makeCuboidShape(0, 6, 0, 9, 10, 16)      // EAST
    };

    protected ShroomShelfBlock(Properties props) {
        super(props);

        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.UP));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        Direction myDir = state.get(FACING);
        if (!canRemain(world, pos, myDir)) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        if (!state.isIn(this)) return true;
        Direction myDir = state.get(FACING);
        return canRemain(world, pos, myDir);
    }

    protected boolean isValidBlockSide(BlockState state, IWorldReader world, BlockPos pos, Direction face) {
        return state.isSideSolidFullSquare(world, pos, face);
    }

    private boolean canRemain(IWorldReader world, BlockPos pos, Direction facing) {
        BlockPos adjPos = pos.offset(facing, -1);
        BlockState adjState = world.getBlockState(adjPos);
        return isValidBlockSide(adjState, world, adjPos, facing);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();

        Direction mainDir = ctx.getFace();
        if (mainDir != Direction.DOWN) {
            if (canRemain(world, pos, mainDir)) {
                return getDefaultState().with(FACING, mainDir);
            }
        }

        for (Direction dir : ctx.getNearestLookingDirections()) {
            if (dir == Direction.DOWN) continue;
            if (canRemain(world, pos, dir)) {
                return getDefaultState().with(FACING, dir);
            }
        }
        return null; // Cancel placement, return null
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(FACING).ordinal()];
    }

    /**
     * @deprecated Not effective as hitbox is chosen per state and currently not changeable
     */
    @Override
    @Deprecated
    public MnPlantBlock hitbox(VoxelShape shape) {
        throw new UnsupportedOperationException("hitbox disabled because of dynamic hitbox");
    }

    /**
     * @deprecated Not effective as hitbox is chosen per state and currently not changeable
     */
    @Override
    @Deprecated
    public MnPlantBlock hitbox(double size, double height) {
        throw new UnsupportedOperationException("hitbox disabled because of dynamic hitbox");
    }

    /**
     * @deprecated Not effective as offsetting shelves and fans looks pretty weird and should not be done in general
     */
    @Override
    @Deprecated
    public MnPlantBlock offset(OffsetType offsetType) {
        throw new UnsupportedOperationException("offset disabled because shelves shouldn't offset");
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
