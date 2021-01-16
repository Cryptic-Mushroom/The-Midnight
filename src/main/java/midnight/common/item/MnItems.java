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

import javax.annotation.Nonnull;

/**
 * This class registers and stores all items of the Midnight.
 *
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public abstract class MnItems {

    //////////////////
    ///// BLOCKS /////
    //////////////////

    // Basic stones
    public static final Item NIGHTSTONE = inj();
    public static final Item NIGHT_BEDROCK = inj();
    public static final Item TRENCHSTONE = inj();

    // Basic soils
    public static final Item NIGHT_DIRT = inj();
    public static final Item COARSE_NIGHT_DIRT = inj();
    public static final Item NIGHT_GRASS_BLOCK = inj();
    public static final Item DECEITFUL_PEAT = inj();
    public static final Item DECEITFUL_MUD = inj();
    public static final Item STRANGE_SAND = inj();
    public static final Item NIGHT_MYCELIUM = inj();

    // Bricks
    public static final Item NIGHTSTONE_BRICKS = inj();
    public static final Item TRENCHSTONE_BRICKS = inj();
    public static final Item SHROOMBRICKS = inj();

    // Fluids
    public static final Item DARK_WATER = inj();

    // Tall night grass
    public static final Item NIGHT_GRASS = inj();
    public static final Item TALL_NIGHT_GRASS = inj();

    // Ghost plant
    public static final Item GHOST_PLANT_STEM = inj();
    public static final Item GHOST_PLANT_LEAF = inj();
    public static final Item GHOST_PLANT = inj();

    // Dead wood
    public static final Item DEAD_WOOD_LOG = inj();
    public static final Item STRIPPED_DEAD_WOOD_LOG = inj();
    public static final Item DEAD_WOOD = inj();
    public static final Item STRIPPED_DEAD_WOOD = inj();
    public static final Item DEAD_WOOD_PLANKS = inj();
    public static final Item DEAD_SAPLING = inj();

    // Shadowroot
    public static final Item SHADOWROOT_LOG = inj();
    public static final Item STRIPPED_SHADOWROOT_LOG = inj();
    public static final Item SHADOWROOT_WOOD = inj();
    public static final Item STRIPPED_SHADOWROOT_WOOD = inj();
    public static final Item SHADOWROOT_LEAVES = inj();
    public static final Item SHADOWROOT_PLANKS = inj();
    public static final Item SHADOWROOT_SAPLING = inj();
    public static final Item SHADOWROOT_BOOKSHELF = inj();

    // Dark willow
    public static final Item DARK_WILLOW_LOG = inj();
    public static final Item STRIPPED_DARK_WILLOW_LOG = inj();
    public static final Item DARK_WILLOW_WOOD = inj();
    public static final Item STRIPPED_DARK_WILLOW_WOOD = inj();
    public static final Item DARK_WILLOW_LEAVES = inj();
    public static final Item HANGING_DARK_WILLOW_LEAVES = inj();
    public static final Item DARK_WILLOW_PLANKS = inj();
    public static final Item DARK_WILLOW_SAPLING = inj();
    public static final Item DARK_WILLOW_BOOKSHELF = inj();

    // Shroom air
    public static final Item SHROOM_AIR = inj();

    // Nightshroom
    public static final Item NIGHTSHROOM_CAP = inj();
    public static final Item NIGHTSHROOM_STEM = inj();
    public static final Item NIGHTSHROOM_PLANKS = inj();
    public static final Item NIGHTSHROOM = inj();
    public static final Item TALL_NIGHTSHROOM = inj();
    public static final Item NIGHTSHROOM_SHELF = inj();
    public static final Item NIGHTSHROOM_FIBRE = inj();
    public static final Item NIGHTSHROOM_ROOTS = inj();
    public static final Item FLOWERING_NIGHTSHROOM_ROOTS = inj();

    // Dewshroom
    public static final Item DEWSHROOM_CAP = inj();
    public static final Item DEWSHROOM_STEM = inj();
    public static final Item DEWSHROOM_PLANKS = inj();
    public static final Item DEWSHROOM = inj();
    public static final Item TALL_DEWSHROOM = inj();
    public static final Item DEWSHROOM_SHELF = inj();
    public static final Item DEWSHROOM_FIBRE = inj();
    public static final Item DEWSHROOM_ROOTS = inj();
    public static final Item FLOWERING_DEWSHROOM_ROOTS = inj();

    // Viridshroom
    public static final Item VIRIDSHROOM_CAP = inj();
    public static final Item VIRIDSHROOM_STEM = inj();
    public static final Item VIRIDSHROOM_PLANKS = inj();
    public static final Item VIRIDSHROOM = inj();
    public static final Item TALL_VIRIDSHROOM = inj();
    public static final Item VIRIDSHROOM_SHELF = inj();
    public static final Item VIRIDSHROOM_FIBRE = inj();
    public static final Item VIRIDSHROOM_ROOTS = inj();
    public static final Item FLOWERING_VIRIDSHROOM_ROOTS = inj();

    // Moonshroom
    public static final Item MOONSHROOM_CAP = inj();
    public static final Item MOONSHROOM_STEM = inj();
    public static final Item MOONSHROOM_PLANKS = inj();
    public static final Item MOONSHROOM = inj();
    public static final Item TALL_MOONSHROOM = inj();
    public static final Item MOONSHROOM_SHELF = inj();
    public static final Item MOONSHROOM_FIBRE = inj();
    public static final Item MOONSHROOM_ROOTS = inj();
    public static final Item FLOWERING_MOONSHROOM_ROOTS = inj();

    // Bogshroom
    public static final Item BOGSHROOM_CAP = inj();
    public static final Item BOGSHROOM_STEM = inj();
    public static final Item BOGSHROOM_PLANKS = inj();
    public static final Item BOGSHROOM = inj();
    public static final Item TALL_BOGSHROOM = inj();
    public static final Item BOGSHROOM_SHELF = inj();
    public static final Item BOGSHROOM_FIBRE = inj();

    // Glob fungus
    public static final Item GLOB_FUNGUS = inj();
    public static final Item GLOB_FUNGUS_CAP = inj();
    public static final Item GLOB_FUNGUS_STEM = inj();
    public static final Item INFESTED_GLOB_FUNGUS_STEM = inj();
    public static final Item GLOB_FUNGUS_HYPHAE = inj();
    public static final Item GLOB_FUNGUS_THATCH = inj();

    // Misc plants
    public static final Item MISTSHROOM = inj();
    public static final Item TALL_MISTSHROOM = inj();
    public static final Item FINGERED_GRASS = inj();
    public static final Item LUMEN_BUD = inj();
    public static final Item TALL_LUMEN_BUD = inj();
    public static final Item RUNEBUSH = inj();
    public static final Item BOGWEED = inj();
    public static final Item CRYSTALOTUS = inj();
    public static final Item SUAVIS = inj();
    public static final Item VIOLEAF = inj();
    public static final Item TENDRILWEED = inj();
    public static final Item NIGHT_REED = inj();
    public static final Item DECEITFUL_MOSS = inj();
    public static final Item DECEITFUL_ALGAE = inj();

    public static final Item REED_THATCH = inj();
    public static final Item CUT_REED_THATCH = inj();

    // Rockshroom
    public static final Item ROCKSHROOM = inj();

    // Crystals
    public static final Item ROUXE_ROCK = inj();
    public static final Item BLOOMCRYSTAL_ROCK = inj();
    public static final Item ROUXE = inj();
    public static final Item BLOOMCRYSTAL = inj();
    public static final Item CRYSTAL_FLOWER = inj();

    // Dark pearl
    public static final Item DARK_PEARL_ORE = inj();
    public static final Item DARK_PEARL_BLOCK = inj();

    // Archaic
    public static final Item ARCHAIC_ORE = inj();
    public static final Item ARCHAIC_GLASS = inj();
    public static final Item ARCHAIC_GLASS_PANE = inj();

    // Tenebrum
    public static final Item TENEBRUM_ORE = inj();
    public static final Item TENEBRUM_BLOCK = inj();

    // Nagrilite
    public static final Item NAGRILITE_ORE = inj();
    public static final Item NAGRILITE_BLOCK = inj();

    // Ebonite
    public static final Item EBONITE_ORE = inj();
    public static final Item EBONITE_BLOCK = inj();

    // Virilux
    public static final Item VIRILUX_ORE = inj();
    public static final Item VIRILUX_BLOCK = inj();


    /////////////////
    ///// ITEMS /////
    /////////////////

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
            item(MnBlocks.NIGHTSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHT_BEDROCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.TRENCHSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),

            item(MnBlocks.NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.COARSE_NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHT_GRASS_BLOCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.DECEITFUL_PEAT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.DECEITFUL_MUD, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.STRANGE_SAND, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHT_MYCELIUM, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),

            item(MnBlocks.NIGHTSTONE_BRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS),
            item(MnBlocks.TRENCHSTONE_BRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS),
            item(MnBlocks.SHROOMBRICKS, MnItemCategory.BRICKS, MnItemGroup.BLOCKS),

            item(MnBlocks.NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.GHOST_PLANT_STEM, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(MnBlocks.GHOST_PLANT_LEAF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(MnBlocks.GHOST_PLANT, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEAD_WOOD_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEAD_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),

            item(MnBlocks.SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.SHADOWROOT_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(MnBlocks.SHADOWROOT_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.SHADOWROOT_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),
            item(MnBlocks.SHADOWROOT_BOOKSHELF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),

            item(MnBlocks.DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(MnBlocks.DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(MnBlocks.HANGING_DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(MnBlocks.DARK_WILLOW_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.DARK_WILLOW_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),
            item(MnBlocks.DARK_WILLOW_BOOKSHELF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),

            item(MnBlocks.NIGHTSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHTSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHTSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.NIGHTSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.NIGHTSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.DEWSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEWSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEWSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.DEWSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.DEWSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.VIRIDSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.VIRIDSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.VIRIDSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.VIRIDSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.VIRIDSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.MOONSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.MOONSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.MOONSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.MOONSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_MOONSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.MOONSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.MOONSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.MOONSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.BOGSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.BOGSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.BOGSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.BOGSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.BOGSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.MISTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_MISTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.FINGERED_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.LUMEN_BUD, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TALL_LUMEN_BUD, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.RUNEBUSH, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.BOGWEED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.CRYSTALOTUS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.SUAVIS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.VIOLEAF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.TENDRILWEED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.NIGHT_REED, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.DECEITFUL_MOSS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.DECEITFUL_ALGAE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.REED_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(MnBlocks.CUT_REED_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),

            item(MnBlocks.GLOB_FUNGUS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(MnBlocks.GLOB_FUNGUS_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(MnBlocks.GLOB_FUNGUS_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.GLOB_FUNGUS_HYPHAE, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(MnBlocks.GLOB_FUNGUS_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),

            item(MnBlocks.ROCKSHROOM, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),

            item(MnBlocks.ROUXE_ROCK, MnItemCategory.CRYSTALS, MnItemGroup.BLOCKS),
            item(MnBlocks.BLOOMCRYSTAL_ROCK, MnItemCategory.CRYSTALS, MnItemGroup.BLOCKS),
            item(MnBlocks.ROUXE, MnItemCategory.CRYSTALS, MnItemGroup.DECOR),
            item(MnBlocks.BLOOMCRYSTAL, MnItemCategory.CRYSTALS, MnItemGroup.DECOR),
            item(MnBlocks.CRYSTAL_FLOWER, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(MnBlocks.DARK_PEARL_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.DARK_PEARL_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS),

            item(MnBlocks.ARCHAIC_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.ARCHAIC_GLASS, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(MnBlocks.ARCHAIC_GLASS_PANE, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),

            item(MnBlocks.TENEBRUM_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.TENEBRUM_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS),

            item(MnBlocks.NAGRILITE_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.NAGRILITE_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS),

            item(MnBlocks.EBONITE_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.EBONITE_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS),

            item(MnBlocks.VIRILUX_ORE, MnItemCategory.ORES, MnItemGroup.BLOCKS),
            item(MnBlocks.VIRILUX_BLOCK, MnItemCategory.MINERAL_BLOCKS, MnItemGroup.BLOCKS)
        );

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

    private static BlockItem item(Block block, MnItemCategory cat, Item.Properties props) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;
        BlockItem item;
        if (block instanceof ICustomBlockItem) {
            item = ((ICustomBlockItem) block).newBlockItem(props);
        } else {
            item = new BlockItem(block, props);
        }
        item.setRegistryName(id);
        cat.add(item);
        return item;
    }

    private static BlockItem item(Block block, MnItemCategory cat, ItemGroup group) {
        return item(block, cat, new Item.Properties().group(group));
    }

    private static Item item(String id, MnItemCategory cat, Item item) {
        item.setRegistryName(Midnight.id(id));
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
