/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.api.plugin;

import midnight.api.MidnightAPI;
import midnight.api.MidnightClientAPI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface MidnightPlugin {

    /**
     * Initialize your plugin.
     */
    default void init(MidnightAPI midnight) {

    }

    /**
     * Initialize your plugin on the client.
     */
    @Environment(EnvType.CLIENT)
    default void initClient(MidnightClientAPI midnight) {

    }

    /**
     * Initialize your plugin on the dedicated server.
     */
    @Environment(EnvType.SERVER)
    default void initServer(MidnightAPI midnight) {

    }
}
