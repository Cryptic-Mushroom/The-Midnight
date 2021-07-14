/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

import midnight.common.world.levelgen.feature.MnFeatures;
import midnight.common.world.levelgen.surface.MnConfiguredSurfaceBuilders;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.MnBiomeBuilder;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;

public class NightPlainsFactory extends BiomeFactory {

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.12f).scale(0.2f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .biomeCategory(Biome.Category.PLAINS)
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
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
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.GRASS_PLAIN)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.TALL_GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.GHOST_PLANTS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.GLOB_FUNGUS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.BRISTLY_GRASS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MnFeatures.ConfiguredFeatures.CRYSTALOTUS)
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0x8e609c)
                           .waterColor(0x2C0266)
                           .build()
                   )
                   .terrainFactors(
                       new TerrainFactors.Builder()
                           .height(1)
                           .difference(2)
                           .hilliness(1)
                           .granularity(0.4)
                           .build()
                   )
                   .build();
    }
}
