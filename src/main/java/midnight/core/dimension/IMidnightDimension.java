/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 4 - 11
 */

package midnight.core.dimension;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;

// TODO Feel free to move this to midnight.core.dimension package if you think it doesn't belong here
@FunctionalInterface
public interface IMidnightDimension {
    void createDefaultDimensionOptions(SimpleRegistry<Dimension> dimensionReg, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed);
}
