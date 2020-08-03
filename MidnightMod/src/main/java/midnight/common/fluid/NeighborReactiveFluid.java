package midnight.common.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface NeighborReactiveFluid {
    boolean reactWithNeighbors(World world, BlockPos pos, BlockState state);
}
