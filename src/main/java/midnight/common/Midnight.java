/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common;

import midnight.MidnightMod;
import midnight.MnInfo;
import midnight.api.event.MidnightInitEvent;
import midnight.api.event.MidnightPostInitEvent;
import midnight.api.event.MidnightPreInitEvent;
import midnight.api.util.IMidnightObjects;
import midnight.client.MidnightClient;
import midnight.common.block.MnBlocks;
import midnight.common.net.MnNetwork;
import midnight.common.world.levelgen.MnLevelgen;
import midnight.core.MidnightCore;
import midnight.core.plugin.PluginManager;
import midnight.core.util.MnObjects;
import midnight.server.MidnightServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class of the Midnight. From here the complete mod is controlled. A few subclasses of this class exist, each one
 * for a different runtime variant of the Midnight:
 * <ul>
 * <li>
 * {@link MidnightClient} is the client-side proxy of this class. It handles additional client-only loading and
 * delegates certain method calls to appropriate client-only classes.
 * </li>
 * <li>
 * {@link MidnightServer} is the server-side proxy of this class. It does not very much other than existing and
 * delegating everything to the {@link Midnight} class itself.
 * </li>
 * </ul>
 *
 * @author Shadew
 * @since 0.6.0
 */
public abstract class Midnight extends MidnightCore {
    public static final Logger LOGGER = LogManager.getLogger("Midnight");

    private final PluginManager pluginManager = new PluginManager();

    /*
     * INITIALIZATION HANDLERS
     */

    /**
     * Called on pre-initialization, in the constructor of the {@link MidnightMod} class.
     */
    public void preInit() {
        if (!MnInfo.DATAGEN) {
            EVENT_BUS.start(); // Start the event bus manually when we're available
            pluginManager.loadPlugins();
            EVENT_BUS.post(new MidnightPreInitEvent(this, getRuntimeDist()));
        }
        MnLevelgen.init();
        MnNetwork.init();
    }

    /**
     * Called on initialization, in the {@link FMLCommonSetupEvent} handler.
     */
    public void init() {
        EVENT_BUS.post(new MidnightInitEvent(this, getRuntimeDist()));

        MnBlocks.setup();
    }

    /**
     * Called on post-initialization, in the {@link FMLLoadCompleteEvent} handler.
     */
    public void postInit() {
        EVENT_BUS.post(new MidnightPostInitEvent(this, getRuntimeDist()));
    }



    /*
     * PLUGIN MANAGER
     */

    /**
     * Gets the {@link PluginManager} of the current Minecraft runtime.
     *
     * @return The {@link PluginManager} of the current Midnight runtime.
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public IMidnightObjects getObjects() {
        return MnObjects.INSTANCE;
    }

    /**
     * Returns the current {@link Midnight} instance, as in {@link MidnightMod#MIDNIGHT}. Prefer using this over using
     * {@link MidnightMod#MIDNIGHT} itself.
     */
    public static Midnight get() {
        return MidnightMod.MIDNIGHT;
    }

    /**
     * Create a {@link ResourceLocation} with {@link MnInfo#MOD_ID midnight} as default namespace.
     * <ul>
     * <li>{@code "minecraft:path"} will yield {@code minecraft:path}</li>
     * <li>{@code "midnight:path"} will yield {@code midnight:path}</li>
     * <li>{@code "path"} will yield {@code midnight:path} (unlike the {@link ResourceLocation#ResourceLocation(String) ResourceLocation} constructor will yield {@code minecraft:path})</li>
     * </ul>
     *
     * @param path The resource path.
     * @return The created {@link ResourceLocation} instance.
     *
     * @see #idStr(String)
     */
    public static ResourceLocation id(String path) {
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new ResourceLocation(path.substring(0, colon), path.substring(colon + 1));
        }
        return new ResourceLocation(MnInfo.MOD_ID, path);
    }

    /**
     * Create a stringified {@link ResourceLocation} with {@link MnInfo#MOD_ID midnight} as default namespace.
     *
     * @param path The resource path.
     * @return The created resource id.
     *
     * @see #id(String)
     */
    public static String idStr(String path) {
        return id(path).toString();
    }
}
