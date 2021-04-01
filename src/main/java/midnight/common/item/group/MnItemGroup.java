/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.item.group;

import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import midnight.core.util.MnObjects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
    public static final MnItemGroup BLOCKS = new MnItemGroup("blocks", () -> MnBlocks.NIGHT_GRASS_BLOCK).categories(
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
    public static final MnItemGroup DECOR = new MnItemGroup("decor", () -> MnBlocks.NIGHT_GRASS).categories(
        MnItemCategory.LEAVES,
        MnItemCategory.SAPLINGS,
        MnItemCategory.COMMON_PLANTS,
        MnItemCategory.CRYSTALS,
        MnItemCategory.UNCATEGORIZED
    );
    public static final MnItemGroup MISC = new MnItemGroup("misc", () -> MnItems.DARK_STICK).categories(
        MnItemCategory.COMMON_ITEMS,
        MnItemCategory.FOOD,
        MnItemCategory.CRYSTALS,
        MnItemCategory.MINERALS,
        MnItemCategory.UNCATEGORIZED
    );

    private final ITextComponent translationKey;
    private final Supplier<IItemProvider> icon;

    private final List<MnItemCategory> categories = new ArrayList<>();

    public MnItemGroup(String label, Supplier<IItemProvider> icon) {
        super(label);
        this.translationKey = new TranslationTextComponent("itemGroup.midnight." + label);
        this.icon = icon;
        MnObjects.addItemGroup(label, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return translationKey;
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(icon.get());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void fillItemList(NonNullList<ItemStack> list) {
        Set<Item> doneItems = new HashSet<>();
        for (MnItemCategory cat : categories) { // Meow
            cat.fill(this, list, doneItems);
        }

        for (Item item : Registry.ITEM) {
            if (!doneItems.contains(item)) {
                item.fillItemCategory(this, list);
            }
        }
    }

    // Purr                     // Mieow
    public MnItemGroup categories(MnItemCategory... cats) {
        categories.addAll(Arrays.asList(cats /* Mreow */));
        return this;   // Murreow
    } // Meow
}
