package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import midnight.common.registry.MidnightTags;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FungiBlock extends MidnightPlantBlock {
    public FungiBlock(Properties properties, boolean glowing, @Nullable Supplier<Block> growSupplier) {
        super(properties, glowing, growSupplier);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isNormalCube(world, pos) && !state.isIn(MidnightTags.Blocks.FUNGI_HATS);
    }
}
