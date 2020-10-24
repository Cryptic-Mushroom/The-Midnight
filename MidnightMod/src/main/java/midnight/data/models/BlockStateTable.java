/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 24
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
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.ExtrudedSquareModelGen.*;
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

        register(MnBlocks.MOONSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.MOONSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.MOONSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.MOONSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.MOONSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.MOONSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.MOONSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_MOONSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.BOGSHROOM_CAP, block -> shroomCap(block, name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_inner")));
        register(MnBlocks.BOGSHROOM_STEM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM_PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.BOGSHROOM_FIBRE, block -> fibre(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_dense")));
        register(MnBlocks.BOGSHROOM_SHELF, block -> shelf(name(block, "block/%s"), name(block, "block/%s"), name(block, "block/%s_fan", "_shelf")));
        register(MnBlocks.TALL_BOGSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));

        register(MnBlocks.GLOB_FUNGUS_CAP, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS_HYPHAE, block -> rotatedPillar(name(block, "block/%s"), cubeAll(name(block, "block/%s_stem_side", "_hyphae"))));
        register(MnBlocks.GLOB_FUNGUS_THATCH, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.GLOB_FUNGUS_STEM, block -> rotatedPillar(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));
        register(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, block -> horizRotatedUVLock(name(block, "block/%s"), cubeFrontSided(name(block, "block/%s"), name(block, "block/glob_fungus_stem_side"), name(block, "block/glob_fungus_stem_end"), name(block, "block/glob_fungus_stem_end"))));

        register(MnBlocks.MISTSHROOM, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.TALL_MISTSHROOM, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));
        register(MnBlocks.FINGERED_GRASS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.LUMEN_BUD, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.TALL_LUMEN_BUD, block -> doublePlant(name(block, "block/%s_lower"), cross(name(block, "block/%s_lower")), name(block, "block/%s_upper"), cross(name(block, "block/%s_upper"))));
        register(MnBlocks.RUNEBUSH, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.BOGWEED, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.CRYSTALOTUS, block -> simple(name(block, "block/%s"), crystalotus(name(block, "block/%s_leaf"), name(block, "block/%s_core"))));
        register(MnBlocks.SUAVIS, block -> suavis(name(block, "block/%s")));
        register(MnBlocks.VIOLEAF, block -> growableRotY(name(block, "block/%s"), flatPlant(name(block, "block/%s")), name(block, "block/%s_grown"), flatPlant(name(block, "block/%s_grown"))));
        register(MnBlocks.TENDRILWEED, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.NIGHT_REED, block -> nightReed(name(block, "block/%s")));
        register(MnBlocks.DECEITFUL_MOSS, block -> facingOrient(name(block, "block/%s"), moss(name(block, "block/%s")), true));
        register(MnBlocks.DECEITFUL_ALGAE, block -> simple(name(block, "block/%s"), layeredPlant(name(block, "block/%s"))));

        register(MnBlocks.REED_THATCH, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.CUT_REED_THATCH, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.ROCKSHROOM, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.ROUXE_ROCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.BLOOMCRYSTAL_ROCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.ROUXE, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.BLOOMCRYSTAL, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(MnBlocks.CRYSTAL_FLOWER, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));

        register(MnBlocks.DARK_PEARL_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.DARK_PEARL_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.TENEBRUM_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.TENEBRUM_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.NAGRILITE_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.NAGRILITE_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(MnBlocks.EBONITE_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.EBONITE_BLOCK, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_end"), name(block, "block/%s_side"))));

        register(MnBlocks.VIRILUX_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MnBlocks.VIRILUX_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
    }

    private static IBlockStateGen none() {
        return VariantBlockStateGen.create(ModelInfo.create("midnight:block/nomodel"));
    }

    private static IBlockStateGen simple(String name, IModelGen model) {
        return VariantBlockStateGen.create(ModelInfo.create(name, model));
    }

    private static IBlockStateGen altering(String name, IModelGen... models) {
        ModelInfo[] info = new ModelInfo[models.length];
        for (int i = 0, l = models.length; i < l; i++) {
            String suffix = i == 0 ? "" : "_alt_" + i;
            info[i] = ModelInfo.create(name + suffix, models[i]);
        }
        return VariantBlockStateGen.create(info);
    }

    private static IBlockStateGen altering(String name, Function<String, IModelGen> gen, String... names) {
        ModelInfo[] info = new ModelInfo[names.length];
        for (int i = 0, l = names.length; i < l; i++) {
            String suffix = i == 0 ? "" : "_alt_" + i;
            info[i] = ModelInfo.create(name + suffix, gen.apply(names[i]));
        }
        return VariantBlockStateGen.create(info);
    }

    private static IBlockStateGen altering(String name, Function<String, IModelGen> gen, int count) {
        ModelInfo[] info = new ModelInfo[count];
        for (int i = 0; i < count; i++) {
            String suffix = i == 0 ? "" : "_alt_" + i;
            info[i] = ModelInfo.create(name + suffix, gen.apply(name + suffix));
        }
        return VariantBlockStateGen.create(info);
    }

    private static IBlockStateGen hangingPlant(String endName, IModelGen endModel, String rootName, IModelGen rootModel) {
        return VariantBlockStateGen.create("end=true", ModelInfo.create(endName, endModel))
                                   .variant("end=false", ModelInfo.create(rootName, rootModel));
    }

    private static IBlockStateGen suavis(String name) {
        return VariantBlockStateGen.create("stage=0", ModelInfo.create(name + "_stage_0", extruded(name + "_side_small", name + "_top").size(16, 4)))
                                   .variant("stage=1", ModelInfo.create(name + "_stage_1", extruded(name + "_side_small", name + "_top").size(16, 7)))
                                   .variant("stage=2", ModelInfo.create(name + "_stage_2", extruded(name + "_side_medium", name + "_top").size(16, 13)))
                                   .variant("stage=3", ModelInfo.create(name + "_stage_3", extruded(name + "_side", name + "_top").size(16, 16)));
    }

    private static IBlockStateGen facingOrient(String name, IModelGen model, boolean uvlock) {
        VariantBlockStateGen gen = new VariantBlockStateGen();
        for (Direction direction : Direction.values()) {
            String variant = "facing=" + direction.getString();
            int x = 0, y = 0;

            if (direction == Direction.DOWN) {
                x = 180;
            } else if (direction != Direction.UP) {
                x = 90;
                y = direction.getOpposite().getHorizontalIndex() * 90;
            }

            gen.variant(variant, ModelInfo.create(name, model).rotate(x, y).uvlock(uvlock));
        }
        return gen;
    }

    private static IBlockStateGen nightReed(String name) {
        return VariantBlockStateGen.create("waterlogged=false,end=false", ModelInfo.create(name, cross(name)))
                                   .variant("waterlogged=true,end=false", ModelInfo.create(name + "_flooded", cross(name + "_flooded")))
                                   .variant("waterlogged=false,end=true", ModelInfo.create(name + "_end", cross(name + "_end")))
                                   .variant("waterlogged=true,end=true", ModelInfo.create(name + "_flooded_end", cross(name + "_flooded_end")));
    }

    private static IBlockStateGen fibre(String model, String lessFibre, String moreFibre) {
        return VariantBlockStateGen.create("dense=true", ModelInfo.create(model + "_dense", layeredPlant(moreFibre, lessFibre)))
                                   .variant("dense=false", ModelInfo.create(model, layeredPlant(lessFibre)));
    }

    private static IBlockStateGen growable(String name, IModelGen model, String grownName, IModelGen grownModel) {
        return VariantBlockStateGen.create("grown=true", ModelInfo.create(grownName, grownModel))
                                   .variant("grown=false", ModelInfo.create(name, model));
    }

    private static IBlockStateGen growableRotY(String name, IModelGen model, String grownName, IModelGen grownModel) {
        return VariantBlockStateGen.create(
            "grown=true",
            ModelInfo.create(grownName, grownModel).rotate(0, 0),
            ModelInfo.create(grownName, grownModel).rotate(0, 90),
            ModelInfo.create(grownName, grownModel).rotate(0, 180),
            ModelInfo.create(grownName, grownModel).rotate(0, 270)
        ).variant(
            "grown=false",
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270)
        );
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

    private static IBlockStateGen horizRotated(String name, IModelGen model) {
        return VariantBlockStateGen.create("facing=north", ModelInfo.create(name, model).rotate(0, 0))
                                   .variant("facing=east", ModelInfo.create(name, model).rotate(0, 90))
                                   .variant("facing=south", ModelInfo.create(name, model).rotate(0, 180))
                                   .variant("facing=west", ModelInfo.create(name, model).rotate(0, 270));
    }

    private static IBlockStateGen horizRotatedUVLock(String name, IModelGen model) {
        return VariantBlockStateGen.create("facing=north", ModelInfo.create(name, model).rotate(0, 0).uvlock(true))
                                   .variant("facing=east", ModelInfo.create(name, model).rotate(0, 90).uvlock(true))
                                   .variant("facing=south", ModelInfo.create(name, model).rotate(0, 180).uvlock(true))
                                   .variant("facing=west", ModelInfo.create(name, model).rotate(0, 270).uvlock(true));
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
