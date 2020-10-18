/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.data.models;

import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import midnight.data.models.modelgen.IModelGen;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.InheritingModelGen.*;

public final class ItemModelTable {
    private static BiConsumer<Item, IModelGen> consumer;

    public static void registerItemModels(BiConsumer<Item, IModelGen> c) {
        consumer = c;

        register(MnBlocks.NIGHT_STONE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_BEDROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TRENCHSTONE, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_GRASS_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.COARSE_NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_MUD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_PEAT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRANGE_SAND, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHT_GRASS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_NIGHT_GRASS, item -> generated(name(item, "block/%s_upper")));

        register(MnBlocks.GHOST_PLANT, item -> generated(name(item, "block/%s")));
        register(MnBlocks.GHOST_PLANT_LEAF, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GHOST_PLANT_STEM, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.DEAD_WOOD_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DEAD_WOOD_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DEAD_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_WOOD_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.SHADOWROOT_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_SHADOWROOT_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_SHADOWROOT_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_LEAVES, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_LEAVES, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.HANGING_DARK_WILLOW_LEAVES, item -> generated(name(item, "block/%s_item")));
        register(MnBlocks.DARK_WILLOW_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.NIGHTSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_SHELF, item -> generated(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_NIGHTSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnItems.NIGHTSHROOM_POWDER, item -> generated(name(item, "item/%s")));

        register(MnBlocks.DEWSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM_SHELF, item -> generated(name(item, "block/%s")));
        register(MnBlocks.DEWSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_DEWSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnItems.DEWSHROOM_POWDER, item -> generated(name(item, "item/%s")));

        register(MnBlocks.VIRIDSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM_SHELF, item -> generated(name(item, "block/%s")));
        register(MnBlocks.VIRIDSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_VIRIDSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnItems.VIRIDSHROOM_POWDER, item -> generated(name(item, "item/%s")));

        register(MnItems.DARK_STICK, item -> generated(name(item, "item/%s")));
        register(MnItems.DARK_PEARL, item -> generated(name(item, "item/%s")));
        register(MnItems.GEODE, item -> generated(name(item, "item/%s")));

        register(MnBlocks.DARK_PEARL_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_PEARL_BLOCK, item -> inherit(name(item, "block/%s")));
    }



    private static void register(IItemProvider provider, Function<Item, IModelGen> genFactory) {
        Item item = provider.asItem();
        IModelGen gen = genFactory.apply(item);
        consumer.accept(item, gen);
    }

    private static String name(Item item, String nameFormat) {
        ResourceLocation id = item.getRegistryName();
        assert id != null;

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Item item) {
        ResourceLocation id = item.getRegistryName();
        assert id != null;
        return id.toString();
    }

    private ItemModelTable() {
    }
}
