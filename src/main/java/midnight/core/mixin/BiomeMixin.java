/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.mixin;

import midnight.api.biome.IMidnightBiome;
import midnight.core.biome.BiomeColoring;
import midnight.core.biome.BiomeConfigLookup;
import midnight.core.biome.TerrainFactors;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;

// TODO This is a mixin class, but there's no injections in it. What is this used for??
@Mixin(Biome.class)
public class BiomeMixin implements IMidnightBiome {
    private BiomeColoring coloring;
    private TerrainFactors terrainFactors;

    private BiomeColoring getColoring() {
        if(coloring != null) return coloring;
        return coloring = BiomeConfigLookup.getColoring(Biome.class.cast(this));
    }

    private TerrainFactors getTerrainFactors() {
        if(terrainFactors != null) return terrainFactors;
        return terrainFactors = BiomeConfigLookup.getTerrainFactors(Biome.class.cast(this));
    }

    @Override
    public int getMidnightGrassColor(double x, double z) {
        return getColoring().getGrassColor();
    }

    @Override
    public int getMidnightWaterColor(double x, double z) {
        return getColoring().getWaterColor();
    }

    @Override
    public int getShadowrootColor(double x, double z) {
        return getColoring().getShadowrootColor();
    }

    @Override
    public double getTerrainHeight() {
        return getTerrainFactors().getHeight();
    }

    @Override
    public double getTerrainDifference() {
        return getTerrainFactors().getDifference();
    }

    @Override
    public double getTerrainHilliness() {
        return getTerrainFactors().getHilliness();
    }

    @Override
    public double getTerrainGranularity() {
        return getTerrainFactors().getGranularity();
    }
}
