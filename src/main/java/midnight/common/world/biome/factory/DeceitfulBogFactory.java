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

public final class DeceitfulBogFactory implements IBiomeFactory {

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(-1.2f).scale(0.2f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .biomeCategory(Biome.Category.SWAMP)
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
                           .surfaceBuilder(MnConfiguredSurfaceBuilders.DECEITFUL_BOG)
                           .addCarver(Carving.AIR, MnCarvers.ConfiguredCarvers.MIDNIGHT_CAVE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.DARK_PEARL_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.EBONITE_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.NAGRILITE_ORE)
                           .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.TENEBRUM_ORE)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.DEAD_LOG)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GRASS_FOREST)                           
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.BOGSHROOM)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TALL_BOGSHROOM)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.BOGWEED)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GLOB_FUNGUS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GHOST_PLANTS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CRYSTALOTUS)
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0x554E70)
                           .waterColor(0x422D54)
                           .build()
                   )
                   .terrainFactors(
                       new TerrainFactors.Builder()
                           .height(0.2)
                           .difference(1)
                           .hilliness(0.5)
                           .granularity(0.8)
                           .build()
                   )
                   .build();
    }
}
