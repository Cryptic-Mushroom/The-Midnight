/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.world.levelgen;

import com.google.common.reflect.Reflection;
import midnight.common.world.biome.MnBiomeProvider;
import midnight.common.world.levelgen.midnight.MnChunkGenerator;

public final class MnLevelgen {
    private MnLevelgen() {
    }

    public static void init() {
        Reflection.initialize(MnChunkGenerator.class);
        Reflection.initialize(MnBiomeProvider.class);
    }
}
