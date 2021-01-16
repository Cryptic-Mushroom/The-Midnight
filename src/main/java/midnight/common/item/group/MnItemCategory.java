/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.item.group;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MnItemCategory {
    public static final MnItemCategory SEDIMENTAL = new MnItemCategory("sedimental");
    public static final MnItemCategory LOGS = new MnItemCategory("logs");
    public static final MnItemCategory LEAVES = new MnItemCategory("leaves");
    public static final MnItemCategory SHROOM_CAPS = new MnItemCategory("shroom_caps");
    public static final MnItemCategory SHROOM_STEMS = new MnItemCategory("shroom_stems");
    public static final MnItemCategory SAPLINGS = new MnItemCategory("saplings");
    public static final MnItemCategory PLANKS = new MnItemCategory("planks");
    public static final MnItemCategory BRICKS = new MnItemCategory("bricks");
    public static final MnItemCategory COMMON_PLANTS = new MnItemCategory("common_plants");
    public static final MnItemCategory COMMON_ITEMS = new MnItemCategory("common_items");
    public static final MnItemCategory FOOD = new MnItemCategory("food");
    public static final MnItemCategory MINERALS = new MnItemCategory("minerals");
    public static final MnItemCategory MINERAL_BLOCKS = new MnItemCategory("mineral_blocks");
    public static final MnItemCategory CRYSTALS = new MnItemCategory("crystals");
    public static final MnItemCategory ORES = new MnItemCategory("ores");

    // These appear at the bottom of the item group
    public static final MnItemCategory UNCATEGORIZED = new MnItemCategory("uncategorized");

    private final List<Item> items = new ArrayList<>();

    public MnItemCategory(String id) {
    }

    public void fill(ItemGroup group, DefaultedList<ItemStack> list, Set<Item> doneItems) {
        for (Item item : items) {
            item.appendStacks(group, list);
            doneItems.add(item);
        }
    }

    public MnItemCategory add(Item item) {
        items.add(item);
        return this;
    }
}
