/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.item.group;

import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Supplier;

/**
 * This class holds all the item groups for the Midnight's creative tabs, and for categorizing recipes in folders during
 * data generation.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MnItemGroup extends ItemGroup {
    public static final MnItemGroup BLOCKS = new MnItemGroup("blocks", () -> Blocks.AIR /*MnBlocks.NIGHT_GRASS_BLOCK*/).categories(
        MnItemCategory.SEDIMENTAL,
        MnItemCategory.LOGS,
        MnItemCategory.SHROOM_STEMS,
        MnItemCategory.SHROOM_CAPS,
        MnItemCategory.BRICKS,
        MnItemCategory.PLANKS,
        MnItemCategory.ORES,
        MnItemCategory.CRYSTALS,
        MnItemCategory.MINERAL_BLOCKS,
        MnItemCategory.UNCATEGORIZED
    );
    public static final MnItemGroup DECOR = new MnItemGroup("decor", () -> Blocks.AIR /*MnBlocks.NIGHT_GRASS*/).categories(
        MnItemCategory.LEAVES,
        MnItemCategory.SAPLINGS,
        MnItemCategory.COMMON_PLANTS,
        MnItemCategory.CRYSTALS,
        MnItemCategory.UNCATEGORIZED
    );
    public static final MnItemGroup MISC = new MnItemGroup("misc", () -> Blocks.AIR /*MnItems.DARK_STICK*/).categories(
        MnItemCategory.COMMON_ITEMS,
        MnItemCategory.FOOD,
        MnItemCategory.CRYSTALS,
        MnItemCategory.MINERALS,
        MnItemCategory.UNCATEGORIZED
    );

    private final Text translationKey;
    private final Supplier<ItemConvertible> icon;

    private final List<MnItemCategory> categories = new ArrayList<>();

    public MnItemGroup(String label, Supplier<ItemConvertible> icon) {
        super(expandArray(), label);
        this.translationKey = new TranslatableText("itemGroup.midnight." + label);
        this.icon = icon;
    }

    private static int expandArray() {
        ((ItemGroupExtensions) BUILDING_BLOCKS).fabric_expandArray();
        return GROUPS.length - 1;
    }

    @Override
    public Text getTranslationKey() {
        return translationKey;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon.get());
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> list) {
        Set<Item> doneItems = new HashSet<>();
        for (MnItemCategory cat : categories) { // Meow
            cat.fill(this, list, doneItems);
        }

        for (Item item : Registry.ITEM) {
            if (!doneItems.contains(item)) {
                item.appendStacks(this, list);
            }
        }
    }

    // Purr                     // Mieow
    public MnItemGroup categories(MnItemCategory... cats) {
        categories.addAll(Arrays.asList(cats /* Mreow */));
        return this;   // Murreow
    } // Meow
}
