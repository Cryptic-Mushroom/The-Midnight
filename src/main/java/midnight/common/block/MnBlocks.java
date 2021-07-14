/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import midnight.api.util.GeodeHardMaterials;
import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.common.misc.tags.MnBlockTags;
import midnight.common.world.biome.MnBiomeColors;
import midnight.core.util.ColorUtil;
import midnight.core.util.IRegistry;
import midnight.core.util.MnUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

/**
 * This class registers and stores the list of Midnight blocks and their respective block items.
 *
 * @author Shadew
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public abstract class MnBlocks {
    private static final Map<ResourceLocation, Block> BLOCKS = new HashMap<>();


    ////////////////////////
    ///// BLOCK FIELDS /////
    ////////////////////////

    // Basic stones
    public static final Block NIGHTSTONE = stone("nightstone", 1.5, 6, MaterialColor.COLOR_BLACK);
    public static final Block NIGHT_BEDROCK = stone("night_bedrock", 1.5, 6, MaterialColor.TERRACOTTA_LIGHT_GRAY);
    public static final Block TRENCHSTONE = stone("trenchstone", 1.5, 6, MaterialColor.COLOR_BLACK);

    // Basic soils
    public static final Block NIGHT_DIRT = dirt("night_dirt", MaterialColor.COLOR_BLACK);
    public static final Block COARSE_NIGHT_DIRT = dirt("coarse_night_dirt", MaterialColor.COLOR_BLACK);
    public static final Block NIGHT_GRASS_BLOCK = grassBlock("night_grass_block");
    public static final Block DECEITFUL_PEAT = peat("deceitful_peat", MaterialColor.TERRACOTTA_PURPLE);
    public static final Block DECEITFUL_MUD = mud("deceitful_mud");
    public static final Block STRANGE_SAND = sand("strange_sand");
    public static final Block NIGHT_MYCELIUM = mycelium("night_mycelium", 2, 6, MaterialColor.COLOR_MAGENTA);

    // Bricks
    public static final Block NIGHTSTONE_BRICKS = bricks("nightstone_bricks", 1.5, 6, MaterialColor.COLOR_BLACK);
    public static final Block TRENCHSTONE_BRICKS = bricks("trenchstone_bricks", 1.5, 6, MaterialColor.COLOR_BLACK);
    public static final Block SHROOMBRICKS = shroombricks("shroombricks");

    // Tall night grass
    public static final Block NIGHT_GRASS = smallGrowable("night_grass", 0, 0, Material.REPLACEABLE_PLANT, MaterialColor.TERRACOTTA_PURPLE, getBlock("tall_night_grass")).hitbox(12, 13).offset(AbstractBlock.OffsetType.XYZ);
    public static final Block TALL_NIGHT_GRASS = tallPlant("tall_night_grass", 0, 0, Material.REPLACEABLE_PLANT, MaterialColor.TERRACOTTA_PURPLE).hitbox(14, 30).offset(AbstractBlock.OffsetType.XYZ);

    // Ghost plant
    public static final Block GHOST_PLANT_STEM = giantGhostPlant("ghost_plant_stem", GhostPlantStemBlock::new);
    public static final Block GHOST_PLANT_LEAF = giantGhostPlant("ghost_plant_leaf", GhostPlantBlock::new);
    public static final Block GHOST_PLANT = ghostPlant("ghost_plant", 0, 0, 9, Material.PLANT, MaterialColor.SNOW).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Dead wood
    public static final Block DEAD_WOOD_LOG = log("dead_wood_log", MaterialColor.PLANT, getBlock("stripped_dead_wood_log"));
    public static final Block STRIPPED_DEAD_WOOD_LOG = log("stripped_dead_wood_log", MaterialColor.PLANT);
    public static final Block DEAD_WOOD = log("dead_wood", MaterialColor.PLANT, getBlock("stripped_dead_wood"));
    public static final Block STRIPPED_DEAD_WOOD = log("stripped_dead_wood", MaterialColor.PLANT);
    public static final Block DEAD_WOOD_PLANKS = wood("dead_wood_planks", MaterialColor.PLANT);
    public static final Block DEAD_SAPLING = plant("dead_sapling", 0, 0, Material.PLANT, MaterialColor.PLANT).hitbox(12, 13);

    // Shadowroot
    public static final Block SHADOWROOT_LOG = log("shadowroot_log", MaterialColor.COLOR_PURPLE, getBlock("stripped_shadowroot_log"));
    public static final Block STRIPPED_SHADOWROOT_LOG = log("stripped_shadowroot_log", MaterialColor.COLOR_PURPLE);
    public static final Block SHADOWROOT_WOOD = log("shadowroot_wood", MaterialColor.COLOR_PURPLE, getBlock("stripped_shadowroot_wood"));
    public static final Block STRIPPED_SHADOWROOT_WOOD = log("stripped_shadowroot_wood", MaterialColor.COLOR_PURPLE);
    public static final Block SHADOWROOT_LEAVES = leaves("shadowroot_leaves", MaterialColor.COLOR_PURPLE);
    public static final Block SHADOWROOT_PLANKS = wood("shadowroot_planks", MaterialColor.COLOR_PURPLE);
    public static final Block SHADOWROOT_SAPLING = plant("shadowroot_sapling", 0, 0, Material.PLANT, MaterialColor.COLOR_PURPLE).hitbox(11, 15);
    public static final Block SHADOWROOT_BOOKSHELF = bookshelf("shadowroot_bookshelf", MaterialColor.COLOR_PURPLE);

    // Dark willow
    public static final Block DARK_WILLOW_LOG = log("dark_willow_log", MaterialColor.COLOR_BLUE, getBlock("stripped_dark_willow_log"));
    public static final Block STRIPPED_DARK_WILLOW_LOG = log("stripped_dark_willow_log", MaterialColor.COLOR_BLUE);
    public static final Block DARK_WILLOW_WOOD = log("dark_willow_wood", MaterialColor.COLOR_BLUE, getBlock("stripped_dark_willow_wood"));
    public static final Block STRIPPED_DARK_WILLOW_WOOD = log("stripped_dark_willow_wood", MaterialColor.COLOR_BLUE);
    public static final Block DARK_WILLOW_LEAVES = growsHangingLeaves("dark_willow_leaves", MaterialColor.TERRACOTTA_BLUE, getBlock("hanging_dark_willow_leaves"));
    public static final Block HANGING_DARK_WILLOW_LEAVES = hangingLeaves("hanging_dark_willow_leaves", MaterialColor.TERRACOTTA_BLUE, getBlock("dark_willow_leaves"), MnBlockTags.DARK_WILLOW_LOGS).hitbox(14, 16);
    public static final Block DARK_WILLOW_PLANKS = wood("dark_willow_planks", MaterialColor.TERRACOTTA_BLUE);
    public static final Block DARK_WILLOW_SAPLING = plant("dark_willow_sapling", 0, 0, Material.PLANT, MaterialColor.TERRACOTTA_BLUE).hitbox(12, 14);
    public static final Block DARK_WILLOW_BOOKSHELF = bookshelf("dark_willow_bookshelf", MaterialColor.COLOR_BLUE);

    // Shroom air
    public static final Block SHROOM_AIR = shroomAir("shroom_air");

    // Nightshroom
    public static final Block NIGHTSHROOM_CAP = shroomCap("nightshroom_cap", MaterialColor.COLOR_BLUE, 0x7566B0);
    public static final Block NIGHTSHROOM_STEM = stem("nightshroom_stem", MaterialColor.COLOR_BLUE);
    public static final Block NIGHTSHROOM_PLANKS = wood("nightshroom_planks", MaterialColor.COLOR_BLUE);
    public static final Block NIGHTSHROOM = smallShroom("nightshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_BLUE, getBlock("tall_nightshroom")).hitbox(14, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_NIGHTSHROOM = tallShroom("tall_nightshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_BLUE).hitbox(14, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block NIGHTSHROOM_SHELF = shelf("nightshroom_shelf", 0, 0, Material.PLANT, MaterialColor.COLOR_BLUE);
    public static final Block NIGHTSHROOM_FIBRE = fibre("nightshroom_fibre", Material.REPLACEABLE_PLANT, MaterialColor.COLOR_BLUE);
    public static final Block NIGHTSHROOM_ROOTS = shroomRoots("nightshroom_roots", Material.PLANT, MaterialColor.COLOR_BLUE).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_NIGHTSHROOM_ROOTS = shroomRoots("flowering_nightshroom_roots", Material.PLANT, MaterialColor.COLOR_BLUE).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Dewshroom
    public static final Block DEWSHROOM_CAP = shroomCap("dewshroom_cap", MaterialColor.COLOR_CYAN, 0x72CFD4);
    public static final Block DEWSHROOM_STEM = stem("dewshroom_stem", MaterialColor.COLOR_CYAN);
    public static final Block DEWSHROOM_PLANKS = wood("dewshroom_planks", MaterialColor.COLOR_CYAN);
    public static final Block DEWSHROOM = smallShroom("dewshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_CYAN, getBlock("tall_dewshroom")).hitbox(14, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_DEWSHROOM = tallShroom("tall_dewshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_CYAN).hitbox(14, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block DEWSHROOM_SHELF = shelf("dewshroom_shelf", 0, 0, Material.PLANT, MaterialColor.COLOR_CYAN);
    public static final Block DEWSHROOM_FIBRE = fibre("dewshroom_fibre", Material.REPLACEABLE_PLANT, MaterialColor.COLOR_CYAN);
    public static final Block DEWSHROOM_ROOTS = shroomRoots("dewshroom_roots", Material.PLANT, MaterialColor.COLOR_CYAN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_DEWSHROOM_ROOTS = shroomRoots("flowering_dewshroom_roots", Material.PLANT, MaterialColor.COLOR_CYAN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Viridshroom
    public static final Block VIRIDSHROOM_CAP = shroomCap("viridshroom_cap", MaterialColor.COLOR_LIGHT_GREEN, 0x84F54C);
    public static final Block VIRIDSHROOM_STEM = stem("viridshroom_stem", MaterialColor.COLOR_LIGHT_GREEN);
    public static final Block VIRIDSHROOM_PLANKS = wood("viridshroom_planks", MaterialColor.COLOR_LIGHT_GREEN);
    public static final Block VIRIDSHROOM = smallShroom("viridshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN, getBlock("tall_viridshroom")).hitbox(14, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_VIRIDSHROOM = tallShroom("tall_viridshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).hitbox(14, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block VIRIDSHROOM_SHELF = shelf("viridshroom_shelf", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN);
    public static final Block VIRIDSHROOM_FIBRE = fibre("viridshroom_fibre", Material.REPLACEABLE_PLANT, MaterialColor.COLOR_LIGHT_GREEN);
    public static final Block VIRIDSHROOM_ROOTS = shroomRoots("viridshroom_roots", Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_VIRIDSHROOM_ROOTS = shroomRoots("flowering_viridshroom_roots", Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Moonshroom
    public static final Block MOONSHROOM_CAP = shroomCap("moonshroom_cap", MaterialColor.COLOR_LIGHT_GRAY, 0xD7F3F5);
    public static final Block MOONSHROOM_STEM = stem("moonshroom_stem", MaterialColor.COLOR_GRAY);
    public static final Block MOONSHROOM_PLANKS = wood("moonshroom_planks", MaterialColor.COLOR_GRAY);
    public static final Block MOONSHROOM = smallShroom("moonshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GRAY, getBlock("tall_moonshroom")).hitbox(14, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_MOONSHROOM = tallShroom("tall_moonshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GRAY).hitbox(14, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block MOONSHROOM_SHELF = shelf("moonshroom_shelf", 0, 0, Material.PLANT, MaterialColor.COLOR_LIGHT_GRAY);
    public static final Block MOONSHROOM_FIBRE = fibre("moonshroom_fibre", Material.REPLACEABLE_PLANT, MaterialColor.COLOR_GRAY);
    public static final Block MOONSHROOM_ROOTS = shroomRoots("moonshroom_roots", Material.PLANT, MaterialColor.COLOR_GRAY).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_MOONSHROOM_ROOTS = shroomRoots("flowering_moonshroom_roots", Material.PLANT, MaterialColor.COLOR_GRAY).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Bogshroom
    public static final Block BOGSHROOM_CAP = shroomCap("bogshroom_cap", MaterialColor.COLOR_ORANGE, 0xF5AF4C);
    public static final Block BOGSHROOM_STEM = stem("bogshroom_stem", MaterialColor.COLOR_ORANGE);
    public static final Block BOGSHROOM_PLANKS = wood("bogshroom_planks", MaterialColor.COLOR_ORANGE);
    public static final Block BOGSHROOM = smallShroom("bogshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_ORANGE, getBlock("tall_bogshroom")).hitbox(14, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_BOGSHROOM = tallShroom("tall_bogshroom", 0, 0, Material.PLANT, MaterialColor.COLOR_ORANGE).hitbox(14, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block BOGSHROOM_SHELF = shelf("bogshroom_shelf", 0, 0, Material.PLANT, MaterialColor.COLOR_ORANGE);
    public static final Block BOGSHROOM_FIBRE = fibre("bogshroom_fibre", Material.REPLACEABLE_PLANT, MaterialColor.COLOR_ORANGE);

    // Glob fungus
    public static final Block GLOB_FUNGUS = smallFungus("glob_fungus", 0, 0, Material.PLANT, MaterialColor.COLOR_MAGENTA).hitbox(13, 13).offset(AbstractBlock.OffsetType.XYZ);
    public static final Block GLOB_FUNGUS_CAP = globCap("glob_fungus_cap", MaterialColor.COLOR_MAGENTA);
    public static final Block GLOB_FUNGUS_STEM = globStem("glob_fungus_stem", MaterialColor.COLOR_PURPLE);
    public static final Block INFESTED_GLOB_FUNGUS_STEM = infestedGlobStem("infested_glob_fungus_stem", MaterialColor.COLOR_PURPLE);
    public static final Block GLOB_FUNGUS_HYPHAE = globStem("glob_fungus_hyphae", MaterialColor.COLOR_PURPLE);
    public static final Block GLOB_FUNGUS_THATCH = thatch("glob_fungus_thatch", MaterialColor.COLOR_PURPLE);

    // Misc plants
    public static final Block MISTSHROOM = smallShroom("mistshroom", 0, 0, Material.PLANT, MaterialColor.TERRACOTTA_CYAN, getBlock("tall_mistshroom")).hitbox(13, 13).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_MISTSHROOM = tallShroom("tall_mistshroom", 0, 0, Material.PLANT, MaterialColor.TERRACOTTA_CYAN).hitbox(13, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block BRISTLY_GRASS = bristlyGrass("bristly_grass").hitbox(12, 12).offset(AbstractBlock.OffsetType.XYZ);
    public static final Block LUMEN_BUD = smallGlowingGrowable("lumen_bud", 0, 0, 10, Material.PLANT, MaterialColor.TERRACOTTA_CYAN, getBlock("tall_lumen_bud")).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block TALL_LUMEN_BUD = tallGlowingPlant("tall_lumen_bud", 0, 0, 10, Material.PLANT, MaterialColor.TERRACOTTA_CYAN).hitbox(13, 30).offset(AbstractBlock.OffsetType.XZ);
    public static final Block RUNEBUSH = glowingBush("runebush", 0, 0, 10, Material.PLANT, MaterialColor.COLOR_CYAN).hitbox(12, 13).offset(AbstractBlock.OffsetType.XZ);
    public static final Block BOGWEED = wetPlant("bogweed", 0, 0, 14, Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).hitbox(12, 12).offset(AbstractBlock.OffsetType.XZ);
    public static final Block CRYSTALOTUS = crystalotus("crystalotus");
    public static final Block SUAVIS = suavis("suavis");
    public static final Block VIOLEAF = violeaf("violeaf").hitbox(7, 10).offset(AbstractBlock.OffsetType.XYZ);
    public static final Block TENDRILWEED = tendrilweed("tendrilweed").hitbox(9, 14).offset(AbstractBlock.OffsetType.XZ); //Supposed to only be placed on nightstone?
    public static final Block NIGHT_REED = nightReed("night_reed").hitbox(13, 11);
    public static final Block DECEITFUL_MOSS = directionalPlant("deceitful_moss", 0.3, 0, Material.PLANT, MaterialColor.TERRACOTTA_BLUE).hitbox(16, 2);
    public static final Block DECEITFUL_ALGAE = floatingAlgae("deceitful_algae", 0, 0, Material.WATER_PLANT, MaterialColor.TERRACOTTA_BLUE).hitbox(16, 1);

    public static final Block REED_THATCH = reedThatch("reed_thatch", MaterialColor.COLOR_PURPLE, getBlock("cut_reed_thatch"));
    public static final Block CUT_REED_THATCH = thatch("cut_reed_thatch", MaterialColor.COLOR_PURPLE);

    // Strange Glass
    public static final Block STRANGE_GLASS = glass("strange_glass");
    public static final Block STRANGE_GLASS_PANE = glassPane("strange_glass_pane");

    // Rockshroom
    public static final Block ROCKSHROOM = rockshroom("rockshroom");

    // Crystals
    public static final Block ROUXE_ROCK = crystalRock("rouxe_rock", 4, 4, 2, MaterialColor.COLOR_RED);
    public static final Block BLOOMCRYSTAL_ROCK = crystalRock("bloomcrystal_rock", 4, 4, 14, MaterialColor.COLOR_PINK);
    public static final Block ROUXE = crystal("rouxe", 4, 4, 3, MaterialColor.COLOR_RED).hitbox(13, 12).offset(AbstractBlock.OffsetType.XZ);
    public static final Block BLOOMCRYSTAL = crystal("bloomcrystal", 4, 4, 15, MaterialColor.COLOR_PINK).hitbox(13, 12).offset(AbstractBlock.OffsetType.XZ);
    public static final Block CRYSTAL_FLOWER = crystalFlower("crystal_flower", 0, 0, Material.PLANT, MaterialColor.COLOR_PINK).hitbox(13, 13).offset(AbstractBlock.OffsetType.XZ);

    // Dark pearl
    public static final Block DARK_PEARL_ORE = stone("dark_pearl_ore", 3, 6, MaterialColor.COLOR_BLACK);
    public static final Block DARK_PEARL_BLOCK = darkPearl("dark_pearl_block", 3, 6, MaterialColor.COLOR_BLACK);

    // Archaic
    public static final Block ARCHAIC_ORE = stone("archaic_ore", 3, 6, MaterialColor.COLOR_BLACK);
    public static final Block ARCHAIC_GLASS = glass("archaic_glass");
    public static final Block ARCHAIC_GLASS_PANE = glassPane("archaic_glass_pane");

    // Tenebrum
    public static final Block TENEBRUM_ORE = ore("tenebrum_ore", 3, 3, MaterialColor.COLOR_BLACK, 3);
    public static final Block TENEBRUM_BLOCK = rareMetal("tenebrum_block", 6.7, 8, MaterialColor.COLOR_BLACK, 3);

    // Nagrilite
    public static final Block NAGRILITE_ORE = ore("nagrilite_ore", 3, 3, MaterialColor.COLOR_BLACK, 2);
    public static final Block NAGRILITE_BLOCK = metal("nagrilite_block", 5, 6, MaterialColor.COLOR_BLACK, 2);

    // Ebonite
    public static final Block EBONITE_ORE = xpOre("ebonite_ore", 3, 3, MaterialColor.COLOR_BLACK, 1, 0, 2);
    public static final Block EBONITE_BLOCK = brittleMetal("ebonite_block", 3, 5, MaterialColor.COLOR_BLACK, 1);

    // Virilux
    public static final Block VIRILUX_ORE = viriluxOre("virilux_ore");
    public static final Block VIRILUX_BLOCK = virilux("virilux_block");



    //////////////////////////
    ///// BLOCK REGISTRY /////
    //////////////////////////

    public static void registerBlocks(IRegistry<Block> registry) {
        registry.registerAll(BLOCKS);
    }


    ///////////////////////////
    ///// COMMON REGISTRY /////
    ///////////////////////////

    public static void setup() {
        GeodeHardMaterials.addMaterial(Material.STONE);
        GeodeHardMaterials.addMaterial(Material.METAL);
        GeodeHardMaterials.addMaterial(Material.SHULKER_SHELL);
        GeodeHardMaterials.addMaterial(Material.HEAVY_METAL);
        GeodeHardMaterials.addMaterial(MnMaterials.CRYSTAL_ROCK);
        GeodeHardMaterials.addMaterial(MnMaterials.VIRILUX);
    }


    ///////////////////////////
    ///// CLIENT REGISTRY /////
    ///////////////////////////

    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        RenderTypeLookup.setRenderLayer(NIGHT_GRASS_BLOCK, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(NIGHT_GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_NIGHT_GRASS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(GHOST_PLANT_STEM, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(GHOST_PLANT_LEAF, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(GHOST_PLANT, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(DEAD_SAPLING, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(SHADOWROOT_LEAVES, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(SHADOWROOT_SAPLING, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(DARK_WILLOW_LEAVES, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(HANGING_DARK_WILLOW_LEAVES, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DARK_WILLOW_SAPLING, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(NIGHTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_NIGHTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_FIBRE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_NIGHTSHROOM_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(DEWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_DEWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_FIBRE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_DEWSHROOM_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(VIRIDSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_VIRIDSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_FIBRE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_VIRIDSHROOM_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MOONSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_MOONSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_FIBRE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_MOONSHROOM_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BOGSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_BOGSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_FIBRE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_SHELF, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(GLOB_FUNGUS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MISTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_MISTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BRISTLY_GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(LUMEN_BUD, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TALL_LUMEN_BUD, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(RUNEBUSH, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BOGWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CRYSTALOTUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(VIOLEAF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TENDRILWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(NIGHT_REED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DECEITFUL_MOSS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DECEITFUL_ALGAE, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(ROUXE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BLOOMCRYSTAL, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CRYSTAL_FLOWER, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(STRANGE_GLASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(STRANGE_GLASS_PANE, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(ARCHAIC_GLASS, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ARCHAIC_GLASS_PANE, RenderType.translucent());


        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        blockColors.register(
            (state, world, pos, tint) -> {
                if (pos == null || world == null) return 0x9A63B8;
                int color = MidnightClient.get().getNightGrassColorCache().getColor(pos, MnBiomeColors.NIGHT_GRASS);
                return MnUtil.modifyGrassColor(color, pos);
            },
            NIGHT_GRASS_BLOCK
        );
        itemColors.register(
            (stack, tint) -> 0x9A63B8,
            NIGHT_GRASS_BLOCK
        );

        blockColors.register(
            (state, world, pos, tint) -> {
                if (pos == null || world == null) return 0x8C74A1;
                int color = MidnightClient.get().getNightGrassColorCache().getColor(pos, MnBiomeColors.NIGHT_GRASS);
                color = MnUtil.modifyGrassColor(color, pos);
                color = ColorUtil.darker(color, 0.3);
                return color;
            },
            NIGHT_GRASS, TALL_NIGHT_GRASS
        );
        itemColors.register(
            (stack, tint) -> 0x8C74A1,
            NIGHT_GRASS, TALL_NIGHT_GRASS
        );

        blockColors.register(
            (state, world, pos, tint) -> {
                if (pos == null || world == null) return 0x3A3154;
                return MidnightClient.get().getShadowrootColorCache().getColor(pos, MnBiomeColors.SHADOWROOT);
            },
            SHADOWROOT_LEAVES
        );
        itemColors.register(
            (stack, tint) -> 0x3A3154,
            SHADOWROOT_LEAVES
        );
    }




    private MnBlocks() {
    }

    /////////////////////////////////
    ///// BLOCK FACTORY METHODS /////
    /////////////////////////////////

    private static <B extends Block> B block(String id, B block) {
        BLOCKS.put(Midnight.id(id), block);
        block.setRegistryName(Midnight.id(id));
        return block;
    }

    private static Block stone(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.STONE, color)
                                    .sound(SoundType.STONE)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block bricks(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.STONE, color)
                                    .sound(SoundType.NETHER_BRICKS)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block ore(String id, double hardness, double resistance, MaterialColor color, int toolLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.STONE, color)
                                    .sound(SoundType.STONE)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(toolLevel)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block xpOre(String id, double hardness, double resistance, MaterialColor color, int toolLevel, int minxp, int maxxp) {
        return block(id, new XPDroppingBlock(
            AbstractBlock.Properties.of(Material.STONE, color)
                                    .sound(SoundType.STONE)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(toolLevel)
                                    .requiresCorrectToolForDrops(),
            minxp, maxxp
        ));
    }

    private static Block mycelium(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new NightMyceliumBlock(
            AbstractBlock.Properties.of(Material.STONE, color)
                                    .sound(SoundType.NYLIUM)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static Block dirt(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            AbstractBlock.Properties.of(Material.DIRT, color)
                                    .sound(SoundType.GRAVEL)
                                    .strength(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block peat(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            AbstractBlock.Properties.of(Material.DIRT, color)
                                    .sound(MnSoundTypes.PEAT)
                                    .strength(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block grassBlock(String id) {
        return block(id, new NightGrassBlock(
            AbstractBlock.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_PURPLE)
                                    .sound(SoundType.GRASS)
                                    .strength(0.6f)
                                    .harvestTool(ToolType.SHOVEL)
                                    .randomTicks()
        ));
    }

    private static Block mud(String id) {
        return block(id, new DeceitfulMudBlock(
            AbstractBlock.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BLUE)
                                    .sound(MnSoundTypes.MUD)
                                    .strength(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block sand(String id) {
        return block(id, new StrangeSandBlock(
            AbstractBlock.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_BLUE)
                                    .sound(SoundType.SAND)
                                    .strength(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block water(String id, Supplier<FlowingFluid> fluid) {
        return block(id, new FlowingFluidBlock(
            fluid,
            AbstractBlock.Properties.of(Material.WATER)
                                    .strength(100)
        ));
    }

    private static Block crystalRock(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(MnMaterials.CRYSTAL_ROCK, color)
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .lightLevel(state -> luminance)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static CrystalBlock crystal(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new CrystalBlock(
            AbstractBlock.Properties.of(MnMaterials.CRYSTAL, color)
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .lightLevel(state -> luminance)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .noOcclusion()
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block rockshroom(String id) {
        return block(id, new RockshroomBlock(
            AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK)
                                    .sound(SoundType.NETHERRACK)
                                    .strength(1.2f, 4)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static Block shroombricks(String id) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK)
                                    .sound(SoundType.BASALT)
                                    .strength(1.2f, 4)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static PlantBlock plant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock crystalFlower(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock bush(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.SWEET_BERRY_BUSH)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static DirectionalPlantBlock directionalPlant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new DirectionalPlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.CROP)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static FloatingPlantBlock floatingAlgae(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new FloatingPlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.WET_GRASS)
                                    .strength((float) hardness, (float) resistance),
            fluid -> fluid == Fluids.WATER
        ));
    }

    private static PlantBlock glowingBush(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.SWEET_BERRY_BUSH)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock wetPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.WET_GRASS)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock bristlyGrass(String id) {
        return block(id, new BristlyGrassBlock(
            AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_BLUE)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .strength(0, 0)
        ));
    }

    private static PlantBlock emissivePlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock ghostPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.ROOTS)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static TallPlantBlock tallPlant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallGrowable(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .strength((float) hardness, (float) resistance),
            tall
        ));
    }

    private static TallPlantBlock tallGlowingPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallGlowingGrowable(String id, double hardness, double resistance, int emission, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .lightLevel(state -> emission)
                                    .strength((float) hardness, (float) resistance),
            tall
        ));
    }

    private static TallPlantBlock tallShroom(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.FUNGUS)
                                    .lightLevel(state -> 10)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallShroom(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.FUNGUS)
                                    .lightLevel(state -> 10)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance),
            tall
        ));
    }

    private static PlantBlock smallFungus(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.FUNGUS)
                                    .lightLevel(state -> 10)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock shroomRoots(String id, Material material, MaterialColor color) {
        return block(id, new ShroomRootsBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.ROOTS)
                                    .strength(0, 0)
        ));
    }

    private static ShroomShelfBlock shelf(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new ShroomShelfBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.FUNGUS)
                                    .strength((float) hardness, (float) resistance)
        ));
    }

    private static CrystalotusBlock crystalotus(String id) {
        return block(id, new CrystalotusBlock(
            AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_LIGHT_BLUE)
                                    .noOcclusion()
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .lightLevel(state -> 13)
                                    .strength(0.2f, 0.5f)
        ));
    }

    private static Block suavis(String id) {
        return block(id, new SuavisBlock(
            AbstractBlock.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_LIGHT_BLUE)
                                    .noOcclusion()
                                    .sound(SoundType.HONEY_BLOCK)
                                    .lightLevel(state -> state.getValue(SuavisBlock.STAGE) * 3 + 3)
                                    .randomTicks()
                                    .isSuffocating((state, world, pos) -> state.getValue(SuavisBlock.STAGE) == 3)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength(1f, 0.2f)
        ));
    }

    private static VioleafBlock violeaf(String id) {
        return block(id, new VioleafBlock(
            AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_PURPLE)
                                    .noOcclusion()
                                    .sound(SoundType.WET_GRASS)
                                    .randomTicks()
                                    .strength(0)
        ));
    }

    private static TendrilweedBlock tendrilweed(String id) {
        return block(id, new TendrilweedBlock(
            AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_RED)
                                    .noOcclusion()
                                    .sound(SoundType.ROOTS)
                                    .randomTicks()
                                    .strength(0.1f, 0)
        ));
    }

    private static NightReedBlock nightReed(String id) {
        return block(id, new NightReedBlock(
            AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_ORANGE)
                                    .noOcclusion()
                                    .sound(SoundType.WET_GRASS)
                                    .randomTicks()
                                    .strength(0)
                                    .emissiveRendering((state, world, pos) -> !state.getValue(NightReedBlock.WATERLOGGED))
                                    .lightLevel(state -> state.getValue(NightReedBlock.WATERLOGGED) ? 0 : 10)
        ));
    }

    private static PlantBlock fibre(String id, Material material, MaterialColor color) {
        return block(id, new FibreBlock(
            AbstractBlock.Properties.of(material, color)
                                    .noOcclusion()
                                    .sound(SoundType.NETHER_SPROUTS)
                                    .strength(0, 0)
        ));
    }

    private static Block giantGhostPlant(String id, Function<AbstractBlock.Properties, Block> factory) {
        return block(id, factory.apply(
            AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SNOW)
                                    .noOcclusion()
                                    .sound(SoundType.SHROOMLIGHT)
                                    .strength(0.3f)
                                    .lightLevel(state -> 15)
        ));
    }

    private static Block leaves(String id, MaterialColor color) {
        return block(id, new LeavesBlock(
            AbstractBlock.Properties.of(Material.LEAVES, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .harvestTool(ToolType.HOE)
                                    .isSuffocating((state, world, pos) -> false)
                                    .strength(0.2f)
        ));
    }

    private static Block growsHangingLeaves(String id, MaterialColor color, Supplier<Block> hanging) {
        return block(id, new HangingLeavesGrowingBlock(
            AbstractBlock.Properties.of(Material.LEAVES, color)
                                    .noOcclusion()
                                    .sound(SoundType.GRASS)
                                    .harvestTool(ToolType.HOE)
                                    .isSuffocating((state, world, pos) -> false)
                                    .strength(0.2f),
            hanging
        ));
    }

    private static HangingLeavesBlock hangingLeaves(String id, MaterialColor color, Supplier<Block> leaves, ITag.INamedTag<Block> logs) {
        return block(id, new HangingLeavesBlock(
            AbstractBlock.Properties.of(Material.LEAVES, color)
                                    .noOcclusion()
                                    .harvestTool(ToolType.HOE)
                                    .sound(SoundType.CROP) // Make them sound a bit less leafier
                                    .strength(0.1f),
            leaves, logs
        ));
    }

    private static Block log(String id, MaterialColor color, Supplier<Block> stripped) {
        return block(id, new StripableRotatedPillarBlock(
            AbstractBlock.Properties.of(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .strength(2f),
            stripped
        ));
    }

    private static Block log(String id, MaterialColor color) {
        return block(id, new RotatedPillarBlock(
            AbstractBlock.Properties.of(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .strength(2f)
        ));
    }

    private static Block stem(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .sound(SoundType.STEM)
                                    .harvestTool(ToolType.AXE)
                                    .strength(2f)
        ));
    }

    private static Block globStem(String id, MaterialColor color) {
        return block(id, new RotatedPillarBlock(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .sound(SoundType.STEM)
                                    .harvestTool(ToolType.AXE)
                                    .strength(1.7f)
        ));
    }

    private static Block infestedGlobStem(String id, MaterialColor color) {
        return block(id, new HorizontalFacingBlock(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .sound(SoundType.STEM)
                                    .harvestTool(ToolType.AXE)
                                    .strength(0.4f)
        ));
    }

    private static Block thatch(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .sound(SoundType.WART_BLOCK)
                                    .harvestTool(ToolType.HOE)
                                    .strength(0.8f)
        ));
    }

    private static Block reedThatch(String id, MaterialColor color, Supplier<Block> cut) {
        return block(id, new ShearableBlock(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .sound(SoundType.WART_BLOCK)
                                    .harvestTool(ToolType.HOE)
                                    .lightLevel(state -> 8)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .strength(0.8f),
            cut
        ));
    }

    private static Block wood(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .strength(2f)
        ));
    }

    private static Block bookshelf(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .strength(1.5f)
        ));
    }

    private static Block shroomAir(String id) {
        return block(id, new ShroomAirBlock(
            AbstractBlock.Properties.of(Material.AIR, MaterialColor.NONE)
                                    .noOcclusion()
                                    .isSuffocating((state, world, pos) -> false)
                                    .lightLevel(state -> 15)
                                    .noDrops()
        ));
    }

    private static Block shroomCap(String id, MaterialColor color, int sporeColor) {
        return block(id, new ShroomCapBlock(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .harvestTool(ToolType.HOE)
                                    .strength(1.7f)
                                    .sound(SoundType.WART_BLOCK),
            sporeColor
        ));
    }

    private static Block globCap(String id, MaterialColor color) {
        return block(id, new BouncyShroomCapBlock(
            AbstractBlock.Properties.of(Material.GRASS, color)
                                    .harvestTool(ToolType.HOE)
                                    .strength(1.1f)
                                    .lightLevel(state -> 15)
                                    .sound(SoundType.SLIME_BLOCK)
        ));
    }

    private static Block darkPearl(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.METAL, color)
                                    .noOcclusion()
                                    .sound(SoundType.BONE_BLOCK)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block metal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.METAL, color)
                                    .sound(SoundType.METAL)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block brittleMetal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.METAL, color)
                                    .sound(MnSoundTypes.BRITTLE_METAL)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block rareMetal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.of(Material.METAL, color)
                                    .sound(SoundType.NETHERITE_BLOCK)
                                    .strength((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block glass(String id) {
        return block(id, new GlassBlock(
            AbstractBlock.Properties.of(Material.GLASS, MaterialColor.COLOR_PINK)
                                    .noOcclusion()
                                    .sound(SoundType.GLASS)
                                    .strength(0.3F)
        ));
    }

    private static Block glassPane(String id) {
        return block(id, new PaneBlock(
            AbstractBlock.Properties.of(Material.GLASS, MaterialColor.COLOR_PINK)
                                    .noOcclusion()
                                    .sound(SoundType.GLASS)
                                    .strength(0.3F)
        ));
    }

    private static Block virilux(String id) {
        return block(id, new Block(
            AbstractBlock.Properties.of(MnMaterials.VIRILUX, MaterialColor.COLOR_LIGHT_GREEN)
                                    .sound(MnSoundTypes.JEWEL)
                                    .strength(6.5F, 8)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(3)
                                    .lightLevel(state -> 15)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .requiresCorrectToolForDrops()
        ));
    }

    private static Block viriluxOre(String id) {
        return block(id, new XPDroppingBlock(
            AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                                    .sound(SoundType.STONE)
                                    .strength(5, 5)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(3)
                                    .lightLevel(state -> 4)
                                    .emissiveRendering((state, world, pos) -> true)
                                    .requiresCorrectToolForDrops(),
            3, 6
        ));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Block> Supplier<T> getBlock(String id) {
        return () -> (T) BLOCKS.get(Midnight.id(id));
    }

    private static Supplier<BlockState> getState(String id) {
        return () -> BLOCKS.get(Midnight.id(id)).defaultBlockState();
    }

    private static Supplier<BlockState> getState(String id, Function<BlockState, BlockState> props) {
        return () -> props.apply(BLOCKS.get(Midnight.id(id)).defaultBlockState());
    }
}
