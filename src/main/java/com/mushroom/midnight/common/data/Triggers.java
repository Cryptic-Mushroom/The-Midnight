package com.mushroom.midnight.common.data;

import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.Arrays;

public final class Triggers {
    public static EnterBlockTrigger.Instance enteredBlock(Block block) {
        return new EnterBlockTrigger.Instance(block, null);
    }

    public static InventoryChangeTrigger.Instance hasItem(MinMaxBounds.IntBound amount, IItemProvider item) {
        return hasItem(ItemPredicate.Builder.create().item(item).count(amount).build());
    }

    public static InventoryChangeTrigger.Instance hasItem(IItemProvider item) {
        return hasItem(ItemPredicate.Builder.create().item(item).build());
    }

    public static InventoryChangeTrigger.Instance hasItems(IItemProvider... items) {
        ItemPredicate[] predicates = Arrays.stream(items)
                .map(item -> ItemPredicate.Builder.create().item(item).build())
                .toArray(ItemPredicate[]::new);
        return hasItem(predicates);
    }

    public static InventoryChangeTrigger.Instance hasItem(Tag<Item> tag) {
        return hasItem(ItemPredicate.Builder.create().tag(tag).build());
    }

    public static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.Instance(MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicates);
    }
}
