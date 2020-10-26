/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.IColorProvider;
import midnight.core.util.ColorUtil;
import midnight.core.util.MnMath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class InterpolateNoiseColorProvider extends NoiseColorProvider {
    private final IColorProvider providerA;
    private final IColorProvider providerB;

    public InterpolateNoiseColorProvider(IColorProvider providerA, IColorProvider providerB) {
        this.providerA = providerA;
        this.providerB = providerB;
    }

    @Override
    protected int computeColor(IWorld world, BlockPos pos, double noise) {
        return ColorUtil.interpolate(
            providerA.getColor(world, pos),
            providerB.getColor(world, pos),
            MnMath.unlerp(-1, 1, noise)
        );
    }

    @Override
    public void initForSeed(long seed) {
        super.initForSeed(seed);
        providerA.initForSeed(seed);
        providerB.initForSeed(seed);
    }
}
