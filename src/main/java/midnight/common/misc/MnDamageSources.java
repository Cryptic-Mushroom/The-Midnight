/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.misc;

import net.minecraft.util.DamageSource;

public final class MnDamageSources {
    public static final DamageSource ROCKSHROOM = damage("rockshroom");

    private static DamageSource damage(String id) {
        return new DamageSource("midnight." + id);
    }

    private MnDamageSources() {
    }
}
