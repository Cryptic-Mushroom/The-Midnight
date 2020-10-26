/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import midnight.core.biome.colors.provider.ErrorColorProvider;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ColorProfileManager implements ISelectiveResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();

    private final IResourceManager resManager;
    private final Consumer<ColorProfileManager> reloadHandler;

    private final ArrayList<ResourceLocation> loading = new ArrayList<>();
    private final HashMap<ResourceLocation, ColorProfile> loaded = new HashMap<>();

    public ColorProfileManager(IResourceManager resManager, Consumer<ColorProfileManager> reloadHandler) {
        this.resManager = resManager;
        this.reloadHandler = reloadHandler;
    }

    public ColorProfile load(ResourceLocation loc) {
        if (loaded.containsKey(loc)) return loaded.get(loc);
        if (loading.contains(loc)) {
            LOGGER.warn("Color profile '{}' references itself! Using error color...", loc);
            return new ColorProfile(ErrorColorProvider.INSTANCE);
        }

        loading.add(loc);
        ColorProfile profile = ColorProfile.load(resManager, loc, this);
        loading.remove(loading.size() - 1);
        loaded.put(loc, profile);
        return profile;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        loaded.clear();
        reloadHandler.accept(this);
    }
}
