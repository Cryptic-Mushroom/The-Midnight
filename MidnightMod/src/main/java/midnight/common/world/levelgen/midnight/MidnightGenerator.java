/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.world.levelgen.midnight;

import net.minecraft.world.biome.provider.BiomeProvider;

public abstract class MidnightGenerator {
    protected final long seed;
    protected final BiomeProvider biomeProvider;
    protected final MidnightChunkGenerator chunkGenerator;

    public MidnightGenerator(long seed, BiomeProvider biomeProvider, MidnightChunkGenerator chunkGenerator) {
        this.seed = seed;
        this.biomeProvider = biomeProvider;
        this.chunkGenerator = chunkGenerator;
    }
}
