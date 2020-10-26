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
import net.shadew.ptg.noise.perlin.FractalPerlin2D;
import net.shadew.ptg.noise.perlin.Perlin2D;

public class Perlin2DColorProvider extends InterpolateNoiseColorProvider {
    private final int octaves;
    private final double scaleX;
    private final double scaleZ;

    public Perlin2DColorProvider(IColorProvider providerA, IColorProvider providerB, int octaves, double scaleX, double scaleZ) {
        super(providerA, providerB);
        this.octaves = octaves;
        this.scaleX = scaleX;
        this.scaleZ = scaleZ;
    }

    @Override
    protected Noise3D createNoise(long seed) {
        int iSeed = (int) seed;
        if (octaves <= 1) {
            return Noise3D.from2DY(new Perlin2D(iSeed, scaleX, scaleZ));
        } else {
            return Noise3D.from2DY(new FractalPerlin2D(iSeed, scaleX, scaleZ, octaves));
        }
    }
}
