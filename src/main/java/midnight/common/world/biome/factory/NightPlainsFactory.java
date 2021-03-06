/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

import midnight.common.world.gen.carver.MnCarvers;
import midnight.common.world.gen.feature.MnFeatures.ConfiguredFeatures;
import midnight.common.world.gen.surface.MnConfiguredSurfaceBuilders;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.MnBiomeBuilder;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Carving;

public final class NightPlainsFactory implements IBiomeFactory {

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
                           .addCarver(Carving.AIR, MnCarvers.ConfiguredCarvers.MIDNIGHT_CAVE)
                           .addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatures.NIGHTSTONE_BOULDER)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.DARK_PEARL_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.EBONITE_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.NAGRILITE_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.TENEBRUM_ORE)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GRASS_PLAIN)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TALL_GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GHOST_PLANTS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GLOB_FUNGUS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TENDRILWEED)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.BRISTLY_GRASS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CRYSTALOTUS)
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
