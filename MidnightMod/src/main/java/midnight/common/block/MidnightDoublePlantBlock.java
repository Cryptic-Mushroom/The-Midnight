package midnight.common.block;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class MidnightDoublePlantBlock extends MidnightPlantBlock {
    protected static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public MidnightDoublePlantBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER));
    }

    public MidnightDoublePlantBlock(Properties properties, boolean glowing) {
        super(properties, glowing);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState updatePostPlacement(BlockState currentState, Direction updatedDirection, BlockState updatedState, IWorld world, BlockPos currentPos, BlockPos updatedPos) {
        if (updatedDirection.getAxis() == Direction.Axis.Y) {
            DoubleBlockHalf half = currentState.get(HALF);
            Direction alternateDirection = this.getAlternateDirection(currentState);

            if (alternateDirection == updatedDirection && (updatedState.getBlock() != this || updatedState.get(HALF) == half)) {
                return Blocks.AIR.getDefaultState();
            }
        }

        return super.updatePostPlacement(currentState, updatedDirection, updatedState, world, currentPos, updatedPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        BlockState state = this.getBaseStateForPlacement(context);
        if (state == null) return null;

        World world = context.getWorld();
        BlockPos alternatePos = this.getAlternatePos(pos, state);
        if (alternatePos.getY() >= world.getHeight() || !world.getBlockState(alternatePos).isReplaceable(context)) {
            return null;
        }

        return state;
    }

    @Nullable
    protected BlockState getBaseStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos alternatePos = this.getAlternatePos(pos, state);
        DoubleBlockHalf alternateHalf = this.getAlternateHalf(state.get(HALF));

        world.setBlockState(alternatePos, state.with(HALF, alternateHalf), 3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return this.isValidBasePosition(state, world, pos);
        }

        BlockPos alternatePos = this.getAlternatePos(pos, state);
        BlockState alternateState = world.getBlockState(alternatePos);
        if (state.getBlock() != this) {
            return this.isValidBasePosition(state, world, pos);
        }

        return alternateState.getBlock() == this && alternateState.get(HALF) == DoubleBlockHalf.LOWER;
    }

    private boolean isValidBasePosition(BlockState state, IWorldReader world, BlockPos pos) {
        Direction down = this.getDownDirection(state);
        BlockPos attachedPos = pos.offset(down);
        return this.isValidGround(world.getBlockState(attachedPos), world, attachedPos);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos alternatePos = this.getAlternatePos(pos, state);
        BlockState alternateState = world.getBlockState(alternatePos);
        if (alternateState.getBlock() == this) {
            world.setBlockState(alternatePos, Blocks.AIR.getDefaultState(), 35);
            world.playEvent(player, Constants.WorldEvents.BREAK_BLOCK_EFFECTS, alternatePos, Block.getStateId(alternateState));
            if (!world.isRemote && !player.isCreative()) {
                spawnDrops(state, world, pos, null, player, player.getHeldItemMainhand());
                spawnDrops(alternateState, world, alternatePos, null, player, player.getHeldItemMainhand());
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public long getPositionRandom(BlockState state, BlockPos pos) {
        return MathHelper.getCoordinateRandom(
                pos.getX(),
                state.get(HALF) == DoubleBlockHalf.LOWER ? pos.getY() : this.getAlternatePos(pos, state).getY(),
                pos.getZ()
        );
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    protected BlockPos getAlternatePos(BlockPos pos, BlockState state) {
        return pos.offset(this.getAlternateDirection(state));
    }

    protected DoubleBlockHalf getAlternateHalf(DoubleBlockHalf half) {
        return half == DoubleBlockHalf.LOWER ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER;
    }

    protected Direction getAlternateDirection(BlockState state) {
        Direction down = this.getDownDirection(state);
        return state.get(HALF) == DoubleBlockHalf.LOWER ? down.getOpposite() : down;
    }

    protected Direction getDownDirection(BlockState state) {
        return Direction.DOWN;
    }

    public static void placeAt(IWorld world, BlockPos pos, BlockState state, int flags) {
        BlockState lowerState = state.with(HALF, DoubleBlockHalf.LOWER);
        BlockState upperState = state.with(HALF, DoubleBlockHalf.UPPER);

        world.setBlockState(pos, lowerState, flags);
        world.setBlockState(pos.up(), upperState, flags);
    }
}
