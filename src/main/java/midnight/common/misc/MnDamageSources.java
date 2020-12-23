/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.misc;

import midnight.core.util.MnObjects;
import net.minecraft.util.DamageSource;

public final class MnDamageSources {
    public static final DamageSource ROCKSHROOM = damage("rockshroom");

    private static DamageSource register(String id, DamageSource src) {
        MnObjects.addDamageSource(id, src);
        return src;
    }

    private static DamageSource damage(String id) {
        return register(id, new DamageSource("midnight." + id));
    }

    private MnDamageSources() {
    }
}
