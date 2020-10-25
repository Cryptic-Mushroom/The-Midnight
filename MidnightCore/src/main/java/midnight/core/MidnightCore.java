/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.core;

import midnight.api.IMidnight;
import net.minecraft.util.registry.DynamicRegistries;

public abstract class MidnightCore implements IMidnight {
    private static MidnightCore instance;

    public MidnightCore() {
        instance = this;
    }

    public abstract DynamicRegistries getDynamicRegistries();

    public static MidnightCore get() {
        return instance;
    }
}
