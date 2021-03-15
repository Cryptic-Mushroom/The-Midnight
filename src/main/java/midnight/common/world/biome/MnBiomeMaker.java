/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.biome;

import midnight.client.audio.MnMusicTicker;
import midnight.common.Midnight;
import midnight.common.world.biome.factory.BiomeFactory;
import midnight.common.world.levelgen.surface.MnConfiguredSurfaceBuilders;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.MnBiomeBuilder;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

/**
 * This class is used to generate the biomes and their settings when they are registered in the biome registry. DO NOT
 * GIVE A MUSIC EFFECT IN THE BUILDER! ALL MIDNIGHT MUSICS (for now) ARE HANDLED BY the {@link MnMusicTicker}.
 *
 * @author Shadew
 * @see midnight.common.world.biome.MnBiomes
 * @since 0.6.0
 * @deprecated This is being replaced with {@link BiomeFactory} now so we don't have one junk of biome factory methods.
 */
@Deprecated
public final class MnBiomeMaker {
    private MnBiomeMaker() {
    }

    /**
     * This method is used to make the night plains biome and contains the settings used for it.
     *
     * @param id The biome id given to the biome.
     * @return The biome with its settings to be registered into the game.
     */
    public static Biome makeNightPlains(String id) {
        return new MnBiomeBuilder(Midnight.id(id))
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
                   .biomeCategory(Biome.Category.MUSHROOM)
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
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
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

    /**
     * This method is used to make the vigilant forest biome and contains the settings used for it.
     *
     * @param id The biome id given to the biome.
     * @return The biome with its settings to be registered into the game.
     */
    public static Biome makeVigilantForest(String id) {
        return new MnBiomeBuilder(Midnight.id(id))
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
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
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
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

    /**
     * This method is used to make the deceitful bog biome and contains the settings used for it.
     *
     * @param id The biome id given to the biome.
     * @return The biome with its settings to be registered into the game.
     */
    public static Biome makeDeceitfulBog(String id) {
        return new MnBiomeBuilder(Midnight.id(id))
                   .depth(0.1f).scale(0.1f)
                   .temperature(1).downfall(0).precipitation(Biome.RainType.NONE)
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
                           .surfaceBuilder(MnConfiguredSurfaceBuilders.DECEITFUL_BOG)
                           .build()
                   )
                   .mobSpawnSettings(
                       new MobSpawnInfo.Builder().build()
                   )
                   .temperatureAdjustment(Biome.TemperatureModifier.NONE)
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
