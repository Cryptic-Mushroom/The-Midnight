/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 3 - 22
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

        register(MnBlocks.NIGHTSTONE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_BEDROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TRENCHSTONE, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_MYCELIUM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_GRASS_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.COARSE_NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_MUD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_PEAT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRANGE_SAND, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHTSTONE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TRENCHSTONE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHROOMBRICKS, item -> inherit(name(item, "block/%s")));

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
        register(MnBlocks.SHADOWROOT_BOOKSHELF, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_LEAVES, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.HANGING_DARK_WILLOW_LEAVES, item -> generated(name(item, "block/%s_item")));
        register(MnBlocks.DARK_WILLOW_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_SAPLING, item -> generated(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_BOOKSHELF, item -> inherit(name(item, "block/%s")));

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

        register(MnBlocks.MOONSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM_SHELF, item -> generated(name(item, "block/%s")));
        register(MnBlocks.MOONSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_MOONSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnItems.MOONSHROOM_POWDER, item -> generated(name(item, "item/%s")));

        register(MnBlocks.BOGSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BOGSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BOGSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BOGSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.BOGSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.BOGSHROOM_SHELF, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_BOGSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnItems.BOGSHROOM_POWDER, item -> generated(name(item, "item/%s")));

        register(MnBlocks.MISTSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_MISTSHROOM, item -> generated(name(item, "block/%s_upper")));
        register(MnBlocks.FINGERED_GRASS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.LUMEN_BUD, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_LUMEN_BUD, item -> generated(name(item, "block/%s_upper")));
        register(MnBlocks.RUNEBUSH, item -> generated(name(item, "block/%s")));
        register(MnBlocks.BOGWEED, item -> generated(name(item, "block/%s")));
        register(MnBlocks.CRYSTALOTUS, item -> generated(name(item, "item/%s")));
        register(MnBlocks.SUAVIS, item -> inherit(name(item, "block/%s_stage_3")));
        register(MnItems.RAW_SUAVIS, item -> generated(name(item, "item/%s")));
        register(MnItems.COOKED_SUAVIS, item -> generated(name(item, "item/%s")));
        register(MnBlocks.VIOLEAF, item -> generated(name(item, "item/%s")));
        register(MnBlocks.TENDRILWEED, item -> generated(name(item, "block/%s")));
        register(MnBlocks.NIGHT_REED, item -> generated(name(item, "item/%s")));
        register(MnBlocks.DECEITFUL_MOSS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_ALGAE, item -> generated(name(item, "block/%s")));

        register(MnBlocks.GLOB_FUNGUS_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GLOB_FUNGUS_THATCH, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GLOB_FUNGUS_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GLOB_FUNGUS_HYPHAE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GLOB_FUNGUS, item -> generated(name(item, "block/%s")));
        register(MnItems.GLOB_FUNGUS_HAND, item -> generated(name(item, "item/%s")));

        register(MnBlocks.REED_THATCH, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.CUT_REED_THATCH, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.DARK_PEARL_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_PEARL_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnItems.DARK_PEARL, item -> generated(name(item, "item/%s")));
        register(MnItems.GEODE, item -> generated(name(item, "item/%s")));

        register(MnBlocks.TENEBRUM_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TENEBRUM_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnItems.TENEBRUM_INGOT, item -> generated(name(item, "item/%s")));
        register(MnItems.TENEBRUM_NUGGET, item -> generated(name(item, "item/%s")));

        register(MnBlocks.NAGRILITE_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NAGRILITE_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnItems.NAGRILITE_INGOT, item -> generated(name(item, "item/%s")));
        register(MnItems.NAGRILITE_NUGGET, item -> generated(name(item, "item/%s")));

        register(MnBlocks.EBONITE_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.EBONITE_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnItems.EBONITE, item -> generated(name(item, "item/%s")));

        register(MnBlocks.VIRILUX_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.VIRILUX_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnItems.VIRILUX, item -> generated(name(item, "item/%s")));

        register(MnBlocks.CORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_CORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.ACTIVE_NIGHTSTONE, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.ROCKSHROOM, item -> inherit(name(item, "block/%s")));
        register(MnItems.ROCKSHROOM_CLUMP, item -> generated(name(item, "item/%s")));

        register(MnBlocks.ROUXE_ROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BLOOMCRYSTAL_ROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.ROUXE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.BLOOMCRYSTAL, item -> generated(name(item, "block/%s")));
        register(MnBlocks.CRYSTAL_FLOWER, item -> generated(name(item, "block/%s")));

        register(MnItems.DARK_STICK, item -> generated(name(item, "item/%s")));

        register(MnBlocks.STRANGE_GLASS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRANGE_GLASS_PANE, item -> generated(name(item, "block/strange_glass")));

        register(MnItems.ARCHAIC_SHARD, item -> generated(name(item, "item/%s")));
        register(MnBlocks.ARCHAIC_ORE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.ARCHAIC_GLASS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.ARCHAIC_GLASS_PANE, item -> generated(name(item, "block/archaic_glass")));

        register(MnBlocks.BLOCK_OF_FURRY, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BLOCK_OF_FOXYGEN, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BRAINLESS_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BLOCK_OF_RYAN, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BLOCK_OF_NYA, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.BLOCK_OF_FYRE, item -> inherit(name(item, "block/%s")));

        register(MnItems.MISSING_BRAIN, item -> generated(name(item, "item/%s")));
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
