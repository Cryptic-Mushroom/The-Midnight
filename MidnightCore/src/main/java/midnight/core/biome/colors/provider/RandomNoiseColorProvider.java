/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.IColorProvider;
import midnight.core.util.MnMath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public abstract class RandomNoiseColorProvider extends NoiseColorProvider {
    private final IColorProvider[] colors;

    protected RandomNoiseColorProvider(IColorProvider[] colors) {
        this.colors = colors;
    }

    @Override
    protected int computeColor(IWorld world, BlockPos pos, double noise) {
        int idx = Math.min(
            MathHelper.floor(
                MnMath.relerp(
                    -1, 1,
                    0, colors.length,
                    noise
                )
            ),
            colors.length - 1
        );
        return colors[idx].getColor(world, pos);
    }

    @Override
    public void initForSeed(long seed) {
        super.initForSeed(seed);
        for (IColorProvider provider : colors) {
            provider.initForSeed(seed);
        }
    }
}
