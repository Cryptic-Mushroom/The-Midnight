package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SoilBlock extends GrowableOnBlock {
    public SoilBlock(Block.Properties properties, boolean applyBonemeal) {
        super(properties, applyBonemeal);
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }
}
