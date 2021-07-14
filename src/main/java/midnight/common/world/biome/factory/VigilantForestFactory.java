/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

import midnight.common.world.levelgen.feature.MnFeatures;
import midnight.common.world.levelgen.feature.MnFeatures.ConfiguredFeatures;
import midnight.common.world.levelgen.surface.MnConfiguredSurfaceBuilders;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.MnBiomeBuilder;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;

public class VigilantForestFactory extends BiomeFactory {

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.155f).scale(0.07f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                   .biomeCategory(Biome.Category.FOREST)
                   .specialEffects(
                       new BiomeAmbience.Builder()
                           .fogColor(0) // TODO Look up from old version
                           .skyColor(0)
                           .waterColor(0x5280EB)
                           .waterFogColor(0x395087)
                           .build()
                   )
                   .generationSettings(
                       new BiomeGenerationSettings.Builder()
                           .surfaceBuilder(MnConfiguredSurfaceBuilders.DEFAULT_NIGHT_GRASS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.DEAD_LOG)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TALL_GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GHOST_PLANTS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.CRYSTALOTUS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.VIOLEAF)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TENDRILWEED)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.SUAVIS_SPARSE)           
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0x5B527D)
                           .waterColor(0x302859)
                           .build()
                   )
                   .terrainFactors(
                       new TerrainFactors.Builder()
                           .height(0.75)
                           .difference(2)
                           .hilliness(2)
                           .granularity(0.7)
                           .build()
                   )
                   .build();
    }
}
