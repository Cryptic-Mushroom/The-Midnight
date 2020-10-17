package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ShroomRootsBlock extends UpsideDownPlantBlock {
    protected ShroomRootsBlock(Properties props) {
        super(props);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isSideSolidFullSquare(world, pos, Direction.DOWN);
    }
}
