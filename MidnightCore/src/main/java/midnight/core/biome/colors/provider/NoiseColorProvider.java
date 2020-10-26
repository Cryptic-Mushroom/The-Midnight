/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.IColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import net.shadew.ptg.noise.Noise3D;

public abstract class NoiseColorProvider implements IColorProvider {
    private Noise3D noise;

    protected abstract Noise3D createNoise(long seed);

    protected abstract int computeColor(IWorld world, BlockPos pos, double noise);

    @Override
    public void initForSeed(long seed) {
        noise = createNoise(seed);
    }

    @Override
    public final int getColor(IWorld world, BlockPos pos) {
        return computeColor(world, pos, noise.generate(pos.getX(), pos.getY(), pos.getZ()));
    }
}
