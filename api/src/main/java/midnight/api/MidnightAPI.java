/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.api;

import midnight.api.plugin.MidnightPlugin;

import java.util.function.Supplier;

/**
 * The base API for the Midnight. When the Midnight is properly loaded an instance can be obtained via {@link
 * MidnightAPI#get()}.
 */
public interface MidnightAPI {

    /**
     * Returns the Midnight build info
     */
    MidnightInfo info();

    /**
     * Initializes a plugin instance. <strong>This is an INTERNAL method and should only be called by the Midnight
     * mod.</strong>
     */
    @Deprecated
    void initPlugin(MidnightPlugin plugin);

    /**
     * Returns the current {@link MidnightAPI} instance.
     *
     * @throws IllegalStateException When the Midnight API implementation is not present
     */
    static MidnightAPI get() {
        if (APIHolder.instance == null) {
            throw new IllegalStateException("Midnight API implementation is not present");
        }
        return APIHolder.instance;
    }

    /**
     * Initializes the instance of the Midnight API. <strong>This is an INTERNAL method and should only be called by the
     * Midnight mod.</strong>
     *
     * @param api The API instance
     */
    @Deprecated
    static void set(MidnightAPI api) {
        if (APIHolder.instance != null) {
            throw new IllegalStateException("Midnight API already initialized");
        }
        APIHolder.instance = api;
    }

    /**
     * Registers a plugin. The plugin is instantiated once the Midnight is loaded.
     *
     * @param plugin A plugin supplier. Can be null, if null the method immediately returns.
     */
    static void registerPlugin(Supplier<MidnightPlugin> plugin) {
        if (plugin == null) return;

        if (APIHolder.ready) {
            MidnightAPI api = APIHolder.instance;
            MidnightPlugin loaded = plugin.get();
            APIHolder.LOADED_PLUGINS.add(loaded);
            api.initPlugin(loaded);
        }
        APIHolder.PLUGINS.add(plugin);
    }

    /**
     * Marks the Midnight API as ready. <strong>This is an INTERNAL method and should only be called by the Midnight
     * mod.</strong>
     */
    @Deprecated
    static void ready() {
        MidnightAPI api = APIHolder.instance;
        if (api == null) {
            throw new IllegalStateException("Midnight API marked as ready before any implementation was specified");
        }
        APIHolder.ready = true;
        for (Supplier<MidnightPlugin> plugin : APIHolder.PLUGINS) {
            MidnightPlugin loaded = plugin.get();
            APIHolder.LOADED_PLUGINS.add(loaded);
            api.initPlugin(loaded);
        }
    }
}
