/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight;

import midnight.api.MidnightAPI;
import midnight.client.MidnightClient;
import net.fabricmc.api.ClientModInitializer;

/**
 * Initializes the Midnight on the client distribution. This instantiates a {@link MidnightClient} instance.
 */
public class MidnightModClient implements ClientModInitializer {
    @Override
    @SuppressWarnings("deprecation") // we may call internal api methods
    public void onInitializeClient() {
        MidnightClient client = new MidnightClient();
        client.initialize();
        MidnightAPI.ready();
    }
}
