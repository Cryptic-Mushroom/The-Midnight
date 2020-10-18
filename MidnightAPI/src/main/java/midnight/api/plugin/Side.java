/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.api.plugin;

import net.minecraftforge.api.distmarker.Dist;

/**
 * The distribution sides a plugin can be on. Specify in {@link MidnightPlugin} and {@link MidnightEventSubscriber} to
 * make them exclusive to a specific distribution.
 *
 * @author Shadew
 * @since 0.6.0
 *
 * @see Dist
 */
public enum Side {
    /**
     * The common side includes both client and server distribution.
     *
     * @see Dist#CLIENT
     * @see Dist#DEDICATED_SERVER
     */
    COMMON,

    /**
     * The client side includes only the client distribution.
     *
     * @see Dist#CLIENT
     */
    CLIENT,

    /**
     * The server side includes only the server distribution.
     *
     * @see Dist#CLIENT
     */
    SERVER
}
