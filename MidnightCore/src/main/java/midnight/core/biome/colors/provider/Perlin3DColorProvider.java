/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;


import midnight.core.biome.colors.IColorProvider;

import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.perlin.FractalPerlin3D;
import net.shadew.ptg.noise.perlin.Perlin3D;

public class Perlin3DColorProvider extends InterpolateNoiseColorProvider {
    private final int octaves;
    private final double scaleX;
    private final double scaleY;
    private final double scaleZ;

    public Perlin3DColorProvider(IColorProvider providerA, IColorProvider providerB, int octaves, double scaleX, double scaleY, double scaleZ) {
        super(providerA, providerB);
        this.octaves = octaves;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    @Override
    protected Noise3D createNoise(long seed) {
        int iSeed = (int) seed;
        if (octaves <= 1) {
            return new Perlin3D(iSeed, scaleX, scaleY, scaleZ);
        } else {
            return new FractalPerlin3D(iSeed, scaleX, scaleY, scaleZ, octaves);
        }
    }
}
