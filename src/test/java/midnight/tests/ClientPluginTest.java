/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.tests;

import midnight.api.plugin.MidnightPlugin;
import midnight.api.plugin.Side;

@MidnightPlugin(side = Side.CLIENT)
public class ClientPluginTest {
    public ClientPluginTest() {
        System.out.println("Client plugin loaded");
    }
}
