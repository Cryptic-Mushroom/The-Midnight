package midnight.common.block;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import midnight.common.registry.MidnightTags;

public class FungiInsideBlock extends AirBlock {
    public FungiInsideBlock(Properties properties) {
        super(properties.setLightLevel(blockstate -> 14));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (!this.hasHatNeighbor(world, currentPos)) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    private boolean hasHatNeighbor(IWorld world, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.offset(facing)).isIn(MidnightTags.Blocks.FUNGI_HATS)) {
                return true;
            }
        }
        return false;
    }
}
