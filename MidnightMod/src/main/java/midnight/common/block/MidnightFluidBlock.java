package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import midnight.common.fluid.NeighborReactiveFluid;

import java.util.function.Supplier;

public class MidnightFluidBlock extends FlowingFluidBlock {
    private final boolean isBurning;

    public MidnightFluidBlock(Supplier<? extends FlowingFluid> fluid, boolean isBurning, Properties properties) {
        super(fluid, properties);
        this.isBurning = isBurning;
    }

    public boolean reactWithNeighbors(World world, BlockPos pos, BlockState state) {
        FlowingFluid fluid = this.getFluid();
        if (fluid instanceof NeighborReactiveFluid) {
            return ((NeighborReactiveFluid) fluid).reactWithNeighbors(world, pos, state);
        }
        return true;
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return this.isBurning;
    }
}
