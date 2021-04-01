/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

import midnight.common.block.MnBlocks;
import midnight.common.world.levelgen.feature.MnFeatureFactory;
import midnight.common.world.levelgen.surface.MnConfiguredSurfaceBuilders;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.MnBiomeBuilder;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class DeceitfulBogFactory extends BiomeFactory {

    private static final ConfiguredFeature<?, ?> GRASS =
        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.defaultBlockState(), 32)
                        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
                        .count(2);

    private static final ConfiguredFeature<?, ?> TALL_GRASS =
        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.defaultBlockState(), 64)
                        .decorated(Features.Placements.ADD_32)
                        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                        .count(7);

    private static final ConfiguredFeature<?, ?> BOGSHROOM =
        MnFeatureFactory.simplePatch(MnBlocks.BOGSHROOM.defaultBlockState(), 32)
                        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
                        .chance(3);

    private static final ConfiguredFeature<?, ?> TALL_BOGSHROOM =
        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_BOGSHROOM.defaultBlockState(), 32)
                        .decorated(Features.Placements.ADD_32)
                        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                        .chance(13);

    private static final ConfiguredFeature<?, ?> DEAD_LOG
        = MnFeatureFactory.deadLog(MnBlocks.DEAD_WOOD_LOG)
                          .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                          .chance(3);

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .biomeCategory(Biome.Category.FOREST)
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
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DEAD_LOG)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, GRASS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TALL_GRASS)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOGSHROOM)
                           .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TALL_BOGSHROOM)
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0x765E8A)
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
