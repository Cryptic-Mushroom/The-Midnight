/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.dimension;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

@FunctionalInterface
public interface IChunkGenFactory {
    /**
     * This method generates the chunk generator for this factory.
     *
     * @param biomeReg    The biome registry to use for the chunk generator.
     * @param settingsReg The dimension settings registry to use for the chunk generator.
     * @param seed        The seed to use for the chunk generator.
     * @return The newly created chunk generator.
     */
    ChunkGenerator createChunkGenerator(Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed);
}
