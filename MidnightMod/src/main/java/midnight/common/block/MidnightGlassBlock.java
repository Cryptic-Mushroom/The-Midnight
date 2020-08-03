package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class MidnightGlassBlock extends GlassBlock {
    public MidnightGlassBlock() {
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.3f, 0f).sound(SoundType.GLASS).notSolid());
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
        return true;
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
