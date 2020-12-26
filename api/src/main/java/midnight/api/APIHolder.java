/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.api;

import midnight.api.plugin.MidnightPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

class APIHolder {
    static MidnightAPI instance;
    static boolean ready;

    static final Set<Supplier<MidnightPlugin>> PLUGINS = new HashSet<>();
    static final Set<MidnightPlugin> LOADED_PLUGINS = new HashSet<>();
}
