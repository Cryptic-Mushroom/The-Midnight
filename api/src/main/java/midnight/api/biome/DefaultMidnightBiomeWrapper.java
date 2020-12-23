/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.api.biome;

import net.minecraft.world.biome.Biome;

import java.util.HashMap;

// TODO Delete this?
// It depends. If we can pass these in as arguments when we create the biomes, we might not need it,
// but we also need to keep people who would make midnight plugins in mind.
/**
 * Default wrapper for vanilla biomes and other biomes not implementing the {@link IMidnightBiome} interface, to be
 * returned by {@link IMidnightBiome#get}.
 *
 * @author Shadew
 * @since 0.6.0
 */
class DefaultMidnightBiomeWrapper implements IMidnightBiome {
    // Cache instances to reduce overloads of GC operations.
    private static final HashMap<Biome, DefaultMidnightBiomeWrapper> CACHE = new HashMap<>();

    private final Biome biome;

    private DefaultMidnightBiomeWrapper(Biome biome) {
        this.biome = biome;
    }

    @Override
    public Biome getBiome() {
        return biome;
    }

    @Override
    public int getMidnightGrassColor(double x, double z) {
        return 0x506C78;
    }

    @Override
    public int getMidnightWaterColor(double x, double z) {
        return 0x22517D;
    }

    @Override
    public int getShadowrootColor(double x, double z) {
        return 0x3A3154;
    }

    @Override
    public double getTerrainHeight() {
        return 2;
    }

    @Override
    public double getTerrainDifference() {
        return 2;
    }

    @Override
    public double getTerrainHilliness() {
        return 1;
    }

    @Override
    public double getTerrainGranularity() {
        return 0.5;
    }

    static DefaultMidnightBiomeWrapper get(Biome biome) {
        return CACHE.computeIfAbsent(biome, DefaultMidnightBiomeWrapper::new);
    }
}
