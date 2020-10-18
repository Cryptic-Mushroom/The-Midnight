/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.common.world.biome;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

/**
 * This class registers and stores the list of Midnight biomes.
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
public final class MnBiomes {
    public static final RegistryKey<Biome> NIGHT_PLAINS = key("night_plains");
    public static final RegistryKey<Biome> VIGILANT_FOREST = key("vigilant_forest");
    public static final RegistryKey<Biome> DECEITFUL_BOG = key("deceitful_bog");

    private static RegistryKey<Biome> key(String key) {
        return RegistryKey.of(Registry.BIOME_KEY, Midnight.resLoc(key));
    }

    public static void registerBiomes(IRegistry<Biome> registry) {
        registry.registerAll(
            MnBiomeMaker.makeNightPlains("night_plains"),
            MnBiomeMaker.makeVigilantForest("vigilant_forest"),
            MnBiomeMaker.makeDeceitfulBog("deceitful_bog")
        );
    }

    private MnBiomes() {
    }
}
