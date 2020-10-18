/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.block;

import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.common.block.fluid.MnFluids;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.common.world.biome.MnBiomeColors;
import midnight.core.util.ColorUtil;
import midnight.core.util.IRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class registers and stores the list of Midnight blocks and their respective block items.
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public final class MnBlocks {

    public static final Block NIGHTSTONE = inj();
    public static final Block NIGHT_BEDROCK = inj();
    public static final Block TRENCHSTONE = inj();

    public static final Block NIGHT_DIRT = inj();
    public static final Block COARSE_NIGHT_DIRT = inj();
    public static final Block NIGHT_GRASS_BLOCK = inj();
    public static final Block DECEITFUL_PEAT = inj();
    public static final Block DECEITFUL_MUD = inj();
    public static final Block STRANGE_SAND = inj();
    public static final Block NIGHT_MYCELIUM = inj();

    public static final Block DARK_WATER = inj();

    public static final Block NIGHT_GRASS = inj();
    public static final Block TALL_NIGHT_GRASS = inj();

    public static final Block GHOST_PLANT_STEM = inj();
    public static final Block GHOST_PLANT_LEAF = inj();
    public static final Block GHOST_PLANT = inj();

    public static final Block DEAD_WOOD_LOG = inj();
    public static final Block STRIPPED_DEAD_WOOD_LOG = inj();
    public static final Block DEAD_WOOD = inj();
    public static final Block STRIPPED_DEAD_WOOD = inj();
    public static final Block DEAD_WOOD_PLANKS = inj();
    public static final Block DEAD_SAPLING = inj();

    public static final Block SHADOWROOT_LOG = inj();
    public static final Block STRIPPED_SHADOWROOT_LOG = inj();
    public static final Block SHADOWROOT_WOOD = inj();
    public static final Block STRIPPED_SHADOWROOT_WOOD = inj();
    public static final Block SHADOWROOT_LEAVES = inj();
    public static final Block SHADOWROOT_PLANKS = inj();
    public static final Block SHADOWROOT_SAPLING = inj();

    public static final Block DARK_WILLOW_LOG = inj();
    public static final Block STRIPPED_DARK_WILLOW_LOG = inj();
    public static final Block DARK_WILLOW_WOOD = inj();
    public static final Block STRIPPED_DARK_WILLOW_WOOD = inj();
    public static final Block DARK_WILLOW_LEAVES = inj();
    public static final Block HANGING_DARK_WILLOW_LEAVES = inj();
    public static final Block DARK_WILLOW_PLANKS = inj();
    public static final Block DARK_WILLOW_SAPLING = inj();

    public static final Block SHROOM_AIR = inj();

    public static final Block NIGHTSHROOM_CAP = inj();
    public static final Block NIGHTSHROOM_STEM = inj();
    public static final Block NIGHTSHROOM_PLANKS = inj();
    public static final Block NIGHTSHROOM = inj();
    public static final Block TALL_NIGHTSHROOM = inj();
    public static final Block NIGHTSHROOM_SHELF = inj();
    public static final Block NIGHTSHROOM_FIBRE = inj();
    public static final Block NIGHTSHROOM_ROOTS = inj();
    public static final Block FLOWERING_NIGHTSHROOM_ROOTS = inj();

    public static final Block DEWSHROOM_CAP = inj();
    public static final Block DEWSHROOM_STEM = inj();
    public static final Block DEWSHROOM_PLANKS = inj();
    public static final Block DEWSHROOM = inj();
    public static final Block TALL_DEWSHROOM = inj();
    public static final Block DEWSHROOM_SHELF = inj();
    public static final Block DEWSHROOM_FIBRE = inj();
    public static final Block DEWSHROOM_ROOTS = inj();
    public static final Block FLOWERING_DEWSHROOM_ROOTS = inj();

    public static final Block VIRIDSHROOM_CAP = inj();
    public static final Block VIRIDSHROOM_STEM = inj();
    public static final Block VIRIDSHROOM_PLANKS = inj();
    public static final Block VIRIDSHROOM = inj();
    public static final Block TALL_VIRIDSHROOM = inj();
    public static final Block VIRIDSHROOM_SHELF = inj();
    public static final Block VIRIDSHROOM_FIBRE = inj();
    public static final Block VIRIDSHROOM_ROOTS = inj();
    public static final Block FLOWERING_VIRIDSHROOM_ROOTS = inj();

    public static final Block BOGSHROOM_CAP = inj();
    public static final Block BOGSHROOM_STEM = inj();
    public static final Block BOGSHROOM_PLANKS = inj();
    public static final Block BOGSHROOM = inj();
    public static final Block TALL_BOGSHROOM = inj();
    public static final Block BOGSHROOM_SHELF = inj();
    public static final Block BOGSHROOM_FIBRE = inj();

    public static final Block GLOB_FUNGUS = inj();
    public static final Block GLOB_FUNGUS_CAP = inj();
    public static final Block GLOB_FUNGUS_STEM = inj();
    public static final Block GLOB_FUNGUS_HYPHAE = inj();
    public static final Block GLOB_FUNGUS_THATCH = inj();

    public static final Block DARK_PEARL_ORE = inj();
    public static final Block DARK_PEARL_BLOCK = inj();

    public static void registerBlocks(IRegistry<Block> registry) {
        registry.registerAll(
            stone("nightstone", 1.5, 6, MaterialColor.OBSIDIAN),
            stone("night_bedrock", 1.5, 6, MaterialColor.LIGHT_GRAY_TERRACOTTA),
            stone("trenchstone", 1.5, 6, MaterialColor.BLACK),

            dirt("night_dirt", MaterialColor.BLACK),
            dirt("coarse_night_dirt", MaterialColor.BLACK),
            grassBlock("night_grass_block"),
            dirt("deceitful_peat", MaterialColor.PURPLE_TERRACOTTA),
            mud("deceitful_mud"),
            sand("strange_sand"),
            mycelium("night_mycelium", 2, 6, MaterialColor.MAGENTA),

            water("dark_water", () -> MnFluids.DARK_WATER),

            smallGrowable("night_grass", 0, 0, Material.TALL_PLANTS, MaterialColor.PURPLE_TERRACOTTA, () -> (MnDoublePlantBlock) TALL_NIGHT_GRASS).setPlantHitbox(12, 13).setOffsetType(Block.OffsetType.XYZ),
            tallPlant("tall_night_grass", 0, 0, Material.TALL_PLANTS, MaterialColor.PURPLE_TERRACOTTA).setPlantHitbox(14, 30).setOffsetType(Block.OffsetType.XYZ),

            giantGhostPlant("ghost_plant_stem", GhostPlantStemBlock::new),
            giantGhostPlant("ghost_plant_leaf", GhostPlantBlock::new),
            emissivePlant("ghost_plant", 0, 0, 9, Material.PLANTS, MaterialColor.SNOW).setPlantHitbox(13, 14).setOffsetType(Block.OffsetType.XZ),

            log("dead_wood_log", MaterialColor.FOLIAGE, () -> STRIPPED_DEAD_WOOD_LOG),
            log("stripped_dead_wood_log", MaterialColor.FOLIAGE),
            log("dead_wood", MaterialColor.FOLIAGE, () -> STRIPPED_DEAD_WOOD),
            log("stripped_dead_wood", MaterialColor.FOLIAGE),
            wood("dead_wood_planks", MaterialColor.FOLIAGE),
            plant("dead_sapling", 0, 0, Material.PLANTS, MaterialColor.FOLIAGE).setPlantHitbox(12, 13),

            log("shadowroot_log", MaterialColor.PURPLE, () -> STRIPPED_SHADOWROOT_LOG),
            log("stripped_shadowroot_log", MaterialColor.PURPLE),
            log("shadowroot_wood", MaterialColor.PURPLE, () -> STRIPPED_SHADOWROOT_WOOD),
            log("stripped_shadowroot_wood", MaterialColor.PURPLE),
            leaves("shadowroot_leaves", MaterialColor.PURPLE),
            wood("shadowroot_planks", MaterialColor.PURPLE),
            plant("shadowroot_sapling", 0, 0, Material.PLANTS, MaterialColor.PURPLE).setPlantHitbox(11, 15),

            log("dark_willow_log", MaterialColor.BLUE, () -> STRIPPED_DARK_WILLOW_LOG),
            log("stripped_dark_willow_log", MaterialColor.BLUE),
            log("dark_willow_wood", MaterialColor.BLUE, () -> STRIPPED_DARK_WILLOW_WOOD),
            log("stripped_dark_willow_wood", MaterialColor.BLUE),
            growsHangingLeaves("dark_willow_leaves", MaterialColor.BLUE_TERRACOTTA, () -> HANGING_DARK_WILLOW_LEAVES),
            hangingLeaves("hanging_dark_willow_leaves", MaterialColor.BLUE_TERRACOTTA, () -> DARK_WILLOW_LEAVES, MnBlockTags.DARK_WILLOW_LOGS).setPlantHitbox(14, 16),
            wood("dark_willow_planks", MaterialColor.BLUE_TERRACOTTA),
            plant("dark_willow_sapling", 0, 0, Material.PLANTS, MaterialColor.BLUE_TERRACOTTA).setPlantHitbox(12, 14),

            shroomAir("shroom_air"),

            shroomCap("nightshroom_cap", MaterialColor.BLUE, 0x7566B0),
            stem("nightshroom_stem", MaterialColor.BLUE),
            wood("nightshroom_planks", MaterialColor.BLUE),
            smallShroom("nightshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.BLUE, () -> (MnDoublePlantBlock) TALL_NIGHTSHROOM).setPlantHitbox(14, 14).setOffsetType(Block.OffsetType.XZ),
            tallShroom("tall_nightshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.BLUE).setPlantHitbox(14, 30).setOffsetType(Block.OffsetType.XZ),
            shelf("nightshroom_shelf", 0, 0, Material.TALL_PLANTS, MaterialColor.BLUE),
            fibre("nightshroom_fibre", Material.TALL_PLANTS, MaterialColor.BLUE),
            shroomRoots("nightshroom_roots", Material.TALL_PLANTS, MaterialColor.BLUE).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),
            shroomRoots("flowering_nightshroom_roots", Material.TALL_PLANTS, MaterialColor.BLUE).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),

            shroomCap("dewshroom_cap", MaterialColor.CYAN, 0x72CFD4),
            stem("dewshroom_stem", MaterialColor.CYAN),
            wood("dewshroom_planks", MaterialColor.CYAN),
            smallShroom("dewshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.CYAN, () -> (MnDoublePlantBlock) TALL_DEWSHROOM).setPlantHitbox(14, 14).setOffsetType(Block.OffsetType.XZ),
            tallShroom("tall_dewshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.CYAN).setPlantHitbox(14, 30).setOffsetType(Block.OffsetType.XZ),
            shelf("dewshroom_shelf", 0, 0, Material.TALL_PLANTS, MaterialColor.CYAN),
            fibre("dewshroom_fibre", Material.TALL_PLANTS, MaterialColor.CYAN),
            shroomRoots("dewshroom_roots", Material.TALL_PLANTS, MaterialColor.CYAN).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),
            shroomRoots("flowering_dewshroom_roots", Material.TALL_PLANTS, MaterialColor.CYAN).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),

            shroomCap("viridshroom_cap", MaterialColor.LIME, 0x84F54C),
            stem("viridshroom_stem", MaterialColor.LIME),
            wood("viridshroom_planks", MaterialColor.LIME),
            smallShroom("viridshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.LIME, () -> (MnDoublePlantBlock) TALL_VIRIDSHROOM).setPlantHitbox(14, 14).setOffsetType(Block.OffsetType.XZ),
            tallShroom("tall_viridshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.LIME).setPlantHitbox(14, 30).setOffsetType(Block.OffsetType.XZ),
            shelf("viridshroom_shelf", 0, 0, Material.TALL_PLANTS, MaterialColor.LIME),
            fibre("viridshroom_fibre", Material.TALL_PLANTS, MaterialColor.LIME),
            shroomRoots("viridshroom_roots", Material.TALL_PLANTS, MaterialColor.LIME).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),
            shroomRoots("flowering_viridshroom_roots", Material.TALL_PLANTS, MaterialColor.LIME).setPlantHitbox(13, 14).setOffsetType(AbstractBlock.OffsetType.XZ),

            shroomCap("bogshroom_cap", MaterialColor.ADOBE, 0xF5AF4C),
            stem("bogshroom_stem", MaterialColor.ADOBE),
            wood("bogshroom_planks", MaterialColor.ADOBE),
            smallShroom("bogshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.ADOBE, () -> (MnDoublePlantBlock) TALL_BOGSHROOM).setPlantHitbox(14, 14).setOffsetType(Block.OffsetType.XZ),
            tallShroom("tall_bogshroom", 0, 0, Material.TALL_PLANTS, MaterialColor.ADOBE).setPlantHitbox(14, 30).setOffsetType(Block.OffsetType.XZ),
            shelf("bogshroom_shelf", 0, 0, Material.TALL_PLANTS, MaterialColor.ADOBE),
            fibre("bogshroom_fibre", Material.TALL_PLANTS, MaterialColor.ADOBE),

            smallFungus("glob_fungus", 0, 0, Material.TALL_PLANTS, MaterialColor.MAGENTA).setPlantHitbox(13, 13).setOffsetType(Block.OffsetType.XYZ),
            globCap("glob_fungus_cap", MaterialColor.MAGENTA),
            globStem("glob_fungus_stem", MaterialColor.PURPLE),
            globStem("glob_fungus_hyphae", MaterialColor.PURPLE),
            globThatch("glob_fungus_thatch", MaterialColor.PURPLE),

            stone("dark_pearl_ore", 3, 6, MaterialColor.OBSIDIAN),
            darkPearl("dark_pearl_block", 3, 6, MaterialColor.BLACK)
        );
    }

    public static void registerItems(IRegistry<Item> registry) {
        registry.registerAll(
            item(NIGHTSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(NIGHT_BEDROCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(TRENCHSTONE, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),

            item(NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(COARSE_NIGHT_DIRT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(NIGHT_GRASS_BLOCK, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(DECEITFUL_PEAT, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(DECEITFUL_MUD, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(STRANGE_SAND, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),
            item(NIGHT_MYCELIUM, MnItemCategory.SEDIMENTAL, MnItemGroup.BLOCKS),

            item(NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(TALL_NIGHT_GRASS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(GHOST_PLANT_STEM, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(GHOST_PLANT_LEAF, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(GHOST_PLANT, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_DEAD_WOOD_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_DEAD_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(DEAD_WOOD_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(DEAD_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),

            item(SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_SHADOWROOT_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_SHADOWROOT_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(SHADOWROOT_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(SHADOWROOT_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(SHADOWROOT_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),

            item(DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_DARK_WILLOW_LOG, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(STRIPPED_DARK_WILLOW_WOOD, MnItemCategory.LOGS, MnItemGroup.BLOCKS),
            item(DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(HANGING_DARK_WILLOW_LEAVES, MnItemCategory.LEAVES, MnItemGroup.DECOR),
            item(DARK_WILLOW_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(DARK_WILLOW_SAPLING, MnItemCategory.SAPLINGS, MnItemGroup.DECOR),

            item(NIGHTSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(NIGHTSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(NIGHTSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(TALL_NIGHTSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(NIGHTSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(NIGHTSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(FLOWERING_NIGHTSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(DEWSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(DEWSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(DEWSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(TALL_DEWSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(DEWSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(DEWSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(FLOWERING_DEWSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(VIRIDSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(VIRIDSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(VIRIDSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(TALL_VIRIDSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(VIRIDSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(VIRIDSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(FLOWERING_VIRIDSHROOM_ROOTS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(BOGSHROOM_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(BOGSHROOM_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(BOGSHROOM_PLANKS, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),
            item(BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(TALL_BOGSHROOM, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(BOGSHROOM_SHELF, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(BOGSHROOM_FIBRE, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),

            item(GLOB_FUNGUS, MnItemCategory.COMMON_PLANTS, MnItemGroup.DECOR),
            item(GLOB_FUNGUS_CAP, MnItemCategory.SHROOM_CAPS, MnItemGroup.BLOCKS),
            item(GLOB_FUNGUS_STEM, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(GLOB_FUNGUS_HYPHAE, MnItemCategory.SHROOM_STEMS, MnItemGroup.BLOCKS),
            item(GLOB_FUNGUS_THATCH, MnItemCategory.PLANKS, MnItemGroup.BLOCKS),

            item(DARK_PEARL_ORE, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS),
            item(DARK_PEARL_BLOCK, MnItemCategory.UNCATEGORIZED, MnItemGroup.BLOCKS)
        );
    }

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

        RenderTypeLookup.setRenderLayer(BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TALL_BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_FIBRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BOGSHROOM_SHELF, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(GLOB_FUNGUS, RenderType.getCutout());


        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        blockColors.register(
            (state, world, pos, tint) -> {
                if (pos == null || world == null) return 0x9A63B8;
                return MidnightClient.get().getNightGrassColorCache().getColor(pos, MnBiomeColors.NIGHT_GRASS);
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

    private static BlockItem item(Block block, MnItemCategory cat, Item.Properties props) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;
        BlockItem item = new BlockItem(block, props);
        item.setRegistryName(id);
        cat.add(item);
        return item;
    }

    private static BlockItem item(Block block, MnItemCategory cat, ItemGroup group) {
        return item(block, cat, new Item.Properties().group(group));
    }

    private static <B extends Block> B block(String id, B block) {
        block.setRegistryName(Midnight.resLoc(id));
        return block;
    }

    private static Block stone(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.STONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    private static Block mycelium(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new NightMyceliumBlock(
            AbstractBlock.Properties.create(Material.ROCK, color)
                                    .sound(SoundType.STONE)
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

    private static MnPlantBlock plant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new MnPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static MnPlantBlock emissivePlant(String id, double hardness, double resistance, int emission, Material material, MaterialColor color) {
        return block(id, new MnPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .luminance(state -> emission)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static MnDoublePlantBlock tallPlant(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new MnDoublePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static MnPlantBlock smallGrowable(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<MnDoublePlantBlock> tall) {
        return block(id, new SmallGrowablePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.PLANT)
                                    .hardnessAndResistance((float) hardness, (float) resistance),
            tall
        ));
    }

    private static MnDoublePlantBlock tallShroom(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new MnDoublePlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .luminance(state -> 10)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static MnPlantBlock smallShroom(String id, double hardness, double resistance, Material material, MaterialColor color, Supplier<MnDoublePlantBlock> tall) {
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

    private static MnPlantBlock smallFungus(String id, double hardness, double resistance, Material material, MaterialColor color) {
        return block(id, new MnPlantBlock(
            AbstractBlock.Properties.create(material, color)
                                    .nonOpaque()
                                    .sound(SoundType.FUNGUS)
                                    .luminance(state -> 10)
                                    .emissiveLighting((state, world, pos) -> true)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
        ));
    }

    private static MnPlantBlock shroomRoots(String id, Material material, MaterialColor color) {
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

    private static MnPlantBlock fibre(String id, Material material, MaterialColor color) {
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
                                    .sound(SoundType.NETHER_STEM)
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
                                    .sound(SoundType.FUNGUS)
                                    .harvestTool(ToolType.AXE)
                                    .hardnessAndResistance(2f)
        ));
    }

    private static Block globThatch(String id, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Properties.create(Material.ORGANIC, color)
                                    .sound(SoundType.WART_BLOCK)
                                    .harvestTool(ToolType.HOE)
                                    .hardnessAndResistance(1.5f)
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
                                    .sound(SoundType.BONE)
                                    .hardnessAndResistance((float) hardness, (float) resistance)
                                    .harvestTool(ToolType.PICKAXE)
        ));
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static Block inj() {
        return null;
    }
}
