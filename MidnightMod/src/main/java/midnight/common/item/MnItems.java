/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.item;

import midnight.common.Midnight;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.core.util.IRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

/**
 * This class registers and stores all items of the Midnight.
 *
 * @version 0.6.0
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public final class MnItems {
    public static final Item DARK_STICK = inj();

    public static final Item DARK_PEARL = inj();
    public static final Item GEODE = inj();

    public static final Item NIGHTSHROOM_POWDER = inj();
    public static final Item DEWSHROOM_POWDER = inj();
    public static final Item VIRIDSHROOM_POWDER = inj();

    private MnItems() {
    }

    public static void registerItems(IRegistry<Item> registry) {
        registry.registerAll(
            item("dark_stick", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),

            item("dark_pearl", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("geode", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),

            item("nightshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("dewshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("viridshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC)
        );
    }

    private static Item item(String id, MnItemCategory cat, Item item) {
        item.setRegistryName(Midnight.resLoc(id));
        cat.add(item);
        return item;
    }

    private static Item item(String id, MnItemCategory cat, Item.Properties properties) {
        return item(id, cat, new Item(properties));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new Item.Properties().group(group));
    }

    private static Item edible(String id, MnItemCategory cat, ItemGroup group, Food food) {
        return item(id, cat, new Item.Properties().group(group).food(food));
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T extends Item> T inj() {
        return null;
    }
}
