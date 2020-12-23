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
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class NightPlainsFactory extends BiomeFactory {

    private static final ConfiguredFeature<?, ?> GRASS =
        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.getDefaultState(), 32)
                        .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                        .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8, 5, 10)));

    private static final ConfiguredFeature<?, ?> TALL_GRASS =
        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.getDefaultState(), 32)
                        .decorate(Features.Placements.SPREAD_32_ABOVE)
                        .decorate(Features.Placements.HEIGHTMAP)
                        .spreadHorizontally()
                        .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8, 0, 7)));

    private static final ConfiguredFeature<?, ?> GHOST_PLANTS =
        MnFeatureFactory.simplePatch(MnBlocks.GHOST_PLANT.getDefaultState(), 32)
                        .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE);


    @Override
    public Biome makeBiome(MnBiomeBuilder builder) {
        return builder
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .category(Biome.Category.MUSHROOM)
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
                           .surfaceBuilder(MnConfiguredSurfaceBuilders.DEFAULT_NIGHT_GRASS)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, GRASS)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, TALL_GRASS)
                           .feature(GenerationStage.Decoration.VEGETAL_DECORATION, GHOST_PLANTS)
                           .build()
                   )
                   .spawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .coloring(
                       new BiomeColoring.Builder()
                           .grassColor(0x8F54A1)
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
