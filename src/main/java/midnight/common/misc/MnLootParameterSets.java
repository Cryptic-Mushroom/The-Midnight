/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.misc;

import midnight.common.Midnight;
import midnight.core.util.MnObjects;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;

import java.util.function.Consumer;

public final class MnLootParameterSets {

    public static final LootParameterSet GEODE = register(
        "geode",
        builder -> builder.required(LootParameters.ORIGIN)
                          .required(LootParameters.THIS_ENTITY)
                          .required(LootParameters.BLOCK_STATE)
    );

    private MnLootParameterSets() {
    }

    private static LootParameterSet register(String id, Consumer<LootParameterSet.Builder> builderConsumer) {
        LootParameterSet set = LootParameterSets.register(Midnight.resStr(id), builderConsumer);
        MnObjects.addLootParameterSet(id, set);
        return set;
    }
}
