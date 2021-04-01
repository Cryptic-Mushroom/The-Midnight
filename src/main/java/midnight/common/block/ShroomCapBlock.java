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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

@SuppressWarnings("deprecation")
public class ShroomCapBlock extends Block {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    private final int sporeColor;

    public ShroomCapBlock(Properties props, int sporeColor) {
        super(props);
        this.sporeColor = sporeColor;

        registerDefaultState(
            getStateDefinition().any()
                               .setValue(UP, true)
                               .setValue(DOWN, true)
                               .setValue(NORTH, true)
                               .setValue(EAST, true)
                               .setValue(SOUTH, true)
                               .setValue(WEST, true)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        state = super.updateShape(state, dir, adjState, world, pos, adjPos);
        if (adjState.is(this) || adjState.isFaceSturdy(world, adjPos, dir.getOpposite())) {
            state = state.setValue(getProp(dir), false);
        }
        if (adjState.isAir(world, adjPos) && !(adjState.getBlock() instanceof ShroomAirBlock) && !state.getValue(getProp(dir))) {
            placeShroomAir(world, adjPos);
        }
        return state;
    }

    private static void placeShroomAir(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir(world, pos) && !(state.getBlock() instanceof ShroomAirBlock)) {
            world.setBlock(pos, MnBlocks.SHROOM_AIR.defaultBlockState(), 3);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rtr) {
        ItemStack usedItem = player.getItemInHand(hand);
        if (usedItem.getItem() == Items.SHEARS) {
            Direction dir = rtr.getDirection();
            BooleanProperty faceProp = getProp(dir);

            if (state.getValue(faceProp)) {
                world.setBlockAndUpdate(pos, state.setValue(faceProp, false));
                placeShroomAir(world, pos.relative(dir));
                usedItem.hurtAndBreak(1, player, p -> {});
                world.playSound(null, pos, SoundEvents.WART_BLOCK_HIT, SoundCategory.BLOCKS, 1, 1);

                return ActionResultType.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, rtr);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = defaultBlockState();
        for (Direction dir : Direction.values()) {
            BlockPos adjPos = pos.relative(dir);
            BlockState adjState = world.getBlockState(adjPos);
            if (adjState.is(this) || adjState.isFaceSturdy(world, adjPos, dir.getOpposite())) {
                state = state.setValue(getProp(dir), false);
            }
        }
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rot) {
        boolean n = state.getValue(NORTH);
        boolean e = state.getValue(EAST);
        boolean s = state.getValue(SOUTH);
        boolean w = state.getValue(WEST);
        switch (rot) {
            case NONE: return state;
            case CLOCKWISE_90: state.setValue(EAST, n).setValue(SOUTH, e).setValue(WEST, s).setValue(NORTH, w);
            case CLOCKWISE_180: state.setValue(EAST, w).setValue(SOUTH, n).setValue(WEST, e).setValue(NORTH, s);
            case COUNTERCLOCKWISE_90: state.setValue(EAST, s).setValue(SOUTH, w).setValue(WEST, n).setValue(NORTH, e);
        }
        throw new IllegalStateException("Universe broke again");
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        boolean n = state.getValue(NORTH);
        boolean e = state.getValue(EAST);
        boolean s = state.getValue(SOUTH);
        boolean w = state.getValue(WEST);
        switch (mirror) {
            case NONE: return state;
            case FRONT_BACK: return state.setValue(NORTH, s).setValue(SOUTH, n);
            case LEFT_RIGHT: return state.setValue(EAST, w).setValue(WEST, e);
        }
        throw new IllegalStateException("Universe broke once again");
    }

    public int getSporeColor() {
        return sporeColor;
    }

    public static BooleanProperty getProp(Direction dir) {
        switch (dir) {
            case UP: return UP;
            case DOWN: return DOWN;
            case NORTH: return NORTH;
            case EAST: return EAST;
            case SOUTH: return SOUTH;
            case WEST: return WEST;
        }
        throw new IllegalStateException("Universe broke");
    }
}
