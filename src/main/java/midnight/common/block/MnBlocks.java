/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import midnight.common.Midnight;
import midnight.common.block.type.BlockTypes;
import midnight.core.util.ToolLevel;
import midnight.core.util.WrappingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public abstract class MnBlocks {
    private static final Map<Identifier, Block> BLOCKS = new HashMap<>();

    // Basic stones
    public static final Block NIGHTSTONE = stone("nightstone", 1.5, 6, MaterialColor.BLACK);
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


    public static final Block NIGHTSTONE_BRICKS = bricks("nightstone_bricks", 1.5, 6, MaterialColor.SPRUCE);
    public static final Block TRENCHSTONE_BRICKS = bricks("trenchstone_bricks", 1.5, 6, MaterialColor.BLACK);
    public static final Block SHROOMBRICKS = shroombricks("shroombricks");

    // Plants
    public static final Block NIGHT_GRASS = Blocks.AIR;// TODO TEMPORARY
    public static final Block GLOB_FUNGUS = Blocks.AIR;// TODO TEMPORARY



    //////////////////////////
    ///// BLOCK REGISTRY /////
    //////////////////////////

    public static void registerBlocks(WrappingRegistry<Block> registry) {
        registry.registerAll(BLOCKS);
    }

    @Environment(EnvType.CLIENT)
    public static void setupRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(NIGHT_GRASS_BLOCK, RenderLayer.getCutoutMipped());
    }



    private MnBlocks() {
    }

    /////////////////////////////////
    ///// BLOCK FACTORY METHODS /////
    /////////////////////////////////

    private static <B extends Block> B block(String id, B block) {
        Registry.register(Registry.BLOCK, Midnight.id(id), block);
        return block;
    }

    private static Block stone(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            BlockTypes.stone(hardness, resistance, color, BlockSoundGroup.STONE, ToolLevel.WOOD)
        ));
    }

    private static Block bricks(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            BlockTypes.stone(hardness, resistance, color, BlockSoundGroup.NETHER_BRICKS, ToolLevel.WOOD)
        ));
    }

    private static Block ore(String id, double hardness, double resistance, MaterialColor color, int toolLevel) {
        return block(id, new Block(
            BlockTypes.stone(hardness, resistance, color, BlockSoundGroup.STONE, toolLevel)
        ));
    }

    private static Block xpOre(String id, double hardness, double resistance, MaterialColor color, int toolLevel, int minxp, int maxxp) {
        return block(id, new XPDroppingBlock(
            BlockTypes.stone(hardness, resistance, color, BlockSoundGroup.STONE, toolLevel),
            minxp, maxxp
        ));
    }

    private static Block mycelium(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new NightMyceliumBlock(
            BlockTypes.stone(hardness, resistance, color, BlockSoundGroup.NYLIUM, ToolLevel.WOOD)
        ));
    }

    private static Block dirt(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            BlockTypes.soil(0.5, color, BlockSoundGroup.GRAVEL)
        ));
    }

    private static Block peat(String id, MaterialColor color) {
        return block(id, new NightDirtBlock(
            BlockTypes.soil(0.5, color, MnBlockSoundGroups.PEAT)
        ));
    }

    private static Block grassBlock(String id) {
        return block(id, new NightGrassBlock(
            BlockTypes.organicSoil(0.5, MaterialColor.PURPLE_TERRACOTTA, BlockSoundGroup.GRAVEL)
        ));
    }

    private static Block mud(String id) {
        return block(id, new DeceitfulMudBlock(
            BlockTypes.soil(0.5, MaterialColor.BLUE_TERRACOTTA, MnBlockSoundGroups.MUD)
        ));
    }

    private static Block sand(String id) {
        return block(id, new StrangeSandBlock(
            BlockTypes.aggregateSoil(0.5, MaterialColor.PURPLE_TERRACOTTA, BlockSoundGroup.SAND)
        ));
    }

    private static Block water(String id, FlowableFluid fluid) {
        return block(id, new MnFluidBlock(
            fluid,
            FabricBlockSettings.of(Material.WATER).strength(100)
        ));
    }

    private static Block crystalRock(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new Block(
            FabricBlockSettings.of(MnMaterials.CRYSTAL_ROCK, color)
                               .sounds(MnBlockSoundGroups.CRYSTAL)
                               .luminance(state -> luminance)
                               .emissiveLighting((state, world, pos) -> true)
                               .strength((float) hardness, (float) resistance)
                               .requiresTool()
                               .breakByHand(false)
                               .breakByTool(FabricToolTags.PICKAXES, ToolLevel.STONE)
        ));
    }

    private static CrystalBlock crystal(String id, double hardness, double resistance, int luminance, MaterialColor color) {
        return block(id, new CrystalBlock(
            FabricBlockSettings.of(MnMaterials.CRYSTAL, color)
                               .sounds(MnBlockSoundGroups.CRYSTAL)
                               .luminance(state -> luminance)
                               .emissiveLighting((state, world, pos) -> true)
                               .strength((float) hardness, (float) resistance)
                               .nonOpaque()
                               .requiresTool()
                               .breakByHand(false)
                               .breakByTool(FabricToolTags.PICKAXES, ToolLevel.STONE)
        ));
    }

    private static Block shroombricks(String id) {
        return block(id, new Block(
            BlockTypes.stone(1.2, 4, MaterialColor.PINK, BlockSoundGroup.BASALT, ToolLevel.WOOD)
        ));
    }
}
