/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.levelgen;

import com.google.common.reflect.Reflection;
import midnight.common.world.biome.TheMidnightBiomeProvider;
import midnight.common.world.levelgen.midnight.TheMidnightChunkGenerator;

public final class MnLevelgen {
    private MnLevelgen() {
    }

    public static void init() {
        Reflection.initialize(TheMidnightChunkGenerator.class);
        Reflection.initialize(TheMidnightBiomeProvider.class);
    }
}
