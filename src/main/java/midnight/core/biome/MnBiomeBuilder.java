/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.biome;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

// TODO Since this is in Midnight Core, we might want to document this.
public class MnBiomeBuilder extends Biome.Builder {
    private final ResourceLocation id;
    private BiomeColoring coloring;
    private TerrainFactors terrainFactors;

    public MnBiomeBuilder(ResourceLocation id) {
        this.id = id;
    }

    public MnBiomeBuilder coloring(BiomeColoring coloring) {
        this.coloring = coloring;
        return this;
    }

    public MnBiomeBuilder terrainFactors(TerrainFactors terrainFactors) {
        this.terrainFactors = terrainFactors;
        return this;
    }

    @Override
    public MnBiomeBuilder precipitation(Biome.RainType type) {
        return (MnBiomeBuilder) super.precipitation(type);
    }

    @Override
    public MnBiomeBuilder biomeCategory(Biome.Category category) {
        return (MnBiomeBuilder) super.biomeCategory(category);
    }

    @Override
    public MnBiomeBuilder depth(float depth) {
        return (MnBiomeBuilder) super.depth(depth);
    }

    @Override
    public MnBiomeBuilder scale(float scale) {
        return (MnBiomeBuilder) super.scale(scale);
    }

    @Override
    public MnBiomeBuilder temperature(float temperature) {
        return (MnBiomeBuilder) super.temperature(temperature);
    }

    @Override
    public MnBiomeBuilder downfall(float downfall) {
        return (MnBiomeBuilder) super.downfall(downfall);
    }

    @Override
    public MnBiomeBuilder specialEffects(BiomeAmbience ambience) {
        return (MnBiomeBuilder) super.specialEffects(ambience);
    }

    @Override
    public MnBiomeBuilder mobSpawnSettings(MobSpawnInfo spawns) {
        return (MnBiomeBuilder) super.mobSpawnSettings(spawns);
    }

    @Override
    public MnBiomeBuilder generationSettings(BiomeGenerationSettings generation) {
        return (MnBiomeBuilder) super.generationSettings(generation);
    }

    @Override
    public MnBiomeBuilder temperatureAdjustment(Biome.TemperatureModifier temperature) {
        return (MnBiomeBuilder) super.temperatureAdjustment(temperature);
    }

    @Override
    public Biome build() {
        Biome biome = super.build();
        biome.setRegistryName(id);
        if (coloring != null)
            BiomeConfigLookup.putColoring(id, coloring);
        if (terrainFactors != null)
            BiomeConfigLookup.putTerrainFactors(id, terrainFactors);
        return biome;
    }
}
