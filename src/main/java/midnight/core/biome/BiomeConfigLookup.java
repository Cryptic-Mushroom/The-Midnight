/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.core.biome;

import midnight.core.MidnightCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

// TODO Revisit, this works but is not logical and should probably move to data packs
public final class BiomeConfigLookup {
    private static final Map<ResourceLocation, BiomeColoring> COLORING_MAP = new HashMap<>();
    private static final Map<ResourceLocation, TerrainFactors> TERRAIN_FACTORS_MAP = new HashMap<>();

    private BiomeConfigLookup() {
    }

    public static void putColoring(ResourceLocation id, BiomeColoring colors) {
        COLORING_MAP.put(id, colors);
    }

    public static void putTerrainFactors(ResourceLocation id, TerrainFactors terrainFactors) {
        TERRAIN_FACTORS_MAP.put(id, terrainFactors);
    }

    public static BiomeColoring getColoring(Biome biome) {
        ResourceLocation id = MidnightCore.get().getDynamicRegistries().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome);
        return COLORING_MAP.computeIfAbsent(id, k -> new BiomeColoring.Builder().build());
    }

    public static TerrainFactors getTerrainFactors(Biome biome) {
        ResourceLocation id = MidnightCore.get().getDynamicRegistries().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome);
        return TERRAIN_FACTORS_MAP.computeIfAbsent(id, k -> new TerrainFactors.Builder().build());
    }
}
