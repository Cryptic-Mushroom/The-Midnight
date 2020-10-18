/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.core.biome;

import midnight.api.biome.IMidnightBiome;

public interface IMidnightBiomeBase extends IMidnightBiome {
    void setColoring(BiomeColoring coloring);
    void setTerrainFactors(TerrainFactors factors);
}
