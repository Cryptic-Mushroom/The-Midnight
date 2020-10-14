package midnight.common.block;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * The block that represents air near the inside of fungi caps. This air emits light and spore particles to give better
 * ambience to giant shrooms. This block emits light rather than the cap itself, since the cap might not need light from
 * all sides. This air also emits spores of the nearby shrooms.
 *
 * @author Shadew
 * @since 0.6.0
 */
@SuppressWarnings("deprecation")
public class ShroomAirBlock extends AirBlock {

    public ShroomAirBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        state = super.updatePostPlacement(state, dir, adjState, world, pos, adjPos);

        for (Direction direction : Direction.values()) {
            BlockState side = world.getBlockState(pos.offset(direction));
            boolean shouldBeShroomAir = side.getBlock() instanceof ShroomCapBlock && !side.get(ShroomCapBlock.getProp(direction.getOpposite()));
            if (shouldBeShroomAir) return state;
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        return Blocks.AIR.getDefaultState();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rng) {
        // TODO Spore particles go here
    }
}
