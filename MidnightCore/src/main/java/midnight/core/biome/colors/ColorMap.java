/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Used to load and read color maps from resource packs
 */
public class ColorMap implements ISelectiveResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ResourceLocation location;
    private final int fallbackColor;
    private boolean loaded;
    private int width;
    private int height;
    private int[] values;

    /**
     * Creates a color map
     *
     * @param location      The location to load from
     * @param fallbackColor The color to use when unable to load
     */
    public ColorMap(ResourceLocation location, int fallbackColor) {
        this.location = location;
        this.fallbackColor = fallbackColor;
    }

    /**
     * Creates a color map, using magenta as error color
     *
     * @param location The location to load from
     */
    public ColorMap(ResourceLocation location) {
        this(location, 0xffff00ff);
    }

    /**
     * Returns the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the color at the specific pixel
     */
    public int get(int x, int y) {
        if (!loaded || outOfBounds(x, y)) return fallbackColor;
        return values[y * width + x];
    }

    /**
     * Get the color at the specific UV coords
     */
    public int get(double x, double y) {
        return get((int) (x * (width - 1)), (int) (y * (width - 1)));
    }

    /**
     * Get the color at the specific UV coords
     */
    public int get(float x, float y) {
        return get((int) (x * (width - 1)), (int) (y * (width - 1)));
    }

    /**
     * Gets a random color according to the specified {@link Random}
     */
    public int random(Random rand) {
        if (!loaded) return fallbackColor;
        return values[rand.nextInt(values.length)];
    }

    /**
     * Checks whether the specified coords are out of bounds
     */
    private boolean outOfBounds(int x, int y) {
        return x >= width || x < 0 || y >= height || y < 0;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(VanillaResourceType.TEXTURES)) {
            reload(resourceManager);
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Reloads the color map from the specified resource manager
     */
    public void reload(IResourceManager manager) {
        try {
            IResource resource = manager.getResource(location);
            InputStream stream = resource.getInputStream();

            BufferedImage image = ImageIO.read(stream);
            if (image == null) throw new IOException("No ImageReader available for current format");
            int w = width = image.getWidth();
            int h = height = image.getHeight();

            LOGGER.debug("Loaded color map will be {}x{}", w, h);

            values = new int[w * h];

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    values[y * w + x] = image.getRGB(x, y);
                }
            }

            loaded = true;
            LOGGER.info("Loaded color map {}", location);
        } catch (IOException exc) {
            LOGGER.error("Unable to load color map " + location, exc);
            LOGGER.error("A fallback color will be used (magenta, unless otherwise configured)");
            loaded = false;
        }
    }
}
