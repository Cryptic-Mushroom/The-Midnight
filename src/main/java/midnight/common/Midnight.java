/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.common;

import midnight.client.MidnightClient;
import midnight.server.MidnightServer;
import net.minecraft.util.Identifier;

/**
 * The main instance of the Midnight mod. This should initialize and hold references to all manager, instances, tickers,
 * whatever we have and provide a getter for them, and in addition do the registry of game objects (though the game
 * objects go into object holder classes).
 *
 * @author Shadew
 */
public abstract class Midnight {


    // ====================================================
    //  INITIALIZER
    // ====================================================

    /**
     * Initialize anything to initialize. Register game objects, etc... When overriding this in {@link MidnightClient}
     * or {@link MidnightServer}
     */
    public void initialize() {

    }



    // ====================================================
    //  INSTANTIATION
    // ====================================================

    /**
     * Get the current {@link Midnight instance}.
     */
    public static Midnight get() {
        return instance;
    }

    private static Midnight instance;

    public Midnight() {
        if (instance != null) {
            throw new IllegalStateException("Midnight initialized twice");
        }
        instance = this;
    }



    // ====================================================
    //  UTILS
    // ====================================================

    // TODO Port MnInfo


    /**
     * Create a {@link Identifier} with {@code midnight} as default namespace.
     * <ul>
     * <li>{@code "minecraft:path"} will yield {@code minecraft:path}</li>
     * <li>{@code "midnight:path"} will yield {@code midnight:path}</li>
     * <li>{@code "path"} will yield {@code midnight:path} (unlike the {@link Identifier#Identifier(String) Identifier}
     * constructor will yield {@code minecraft:path})</li>
     * </ul>
     *
     * @param path The resource path.
     * @return The created {@link Identifier} instance.
     */
    public static Identifier id(String path) {
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new Identifier(path.substring(0, colon), path.substring(colon + 1));
        }
        return new Identifier("midnight", path);
    }

    /**
     * Create a stringified {@link Identifier} with {@code midnight} as default namespace. See {@link #id}.
     *
     * @param path The resource path.
     * @return The created resource id.
     */
    public static String idStr(String path) {
        if (path.indexOf(':') >= 0) {
            return path;
        }
        return "midnight:" + path;
    }
}
