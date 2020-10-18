/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.data.models;

import midnight.common.block.MnBlocks;
import midnight.common.block.ShroomCapBlock;
import midnight.data.models.modelgen.IModelGen;
import midnight.data.models.modelgen.InheritingModelGen;
import midnight.data.models.stategen.IBlockStateGen;
import midnight.data.models.stategen.ModelInfo;
import midnight.data.models.stategen.VariantBlockStateGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.InheritingModelGen.*;

public final class BlockStateTable {
    private static BiConsumer<Block, IBlockStateGen> consumer;

    public static void registerBlockStates(BiConsumer<Block, IBlockStateGen> c) {
        consumer = c;

        // Please keep these on single lines for the ease of line duplicating

        register(MnBlocks.NIGHTSTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NIGHT_BEDROCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.TRENCHSTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.NIGHT_DIRT, block -> rotateY(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NIGHT_GRASS_BLOCK, block -> rotateY(name(block, "block/%s"), grassBlock(name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/night_dirt"), name(block, "block/%s_overlay"))));
        register(MnBlocks.COARSE_NIGHT_DIRT, block -> rotateY(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DECEITFUL_MUD, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DECEITFUL_PEAT, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.STRANGE_SAND, block -> rotateXY(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NIGHT_MYCELIUM, block -> rotateY(name(block, "block/%s"), cubeBottomTop(name(block, "block/nightstone"), name(block, "block/%s_top"), name(block, "block/%s_side"))));

        register(MnBlocks.NIGHT_GRASS, block -> simple(name(block, "block/%s"), tintedCross(name(block, "block/%s"))));
        register(MnBlocks.TALL_NIGHT_GRASS, block -> doublePlant(name(block, "block/%s_lower"), tintedCross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), tintedCross(name(block, "block/%s_upper"))));

        register(MnBlocks.DARK_WATER, block -> none());

        register(MnBlocks.GHOST_PLANT, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.GHOST_PLANT_LEAF, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.GHOST_PLANT_STEM, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));

        register(MnBlocks.DEAD_WOOD_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.STRIPPED_DEAD_WOOD_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.DEAD_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side"))));
        register(MnBlocks.STRIPPED_DEAD_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side"))));
        register(MnBlocks.DEAD_WOOD_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DEAD_SAPLING, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));

        register(MnBlocks.SHADOWROOT_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.STRIPPED_SHADOWROOT_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.SHADOWROOT_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side", "_wood"))));
        register(MnBlocks.STRIPPED_SHADOWROOT_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side", "_wood"))));
        register(MnBlocks.SHADOWROOT_LEAVES, block -> simple(name(block, "block/%s"), leaves(name(block, "block/%s"))));
        register(MnBlocks.SHADOWROOT_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.SHADOWROOT_SAPLING, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));


        register(MnBlocks.DARK_WILLOW_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.STRIPPED_DARK_WILLOW_LOG, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.DARK_WILLOW_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side", "_wood"))));
        register(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_log_side", "_wood"))));
        register(MnBlocks.DARK_WILLOW_LEAVES, block -> simple(name(block, "block/%s"), leaves(name(block, "block/%s"))));
        register(MnBlocks.HANGING_DARK_WILLOW_LEAVES, block -> hangingPlant(name(block, "block/%s_end"), hangingLeavesEnd(name(block, "block/%s_inner"), name(block, "block/%s_end")), name(block, "block/%s"), hangingLeavesRoot(name(block, "block/%s_inner"), name(block, "block/%s_outer"))));
        register(MnBlocks.DARK_WILLOW_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DARK_WILLOW_SAPLING, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));

        register(MnBlocks.SHROOM_AIR, block -> none());

        register(MnBlocks.NIGHTSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.NIGHTSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NIGHTSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NIGHTSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.NIGHTSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.NIGHTSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.NIGHTSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_NIGHTSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.DEWSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.DEWSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DEWSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DEWSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.DEWSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.DEWSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.DEWSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_DEWSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.VIRIDSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.VIRIDSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.VIRIDSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.VIRIDSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.VIRIDSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.VIRIDSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.VIRIDSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_VIRIDSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.BOGSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.BOGSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.BOGSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_BOGSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.GLOB_FUNGUS_CAP, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS_HYPHAE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s_stem_side", "_hyphae"))));
        register(MnBlocks.GLOB_FUNGUS_THATCH, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS_STEM, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));

        register(MnBlocks.DARK_PEARL_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DARK_PEARL_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
    }

    private static IBlockStateGen none() {
        return VariantBlockStateGen.create(ModelInfo.create("midnight:block/nomodel"));
    }

    private static IBlockStateGen simple(String name, IModelGen model) {
        return VariantBlockStateGen.create(ModelInfo.create(name, model));
    }

    private static IBlockStateGen hangingPlant(String endName, IModelGen endModel, String rootName, IModelGen rootModel) {
        return VariantBlockStateGen.create("end=true", ModelInfo.create(endName, endModel))
                                   .variant("end=false", ModelInfo.create(rootName, rootModel));
    }

    private static IBlockStateGen fibre(String model, String lessFibre, String moreFibre) {
        return VariantBlockStateGen.create("dense=true", ModelInfo.create(model + "_dense", layeredPlant(moreFibre, lessFibre)))
                                   .variant("dense=false", ModelInfo.create(model, layeredPlant(lessFibre)));
    }

    private static IBlockStateGen rotateY(String name, IModelGen model) {
        return VariantBlockStateGen.create(
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270)
        );
    }

    private static IBlockStateGen rotateXY(String name, IModelGen model) {
        return VariantBlockStateGen.create(
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270),
            ModelInfo.create(name, model).rotate(90, 0),
            ModelInfo.create(name, model).rotate(90, 90),
            ModelInfo.create(name, model).rotate(90, 180),
            ModelInfo.create(name, model).rotate(90, 270),
            ModelInfo.create(name, model).rotate(180, 0),
            ModelInfo.create(name, model).rotate(180, 90),
            ModelInfo.create(name, model).rotate(180, 180),
            ModelInfo.create(name, model).rotate(180, 270),
            ModelInfo.create(name, model).rotate(270, 0),
            ModelInfo.create(name, model).rotate(270, 90),
            ModelInfo.create(name, model).rotate(270, 180),
            ModelInfo.create(name, model).rotate(270, 270)
        );
    }

    private static IBlockStateGen doublePlant(String lower, IModelGen lowerModel, String upper, IModelGen upperModel) {
        return VariantBlockStateGen.create("half=lower", ModelInfo.create(lower, lowerModel))
                                   .variant("half=upper", ModelInfo.create(upper, upperModel));
    }

    private static IBlockStateGen rotatedPillar(String name, IModelGen model) {
        return VariantBlockStateGen.create("axis=y", ModelInfo.create(name, model).rotate(0, 0))
                                   .variant("axis=z", ModelInfo.create(name, model).rotate(90, 0))
                                   .variant("axis=x", ModelInfo.create(name, model).rotate(90, 90));
    }

    private static IBlockStateGen shelf(String name, String shelf, String fan) {
        IModelGen fanModel = InheritingModelGen.fan(name);
        IModelGen shelfModel = InheritingModelGen.shelf(name);
        return VariantBlockStateGen.create("facing=up", ModelInfo.create(fan, fanModel))
                                   .variant("facing=north", ModelInfo.create(shelf, shelfModel).rotate(0, 0))
                                   .variant("facing=east", ModelInfo.create(shelf, shelfModel).rotate(0, 90))
                                   .variant("facing=south", ModelInfo.create(shelf, shelfModel).rotate(0, 180))
                                   .variant("facing=west", ModelInfo.create(shelf, shelfModel).rotate(0, 270));
    }

    private static IBlockStateGen shroomCap(Block block, String name, String capTex, String innerTex) {
        VariantBlockStateGen gen = new VariantBlockStateGen();
        StateContainer<Block, BlockState> states = block.getStateContainer();

        // Yay data generation - skips us writing 64 different variants in a JSON file
        for (BlockState state : states.getValidStates()) {
            String up = state.get(ShroomCapBlock.UP) ? capTex : innerTex;
            String down = state.get(ShroomCapBlock.DOWN) ? capTex : innerTex;
            String north = state.get(ShroomCapBlock.NORTH) ? capTex : innerTex;
            String east = state.get(ShroomCapBlock.EAST) ? capTex : innerTex;
            String south = state.get(ShroomCapBlock.SOUTH) ? capTex : innerTex;
            String west = state.get(ShroomCapBlock.WEST) ? capTex : innerTex;

            String side = "";
            if (!state.get(ShroomCapBlock.UP)) side += "u";
            if (!state.get(ShroomCapBlock.DOWN)) side += "d";
            if (!state.get(ShroomCapBlock.NORTH)) side += "n";
            if (!state.get(ShroomCapBlock.EAST)) side += "e";
            if (!state.get(ShroomCapBlock.SOUTH)) side += "s";
            if (!state.get(ShroomCapBlock.WEST)) side += "w";
            if (!side.isEmpty()) side = "_" + side;

            gen.variant(state, ModelInfo.create(name + side, cube(north, east, south, west, up, down).texture("particle", innerTex)));
        }
        return gen;
    }

    private static void register(Block block, Function<Block, IBlockStateGen> genFactory) {
        consumer.accept(block, genFactory.apply(block));
    }

    private static String name(Block block, String nameFormat) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Block block, String nameFormat, String removeSuffix) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;

        String pth = id.getPath();
        if (pth.endsWith(removeSuffix))
            pth = pth.substring(0, pth.length() - removeSuffix.length());

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, pth));
    }

    private static String name(Block block) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;
        return id.toString();
    }


    private BlockStateTable() {
    }
}
