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
import midnight.core.dimension.IChunkGenFactory;
import midnight.core.dimension.IMidnightDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
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

public final class TheMidnightDimension implements IMidnightDimension, IChunkGenFactory {
    static final DimensionType TYPE = new MnDimensionType(
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
    public static final MnDimension REFERENCE = MnDimension.THE_MIDNIGHT;

    private static final RegistryKey<Dimension> DIMENSION_KEY = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, REFERENCE.getId());

    public TheMidnightDimension() {
    }

    @Override
    public void createDefaultDimensionOptions(SimpleRegistry<Dimension> dimensionReg, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        dimensionReg.register(DIMENSION_KEY, new Dimension(
            () -> TYPE,
            this.createChunkGenerator(biomeReg, settingsReg, seed)
        ), Lifecycle.stable());
    }

    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> configs, long seed) {
        return new TheMidnightChunkGenerator(
            seed,
            new TheMidnightBiomeProvider(seed, biomes)
        );
    }
}
