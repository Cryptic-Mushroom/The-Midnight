package com.mushroom.midnight.common.block;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BladeshroomBlock extends MidnightPlantBlock implements IGrowable {
    public static final EnumProperty<Stage> STAGE = EnumProperty.create("stage", Stage.class);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final int REGROW_CHANCE = 10;

    private static final ImmutableList<VoxelShape> STEM_BOUNDS = new ImmutableList.Builder<VoxelShape>()
            .add(makeCuboidShape(4d, 0d, 4d, 12d, 8d, 12d)) // DOWN
            .add(makeCuboidShape(4d, 8d, 4d, 12d, 16d, 12d)) // UP
            .add(makeCuboidShape(4d, 4d, 0d, 12d, 12d, 8d)) // NORTH
            .add(makeCuboidShape(4d, 4d, 8d, 12d, 12d, 16d)) // SOUTH
            .add(makeCuboidShape(0d, 4d, 4d, 8d, 12d, 12d)) // WEST
            .add(makeCuboidShape(8d, 4d, 4d, 16d, 12d, 12d)) // EAST
            .build();

    private static final ImmutableList<VoxelShape> BOUNDS = new ImmutableList.Builder<VoxelShape>()
            .add(makeCuboidShape(1d, 0d, 1d, 15d, 8d, 15d)) // DOWN
            .add(makeCuboidShape(1d, 8d, 1d, 15d, 16d, 15d)) // UP
            .add(makeCuboidShape(1d, 1d, 0d, 15d, 15d, 8d)) // NORTH
            .add(makeCuboidShape(1d, 1d, 8d, 15d, 15d, 16d)) // SOUTH
            .add(makeCuboidShape(0d, 1d, 1d, 8d, 15d, 15d)) // WEST
            .add(makeCuboidShape(8d, 1d, 1d, 16d, 15d, 15d)) // EAST
            .build();

    private static final DamageSource BLADESHROOM_DAMAGE = new MidnightDamageSource("bladeshroom").setDamageBypassesArmor().setDamageIsAbsolute();

    public BladeshroomBlock(Properties properties) {
        super(properties, false);
        setDefaultState(this.stateContainer.getBaseState().with(STAGE, Stage.SPORE).with(FACING, Direction.DOWN));
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return state.get(STAGE) == Stage.CAPPED ? PathNodeType.DAMAGE_CACTUS : super.getAiPathNodeType(state, world, pos, entity);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos attachedPos = pos.offset(facing);
        return Block.hasSolidSide(world.getBlockState(attachedPos), world, attachedPos, facing);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int facingIndex = state.get(FACING).getIndex();
        return state.get(STAGE) == Stage.CAPPED ? BOUNDS.get(facingIndex) : STEM_BOUNDS.get(facingIndex);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getFace().getOpposite());
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(FACING).getOpposite() == facing && !state.isValidPosition(world, currentPos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (state.get(STAGE) == Stage.CAPPED) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(MidnightItems.BLADESHROOM_CAP));
            world.setBlockState(pos, state.with(STAGE, Stage.STEM));
            if (MidnightConfig.general.bladeshroomDamageChance.get() != 0 && world.rand.nextInt(100) < MidnightConfig.general.bladeshroomDamageChance.get()) {
                player.attackEntityFrom(BLADESHROOM_DAMAGE, 1.0F);
            }
            return true;
        }

        return false;
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.NONE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(STAGE) == Stage.CAPPED) {
            entity.attackEntityFrom(BLADESHROOM_DAMAGE, 1.0F);
        }
    }

    @Override
    public void randomTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(REGROW_CHANCE) == 0) {
            this.incrementStage(world, pos, state);
        }
    }

    private void incrementStage(World world, BlockPos pos, BlockState state) {
        Stage nextStage = state.get(STAGE).next();
        if (nextStage == null) {
            return;
        }
        world.setBlockState(pos, state.with(STAGE, nextStage));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE, FACING);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(STAGE) != Stage.CAPPED;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        this.incrementStage(world, pos, state);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(MidnightItems.BLADESHROOM_SPORES);
    }

    public enum Stage implements IStringSerializable {
        SPORE,
        STEM,
        CAPPED;

        @Nullable
        public Stage next() {
            Stage[] stages = values();
            int ordinal = this.ordinal();
            if (ordinal < stages.length - 1) {
                return stages[ordinal + 1];
            }
            return null;
        }

        public static Stage deserialize(int meta) {
            Stage[] stages = values();
            return stages[meta % stages.length];
        }

        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
