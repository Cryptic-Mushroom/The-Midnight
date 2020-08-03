package midnight.common.block;

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
import net.minecraft.util.*;
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
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItem() == Items.SHEARS) {
            if (!world.isRemote) {
                BooleanProperty faceProperty = getFaceProperty(hitResult.getFace());
                if (!state.get(faceProperty)) {
                    world.setBlockState(pos, state.with(faceProperty, true), 11);

                    world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
                }
            }

            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, world, pos, player, hand, hitResult);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, world, pos, oldState, isMoving);
        placeFungiInsides(world, state, pos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        placeFungiInsides(world, state, pos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.8f;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
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

    private static void placeFungiInsides(World world, BlockState state, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BooleanProperty property = getFaceProperty(direction);
            if (state.get(property)) placeFungiInside(world, pos, direction);
        }
    }

    private static void placeFungiInside(World world, BlockPos pos, Direction direction) {
        BlockPos insidePos = pos.offset(direction);
        if (world.isAirBlock(insidePos)) {
            world.setBlockState(insidePos, MnBlocks.FUNGI_INSIDE.getDefaultState());
        }
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
}
