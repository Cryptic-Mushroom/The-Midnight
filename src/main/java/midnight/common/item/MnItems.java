/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.item;

import midnight.common.Midnight;
import midnight.common.block.CustomBlockItem;
import midnight.common.block.MnBlocks;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.core.parity.RegistryIds;
import midnight.core.util.WrappingRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public abstract class MnItems {
    private static final Map<Identifier, Item> ITEMS = new HashMap<>();

    //////////////////
    ///// BLOCKS /////
    //////////////////

    // Basic stones
    public static final Item NIGHTSTONE = item(MnBlocks.NIGHTSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item NIGHT_BEDROCK = item(MnBlocks.NIGHT_BEDROCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item TRENCHSTONE = item(MnBlocks.TRENCHSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);

    private MnItems() {
    }

    public static void registerItems(WrappingRegistry<Item> registry) {
        registry.registerAll(ITEMS);
    }

    private static Item item(Block block, MnItemCategory cat, Item.Settings props) {
        Identifier id = RegistryIds.get(block);
        assert id != null;

        Item item;
        if (block instanceof CustomBlockItem) {
            item = ((CustomBlockItem) block).newBlockItem(props);
        } else {
            item = new BlockItem(block, props);
        }
        cat.add(item); // Meow
        ITEMS.put(id, item);
        return item;
    }

    private static Item item(Block block, MnItemCategory cat, ItemGroup group) {
        return item(block, cat, new Item.Settings().group(group));
    }

    private static Item item(String id, MnItemCategory cat, Item item) {
        cat.add(item); // Meow
        ITEMS.put(Midnight.id(id), item);
        return item;
    }

    private static Item item(String id, MnItemCategory cat, Item.Settings properties) {
        return item(id, cat, new Item(properties));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new Item.Settings().group(group));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group, int stackability) {
        return item(id, cat, new Item.Settings().group(group).maxCount(stackability));
    }

    private static Item edible(String id, MnItemCategory cat, ItemGroup group, FoodComponent food) {
        return item(id, cat, new Item.Settings().group(group).food(food));
    }
}
