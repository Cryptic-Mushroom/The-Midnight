/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.api.biome;

import net.minecraft.world.biome.Biome;

/**
 * Marks a biome set to have special interactions with the Midnight, such as specific coloring of Night Grass and Dark
 * Water. All builtin biomes of the Midnight implement this interface. To add or modify a Midnight biome, use this
 * interface.
 * <p>
 * Implementing this interface on a class that does not extend {@link Biome} may cause unexpected behaviour.
 * </p>
 *
 * @author Shadew
 * @since 0.6.0
 */
// TODO Mix this class into Biome
/* (Removed part of javadoc)
 * Notable is that builtin Midnight biomes dignore any Features, Carvers, Structures and Spawns that were added via the
 * default biome methods, in order to prevent features etc. from mods that modify all registered biomes to their
 * consent. If a mod needs to modify Midnight biomes specifically, the Midnight biomes should be modified via this
 * interface and not via the biome class itself. For example, to add a feature to a Midnight biome, one should use
 * {@link IMidnightBiome#addMidnightFeature} instead of {@link Biome#addFeature}.
 */
public interface IMidnightBiome {

    /**
     * Returns the {@link Biome} that implemented this interface.
     */
    default Biome getBiome() {
        // Biome is final, cast
        return Biome.class.cast(this);
    }

    /**
     * Returns the Night Grass color for this biome at a specific position. For most biomes this color is independent of
     * the position but a single biome may use noise to alter the color for a specific location.
     */
    int getMidnightGrassColor(double x, double z);

    /**
     * Returns the Dark Water color for this biome at a specific position. For most biomes this color is independent of
     * the position but a single biome may use noise to alter the color for a specific location.
     */
    int getMidnightWaterColor(double x, double z);

    /**
     * Returns the Shadowroot Leaves color for this biome at a specific position. For most biomes this color is
     * independent of the position but a single biome may use noise to alter the color for a specific location.
     */
    int getShadowrootColor(double x, double z);

    /**
     * Returns the base terrain height of this biome, as an offset from the water level. This height factor is scaled by
     * 4, so if this returns 1 the terrain is raised by 4 blocks.
     */
    double getTerrainHeight();

    /**
     * Returns the noise interpolation range of this biome - the difference. This range factor is scaled by 4, so if
     * this returns 1 the terrain differs 4 blocks in height.
     */
    double getTerrainDifference();

    /**
     * Returns the variation in the base terrain height of this biome - the hilliness. This hilliness factor is scaled
     * by 4, so if this returns 1 the base height differs by 4 blocks.
     */
    double getTerrainHilliness();

    /**
     * Returns the granularity of the terrain, determining how cliffy and eroded the terrain looks. Lower values make a
     * smoother terrain, while higher values make a more granulated terrain.
     */
    double getTerrainGranularity();

    /**
     * Get the {@link IMidnightBiome} for a specific biome. This will always return a valid {@link IMidnightBiome}
     * instance, even for biomes that do not implement that interface - if there is no implementation present, a default
     * implementation wrapper will be returned. This means that you <b>CANNOT</b> cast the result of this method to
     * {@link Biome} directly (it may return a wrapper) - use {@link #getBiome} for that instead.
     */
    @SuppressWarnings("ConstantConditions")
    static IMidnightBiome get(Biome biome) {
        if (IMidnightBiome.class.isInstance(biome)) {
            return IMidnightBiome.class.cast(biome);
        }
        return DefaultMidnightBiomeWrapper.get(biome);
    }
}
