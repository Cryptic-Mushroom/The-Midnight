/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.common.world.biome;

import midnight.MidnightMod;
import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import midnight.core.util.MnObjects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

/**
 * This class registers and stores the list of Midnight biomes.
 *
 * @author Shadew
 * @since 0.6.0
 */
public final class MnBiomes {
    public static final RegistryKey<Biome> NIGHT_PLAINS = key("night_plains");
    public static final RegistryKey<Biome> VIGILANT_FOREST = key("vigilant_forest");
    public static final RegistryKey<Biome> DECEITFUL_BOG = key("deceitful_bog");

    private static RegistryKey<Biome> key(String key) {
        RegistryKey<Biome> rk = RegistryKey.of(Registry.BIOME_KEY, Midnight.resLoc(key));
        MnObjects.addBiome(key, rk);
        return rk;
    }

    public static void registerBiomes(IRegistry<Biome> registry) {
        registry.registerAll(
            MnBiomeMaker.makeNightPlains("night_plains"),
            MnBiomeMaker.makeVigilantForest("vigilant_forest"),
            MnBiomeMaker.makeDeceitfulBog("deceitful_bog")
        );
    }

    public static RegistryKey<Biome> getKeyFromBiome(World world, Biome biomeIn)
    {
        Optional<RegistryKey<Biome>> biome = world.getRegistryManager().get(Registry.BIOME_KEY).getKeyOptional(biomeIn);

        if (biome.isPresent())
            return biome.get();

        Midnight.LOGGER.error("Failed to get the registry key from " + biomeIn + ". This is not good!");
        return null;
    }

    private MnBiomes() {
    }
}
