/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome;

import midnight.api.biome.IMidnightBiome;
import net.minecraft.world.level.ColorResolver;

public final class MnBiomeColors {
    public static final ColorResolver NIGHT_GRASS = (biome, x, z) -> IMidnightBiome.get(biome).getMidnightGrassColor(x, z);
    public static final ColorResolver DARK_WATER = (biome, x, z) -> IMidnightBiome.get(biome).getMidnightWaterColor(x, z);
    public static final ColorResolver SHADOWROOT = (biome, x, z) -> IMidnightBiome.get(biome).getShadowrootColor(x, z);

    private MnBiomeColors() {
    }
}
