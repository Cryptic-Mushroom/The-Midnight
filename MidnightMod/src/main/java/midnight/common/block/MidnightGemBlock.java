package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class MidnightGemBlock extends Block {
    public MidnightGemBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silkTouch) {
        if (silkTouch != 0) return 0;
        return MathHelper.nextInt(this.RANDOM, 3, 7);
    }
}
