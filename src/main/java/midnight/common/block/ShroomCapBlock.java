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

        setDefaultState(
            getStateContainer().getBaseState()
                               .with(UP, true)
                               .with(DOWN, true)
                               .with(NORTH, true)
                               .with(EAST, true)
                               .with(SOUTH, true)
                               .with(WEST, true)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        state = super.updatePostPlacement(state, dir, adjState, world, pos, adjPos);
        if (adjState.isIn(this) || adjState.isSideSolidFullSquare(world, adjPos, dir.getOpposite())) {
            state = state.with(getProp(dir), false);
        }
        if (adjState.isAir(world, adjPos) && !(adjState.getBlock() instanceof ShroomAirBlock) && !state.get(getProp(dir))) {
            placeShroomAir(world, adjPos);
        }
        return state;
    }

    private static void placeShroomAir(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir(world, pos) && !(state.getBlock() instanceof ShroomAirBlock)) {
            world.setBlockState(pos, MnBlocks.SHROOM_AIR.getDefaultState(), 3);
        }
    }

    @Override
    public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rtr) {
        ItemStack usedItem = player.getHeldItem(hand);
        if (usedItem.getItem() == Items.SHEARS) {
            Direction dir = rtr.getFace();
            BooleanProperty faceProp = getProp(dir);

            if (state.get(faceProp)) {
                world.setBlockState(pos, state.with(faceProp, false));
                placeShroomAir(world, pos.offset(dir));
                usedItem.damageItem(1, player, p -> {});
                world.playSound(null, pos, SoundEvents.BLOCK_WART_BLOCK_HIT, SoundCategory.BLOCKS, 1, 1);

                return ActionResultType.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, rtr);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        BlockState state = getDefaultState();
        for (Direction dir : Direction.values()) {
            BlockPos adjPos = pos.offset(dir);
            BlockState adjState = world.getBlockState(adjPos);
            if (adjState.isIn(this) || adjState.isSideSolidFullSquare(world, adjPos, dir.getOpposite())) {
                state = state.with(getProp(dir), false);
            }
        }
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rot) {
        boolean n = state.get(NORTH);
        boolean e = state.get(EAST);
        boolean s = state.get(SOUTH);
        boolean w = state.get(WEST);
        switch (rot) {
            case NONE: return state;
            case CLOCKWISE_90: state.with(EAST, n).with(SOUTH, e).with(WEST, s).with(NORTH, w);
            case CLOCKWISE_180: state.with(EAST, w).with(SOUTH, n).with(WEST, e).with(NORTH, s);
            case COUNTERCLOCKWISE_90: state.with(EAST, s).with(SOUTH, w).with(WEST, n).with(NORTH, e);
        }
        throw new IllegalStateException("Universe broke again");
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        boolean n = state.get(NORTH);
        boolean e = state.get(EAST);
        boolean s = state.get(SOUTH);
        boolean w = state.get(WEST);
        switch (mirror) {
            case NONE: return state;
            case FRONT_BACK: return state.with(NORTH, s).with(SOUTH, n);
            case LEFT_RIGHT: return state.with(EAST, w).with(WEST, e);
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
