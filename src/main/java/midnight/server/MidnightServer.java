/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.server;

import midnight.common.Midnight;

/**
 * The dedicated server proxy of the Midnight mod. This inherits from {@link Midnight} and must in addition initialize
 * everything that must only initialized on the dedicated server distribution.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightServer extends Midnight {

    @Override
    public void initialize() {
        super.initialize(); // Call super to initialize common stuff
    }

    public static MidnightServer get() {
        return (MidnightServer) Midnight.get();
    }
}
