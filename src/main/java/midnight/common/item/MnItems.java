/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.item;

import midnight.common.Midnight;
import midnight.common.entity.ThrownGeodeEntity;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.common.misc.MnSoundEvents;
import midnight.core.util.IRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

/**
 * This class registers and stores all items of the Midnight.
 *
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public abstract class MnItems {
    public static final Item DARK_STICK = inj();

    public static final Item DARK_PEARL = inj();
    public static final Item GEODE = inj();

    public static final Item TENEBRUM_INGOT = inj();
    public static final Item TENEBRUM_NUGGET = inj();

    public static final Item NAGRILITE_INGOT = inj();
    public static final Item NAGRILITE_NUGGET = inj();

    public static final Item EBONITE = inj();

    public static final Item VIRILUX = inj();

    public static final Item NIGHTSHROOM_POWDER = inj();
    public static final Item DEWSHROOM_POWDER = inj();
    public static final Item VIRIDSHROOM_POWDER = inj();
    public static final Item BOGSHROOM_POWDER = inj();
    public static final Item MOONSHROOM_POWDER = inj();

    public static final Item GLOB_FUNGUS_HAND = inj();

    public static final Item ROCKSHROOM_CLUMP = inj();

    public static final Item RAW_SUAVIS = inj();
    public static final Item COOKED_SUAVIS = inj();

    public static final Item ARCHAIC_SHARD = inj();

    private MnItems() {
    }

    public static void registerItems(IRegistry<Item> registry) {
        registry.registerAll(
            item("dark_stick", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),

            item("dark_pearl", MnItemCategory.MINERALS, MnItemGroup.MISC, 16),
            geode("geode", MnItemCategory.MINERALS, MnItemGroup.MISC),

            item("tenebrum_ingot", MnItemCategory.MINERALS, MnItemGroup.MISC),
            item("tenebrum_nugget", MnItemCategory.MINERALS, MnItemGroup.MISC),

            item("nagrilite_ingot", MnItemCategory.MINERALS, MnItemGroup.MISC),
            item("nagrilite_nugget", MnItemCategory.MINERALS, MnItemGroup.MISC),

            item("ebonite", MnItemCategory.MINERALS, MnItemGroup.MISC),

            item("virilux", MnItemCategory.MINERALS, MnItemGroup.MISC),

            item("nightshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("dewshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("viridshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("bogshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("moonshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),

            edible("glob_fungus_hand", MnItemCategory.FOOD, MnItemGroup.MISC, MnFoods.GLOB_FUNGUS_HAND),

            item("rockshroom_clump", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),

            rawSuavis("raw_suavis", MnItemCategory.FOOD, MnItemGroup.MISC),
            edible("cooked_suavis", MnItemCategory.FOOD, MnItemGroup.MISC, MnFoods.COOKED_SUAVIS),
            item("archaic_shard", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC)
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

    private static Item item(String id, MnItemCategory cat, ItemGroup group, int stackability) {
        return item(id, cat, new Item.Properties().group(group).maxStackSize(stackability));
    }

    private static Item edible(String id, MnItemCategory cat, ItemGroup group, Food food) {
        return item(id, cat, new Item.Properties().group(group).food(food));
    }

    private static Item rawSuavis(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new RawSuavisItem(new Item.Properties().group(group).food(MnFoods.RAW_SUAVIS)));
    }

    private static Item geode(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new ThrowableItem(
            new Item.Properties().group(group).maxStackSize(16),
            () -> MnSoundEvents.ENTITY_GEODE_THROW,
            ThrownGeodeEntity::new
        ));
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T extends Item> T inj() {
        return null;
    }
}
