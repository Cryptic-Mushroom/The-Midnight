/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;


import midnight.core.biome.colors.IColorProvider;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.Noise3D;

public class RandomNoise2DColorProvider extends RandomNoiseColorProvider {
    private final int sizeX;
    private final int sizeZ;

    public RandomNoise2DColorProvider(IColorProvider[] colors, int sizeX, int sizeZ) {
        super(colors);
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
    }

    @Override
    protected Noise3D createNoise(long seed) {
        int iSeed = (int) seed;
        return Noise3D.from2DY(
            Noise2D.random(iSeed).scale(1D / sizeX, 1D / sizeZ)
        );
    }
}
