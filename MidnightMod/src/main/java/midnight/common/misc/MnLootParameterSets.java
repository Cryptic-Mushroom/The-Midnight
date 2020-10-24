/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.common.misc;

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

    // Reflects register in LootParameterSets
    private static LootParameterSet register(String id, Consumer<LootParameterSet.Builder> builderConsumer) {
        return LootParameterSets.register(id, builderConsumer);
    }
}
