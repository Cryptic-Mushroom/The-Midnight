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

    private VoxelShape highShape = VoxelShapes.block();

    public UpwardPlantBlock(Properties props) {
        super(props);

        registerDefaultState(defaultBlockState().setValue(ROOT, false).setValue(END, false));
    }

    @Override
    public PlantBlock hitbox(double size, double height) {
        double r = size / 2;
        highShape = box(8 - r, 0, 8 - r, 8 + r, 16, 8 + r);
        return super.hitbox(size, height);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROOT, END);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (!canRemain(state, world, pos)) {
            world.getBlockTicks().scheduleTick(pos, this, 1);
        }

        return updateRootEnd(state, world, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return updateRootEnd(defaultBlockState(), ctx.getLevel(), ctx.getClickedPos());
    }

    protected BlockState updateRootEnd(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos down = pos.below();
        BlockState stateDown = world.getBlockState(down);
        boolean root = !stateDown.is(this) && !stateDown.isAir(world, down);
        boolean end = !world.getBlockState(pos.above()).is(this);

        return state.setValue(ROOT, root).setValue(END, end);
    }

    private boolean canRemain(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos down = pos.below();
        BlockState stateDown = world.getBlockState(down);
        if (stateDown.is(this))
            return true;
        if (state.getBlock() == this)
            return stateDown.canSustainPlant(world, down, Direction.UP, this);
        return mayPlaceOn(stateDown, world, down);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        return canRemain(state, world, pos);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        if (!canRemain(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
        super.tick(state, world, pos, rng);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.getValue(END) ? super.getShape(state, world, pos, context) : highShape;
    }
}
