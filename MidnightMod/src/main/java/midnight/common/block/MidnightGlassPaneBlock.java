package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class MidnightGlassPaneBlock extends PaneBlock {
    public MidnightGlassPaneBlock() {
        super(Properties.create(Material.GLASS).sound(SoundType.GLASS).notSolid());
    }


    private static final float[] BEACON_COLOR = {
            177 / 255F, 143 / 255F, 217 / 255F
    };

    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(BlockState state, IWorldReader world, BlockPos pos, BlockPos beaconPos) {
        return BEACON_COLOR;
    }
}
