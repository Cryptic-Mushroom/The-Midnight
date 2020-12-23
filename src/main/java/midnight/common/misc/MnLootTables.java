/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.misc;

import midnight.common.Midnight;
import net.minecraft.util.ResourceLocation;

public final class MnLootTables {
    public static final ResourceLocation GAMEPLAY_GEODE = table("gameplay/geode");

    private MnLootTables() {
    }

    private static ResourceLocation table(String id) {
        return Midnight.resLoc(id);
    }
}
