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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class UpwardPlantBlock extends PlantBlock {
    public static final BooleanProperty ROOT = MnBlockStateProperties.ROOT;
    public static final BooleanProperty END = MnBlockStateProperties.END;

    private VoxelShape highShape = VoxelShapes.fullCube();

    public UpwardPlantBlock(Properties props) {
        super(props);

        setDefaultState(getDefaultState().with(ROOT, false).with(END, false));
    }

    @Override
    public PlantBlock hitbox(double size, double height) {
        double r = size / 2;
        highShape = makeCuboidShape(8 - r, 0, 8 - r, 8 + r, 16, 8 + r);
        return super.hitbox(size, height);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROOT, END);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (!canRemain(state, world, pos)) {
            world.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }

        return updateRootEnd(state, world, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return updateRootEnd(getDefaultState(), ctx.getWorld(), ctx.getPos());
    }

    protected BlockState updateRootEnd(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos down = pos.down();
        BlockState stateDown = world.getBlockState(down);
        boolean root = !stateDown.isIn(this) && !stateDown.isAir(world, down);
        boolean end = !world.getBlockState(pos.up()).isIn(this);

        return state.with(ROOT, root).with(END, end);
    }

    private boolean canRemain(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos down = pos.down();
        BlockState stateDown = world.getBlockState(down);
        if (stateDown.isIn(this))
            return true;
        if (state.getBlock() == this)
            return stateDown.canSustainPlant(world, down, Direction.UP, this);
        return isValidGround(stateDown, world, down);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return canRemain(state, world, pos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        if (!canRemain(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
        super.scheduledTick(state, world, pos, rng);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.get(END) ? super.getShape(state, world, pos, context) : highShape;
    }
}
