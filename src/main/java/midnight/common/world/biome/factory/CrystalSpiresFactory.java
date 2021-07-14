/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

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

public class CrystalSpiresFactory extends BiomeFactory {
    

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.6f).scale(0.2f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                   .biomeCategory(Biome.Category.PLAINS)
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
                           .addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatures.HUGE_CRYSTAL_SPIRE)
                           .addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatures.CRYSTAL_SPIRE)
                           .addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatures.CRYSTAL_CLUSTER)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.TALL_GRASS_FOREST)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CRYSTAL_FLOWERS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.GHOST_PLANTS)
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0xB855CF)
                           .waterColor(0x2C0266)
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
