/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.item;

import midnight.common.Midnight;
import midnight.common.block.ICustomBlockItem;
import midnight.common.block.MnBlocks;
import midnight.common.entity.ThrownGeodeEntity;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.common.misc.MnSoundEvents;
import midnight.core.util.IRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * This class registers and stores all items of the Midnight.
 *
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public abstract class MnItems {
    private static final Map<ResourceLocation, Item> ITEMS = new HashMap<>();

    //////////////////
    ///// BLOCKS /////
    //////////////////

    // Basic stones
    public static final Item NIGHTSTONE = item(MnBlocks.NIGHTSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item NIGHT_BEDROCK = item(MnBlocks.NIGHT_BEDROCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item TRENCHSTONE = item(MnBlocks.TRENCHSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);

    // Basic soils
    public static final Item NIGHT_DIRT = item(MnBlocks.NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item COARSE_NIGHT_DIRT = item(MnBlocks.COARSE_NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item NIGHT_GRASS_BLOCK = item(MnBlocks.NIGHT_GRASS_BLOCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item DECEITFUL_PEAT = item(MnBlocks.DECEITFUL_PEAT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item DECEITFUL_MUD = item(MnBlocks.DECEITFUL_MUD, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item STRANGE_SAND = item(MnBlocks.STRANGE_SAND, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);
    public static final Item NIGHT_MYCELIUM = item(MnBlocks.NIGHT_MYCELIUM, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS);

    // Bricks
    public static final Item NIGHTSTONE_BRICKS = item(MnBlocks.NIGHTSTONE_BRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS);
    public static final Item TRENCHSTONE_BRICKS = item(MnBlocks.TRENCHSTONE_BRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS);
    public static final Item SHROOMBRICKS = item(MnBlocks.SHROOMBRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS);

    // Tall night grass
    public static final Item NIGHT_GRASS = item(MnBlocks.NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_NIGHT_GRASS = item(MnBlocks.TALL_NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Ghost plant
    public static final Item GHOST_PLANT_STEM = item(MnBlocks.GHOST_PLANT_STEM, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);
    public static final Item GHOST_PLANT_LEAF = item(MnBlocks.GHOST_PLANT_LEAF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);
    public static final Item GHOST_PLANT = item(MnBlocks.GHOST_PLANT, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Dead wood
    public static final Item DEAD_WOOD_LOG = item(MnBlocks.DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_DEAD_WOOD_LOG = item(MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item DEAD_WOOD = item(MnBlocks.DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_DEAD_WOOD = item(MnBlocks.STRIPPED_DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item DEAD_WOOD_PLANKS = item(MnBlocks.DEAD_WOOD_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item DEAD_SAPLING = item(MnBlocks.DEAD_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR);

    // Shadowroot
    public static final Item SHADOWROOT_LOG = item(MnBlocks.SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_SHADOWROOT_LOG = item(MnBlocks.STRIPPED_SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item SHADOWROOT_WOOD = item(MnBlocks.SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_SHADOWROOT_WOOD = item(MnBlocks.STRIPPED_SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item SHADOWROOT_LEAVES = item(MnBlocks.SHADOWROOT_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR);
    public static final Item SHADOWROOT_PLANKS = item(MnBlocks.SHADOWROOT_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item SHADOWROOT_SAPLING = item(MnBlocks.SHADOWROOT_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR);
    public static final Item SHADOWROOT_BOOKSHELF = item(MnBlocks.SHADOWROOT_BOOKSHELF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);

    // Dark willow
    public static final Item DARK_WILLOW_LOG = item(MnBlocks.DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_DARK_WILLOW_LOG = item(MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item DARK_WILLOW_WOOD = item(MnBlocks.DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item STRIPPED_DARK_WILLOW_WOOD = item(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS);
    public static final Item DARK_WILLOW_LEAVES = item(MnBlocks.DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR);
    public static final Item HANGING_DARK_WILLOW_LEAVES = item(MnBlocks.HANGING_DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR);
    public static final Item DARK_WILLOW_PLANKS = item(MnBlocks.DARK_WILLOW_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item DARK_WILLOW_SAPLING = item(MnBlocks.DARK_WILLOW_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR);
    public static final Item DARK_WILLOW_BOOKSHELF = item(MnBlocks.DARK_WILLOW_BOOKSHELF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);

    // Nightshroom
    public static final Item NIGHTSHROOM_CAP = item(MnBlocks.NIGHTSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item NIGHTSHROOM_STEM = item(MnBlocks.NIGHTSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item NIGHTSHROOM_PLANKS = item(MnBlocks.NIGHTSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item NIGHTSHROOM = item(MnBlocks.NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_NIGHTSHROOM = item(MnBlocks.TALL_NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item NIGHTSHROOM_SHELF = item(MnBlocks.NIGHTSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item NIGHTSHROOM_FIBRE = item(MnBlocks.NIGHTSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item NIGHTSHROOM_ROOTS = item(MnBlocks.NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item FLOWERING_NIGHTSHROOM_ROOTS = item(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Dewshroom
    public static final Item DEWSHROOM_CAP = item(MnBlocks.DEWSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item DEWSHROOM_STEM = item(MnBlocks.DEWSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item DEWSHROOM_PLANKS = item(MnBlocks.DEWSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item DEWSHROOM = item(MnBlocks.DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_DEWSHROOM = item(MnBlocks.TALL_DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item DEWSHROOM_SHELF = item(MnBlocks.DEWSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item DEWSHROOM_FIBRE = item(MnBlocks.DEWSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item DEWSHROOM_ROOTS = item(MnBlocks.DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item FLOWERING_DEWSHROOM_ROOTS = item(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Viridshroom
    public static final Item VIRIDSHROOM_CAP = item(MnBlocks.VIRIDSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item VIRIDSHROOM_STEM = item(MnBlocks.VIRIDSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item VIRIDSHROOM_PLANKS = item(MnBlocks.VIRIDSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item VIRIDSHROOM = item(MnBlocks.VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_VIRIDSHROOM = item(MnBlocks.TALL_VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item VIRIDSHROOM_SHELF = item(MnBlocks.VIRIDSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item VIRIDSHROOM_FIBRE = item(MnBlocks.VIRIDSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item VIRIDSHROOM_ROOTS = item(MnBlocks.VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item FLOWERING_VIRIDSHROOM_ROOTS = item(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Moonshroom
    public static final Item MOONSHROOM_CAP = item(MnBlocks.MOONSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item MOONSHROOM_STEM = item(MnBlocks.MOONSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item MOONSHROOM_PLANKS = item(MnBlocks.MOONSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item MOONSHROOM = item(MnBlocks.MOONSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_MOONSHROOM = item(MnBlocks.TALL_MOONSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item MOONSHROOM_SHELF = item(MnBlocks.MOONSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item MOONSHROOM_FIBRE = item(MnBlocks.MOONSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item MOONSHROOM_ROOTS = item(MnBlocks.MOONSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item FLOWERING_MOONSHROOM_ROOTS = item(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Bogshroom
    public static final Item BOGSHROOM_CAP = item(MnBlocks.BOGSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item BOGSHROOM_STEM = item(MnBlocks.BOGSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item BOGSHROOM_PLANKS = item(MnBlocks.BOGSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item BOGSHROOM = item(MnBlocks.BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_BOGSHROOM = item(MnBlocks.TALL_BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item BOGSHROOM_SHELF = item(MnBlocks.BOGSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item BOGSHROOM_FIBRE = item(MnBlocks.BOGSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Glob fungus
    public static final Item GLOB_FUNGUS = item(MnBlocks.GLOB_FUNGUS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item GLOB_FUNGUS_CAP = item(MnBlocks.GLOB_FUNGUS_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);
    public static final Item GLOB_FUNGUS_STEM = item(MnBlocks.GLOB_FUNGUS_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item INFESTED_GLOB_FUNGUS_STEM = item(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item GLOB_FUNGUS_HYPHAE = item(MnBlocks.GLOB_FUNGUS_HYPHAE, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS);
    public static final Item GLOB_FUNGUS_THATCH = item(MnBlocks.GLOB_FUNGUS_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);

    // Misc plants
    public static final Item MISTSHROOM = item(MnBlocks.MISTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_MISTSHROOM = item(MnBlocks.TALL_MISTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item FINGERED_GRASS = item(MnBlocks.FINGERED_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item LUMEN_BUD = item(MnBlocks.LUMEN_BUD, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TALL_LUMEN_BUD = item(MnBlocks.TALL_LUMEN_BUD, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item RUNEBUSH = item(MnBlocks.RUNEBUSH, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item BOGWEED = item(MnBlocks.BOGWEED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item CRYSTALOTUS = item(MnBlocks.CRYSTALOTUS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item SUAVIS = item(MnBlocks.SUAVIS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item VIOLEAF = item(MnBlocks.VIOLEAF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item TENDRILWEED = item(MnBlocks.TENDRILWEED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item NIGHT_REED = item(MnBlocks.NIGHT_REED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item DECEITFUL_MOSS = item(MnBlocks.DECEITFUL_MOSS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);
    public static final Item DECEITFUL_ALGAE = item(MnBlocks.DECEITFUL_ALGAE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    public static final Item REED_THATCH = item(MnBlocks.REED_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);
    public static final Item CUT_REED_THATCH = item(MnBlocks.CUT_REED_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS);

    // Rockshroom
    public static final Item ROCKSHROOM = item(MnBlocks.ROCKSHROOM, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS);

    // Crystals
    public static final Item ROUXE_ROCK = item(MnBlocks.ROUXE_ROCK, MnItemCategory.CRYSTALS, MnItemGroup.BLOCKS);
    public static final Item BLOOMCRYSTAL_ROCK = item(MnBlocks.BLOOMCRYSTAL_ROCK, MnItemCategory.CRYSTALS, MnItemGroup.BLOCKS);
    public static final Item ROUXE = item(MnBlocks.ROUXE, MnItemCategory.CRYSTALS, MnItemGroup.DECOR);
    public static final Item BLOOMCRYSTAL = item(MnBlocks.BLOOMCRYSTAL, MnItemCategory.CRYSTALS, MnItemGroup.DECOR);
    public static final Item CRYSTAL_FLOWER = item(MnBlocks.CRYSTAL_FLOWER, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR);

    // Dark pearl
    public static final Item DARK_PEARL_ORE = item(MnBlocks.DARK_PEARL_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item DARK_PEARL_BLOCK = item(MnBlocks.DARK_PEARL_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS);

    //Strange Glass
    public static final Item STRANGE_GLASS = item(MnBlocks.STRANGE_GLASS, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);
    public static final Item STRANGE_GLASS_PANE = item(MnBlocks.STRANGE_GLASS_PANE, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);

    // Archaic
    public static final Item ARCHAIC_ORE = item(MnBlocks.ARCHAIC_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item ARCHAIC_GLASS = item(MnBlocks.ARCHAIC_GLASS, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);
    public static final Item ARCHAIC_GLASS_PANE = item(MnBlocks.ARCHAIC_GLASS_PANE, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS);

    // Tenebrum
    public static final Item TENEBRUM_ORE = item(MnBlocks.TENEBRUM_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item TENEBRUM_BLOCK = item(MnBlocks.TENEBRUM_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS);

    // Nagrilite
    public static final Item NAGRILITE_ORE = item(MnBlocks.NAGRILITE_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item NAGRILITE_BLOCK = item(MnBlocks.NAGRILITE_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS);

    // Ebonite
    public static final Item EBONITE_ORE = item(MnBlocks.EBONITE_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item EBONITE_BLOCK = item(MnBlocks.EBONITE_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS);

    // Virilux
    public static final Item VIRILUX_ORE = item(MnBlocks.VIRILUX_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS);
    public static final Item VIRILUX_BLOCK = item(MnBlocks.VIRILUX_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS);




    /////////////////
    ///// ITEMS /////
    /////////////////

    public static final Item DARK_STICK = item("dark_stick", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);

    public static final Item DARK_PEARL = item("dark_pearl", MnItemCategory.MINERALS, MnItemGroup.MISC, 16);
    public static final Item GEODE = geode("geode", MnItemCategory.MINERALS, MnItemGroup.MISC);

    public static final Item TENEBRUM_INGOT = item("tenebrum_ingot", MnItemCategory.MINERALS, MnItemGroup.MISC);
    public static final Item TENEBRUM_NUGGET = item("tenebrum_nugget", MnItemCategory.MINERALS, MnItemGroup.MISC);

    public static final Item NAGRILITE_INGOT = item("nagrilite_ingot", MnItemCategory.MINERALS, MnItemGroup.MISC);
    public static final Item NAGRILITE_NUGGET = item("nagrilite_nugget", MnItemCategory.MINERALS, MnItemGroup.MISC);

    public static final Item EBONITE = item("ebonite", MnItemCategory.MINERALS, MnItemGroup.MISC);

    public static final Item VIRILUX = item("virilux", MnItemCategory.MINERALS, MnItemGroup.MISC);

    public static final Item NIGHTSHROOM_POWDER = item("nightshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);
    public static final Item DEWSHROOM_POWDER = item("dewshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);
    public static final Item VIRIDSHROOM_POWDER = item("viridshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);
    public static final Item BOGSHROOM_POWDER = item("bogshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);
    public static final Item MOONSHROOM_POWDER = item("moonshroom_powder", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);

    public static final Item GLOB_FUNGUS_HAND = edible("glob_fungus_hand", MnItemCategory.FOOD, MnItemGroup.MISC, MnFoods.GLOB_FUNGUS_HAND);

    public static final Item ROCKSHROOM_CLUMP = item("rockshroom_clump", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);

    public static final Item RAW_SUAVIS = rawSuavis("raw_suavis", MnItemCategory.FOOD, MnItemGroup.MISC);
    public static final Item COOKED_SUAVIS = edible("cooked_suavis", MnItemCategory.FOOD, MnItemGroup.MISC, MnFoods.COOKED_SUAVIS);
    public static final Item ARCHAIC_SHARD = item("archaic_shard", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC);

    private MnItems() {
    }

    public static void registerItems(IRegistry<Item> registry) {
        registry.registerAll(ITEMS);
    }

    private static Item item(Block block, MnItemCategory cat, Item.Properties props) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;

        Item item;
        if (block instanceof ICustomBlockItem) {
            item = ((ICustomBlockItem) block).newBlockItem(props);
        } else {
            item = new BlockItem(block, props);
        }
        cat.add(item); // Meow
        ITEMS.put(id, item);
        item.setRegistryName(id);
        return item;
    }

    private static Item item(Block block, MnItemCategory cat, ItemGroup group) {
        return item(block, cat, new Item.Properties().tab(group));
    }

    private static Item item(String id, MnItemCategory cat, Item item) {
        cat.add(item); // Meow
        ITEMS.put(Midnight.id(id), item);
        item.setRegistryName(Midnight.id(id));
        return item;
    }

    private static Item item(String id, MnItemCategory cat, Item.Properties properties) {
        return item(id, cat, new Item(properties));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new Item.Properties().tab(group));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group, int stackability) {
        return item(id, cat, new Item.Properties().tab(group).stacksTo(stackability));
    }

    private static Item edible(String id, MnItemCategory cat, ItemGroup group, Food food) {
        return item(id, cat, new Item.Properties().tab(group).food(food));
    }

    private static Item rawSuavis(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new RawSuavisItem(new Item.Properties().tab(group).food(MnFoods.RAW_SUAVIS)));
    }

    private static Item geode(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new ThrowableItem(
            new Item.Properties().tab(group).stacksTo(16),
            () -> MnSoundEvents.ENTITY_GEODE_THROW,
            ThrownGeodeEntity::new
        ));
    }
}
