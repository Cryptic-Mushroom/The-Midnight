package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HangablePlantBlock extends MidnightPlantBlock {
    private static final VoxelShape BOUNDS = makeCuboidShape(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);

    public HangablePlantBlock(Properties properties) {
        super(properties, false);
    }

    public HangablePlantBlock(Properties properties, boolean glowing) {
        super(properties, glowing);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isNormalCube(world, pos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return isValidGround(world.getBlockState(pos.up()), world, pos.up());
    }
}
