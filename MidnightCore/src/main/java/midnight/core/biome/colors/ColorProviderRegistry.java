/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import midnight.core.biome.colors.deserializer.*;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public final class ColorProviderRegistry {
    private static final HashMap<ResourceLocation, IColorProviderDeserializer> REGISTRY = new HashMap<>();

    private ColorProviderRegistry() {
    }

    public static void register(ResourceLocation loc, IColorProviderDeserializer deserializer) {
        REGISTRY.put(loc, deserializer);
    }

    public static void setup() {
        register(new ResourceLocation("rgb"), new RGBColorProviderDeserializer());
        register(new ResourceLocation("hsv"), new HSVColorProviderDeserializer());
        register(new ResourceLocation("checkerboard"), new Checkerboard2DColorProviderDeserializer());
        register(new ResourceLocation("checkerboard2d"), new Checkerboard2DColorProviderDeserializer());
        register(new ResourceLocation("checkerboard3d"), new Checkerboard3DColorProviderDeserializer());
        register(new ResourceLocation("cellnoise"), new CellNoise3DColorProviderDeserializer());
        register(new ResourceLocation("cellnoise2d"), new CellNoise2DColorProviderDeserializer());
        register(new ResourceLocation("cellnoise3d"), new CellNoise3DColorProviderDeserializer());
        register(new ResourceLocation("perlin"), new Perlin3DColorProviderDeserializer());
        register(new ResourceLocation("perlin3d"), new Perlin3DColorProviderDeserializer());
        register(new ResourceLocation("perlin2d"), new Perlin2DColorProviderDeserializer());
        register(new ResourceLocation("opensimplex"), new OpenSimplex3DColorProviderDeserializer());
        register(new ResourceLocation("opensimplex3d"), new OpenSimplex3DColorProviderDeserializer());
        register(new ResourceLocation("opensimplex2d"), new OpenSimplex2DColorProviderDeserializer());
        register(new ResourceLocation("random"), new Random3DColorProviderDeserializer());
        register(new ResourceLocation("random3d"), new Random3DColorProviderDeserializer());
        register(new ResourceLocation("random2d"), new Random2DColorProviderDeserializer());
        register(new ResourceLocation("biomes"), new BiomeColorProviderDeserializer());
        register(new ResourceLocation("colormap"), new ColorMapColorProviderDeserializer());
        register(new ResourceLocation("expression"), new ExpressionColorProviderDeserializer());
        register(new ResourceLocation("ref"), new ReferencedColorProviderDeserializer());
        register(new ResourceLocation("heightmap"), new HeightmapProviderDeserializer());
    }

    public static IColorProviderDeserializer deserializer(ResourceLocation loc) {
        return REGISTRY.get(loc);
    }
}
