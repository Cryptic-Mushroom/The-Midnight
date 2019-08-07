package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MidnightFungiHatBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public MidnightFungiHatBlock(MaterialColor materialColor) {
        super(Block.Properties.create(Material.WOOD, materialColor).sound(SoundType.WOOD).hardnessAndResistance(0.5f, 0f));
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
        );
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItem() == Items.SHEARS) {
            if (!world.isRemote) {
                BooleanProperty faceProperty = getFaceProperty(hitResult.getFace());
                world.setBlockState(pos, state.with(faceProperty, true), 11);

                world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
            }

            return true;
        }

        return super.onBlockActivated(state, world, pos, player, hand, hitResult);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, world, pos, oldState, isMoving);

        if (state.get(UP)) placeFungiInside(world, pos, Direction.UP);
        if (state.get(DOWN)) placeFungiInside(world, pos, Direction.DOWN);
        if (state.get(NORTH)) placeFungiInside(world, pos, Direction.NORTH);
        if (state.get(SOUTH)) placeFungiInside(world, pos, Direction.SOUTH);
        if (state.get(EAST)) placeFungiInside(world, pos, Direction.EAST);
        if (state.get(WEST)) placeFungiInside(world, pos, Direction.WEST);
    }

    private static void placeFungiInside(World world, BlockPos pos, Direction direction) {
        BlockPos insidePos = pos.offset(direction);
        if (world.isAirBlock(insidePos)) {
            world.setBlockState(insidePos, MidnightBlocks.FUNGI_INSIDE.getDefaultState());
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    //getAmbientOcclusionLightValue
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.8f;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }

    private static BooleanProperty getFaceProperty(Direction direction) {
        switch (direction) {
            case DOWN:
                return DOWN;
            case UP:
                return UP;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
            default:
                throw new Error();
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        boolean north = state.get(NORTH);
        boolean south = state.get(SOUTH);
        boolean east = state.get(EAST);
        boolean west = state.get(WEST);

        switch (rotation) {
            case CLOCKWISE_90:
                return state.with(NORTH, west).with(SOUTH, east).with(EAST, north).with(WEST, south);
            case CLOCKWISE_180:
                return state.with(NORTH, south).with(SOUTH, north).with(EAST, west).with(WEST, east);
            case COUNTERCLOCKWISE_90:
                return state.with(NORTH, east).with(SOUTH, west).with(EAST, south).with(WEST, north);
        }

        return state;
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        boolean north = state.get(NORTH);
        boolean south = state.get(SOUTH);
        boolean east = state.get(EAST);
        boolean west = state.get(WEST);

        switch (mirror) {
            case LEFT_RIGHT:
                return state.with(NORTH, south).with(SOUTH, north);
            case FRONT_BACK:
                return state.with(EAST, west).with(WEST, east);
        }

        return state;
    }
}
