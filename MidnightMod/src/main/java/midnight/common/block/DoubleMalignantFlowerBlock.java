package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class DoubleMalignantFlowerBlock extends MidnightDoublePlantBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.VERTICAL);

    public DoubleMalignantFlowerBlock(Properties properties) {
        super(properties, true);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(FACING, Direction.UP));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Block.isOpaque(state.getCollisionShape(world, pos));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    @Override
    protected Direction getDownDirection(BlockState state) {
        return state.get(FACING) == Direction.UP ? Direction.DOWN : Direction.UP;
    }

    @Override
    protected BlockState getBaseStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getFace();
        if (facing.getAxis() != Direction.Axis.Y) return null;

        return super.getBaseStateForPlacement(context).with(FACING, facing);
    }

    public static void placeAt(IWorld world, BlockState state, BlockPos lowerPos, int flags) {
        DoubleMalignantFlowerBlock block = (DoubleMalignantFlowerBlock) state.getBlock();

        BlockState lowerState = state.with(HALF, DoubleBlockHalf.LOWER);
        BlockState upperState = state.with(HALF, DoubleBlockHalf.UPPER);

        BlockPos upperPos = block.getAlternatePos(lowerPos, lowerState);

        world.setBlockState(lowerPos, lowerState, flags);
        world.setBlockState(upperPos, upperState, flags);
    }
}
