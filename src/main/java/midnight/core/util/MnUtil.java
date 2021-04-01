/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.util;

import midnight.MnInfo;
import midnight.api.util.INightGrassColorModifying;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * General utility class for the Midnight.
 *
 * @author Shadew
 * @since 0.6.0
 */
public final class MnUtil {
    private MnUtil() {
    }

    /**
     * Returns null and deliberately violates its contract of returning a non-null value. Used for fields that get
     * injected by ASM.
     */
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T injected() {
        return null;
    }

    /**
     * Gets an instance specifically for data generation, or another instance if we're not running the data generator.
     */
    public static <T> T callForDatagen(Supplier<Callable<T>> datagen, Supplier<Callable<T>> production) {
        try {
            if (MnInfo.DATAGEN) {
                return datagen.get().call();
            } else {
                return production.get().call();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Modifies the given base night grass color to adapt to nearby color modifiers (see {@link
     * INightGrassColorModifying}). Takes {@link Minecraft#level} to sample blocks.
     *
     * @param base The base color computed by the color provider
     * @param pos  The location to search nearby color modifiers at
     * @return The modified color
     */
    @OnlyIn(Dist.CLIENT)
    public static int modifyGrassColor(int base, BlockPos pos) {
        World world = Minecraft.getInstance().level;
        if (world == null)
            return base;

        int minDistSq = 100;
        int nearestCol = base;

        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int dx = -2; dx < 2; dx++) {
            for (int dy = -2; dy < 2; dy++) {
                for (int dz = -2; dz < 2; dz++) {

                    mpos.set(pos).move(dx, dy, dz);

                    if (mpos.equals(pos))
                        continue;

                    BlockState state = world.getBlockState(mpos);
                    Block block = state.getBlock();

                    if (block instanceof INightGrassColorModifying) {

                        // Squared distance
                        int distSq = dx * dx + dy * dy + dz * dz;

                        if (distSq < minDistSq) {
                            minDistSq = distSq;

                            INightGrassColorModifying mod = (INightGrassColorModifying) block;
                            nearestCol = mod.getColorModifier(world, mpos.immutable(), state);
                        }
                    }
                }
            }
        }

        if (nearestCol == base)
            return base; // Skip unnecessary sqrt operation if it doesn't make sense

        // Compute linear distance (we got the squared distance currently)
        double minDist = Math.sqrt(minDistSq);

        return ColorUtil.interpolate(nearestCol, base, MnMath.clamp(0, 1, minDist / 2));
    }
}
