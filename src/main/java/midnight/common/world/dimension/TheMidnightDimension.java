/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import midnight.common.world.biome.TheMidnightBiomeProvider;
import midnight.common.world.levelgen.midnight.TheMidnightChunkGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

public final class TheMidnightDimension extends AbstractMnDimension {
    public static final MnDimension REFERENCE = MnDimension.THE_MIDNIGHT;
    static final DimensionType TYPE =
        MnDimensionType.builder()
                       .fixedTime(18000)
                       .infiniburn(new ResourceLocation("infiniburn_overworld"))
                       .sky(REFERENCE.getId())
                       .build();

    public TheMidnightDimension() {
        super(REFERENCE);
    }

    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> configs, long seed) {
        return new TheMidnightChunkGenerator(
            seed,
            new TheMidnightBiomeProvider(seed, biomes)
        );
    }
}
