/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.common.item.group;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MnItemCategory {
    public static final MnItemCategory SEDIMENTAL = new MnItemCategory();
    public static final MnItemCategory LOGS = new MnItemCategory();
    public static final MnItemCategory LEAVES = new MnItemCategory();
    public static final MnItemCategory SHROOM_CAPS = new MnItemCategory();
    public static final MnItemCategory SHROOM_STEMS = new MnItemCategory();
    public static final MnItemCategory SAPLINGS = new MnItemCategory();
    public static final MnItemCategory PLANKS = new MnItemCategory();
    public static final MnItemCategory COMMON_PLANTS = new MnItemCategory();
    public static final MnItemCategory COMMON_ITEMS = new MnItemCategory();

    // These appear at the bottom of the item group
    public static final MnItemCategory UNCATEGORIZED = new MnItemCategory();

    private final List<Item> items = new ArrayList<>();

    public void fill(ItemGroup group, NonNullList<ItemStack> list, Set<Item> doneItems) {
        for(Item item : items) {
            item.fillItemGroup(group, list);
            doneItems.add(item);
        }
    }

    public MnItemCategory add(Item item) {
        items.add(item);
        return this;
    }
}
