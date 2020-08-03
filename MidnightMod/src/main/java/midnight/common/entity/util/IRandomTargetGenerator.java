package midnight.common.entity.util;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public interface IRandomTargetGenerator {
    @Nullable
    BlockPos generate(@Nullable BlockPos anchor);
}
