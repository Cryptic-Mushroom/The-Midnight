/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class DeadLogFeature extends Feature<DeadLogFeatureConfig> {
    public DeadLogFeature() {
        super(DeadLogFeatureConfig.CODEC);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator gen, Random rand, BlockPos pos, DeadLogFeatureConfig config) {
        int lenMinL = config.getLengthMin();
        int lenMaxL = config.getLengthMax();
        int lenMin = Math.min(lenMinL, lenMaxL); // Fix negative ranges, java.util.Random doesn't like that
        int lenMax = Math.max(lenMinL, lenMaxL);

        BlockState xState = config.getXAxisLog().getState(rand, pos);
        BlockState zState = config.getZAxisLog().getState(rand, pos);

        int len = rand.nextInt(lenMax - lenMin + 1) + lenMin;
        Direction dir = rand.nextBoolean() ? Direction.EAST : Direction.SOUTH;
        BlockPos off = pos.relative(dir, -len / 2); // Offset, so center of log is at 'pos'

        return generate(
            world, off, rand,
            config.getBranchingChance(), config.getBranchLengthMax(), len,
            dir == Direction.EAST ? xState : zState, // The state of the log itself
            dir == Direction.EAST ? zState : xState, // The state of a branching log
            dir
        );
    }

    /**
     * Generates an actual log
     *
     * @param world        The world to generate in
     * @param pos          The position to start at (one end of the log is at this position)
     * @param rand         Random numbers
     * @param branchChance Chance for branching, no branches are generated if 0
     * @param branchLenMax Max branch length
     * @param len          Length of the generated log
     * @param state        The material of the log
     * @param oppositeAxis The material of the log in the opposite axis (X-Z), for branches
     * @param dir          The direction to generate in
     * @return True when something has been generated
     */
    protected boolean generate(ISeedReader world, BlockPos pos, Random rand, int branchChance, int branchLenMax, int len, BlockState state, BlockState oppositeAxis, Direction dir) {
        BlockPos.Mutable mpos = new BlockPos.Mutable().set(pos);

        // Check valid space
        for (int i = 0; i < len; i++) {
            BlockState medium = world.getBlockState(mpos);
            if (!isValidMedium(world, mpos, medium)) {
                return false;
            }
            mpos.move(Direction.DOWN);

            BlockState ground = world.getBlockState(mpos);
            if (!isValidGround(world, mpos, ground)) {
                return false;
            }
            mpos.move(Direction.UP);
            mpos.move(dir);
        }

        mpos.set(pos);

        // Place
        for (int i = 0; i < len; i++) {
            world.setBlock(mpos, state, 2);

            // Generate branches recursively
            if (branchChance > 0 && branchLenMax > 0 && rand.nextInt(branchChance) == 0) {
                // Biased random, shorter lengths are more common
                int branchLen = rand.nextInt(rand.nextInt(branchLenMax + 1) + 1) + 1;
                Direction branchDir = rand.nextBoolean() ? dir.getCounterClockWise() : dir.getClockWise();
                BlockPos branchPos = mpos.immutable().relative(branchDir);
                // Note that we swap state and oppositeAxis, because the branch is rotated the axis it goes in flips
                // We also set branchChance and branchLenMax to 0 since we don't want branches to actually have
                // other branches
                generate(world, branchPos, rand, 0, 0, branchLen, oppositeAxis, state, branchDir);
            }

            mpos.move(dir);
        }

        return true;
    }

    protected boolean isValidGround(ISeedReader world, BlockPos pos, BlockState state) {
        return state.isFaceSturdy(world, pos, Direction.UP);
    }

    protected boolean isValidMedium(ISeedReader world, BlockPos pos, BlockState state) {
        return state.getMaterial().isLiquid() || !state.getMaterial().blocksMotion()
                   // Leave block movement but logs usually replace them so special check for 'em
                   || state.is(BlockTags.LEAVES);
    }
}
