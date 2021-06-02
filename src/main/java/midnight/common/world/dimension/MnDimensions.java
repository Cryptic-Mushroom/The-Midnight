/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 6 - 2
 */

package midnight.common.world.dimension;

import midnight.common.world.biome.TheMidnightBiomeProvider;
import midnight.common.world.levelgen.midnight.TheMidnightChunkGenerator;
import midnight.core.dimension.DimensionUtil;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all dimensions used by The Midnight mod and has methods to add dimensions to be managed by the mod.
 *
 * @author Jonathan Colmenares
 * @since 0.6.0
 */
public final class MnDimensions {
    /**
     * This list is used by {@link DimensionUtil#registerDimensions(SimpleRegistry, Registry, Registry, long)} to
     * register all {@link AbstractMnDimension}s in this list to the server world. If you would rather register your own
     * midnight dimension yourself, don't add it to this list or use the {@link #addMnDimensionToList(AbstractMnDimension)}.
     */
    public static final List<AbstractMnDimension> ALL = new ArrayList<>();

    // Dimensions (use these!)
    public static final AbstractMnDimension THE_MIDNIGHT = createMidnightDimension();

    private MnDimensions() {
    }

    private static AbstractMnDimension createMidnightDimension() {
        final String name = "the_midnight";
        NonNullLazy<DimensionType> type =
            () -> new MnDimensionType.Builder()
                                 .fixedTime(18000)
                                 .effectsLocation(name)
                                 .build();

        AbstractMnDimension dimension = new AbstractMnDimension(name, type) {
            @Override
            public ChunkGenerator createChunkGenerator(Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
                return new TheMidnightChunkGenerator(seed, new TheMidnightBiomeProvider(seed, biomeReg));
            }
        };
        return addMnDimensionToList(dimension);
    }

    public static AbstractMnDimension addMnDimensionToList(AbstractMnDimension dimension) {
        ALL.add(dimension);
        return dimension;
    }
}
