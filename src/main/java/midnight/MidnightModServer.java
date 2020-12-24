/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight;

import midnight.server.MidnightServer;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * Initializes the Midnight on the dedicateds server distribution. This instantiates a {@link MidnightServer} instance.
 */
public class MidnightModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        MidnightServer server = new MidnightServer();
        server.initialize();
    }
}
