/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.Midnight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class HangingLeavesGrowingBlock extends LeavesBlock implements IGrowable {
    private final Supplier<Block> hangingLeaves;

    public HangingLeavesGrowingBlock(Properties properties, Supplier<Block> hangingLeaves) {
        super(properties);
        this.hangingLeaves = hangingLeaves;
    }


    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean client) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rng, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rng, BlockPos pos, BlockState state) {
        try {
            HangingLeavesBlock hangLeaves = (HangingLeavesBlock) hangingLeaves.get();
            BlockPos.Mutable mpos = new BlockPos.Mutable();
            for (int i = 0; i < 40; i++) {
                // Gaussian distribution, place more blocks near the selected block
                int randx = rng.nextInt(2) - rng.nextInt(2) + rng.nextInt(2) - rng.nextInt(2);
                int randy = rng.nextInt(2) - rng.nextInt(2) + rng.nextInt(2) - rng.nextInt(2);
                int randz = rng.nextInt(2) - rng.nextInt(2) + rng.nextInt(2) - rng.nextInt(2);

                mpos.set(pos).move(randx, randy, randz);
                BlockState nearState = world.getBlockState(mpos);

                // Only grow on other leaves, even though placing on log blocks is possible
                if (!nearState.is(this) && !nearState.is(hangLeaves)) continue;

                mpos.move(Direction.DOWN);
                BlockState belowState = world.getBlockState(mpos);

                if (!belowState.getBlock().isAir(belowState, world, mpos)) continue;
                if (!hangLeaves.canSurvive(belowState, world, mpos)) continue;

                boolean root = !nearState.is(hangLeaves);
                if (!root) {
                    // Make sure blockstates match when placing on other hanging leaves
                    mpos.move(Direction.UP);
                    world.setBlockAndUpdate(mpos, nearState.setValue(HangingLeavesBlock.END, false));
                    mpos.move(Direction.DOWN);
                }

                world.setBlockAndUpdate(mpos, hangLeaves.defaultBlockState().setValue(HangingLeavesBlock.ROOT, root).setValue(HangingLeavesBlock.END, true));
            }
        } catch (ClassCastException exc) {
            // This isn't a good reason to crash, an error in the console is enough
            Midnight.LOGGER.error("Cannot grow hanging leaves as they are not HangingLeavesBlock", exc);
        }
    }
}
