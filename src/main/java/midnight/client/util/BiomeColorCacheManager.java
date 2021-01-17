/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.client.util;

import net.minecraft.util.math.ChunkPos;

import java.util.HashSet;
import java.util.Set;

public class BiomeColorCacheManager {
    private final Set<BiomeColorCache> caches = new HashSet<>();

    public BiomeColorCache newCache() {
        BiomeColorCache cache = new BiomeColorCache();
        caches.add(cache);
        return cache;
    }

    public void reset() {
        caches.forEach(BiomeColorCache::reset);
    }

    public void chunkLoad(ChunkPos pos) {
        caches.forEach(cache -> cache.chunkLoad(pos));
    }
}
