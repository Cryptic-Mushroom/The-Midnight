/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import com.mojang.serialization.Lifecycle;
import midnight.common.Midnight;
import midnight.common.world.biome.TheMidnightBiomeProvider;
import midnight.common.world.levelgen.midnight.TheMidnightChunkGenerator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

import java.util.OptionalLong;

public final class TheMidnightDimension {
    static final ResourceLocation ID = Midnight.id("the_midnight");
    static final RegistryKey<World> KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, ID);

    private static final DimensionType TYPE = new MnDimensionType(
        OptionalLong.of(18000), // Fixed time
        false, // Sky light
        false, // Ceiling
        false, // Ultra warm
        false, // Natural
        1,     // Coordinate scaling
        false, // Ender dragon
        false, // Piglin-safe
        false, // Bed allowed
        false, // Respawn anchor allowed
        false, // Raids
        256,   // Height
        FuzzedBiomeMagnifier.INSTANCE, // Biome magnifier
        new ResourceLocation("infiniburn_overworld"), // Infiniburn tag         TODO Midnight infiniburn
        ID,    // Environment renderer type
        0      // Ambient lighting
    );

    private static final RegistryKey<Dimension> DIMENSION_KEY = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, ID);
    private static final RegistryKey<DimensionType> TYPE_KEY = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY, ID);


    public static void addRegistryDefaults(DynamicRegistries.Impl dynaRegs) {
        MutableRegistry<DimensionType> registry = dynaRegs.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        registry.register(TYPE_KEY, TYPE, Lifecycle.stable());
    }

    public static void createDefaultDimensionOptions(SimpleRegistry<Dimension> registry, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        registry.register(DIMENSION_KEY, new Dimension(
            () -> TYPE,
            createChunkGenerator(biomeReg, settingsReg, seed)
        ), Lifecycle.stable());
    }

    private static ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> configs, long seed) {
        return new TheMidnightChunkGenerator(
            seed,
            new TheMidnightBiomeProvider(seed, biomes)
        );
    }
}
