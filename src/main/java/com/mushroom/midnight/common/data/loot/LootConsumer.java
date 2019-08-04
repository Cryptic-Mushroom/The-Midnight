package com.mushroom.midnight.common.data.loot;

import net.minecraft.block.Block;
import net.minecraft.world.storage.loot.LootTable;

public interface LootConsumer {
    void accept(Block block, LootTable.Builder builder);
}
