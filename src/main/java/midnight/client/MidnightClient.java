/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.client;

import midnight.api.MidnightClientAPI;
import midnight.api.plugin.MidnightPlugin;
import midnight.common.Midnight;

/**
 * The dedicated server proxy of the Midnight mod. This inherits from {@link Midnight} and must in addition initialize
 * everything that must only initialized on the dedicated server distribution.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightClient extends Midnight implements MidnightClientAPI {

    @Override
    public void initialize() {
        super.initialize(); // Call super to initialize common stuff
    }

    @Override
    public void initPlugin(MidnightPlugin plugin) {
        super.initPlugin(plugin);
        plugin.initClient(this);
    }

    public static MidnightClient get() {
        return (MidnightClient) Midnight.get();
    }
}
