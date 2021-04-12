/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import com.mojang.serialization.Lifecycle;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

public final class TheMidnightDimension implements IMidnightDimension, IChunkGenFactory {
    public static final MnDimension REFERENCE = MnDimension.THE_MIDNIGHT;
    static final DimensionType TYPE =
        MnDimensionType.builder()
                       .fixedTime(18000)
                       .infiniburn(new ResourceLocation("infiniburn_overworld"))
                       .sky(REFERENCE.getId())
                       .build();

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
