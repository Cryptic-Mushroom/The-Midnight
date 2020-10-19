/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.block;

import midnight.common.misc.MnParticleTypes;
import midnight.core.util.ColorUtil;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
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
        // Add little spories for nearby shroom caps
        ArrayList<Integer> colors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            BlockState adjState = world.getBlockState(pos.offset(dir));
            Block adj = adjState.getBlock();
            if (adj instanceof ShroomCapBlock && !adjState.get(ShroomCapBlock.getProp(dir.getOpposite()))) {
                colors.add(((ShroomCapBlock) adj).getSporeColor());
            }
        }

        if (!colors.isEmpty()) {
            if (rng.nextInt(39) < 4) {
                int count = rng.nextInt(2) + 1;
                for (int i = 0; i < count; i++) {
                    int col = colors.get(rng.nextInt(colors.size()));

                    double r = ColorUtil.redd(col);
                    double g = ColorUtil.greend(col);
                    double b = ColorUtil.blued(col);

                    double x = pos.getX() + 0.5 + (rng.nextDouble() - rng.nextDouble() + rng.nextDouble() - rng.nextDouble()) / 2;
                    double y = pos.getY() + 0.5 + (rng.nextDouble() - rng.nextDouble() + rng.nextDouble() - rng.nextDouble()) / 2;
                    double z = pos.getZ() + 0.5 + (rng.nextDouble() - rng.nextDouble() + rng.nextDouble() - rng.nextDouble()) / 2;

                    world.addParticle(MnParticleTypes.SHROOM_SPORE, x, y, z, r, g, b);
                }
            }
        }
    }
}
