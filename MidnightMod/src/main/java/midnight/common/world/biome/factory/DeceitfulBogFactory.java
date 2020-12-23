/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.biome.factory;

import midnight.common.block.MnBlocks;
import midnight.common.world.levelgen.feature.MnCfgFeatures;
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
        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.getDefaultState(), 32)
                        .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                        .repeat(2);

    private static final ConfiguredFeature<?, ?> TALL_GRASS =
        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.getDefaultState(), 64)
                        .decorate(MnCfgFeatures.Placements.SPREAD_32_ABOVE)
                        .decorate(MnCfgFeatures.Placements.SQUARE_HEIGHTMAP)
                        .repeat(7);

    private static final ConfiguredFeature<?, ?> BOGSHROOM =
        MnFeatureFactory.simplePatch(MnBlocks.BOGSHROOM.getDefaultState(), 32)
                        .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                        .applyChance(1);

    private static final ConfiguredFeature<?, ?> TALL_BOGSHROOM =
        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_BOGSHROOM.getDefaultState(), 32)
                        .decorate(MnCfgFeatures.Placements.SPREAD_32_ABOVE)
                        .decorate(MnCfgFeatures.Placements.SQUARE_HEIGHTMAP)
                        .applyChance(13);

    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .category(Biome.Category.FOREST)
                   .temperatureModifier(Biome.TemperatureModifier.NONE)
                   .effects(
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
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, GRASS)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, TALL_GRASS)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, BOGSHROOM)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, TALL_BOGSHROOM)
                           .build()
                   )
                   .spawnSettings(
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
