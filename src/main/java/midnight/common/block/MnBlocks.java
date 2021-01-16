/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;

import midnight.api.util.GeodeHardMaterials;
import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.common.block.fluid.MnFluids;
import midnight.common.misc.tags.MnBlockTags;
import midnight.common.world.biome.MnBiomeColors;
import midnight.core.util.ColorUtil;
import midnight.core.util.IRegistry;
import midnight.core.util.MnUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public static final Block NIGHTSTONE = stone("nightstone", 1.5, 6, MaterialColor.OBSIDIAN);
    public static final Block NIGHT_BEDROCK = stone("night_bedrock", 1.5, 6, MaterialColor.LIGHT_GRAY_TERRACOTTA);
    public static final Block TRENCHSTONE = stone("trenchstone", 1.5, 6, MaterialColor.BLACK);

    // Basic soils
    public static final Block NIGHT_DIRT = dirt("night_dirt", MaterialColor.BLACK);
    public static final Block COARSE_NIGHT_DIRT = dirt("coarse_night_dirt", MaterialColor.BLACK);
    public static final Block NIGHT_GRASS_BLOCK = grassBlock("night_grass_block");
    public static final Block DECEITFUL_PEAT = peat("deceitful_peat", MaterialColor.PURPLE_TERRACOTTA);
    public static final Block DECEITFUL_MUD = mud("deceitful_mud");
    public static final Block STRANGE_SAND = sand("strange_sand");
    public static final Block NIGHT_MYCELIUM = mycelium("night_mycelium", 2, 6, MaterialColor.MAGENTA);

    // Bricks
    public static final Block NIGHTSTONE_BRICKS = bricks("nightstone_bricks", 1.5, 6, MaterialColor.OBSIDIAN);
    public static final Block TRENCHSTONE_BRICKS = bricks("trenchstone_bricks", 1.5, 6, MaterialColor.BLACK);
    public static final Block SHROOMBRICKS = shroombricks("shroombricks");

    // Fluids
    public static final Block DARK_WATER = water("dark_water", () -> MnFluids.DARK_WATER);

    // Tall night grass
    public static final Block NIGHT_GRASS = smallGrowable("night_grass", 0, 0, Material.TALL_PLANTS, MaterialColor.PURPLE_TERRACOTTA, getBlock("tall_night_grass")).hitbox(12, 13).offset(Block.OffsetType.XYZ);
    public static final Block TALL_NIGHT_GRASS = tallPlant("tall_night_grass", 0, 0, Material.TALL_PLANTS, MaterialColor.PURPLE_TERRACOTTA).hitbox(14, 30).offset(Block.OffsetType.XYZ);

    // Ghost plant
    public static final Block GHOST_PLANT_STEM = giantGhostPlant("ghost_plant_stem", GhostPlantStemBlock::new);
    public static final Block GHOST_PLANT_LEAF = giantGhostPlant("ghost_plant_leaf", GhostPlantBlock::new);
    public static final Block GHOST_PLANT = ghostPlant("ghost_plant", 0, 0, 9, Material.PLANTS, MaterialColor.SNOW).hitbox(13, 14).offset(Block.OffsetType.XZ);

    // Dead wood
    public static final Block DEAD_WOOD_LOG = log("dead_wood_log", MaterialColor.FOLIAGE, getBlock("stripped_dead_wood_log"));
    public static final Block STRIPPED_DEAD_WOOD_LOG = log("stripped_dead_wood_log", MaterialColor.FOLIAGE);
    public static final Block DEAD_WOOD = log("dead_wood", MaterialColor.FOLIAGE, getBlock("stripped_dead_wood"));
    public static final Block STRIPPED_DEAD_WOOD = log("stripped_dead_wood", MaterialColor.FOLIAGE);
    public static final Block DEAD_WOOD_PLANKS = wood("dead_wood_planks", MaterialColor.FOLIAGE);
    public static final Block DEAD_SAPLING = plant("dead_sapling", 0, 0, Material.PLANTS, MaterialColor.FOLIAGE).hitbox(12, 13);

    // Shadowroot
    public static final Block SHADOWROOT_LOG = log("shadowroot_log", MaterialColor.PURPLE, getBlock("stripped_shadowroot_log"));
    public static final Block STRIPPED_SHADOWROOT_LOG = log("stripped_shadowroot_log", MaterialColor.PURPLE);
    public static final Block SHADOWROOT_WOOD = log("shadowroot_wood", MaterialColor.PURPLE, getBlock("stripped_shadowroot_wood"));
    public static final Block STRIPPED_SHADOWROOT_WOOD = log("stripped_shadowroot_wood", MaterialColor.PURPLE);
    public static final Block SHADOWROOT_LEAVES = leaves("shadowroot_leaves", MaterialColor.PURPLE);
    public static final Block SHADOWROOT_PLANKS = wood("shadowroot_planks", MaterialColor.PURPLE);
    public static final Block SHADOWROOT_SAPLING = plant("shadowroot_sapling", 0, 0, Material.PLANTS, MaterialColor.PURPLE).hitbox(11, 15);
    public static final Block SHADOWROOT_BOOKSHELF = bookshelf("shadowroot_bookshelf", MaterialColor.PURPLE);

    // Dark willow
    public static final Block DARK_WILLOW_LOG = log("dark_willow_log", MaterialColor.BLUE, getBlock("stripped_dark_willow_log"));
    public static final Block STRIPPED_DARK_WILLOW_LOG = log("stripped_dark_willow_log", MaterialColor.BLUE);
    public static final Block DARK_WILLOW_WOOD = log("dark_willow_wood", MaterialColor.BLUE, getBlock("stripped_dark_willow_wood"));
    public static final Block STRIPPED_DARK_WILLOW_WOOD = log("stripped_dark_willow_wood", MaterialColor.BLUE);
    public static final Block DARK_WILLOW_LEAVES = growsHangingLeaves("dark_willow_leaves", MaterialColor.BLUE_TERRACOTTA, getBlock("hanging_dark_willow_leaves"));
    public static final Block HANGING_DARK_WILLOW_LEAVES = hangingLeaves("hanging_dark_willow_leaves", MaterialColor.BLUE_TERRACOTTA, getBlock("dark_willow_leaves"), MnBlockTags.DARK_WILLOW_LOGS).hitbox(14, 16);
    public static final Block DARK_WILLOW_PLANKS = wood("dark_willow_planks", MaterialColor.BLUE_TERRACOTTA);
    public static final Block DARK_WILLOW_SAPLING = plant("dark_willow_sapling", 0, 0, Material.PLANTS, MaterialColor.BLUE_TERRACOTTA).hitbox(12, 14);
    public static final Block DARK_WILLOW_BOOKSHELF = bookshelf("dark_willow_bookshelf", MaterialColor.BLUE);

    // Shroom air
    public static final Block SHROOM_AIR = shroomAir("shroom_air");

    // Nightshroom
    public static final Block NIGHTSHROOM_CAP = shroomCap("nightshroom_cap", MaterialColor.BLUE, 0x7566B0);
    public static final Block NIGHTSHROOM_STEM = stem("nightshroom_stem", MaterialColor.BLUE);
    public static final Block NIGHTSHROOM_PLANKS = wood("nightshroom_planks", MaterialColor.BLUE);
    public static final Block NIGHTSHROOM = smallShroom("nightshroom", 0, 0, Material.PLANTS, MaterialColor.BLUE, getBlock("tall_nightshroom")).hitbox(14, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_NIGHTSHROOM = tallShroom("tall_nightshroom", 0, 0, Material.PLANTS, MaterialColor.BLUE).hitbox(14, 30).offset(Block.OffsetType.XZ);
    public static final Block NIGHTSHROOM_SHELF = shelf("nightshroom_shelf", 0, 0, Material.PLANTS, MaterialColor.BLUE);
    public static final Block NIGHTSHROOM_FIBRE = fibre("nightshroom_fibre", Material.TALL_PLANTS, MaterialColor.BLUE);
    public static final Block NIGHTSHROOM_ROOTS = shroomRoots("nightshroom_roots", Material.PLANTS, MaterialColor.BLUE).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_NIGHTSHROOM_ROOTS = shroomRoots("flowering_nightshroom_roots", Material.PLANTS, MaterialColor.BLUE).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Dewshroom
    public static final Block DEWSHROOM_CAP = shroomCap("dewshroom_cap", MaterialColor.CYAN, 0x72CFD4);
    public static final Block DEWSHROOM_STEM = stem("dewshroom_stem", MaterialColor.CYAN);
    public static final Block DEWSHROOM_PLANKS = wood("dewshroom_planks", MaterialColor.CYAN);
    public static final Block DEWSHROOM = smallShroom("dewshroom", 0, 0, Material.PLANTS, MaterialColor.CYAN, getBlock("tall_dewshroom")).hitbox(14, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_DEWSHROOM = tallShroom("tall_dewshroom", 0, 0, Material.PLANTS, MaterialColor.CYAN).hitbox(14, 30).offset(Block.OffsetType.XZ);
    public static final Block DEWSHROOM_SHELF = shelf("dewshroom_shelf", 0, 0, Material.PLANTS, MaterialColor.CYAN);
    public static final Block DEWSHROOM_FIBRE = fibre("dewshroom_fibre", Material.TALL_PLANTS, MaterialColor.CYAN);
    public static final Block DEWSHROOM_ROOTS = shroomRoots("dewshroom_roots", Material.PLANTS, MaterialColor.CYAN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_DEWSHROOM_ROOTS = shroomRoots("flowering_dewshroom_roots", Material.PLANTS, MaterialColor.CYAN).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Viridshroom
    public static final Block VIRIDSHROOM_CAP = shroomCap("viridshroom_cap", MaterialColor.LIME, 0x84F54C);
    public static final Block VIRIDSHROOM_STEM = stem("viridshroom_stem", MaterialColor.LIME);
    public static final Block VIRIDSHROOM_PLANKS = wood("viridshroom_planks", MaterialColor.LIME);
    public static final Block VIRIDSHROOM = smallShroom("viridshroom", 0, 0, Material.PLANTS, MaterialColor.LIME, getBlock("tall_viridshroom")).hitbox(14, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_VIRIDSHROOM = tallShroom("tall_viridshroom", 0, 0, Material.PLANTS, MaterialColor.LIME).hitbox(14, 30).offset(Block.OffsetType.XZ);
    public static final Block VIRIDSHROOM_SHELF = shelf("viridshroom_shelf", 0, 0, Material.PLANTS, MaterialColor.LIME);
    public static final Block VIRIDSHROOM_FIBRE = fibre("viridshroom_fibre", Material.TALL_PLANTS, MaterialColor.LIME);
    public static final Block VIRIDSHROOM_ROOTS = shroomRoots("viridshroom_roots", Material.PLANTS, MaterialColor.LIME).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_VIRIDSHROOM_ROOTS = shroomRoots("flowering_viridshroom_roots", Material.PLANTS, MaterialColor.LIME).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Moonshroom
    public static final Block MOONSHROOM_CAP = shroomCap("moonshroom_cap", MaterialColor.LIGHT_GRAY, 0xD7F3F5);
    public static final Block MOONSHROOM_STEM = stem("moonshroom_stem", MaterialColor.GRAY);
    public static final Block MOONSHROOM_PLANKS = wood("moonshroom_planks", MaterialColor.GRAY);
    public static final Block MOONSHROOM = smallShroom("moonshroom", 0, 0, Material.PLANTS, MaterialColor.LIGHT_GRAY, getBlock("tall_moonshroom")).hitbox(14, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_MOONSHROOM = tallShroom("tall_moonshroom", 0, 0, Material.PLANTS, MaterialColor.LIGHT_GRAY).hitbox(14, 30).offset(Block.OffsetType.XZ);
    public static final Block MOONSHROOM_SHELF = shelf("moonshroom_shelf", 0, 0, Material.PLANTS, MaterialColor.LIGHT_GRAY);
    public static final Block MOONSHROOM_FIBRE = fibre("moonshroom_fibre", Material.TALL_PLANTS, MaterialColor.GRAY);
    public static final Block MOONSHROOM_ROOTS = shroomRoots("moonshroom_roots", Material.PLANTS, MaterialColor.GRAY).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);
    public static final Block FLOWERING_MOONSHROOM_ROOTS = shroomRoots("flowering_moonshroom_roots", Material.PLANTS, MaterialColor.GRAY).hitbox(13, 14).offset(AbstractBlock.OffsetType.XZ);

    // Bogshroom
    public static final Block BOGSHROOM_CAP = shroomCap("bogshroom_cap", MaterialColor.ADOBE, 0xF5AF4C);
    public static final Block BOGSHROOM_STEM = stem("bogshroom_stem", MaterialColor.ADOBE);
    public static final Block BOGSHROOM_PLANKS = wood("bogshroom_planks", MaterialColor.ADOBE);
    public static final Block BOGSHROOM = smallShroom("bogshroom", 0, 0, Material.PLANTS, MaterialColor.ADOBE, getBlock("tall_bogshroom")).hitbox(14, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_BOGSHROOM = tallShroom("tall_bogshroom", 0, 0, Material.PLANTS, MaterialColor.ADOBE).hitbox(14, 30).offset(Block.OffsetType.XZ);
    public static final Block BOGSHROOM_SHELF = shelf("bogshroom_shelf", 0, 0, Material.PLANTS, MaterialColor.ADOBE);
    public static final Block BOGSHROOM_FIBRE = fibre("bogshroom_fibre", Material.TALL_PLANTS, MaterialColor.ADOBE);

    // Glob fungus
    public static final Block GLOB_FUNGUS = smallFungus("glob_fungus", 0, 0, Material.PLANTS, MaterialColor.MAGENTA).hitbox(13, 13).offset(Block.OffsetType.XYZ);
    public static final Block GLOB_FUNGUS_CAP = globCap("glob_fungus_cap", MaterialColor.MAGENTA);
    public static final Block GLOB_FUNGUS_STEM = globStem("glob_fungus_stem", MaterialColor.PURPLE);
    public static final Block INFESTED_GLOB_FUNGUS_STEM = infestedGlobStem("infested_glob_fungus_stem", MaterialColor.PURPLE);
    public static final Block GLOB_FUNGUS_HYPHAE = globStem("glob_fungus_hyphae", MaterialColor.PURPLE);
    public static final Block GLOB_FUNGUS_THATCH = thatch("glob_fungus_thatch", MaterialColor.PURPLE);

    // Misc plants
    public static final Block MISTSHROOM = smallShroom("mistshroom", 0, 0, Material.PLANTS, MaterialColor.CYAN_TERRACOTTA, getBlock("tall_mistshroom")).hitbox(13, 13).offset(Block.OffsetType.XZ);
    public static final Block TALL_MISTSHROOM = tallShroom("tall_mistshroom", 0, 0, Material.PLANTS, MaterialColor.CYAN_TERRACOTTA).hitbox(13, 30).offset(Block.OffsetType.XZ);
    public static final Block FINGERED_GRASS = fingeredGrass("fingered_grass").hitbox(12, 12).offset(Block.OffsetType.XYZ);
    public static final Block LUMEN_BUD = smallGlowingGrowable("lumen_bud", 0, 0, 10, Material.PLANTS, MaterialColor.CYAN_TERRACOTTA, getBlock("tall_lumen_bud")).hitbox(13, 14).offset(Block.OffsetType.XZ);
    public static final Block TALL_LUMEN_BUD = tallGlowingPlant("tall_lumen_bud", 0, 0, 10, Material.PLANTS, MaterialColor.CYAN_TERRACOTTA).hitbox(13, 30).offset(Block.OffsetType.XZ);
    public static final Block RUNEBUSH = glowingBush("runebush", 0, 0, 10, Material.PLANTS, MaterialColor.CYAN).hitbox(12, 13).offset(Block.OffsetType.XZ);
    public static final Block BOGWEED = wetPlant("bogweed", 0, 0, 14, Material.PLANTS, MaterialColor.LIME).hitbox(12, 12).offset(Block.OffsetType.XZ);
    public static final Block CRYSTALOTUS = crystalotus("crystalotus");
    public static final Block SUAVIS = suavis("suavis");
    public static final Block VIOLEAF = violeaf("violeaf").hitbox(7, 10).offset(Block.OffsetType.XYZ);
    public static final Block TENDRILWEED = tendrilweed("tendrilweed").hitbox(9, 14).offset(Block.OffsetType.XZ);
    public static final Block NIGHT_REED = nightReed("night_reed").hitbox(13, 11);
    public static final Block DECEITFUL_MOSS = directionalPlant("deceitful_moss", 0.3, 0, Material.PLANTS, MaterialColor.BLUE_TERRACOTTA).hitbox(16, 2);
    public static final Block DECEITFUL_ALGAE = floatingAlgae("deceitful_algae", 0, 0, Material.OCEAN_PLANT, MaterialColor.BLUE_TERRACOTTA).hitbox(16, 1);

    public static final Block REED_THATCH = reedThatch("reed_thatch", MaterialColor.PURPLE, getBlock("cut_reed_thatch"));
    public static final Block CUT_REED_THATCH = thatch("cut_reed_thatch", MaterialColor.PURPLE);

    // Rockshroom
    public static final Block ROCKSHROOM = rockshroom("rockshroom");

    // Crystals
    public static final Block ROUXE_ROCK = crystalRock("rouxe_rock", 4, 4, 2, MaterialColor.RED);
    public static final Block BLOOMCRYSTAL_ROCK = crystalRock("bloomcrystal_rock", 4, 4, 14, MaterialColor.PINK);
    public static final Block ROUXE = crystal("rouxe", 4, 4, 3, MaterialColor.RED).hitbox(13, 12).offset(AbstractBlock.OffsetType.XZ);
    public static final Block BLOOMCRYSTAL = crystal("bloomcrystal", 4, 4, 15, MaterialColor.PINK).hitbox(13, 12).offset(AbstractBlock.OffsetType.XZ);
    public static final Block CRYSTAL_FLOWER = crystalFlower("crystal_flower", 0, 0, Material.PLANTS, MaterialColor.PINK).hitbox(13, 13).offset(AbstractBlock.OffsetType.XZ);

    // Dark pearl
    public static final Block DARK_PEARL_ORE = stone("dark_pearl_ore", 3, 6, MaterialColor.OBSIDIAN);
    public static final Block DARK_PEARL_BLOCK = darkPearl("dark_pearl_block", 3, 6, MaterialColor.BLACK);

    // Archaic
    public static final Block ARCHAIC_ORE = stone("archaic_ore", 3, 6, MaterialColor.OBSIDIAN);
    public static final Block ARCHAIC_GLASS = glass("archaic_glass");
    public static final Block ARCHAIC_GLASS_PANE = glassPane("archaic_glass_pane");

    // Tenebrum
    public static final Block TENEBRUM_ORE = ore("tenebrum_ore", 3, 3, MaterialColor.OBSIDIAN, 3);
    public static final Block TENEBRUM_BLOCK = rareMetal("tenebrum_block", 6.7, 8, MaterialColor.BLACK, 3);

    // Nagrilite
    public static final Block NAGRILITE_ORE = ore("nagrilite_ore", 3, 3, MaterialColor.OBSIDIAN, 2);
    public static final Block NAGRILITE_BLOCK = metal("nagrilite_block", 5, 6, MaterialColor.BLACK, 2);

    // Ebonite
    public static final Block EBONITE_ORE = xpOre("ebonite_ore", 3, 3, MaterialColor.OBSIDIAN, 1, 0, 2);
    public static final Block EBONITE_BLOCK = brittleMetal("ebonite_block", 3, 5, MaterialColor.BLACK, 1);

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
        GeodeHardMaterials.addMaterial(Material.ROCK);
        GeodeHardMaterials.addMaterial(Material.IRON);
        GeodeHardMaterials.addMaterial(Material.SHULKER);
        GeodeHardMaterials.addMaterial(Material.ANVIL);
        GeodeHardMaterials.addMaterial(MnMaterials.CRYSTAL_ROCK);
        GeodeHardMaterials.addMaterial(MnMaterials.VIRILUX);
    }


    ///////////////////////////
    ///// CLIENT REGISTRY /////
    ///////////////////////////

    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        RenderTypeLookup.setRenderLayer(NIGHT_GRASS_BLOCK, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(NIGHT_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_NIGHT_GRASS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(GHOST_PLANT_STEM, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(GHOST_PLANT_LEAF, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(GHOST_PLANT, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(DEAD_SAPLING, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(SHADOWROOT_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(SHADOWROOT_SAPLING, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(DARK_WILLOW_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(HANGING_DARK_WILLOW_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DARK_WILLOW_SAPLING, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(NIGHTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_NIGHTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(NIGHTSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_NIGHTSHROOM_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(DEWSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_DEWSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DEWSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_DEWSHROOM_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(VIRIDSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_VIRIDSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(VIRIDSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_VIRIDSHROOM_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MOONSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_MOONSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MOONSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWERING_MOONSHROOM_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_SHELF, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(GLOB_FUNGUS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MISTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_MISTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FINGERED_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(LUMEN_BUD, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_LUMEN_BUD, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RUNEBUSH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BOGWEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(CRYSTALOTUS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(VIOLEAF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TENDRILWEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(NIGHT_REED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DECEITFUL_MOSS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DECEITFUL_ALGAE, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ROUXE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BLOOMCRYSTAL, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(CRYSTAL_FLOWER, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ARCHAIC_GLASS, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ARCHAIC_GLASS_PANE, RenderType.getTranslucent());


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
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.STONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresTool()
        ));
    }

    private static Block bricks(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.NETHER_BRICKS)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresTool()
        ));
    }

    private static Block ore(String id, double hardness, double resistance, MaterialColor color, int toolLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.STONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(toolLevel)
                                    .requiresTool()
        ));
    }

    private static Block xpOre(String id, double hardness, double resistance, MaterialColor color, int toolLevel, int minxp, int maxxp) {
        return block(id, new XPDroppingBlock(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.STONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(toolLevel)
                                    .requiresTool(),
            minxp, maxxp
        ));
    }

    private static Block mycelium(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new NightMyceliumBlock(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.NYLIUM)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static Block dirt(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            AbstractBlock.Properties.create(Material.EARTH, color)
                                    .sound(SoundType.GROUND)
                                    .hardnessAndResistance(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block peat(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            AbstractBlock.Properties.create(Material.EARTH, color)
                                    .sound(MnSoundTypes.PEAT)
                                    .hardnessAndResistance(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block grassBlock(String id) {
        return block(id, new NightGrassBlock(
            AbstractBlock.Properties.create(Material.EARTH, MaterialColor.PURPLE_TERRACOTTA)
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance(0.6f)
                                    .harvestTool(ToolType.SHOVEL)
                                    .tickRandomly()
        ));
    }

    private static Block mud(String id) {
        return block(id, new DeceitfulMudBlock(
            AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                    .sound(MnSoundTypes.MUD)
                                    .hardnessAndResistance(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block sand(String id) {
        return block(id, new StrangeSandBlock(
            AbstractBlock.Properties.create(Material.SAND, MaterialColor.BLUE_TERRACOTTA)
                                    .sound(SoundType.SAND)
                                    .hardnessAndResistance(0.5f)
                                    .harvestTool(ToolType.SHOVEL)
        ));
    }

    private static Block water(String id, Supplier<FlowingFluid> fluid) {
        return block(id, new FlowingFluidBlock(
            fluid,
            AbstractBlock.Properties.create(Material.WATER)
                                    .hardnessAndResistance(100)
        ));
    }

    private static Block crystalRock(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(MnMaterials.CRYSTAL_ROCK, color)
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .luminance(state -> luminance)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresTool()
        ));
    }

    private static CrystalBlock crystal(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new CrystalBlock(
            AbstractBlock.Properties.create(MnMaterials.CRYSTAL, color)
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .luminance(state -> luminance)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .nonOpaque()
                                    .requiresTool()
        ));
    }

    private static Block rockshroom(String id) {
        return block(id, new RockshroomBlock(
            AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK)
                                    .sound(SoundType.NETHERRACK)
                                    .hardnessAndResistance(1.2f, 4)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static Block shroombricks(String id) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK)
                                    .sound(SoundType.BASALT)
                                    .hardnessAndResistance(1.2f, 4)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static PlantBlock plant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock crystalFlower(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock bush(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.SWEET_BERRY_BUSH)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static DirectionalPlantBlock directionalPlant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new DirectionalPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.CROP)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static FloatingPlantBlock floatingAlgae(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new FloatingPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.WET_GRASS)
                                    .hardnessAndResistance((float) hardness, (float) resistance),
            fluid -> fluid == MnFluids.DARK_WATER
        ));
    }

    private static PlantBlock glowingBush(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.SWEET_BERRY_BUSH)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock wetPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.WET_GRASS)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock fingeredGrass(String id) {
        return block(id, new FingeredGrassBlock(
            AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.BLUE)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance(0, 0)
        ));
    }

    private static PlantBlock emissivePlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock ghostPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.ROOTS)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static TallPlantBlock tallPlant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallGrowable(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance),
            tall
        ));
    }

    private static TallPlantBlock tallGlowingPlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallGlowingGrowable(String id, double hardness, double resistance, int emission, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance),
            tall
        ));
    }

    private static TallPlantBlock tallShroom(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new TallPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .luminance(state -> 10)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock smallShroom(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<TallPlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .luminance(state -> 10)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance),
            tall
        ));
    }

    private static PlantBlock smallFungus(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new PlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .luminance(state -> 10)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static PlantBlock shroomRoots(String id, Material material, MaterialColor color) {
        return block(id, new ShroomRootsBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.ROOTS)
                                    .hardnessAndResistance(0, 0)
        ));
    }

    private static ShroomShelfBlock shelf(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new ShroomShelfBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static CrystalotusBlock crystalotus(String id) {
        return block(id, new CrystalotusBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.LIGHT_BLUE)
                                    .nonOpaque()
                                    .sound(MnSoundTypes.CRYSTAL)
                                    .luminance(state -> 13)
                                    .hardnessAndResistance(0.2f, 0.5f)
        ));
    }

    private static Block suavis(String id) {
        return block(id, new SuavisBlock(
            AbstractBlock.Properties.create(Material.GOURD, MaterialColor.LIGHT_BLUE)
                                    .nonOpaque()
                                    .sound(SoundType.HONEY)
                                    .luminance(state -> state.get(SuavisBlock.STAGE) * 3 + 3)
                                    .tickRandomly()
                                    .suffocates((state, world, pos) -> state.get(SuavisBlock.STAGE) == 3)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance(1f, 0.2f)
        ));
    }

    private static VioleafBlock violeaf(String id) {
        return block(id, new VioleafBlock(
            AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE)
                                    .nonOpaque()
                                    .sound(SoundType.WET_GRASS)
                                    .tickRandomly()
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance(0)
        ));
    }

    private static TendrilweedBlock tendrilweed(String id) {
        return block(id, new TendrilweedBlock(
            AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED)
                                    .nonOpaque()
                                    .sound(SoundType.ROOTS)
                                    .tickRandomly()
                                    .hardnessAndResistance(0.1f, 0)
        ));
    }

    private static NightReedBlock nightReed(String id) {
        return block(id, new NightReedBlock(
            AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.ADOBE)
                                    .nonOpaque()
                                    .sound(SoundType.WET_GRASS)
                                    .tickRandomly()
                                    .hardnessAndResistance(0)
                                    .emissiveLighting((state, world, pos) -> !state.get(NightReedBlock.WATERLOGGED))
                                    .luminance(state -> state.get(NightReedBlock.WATERLOGGED) ? 0 : 10)
        ));
    }

    private static PlantBlock fibre(String id, Material material, MaterialColor color) {
        return block(id, new FibreBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.NETHER_SPROUTS)
                                    .hardnessAndResistance(0, 0)
        ));
    }

    private static Block giantGhostPlant(String id, Function<Block.Properties, Block> factory) {
        return block(id, factory.apply(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SNOW)
                                    .nonOpaque()
                                    .sound(SoundType.SHROOMLIGHT)
                                    .hardnessAndResistance(0.3f)
                                    .luminance(state -> 15)
        ));
    }

    private static Block leaves(String id, MaterialColor color) {
        return block(id, new LeavesBlock(
            AbstractBlock.Properties.create(Material.LEAVES, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .harvestTool(ToolType.HOE)
                                    .suffocates((state, world, pos) -> false)
                                    .hardnessAndResistance(0.2f)
        ));
    }

    private static Block growsHangingLeaves(String id, MaterialColor color, Supplier<Block> hanging) {
        return block(id, new HangingLeavesGrowingBlock(
            AbstractBlock.Properties.create(Material.LEAVES, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .harvestTool(ToolType.HOE)
                                    .suffocates((state, world, pos) -> false)
                                    .hardnessAndResistance(0.2f),
            hanging
        ));
    }

    private static HangingLeavesBlock hangingLeaves(String id, MaterialColor color, Supplier<Block> leaves, ITag.INamedTag<Block> logs) {
        return block(id, new HangingLeavesBlock(
            AbstractBlock.Properties.create(Material.LEAVES, color)
                                    .nonOpaque()
                                    .harvestTool(ToolType.HOE)
                                    .sound(SoundType.CROP) // Make them sound a bit less leafier
                                    .hardnessAndResistance(0.1f),
            leaves, logs
        ));
    }

    private static Block log(String id, MaterialColor color, Supplier<Block> stripped) {
        return block(id, new StripableRotatedPillarBlock(
            AbstractBlock.Properties.create(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(2f),
            stripped
        ));
    }

    private static Block log(String id, MaterialColor color) {
        return block(id, new RotatedPillarBlock(
            AbstractBlock.Properties.create(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(2f)
        ));
    }

    private static Block stem(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.NETHER_STEM)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(2f)
        ));
    }

    private static Block globStem(String id, MaterialColor color) {
        return block(id, new RotatedPillarBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.NETHER_STEM)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(1.7f)
        ));
    }

    private static Block infestedGlobStem(String id, MaterialColor color) {
        return block(id, new HorizontalFacingBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.NETHER_STEM)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(0.4f)
        ));
    }

    private static Block thatch(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.WART_BLOCK)
                                    .harvestTool(ToolType.HOE)
                                    .hardnessAndResistance(0.8f)
        ));
    }

    private static Block reedThatch(String id, MaterialColor color, Supplier<Block> cut) {
        return block(id, new ShearableBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.WART_BLOCK)
                                    .harvestTool(ToolType.HOE)
                                    .luminance(state -> 8)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance(0.8f),
            cut
        ));
    }

    private static Block wood(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(2f)
        ));
    }

    private static Block bookshelf(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.WOOD, color)
                                    .sound(SoundType.WOOD)
                                    .hardnessAndResistance(1.5f)
        ));
    }

    private static Block shroomAir(String id) {
        return block(id, new ShroomAirBlock(
            AbstractBlock.Properties.create(Material.AIR, MaterialColor.AIR)
                                    .nonOpaque()
                                    .suffocates((state, world, pos) -> false)
                                    .luminance(state -> 15)
                                    .noDrops()
        ));
    }

    private static Block shroomCap(String id, MaterialColor color, int sporeColor) {
        return block(id, new ShroomCapBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .harvestTool(ToolType.HOE)
                                    .hardnessAndResistance(1.7f)
                                    .sound(SoundType.WART_BLOCK),
            sporeColor
        ));
    }

    private static Block globCap(String id, MaterialColor color) {
        return block(id, new BouncyShroomCapBlock(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .harvestTool(ToolType.HOE)
                                    .hardnessAndResistance(1.1f)
                                    .luminance(state -> 15)
                                    .sound(SoundType.SLIME)
        ));
    }

    private static Block darkPearl(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.IRON, color)
                                    .nonOpaque()
                                    .sound(SoundType.BONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .requiresTool()
        ));
    }

    private static Block metal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.IRON, color)
                                    .sound(SoundType.METAL)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresTool()
        ));
    }

    private static Block brittleMetal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.IRON, color)
                                    .sound(MnSoundTypes.BRITTLE_METAL)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresTool()
        ));
    }

    private static Block rareMetal(String id, double hardness, double resistance, MaterialColor color, int harvestLevel) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.IRON, color)
                                    .sound(SoundType.NETHERITE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(harvestLevel)
                                    .requiresTool()
        ));
    }

    private static Block glass(String id) {
        return block(id, new GlassBlock(
            AbstractBlock.Properties.create(Material.GLASS, MaterialColor.PINK)
                                    .nonOpaque()
                                    .sound(SoundType.GLASS)
                                    .hardnessAndResistance(0.3F)
        ));
    }

    private static Block glassPane(String id) {
        return block(id, new PaneBlock(
            AbstractBlock.Properties.create(Material.GLASS, MaterialColor.PINK)
                                    .nonOpaque()
                                    .sound(SoundType.GLASS)
                                    .hardnessAndResistance(0.3F)
        ));
    }

    private static Block virilux(String id) {
        return block(id, new Block(
            AbstractBlock.Properties.create(MnMaterials.VIRILUX, MaterialColor.LIME)
                                    .sound(MnSoundTypes.JEWEL)
                                    .hardnessAndResistance(6.5F, 8)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(3)
                                    .luminance(state -> 15)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .requiresTool()
        ));
    }

    private static Block viriluxOre(String id) {
        return block(id, new XPDroppingBlock(
            AbstractBlock.Properties.create(Material.ROCK, MaterialColor.OBSIDIAN)
                                    .sound(SoundType.STONE)
                                    .hardnessAndResistance(5, 5)
                                    .harvestTool(ToolType.PICKAXE)
                                    .harvestLevel(3)
                                    .luminance(state -> 4)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .requiresTool(),
            3, 6
        ));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Block> Supplier<T> getBlock(String id) {
        return () -> (T) BLOCKS.get(Midnight.id(id));
    }

    private static Supplier<BlockState> getState(String id) {
        return () -> BLOCKS.get(Midnight.id(id)).getDefaultState();
    }

    private static Supplier<BlockState> getState(String id, Function<BlockState, BlockState> props) {
        return () -> props.apply(BLOCKS.get(Midnight.id(id)).getDefaultState());
    }
}
