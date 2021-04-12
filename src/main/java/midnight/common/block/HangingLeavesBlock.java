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
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class HangingLeavesBlock extends HangingPlantBlock implements IGrowable {
    public static final BooleanProperty END = MnBlockStateProperties.END;
    public static final BooleanProperty ROOT = MnBlockStateProperties.ROOT;

    private final Supplier<Block> leavesBlock;
    private final ITag.INamedTag<Block> logBlockTag;

    public HangingLeavesBlock(Properties properties, Supplier<Block> leavesBlock, ITag.INamedTag<Block> logBlockTag) {
        super(properties);
        this.leavesBlock = leavesBlock;
        this.logBlockTag = logBlockTag;
        registerDefaultState(
            getStateDefinition().any()
                                .setValue(ROOT, true)
                                .setValue(END, false)
        );
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (dir.getAxis() == Direction.Axis.Y)
            state = getAppropriateState(world, pos);
        return super.updateShape(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return getAppropriateState(ctx.getLevel(), ctx.getClickedPos());
    }

    private BlockState getAppropriateState(IWorld world, BlockPos pos) {
        BlockState aboveState = world.getBlockState(pos.above());
        BlockState belowState = world.getBlockState(pos.below());
        return defaultBlockState()
                   .setValue(END, belowState.getBlock() != this)
                   .setValue(ROOT, aboveState.getBlock() != this);
    }

    @Override
    public boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.getBlock() == this) {
            return state.getValue(ROOT);
        }
        return state.is(leavesBlock.get()) || state.is(logBlockTag);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(END, ROOT);
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean client) {
        if (!state.getValue(ROOT) || !state.getValue(END)) return false;
        BlockPos down = pos.below();
        BlockState below = world.getBlockState(down);
        return below.isAir(world, down);
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rng, BlockPos pos, BlockState state) {
        return state.getValue(ROOT) && state.getValue(END);
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rng, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos.below(), state.setValue(ROOT, false).setValue(END, true));
    }
}
