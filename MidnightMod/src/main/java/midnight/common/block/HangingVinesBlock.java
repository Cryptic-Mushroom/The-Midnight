package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HangingVinesBlock extends MidnightPlantBlock {
    private static final VoxelShape SHAPE = makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public HangingVinesBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Block.doesSideFillSquare(state.getCollisionShape(world, pos), Direction.DOWN);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos abovePos = pos.up();
        return this.isValidGround(world.getBlockState(abovePos), world, abovePos);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
