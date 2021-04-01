/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

/**
 * A plant block that can be oriented to grow in a certain direction. All directions are allowed, meaning that the plant
 * is placeable on the floor, a wall or on the ceiling. Other than a direction, the plant has no other orientations.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class DirectionalPlantBlock extends PlantBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private final VoxelShape[] shapes = new VoxelShape[6];

    protected DirectionalPlantBlock(Properties props) {
        super(props);
        hitbox(VoxelShapes.block());

        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction facing = ctx.getClickedFace();
        Direction[] nearestDirs = ctx.getNearestLookingDirections();

        // Find best facing, if any... Tries 'facing' first, and if not possible updates 'facing' to the best possible
        // facing
        findDirection:
        if (!canHang(world, pos, facing)) {
            for (Direction dir : nearestDirs) {
                facing = dir.getOpposite();

                if (canHang(world, pos, facing))
                    break findDirection; // Skip return null
            }

            // There is no possible facing, return null to indicate this is an invalid position
            return null;
        }

        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        Direction facing = state.getValue(FACING);
        if (!canHang(world, pos, facing))
            return Blocks.AIR.defaultBlockState();
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        return true; // Return true - placement check goes in getStateForPlacement
    }

    /**
     * Checks whether the plant is properly sustained at a specific position, assuming it is hanging in the given
     * direction.
     *
     * @param world  The world
     * @param pos    The position of the plant
     * @param facing The direction of the plant
     * @return True if the given position allows the plant to hang in the given direction
     */
    protected boolean canHang(IBlockReader world, BlockPos pos, Direction facing) {
        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = world.getBlockState(supportPos);
        return isValidSupportBlock(supportState, world, supportPos, facing);
    }

    /**
     * Checks whether this plant can hang on the side of the given block.
     *
     * @param state  The block state to hang on
     * @param world  The world
     * @param pos    The position of the block to hang on
     * @param facing The side to hang on
     * @return True if the block's side is sustainable
     */
    protected boolean isValidSupportBlock(BlockState state, IBlockReader world, BlockPos pos, Direction facing) {
        // Mud has no solid side or top - we still want the ability to grow on that though
        return state.isFaceSturdy(world, pos, facing) || state.is(MnBlocks.DECEITFUL_MUD);
    }

    /**
     * @deprecated This should not be used or overridden, support checking goes to {@link #isValidSupportBlock}
     */
    @Override
    @Deprecated
    protected final boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return shapes[state.getValue(FACING).ordinal()];
    }

    /**
     * Set the hitbox of this plant to the given shape. Note that this generates the same shape for every direction the
     * plant can be placed in. This does not rotate the shape! For a dynamically rotated shape, you might want to use
     * {@link #hitbox(double, double)}.
     *
     * @param hitbox The hitbox for this plant
     * @return This instance for method chaining
     */
    @Override
    public DirectionalPlantBlock hitbox(VoxelShape hitbox) {
        for (int i = 0; i < 6; i++) {
            shapes[i] = hitbox;
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Directional are placeable in all six directions and therefore this method generates shapes extruding in every
     * direction. The shape is then chosen based on the {@code facing} property of this block.
     * </p>
     *
     * @param size   The diameter of the base square of the shape
     * @param height The height of the shape
     * @return This instance for method chaining
     */
    @Override
    public DirectionalPlantBlock hitbox(double size, double height) {
        double r = size / 2;
        for (Direction dir : Direction.values()) {
            Direction.Axis axis = dir.getAxis();
            Direction.AxisDirection axdir = dir.getAxisDirection();
            boolean neg = axdir == Direction.AxisDirection.NEGATIVE;

            double c0 = 8 - r;
            double c1 = 8 + r;
            double e0 = neg ? 16 - height : 0;
            double e1 = neg ? 16 : height;

            VoxelShape shape;
            if (axis == Direction.Axis.X) {
                shape = box(e0, c0, c0, e1, c1, c1);
            } else if (axis == Direction.Axis.Y) {
                shape = box(c0, e0, c0, c1, e1, c1);
            } else {
                shape = box(c0, c0, e0, c1, c1, e1);
            }

            shapes[dir.ordinal()] = shape;
        }
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException By definition
     * @deprecated Directional plants do not support offsetting. Using this method will result in an {@link
     *     UnsupportedOperationException}.
     */
    @Override
    @Deprecated
    public PlantBlock offset(OffsetType offsetType) {
        return super.offset(offsetType);
    }
}
