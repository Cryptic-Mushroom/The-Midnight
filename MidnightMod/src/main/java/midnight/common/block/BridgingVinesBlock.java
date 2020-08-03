package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class BridgingVinesBlock extends MidnightPlantBlock {
    private static final Direction[] ATTACH_DIRECTIONS = new Direction[] {Direction.WEST, Direction.SOUTH, Direction.EAST, Direction.NORTH};

    public BridgingVinesBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Block.isOpaque(state.getCollisionShape(world, pos));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        int count = 0;
        for (Direction direction : ATTACH_DIRECTIONS) {
            BlockPos attachPos = pos.offset(direction);
            if (this.isValidGround(world.getBlockState(attachPos), world, attachPos)) {
                count++;
            }
        }
        return count >= 2;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
