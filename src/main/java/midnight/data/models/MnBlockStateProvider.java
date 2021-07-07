/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 7 - 6
 */

package midnight.data.models;

import com.mojang.datafixers.util.Pair;
import midnight.MnInfo;
import midnight.common.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraftforge.client.model.generators.ModelProvider.*;

public class MnBlockStateProvider extends BlockStateProvider {
    public MnBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s - Block States and Models", MnInfo.NAME);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simple(MnBlocks.NIGHTSTONE, this::cubeAll);
        this.simple(MnBlocks.NIGHT_BEDROCK, this::cubeAll);
        this.simple(MnBlocks.TRENCHSTONE, this::cubeAll);

        this.rotateY(MnBlocks.NIGHT_DIRT, this::cubeAll);
        this.rotateY(MnBlocks.NIGHT_GRASS_BLOCK, block -> this.grassBlock(
            block, // name
            this.blockTexture(block, "side"), // side
            this.blockTexture(MnBlocks.NIGHT_DIRT), // bottom
            this.blockTexture(block, "top"), // top
            this.blockTexture(block, "overlay") // overlay
        ));
        this.rotateY(MnBlocks.COARSE_NIGHT_DIRT, this::cubeAll);
        this.simple(MnBlocks.DECEITFUL_MUD, this::cubeAll);
        this.simple(MnBlocks.DECEITFUL_PEAT, this::cubeAll);
        this.rotateXY(MnBlocks.STRANGE_SAND, this::cubeAll);
        this.rotateY(MnBlocks.NIGHT_MYCELIUM, block -> this.cubeBottomTop(
            block, // name
            this.blockTexture(block, "side"), // side
            this.blockTexture(MnBlocks.NIGHTSTONE), // bottom
            this.blockTexture(block, "top") // top
        ));

        this.simple(MnBlocks.NIGHTSTONE_BRICKS, this::cubeAll);
        this.simple(MnBlocks.TRENCHSTONE_BRICKS, this::cubeAll);
        this.simple(MnBlocks.SHROOMBRICKS, this::cubeAll);

        this.simple(MnBlocks.NIGHT_GRASS, this::tintedCross);
        this.doublePlant(MnBlocks.TALL_NIGHT_GRASS, this::tintedCross);

        this.simple(MnBlocks.GHOST_PLANT, this::cross);
        this.simple(MnBlocks.GHOST_PLANT_LEAF, this::cubeAll);
        this.rotatedPillar(MnBlocks.GHOST_PLANT_STEM, this::cubeColumn);

        this.rotatedPillar(MnBlocks.DEAD_WOOD_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.STRIPPED_DEAD_WOOD_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.DEAD_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side")));
        this.rotatedPillar(MnBlocks.STRIPPED_DEAD_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side")));
        this.simple(MnBlocks.DEAD_WOOD_PLANKS, this::cubeAll);
        this.simple(MnBlocks.DEAD_SAPLING, this::cross);

        this.rotatedPillar(MnBlocks.SHADOWROOT_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.STRIPPED_SHADOWROOT_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.SHADOWROOT_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side", "wood")));
        this.rotatedPillar(MnBlocks.STRIPPED_SHADOWROOT_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side", "wood")));
        this.simple(MnBlocks.SHADOWROOT_LEAVES, this::leaves);
        this.simple(MnBlocks.SHADOWROOT_PLANKS, this::cubeAll);
        this.simple(MnBlocks.SHADOWROOT_SAPLING, this::cross);
        this.simple(MnBlocks.SHADOWROOT_BOOKSHELF, this::cubeColumn);


        this.rotatedPillar(MnBlocks.DARK_WILLOW_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.STRIPPED_DARK_WILLOW_LOG, this::cubeColumn);
        this.rotatedPillar(MnBlocks.DARK_WILLOW_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side", "wood")));
        this.rotatedPillar(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, block -> this.cubeAll(block, this.blockTexture(block, "log_side", "wood")));
        this.simple(MnBlocks.DARK_WILLOW_LEAVES, this::leaves);
        this.hangingPlant(MnBlocks.HANGING_DARK_WILLOW_LEAVES, this::hangingLeaves);
        this.simple(MnBlocks.DARK_WILLOW_PLANKS, this::cubeAll);
        this.simple(MnBlocks.DARK_WILLOW_SAPLING, this::cross);
        this.simple(MnBlocks.DARK_WILLOW_BOOKSHELF, this::cubeColumn);

        // NOTE: MnBlocks.SHROOM_AIR has no block state

        this.shroomCap(MnBlocks.NIGHTSHROOM_CAP);
        this.simple(MnBlocks.NIGHTSHROOM_STEM, this::cubeAll);
        this.simple(MnBlocks.NIGHTSHROOM_PLANKS, this::cubeAll);
        this.simple(MnBlocks.NIGHTSHROOM, this::cross);
        this.simple(MnBlocks.NIGHTSHROOM_ROOTS, this::cross);
        this.simple(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, this::cross);
        this.fibre(MnBlocks.NIGHTSHROOM_FIBRE, this::doubleLayerPlant);
        this.shelf(MnBlocks.NIGHTSHROOM_SHELF);
        this.doublePlant(MnBlocks.TALL_NIGHTSHROOM, this::cross);

        this.shroomCap(MnBlocks.DEWSHROOM_CAP);
        this.simple(MnBlocks.DEWSHROOM_STEM, this::cubeAll);
        this.simple(MnBlocks.DEWSHROOM_PLANKS, this::cubeAll);
        this.simple(MnBlocks.DEWSHROOM, this::cross);
        this.simple(MnBlocks.DEWSHROOM_ROOTS, this::cross);
        this.simple(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, this::cross);
        this.fibre(MnBlocks.DEWSHROOM_FIBRE, this::doubleLayerPlant);
        this.shelf(MnBlocks.DEWSHROOM_SHELF);
        this.doublePlant(MnBlocks.TALL_DEWSHROOM, this::cross);

        this.shroomCap(MnBlocks.VIRIDSHROOM_CAP);
        this.simple(MnBlocks.VIRIDSHROOM_STEM, this::cubeAll);
        this.simple(MnBlocks.VIRIDSHROOM_PLANKS, this::cubeAll);
        this.simple(MnBlocks.VIRIDSHROOM, this::cross);
        this.simple(MnBlocks.VIRIDSHROOM_ROOTS, this::cross);
        this.simple(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, this::cross);
        this.fibre(MnBlocks.VIRIDSHROOM_FIBRE, this::doubleLayerPlant);
        this.shelf(MnBlocks.VIRIDSHROOM_SHELF);
        this.doublePlant(MnBlocks.TALL_VIRIDSHROOM, this::cross);

        this.shroomCap(MnBlocks.MOONSHROOM_CAP);
        this.simple(MnBlocks.MOONSHROOM_STEM, this::cubeAll);
        this.simple(MnBlocks.MOONSHROOM_PLANKS, this::cubeAll);
        this.simple(MnBlocks.MOONSHROOM, this::cross);
        this.simple(MnBlocks.MOONSHROOM_ROOTS, this::cross);
        this.simple(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, this::cross);
        this.fibre(MnBlocks.MOONSHROOM_FIBRE, this::doubleLayerPlant);
        this.shelf(MnBlocks.MOONSHROOM_SHELF);
        this.doublePlant(MnBlocks.TALL_MOONSHROOM, this::cross);

        this.shroomCap(MnBlocks.BOGSHROOM_CAP);
        this.simple(MnBlocks.BOGSHROOM_STEM, this::cubeAll);
        this.simple(MnBlocks.BOGSHROOM_PLANKS, this::cubeAll);
        this.simple(MnBlocks.BOGSHROOM, this::cross);
        this.fibre(MnBlocks.BOGSHROOM_FIBRE, this::doubleLayerPlant);
        this.shelf(MnBlocks.BOGSHROOM_SHELF);
        this.doublePlant(MnBlocks.TALL_BOGSHROOM, this::cross);

        this.simple(MnBlocks.GLOB_FUNGUS_CAP, this::cubeAll);
        this.rotatedPillar(MnBlocks.GLOB_FUNGUS_HYPHAE, block -> this.cubeAll(block, this.blockTexture(block, "stem_side", "hyphae")));
        this.simple(MnBlocks.GLOB_FUNGUS_THATCH, this::cubeAll);
        this.simple(MnBlocks.GLOB_FUNGUS, this::cross);
        this.rotatedPillar(MnBlocks.GLOB_FUNGUS_STEM, this::cubeColumn);
        this.horizRotated(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, block -> this.cubeFrontSided(
            block, // name
            this.blockTexture(block), // front
            this.blockFolder("glob_fungus_stem_side", true), // side
            this.blockFolder("glob_fungus_stem_end", true), // bottom
            this.blockFolder("glob_fungus_stem_end", true) // top
        ), true);

        this.simple(MnBlocks.MISTSHROOM, this::cross);
        this.doublePlant(MnBlocks.TALL_MISTSHROOM, this::cross);
        this.simple(MnBlocks.BRISTLY_GRASS, this::cross);
        this.simple(MnBlocks.LUMEN_BUD, this::cross);
        this.doublePlant(MnBlocks.TALL_LUMEN_BUD, this::cross);
        this.simple(MnBlocks.RUNEBUSH, this::cross);
        this.simple(MnBlocks.BOGWEED, this::cross);
        this.simple(MnBlocks.CRYSTALOTUS, this::crystalotus);
        // MnBlocks.SUAVIS block state and models has been moved to the main/resources folder.
        this.growableRotY(MnBlocks.VIOLEAF, this::flatPlant, VioleafBlock.GROWN);
        this.simple(MnBlocks.TENDRILWEED, this::cross);
        this.nightReed(MnBlocks.NIGHT_REED, this::cross);
        this.facingOrient(MnBlocks.DECEITFUL_MOSS, this::moss, true);
        this.simple(MnBlocks.DECEITFUL_ALGAE, this::singleLayerPlant);

        this.simple(MnBlocks.REED_THATCH, this::cubeAll);
        this.simple(MnBlocks.CUT_REED_THATCH, this::cubeAll);

        this.simple(MnBlocks.ROCKSHROOM, this::cubeAll);

        this.simple(MnBlocks.ROUXE_ROCK, this::cubeAll);
        this.simple(MnBlocks.BLOOMCRYSTAL_ROCK, this::cubeAll);
        this.simple(MnBlocks.ROUXE, this::cross);
        this.simple(MnBlocks.BLOOMCRYSTAL, this::cross);
        this.simple(MnBlocks.CRYSTAL_FLOWER, this::cross);

        this.simple(MnBlocks.DARK_PEARL_ORE, this::cubeAll);
        this.simple(MnBlocks.DARK_PEARL_BLOCK, this::cubeAll);

        this.simple(MnBlocks.STRANGE_GLASS, this::cubeAll);
        this.glassPane(MnBlocks.STRANGE_GLASS_PANE, block -> this.blockTexture(block, null, "pane"), block -> this.blockTexture(block, "side"));

        this.simple(MnBlocks.ARCHAIC_ORE, this::cubeAll);
        this.simple(MnBlocks.ARCHAIC_GLASS, this::cubeAll);
        this.glassPane(MnBlocks.ARCHAIC_GLASS_PANE, block -> this.blockTexture(block, null, "pane"), block -> this.blockTexture(block, "edge"));

        this.simple(MnBlocks.TENEBRUM_ORE, this::cubeAll);
        this.simple(MnBlocks.TENEBRUM_BLOCK, this::cubeAll);

        this.simple(MnBlocks.NAGRILITE_ORE, this::cubeAll);
        this.simple(MnBlocks.NAGRILITE_BLOCK, this::cubeAll);

        this.simple(MnBlocks.EBONITE_ORE, this::cubeAll);
        this.simple(MnBlocks.EBONITE_BLOCK, this::cubeColumn);

        this.simple(MnBlocks.VIRILUX_ORE, this::cubeAll);
        this.simple(MnBlocks.VIRILUX_BLOCK, this::cubeAll);
    }


    /*
     * BLOCK STATE GENERATION
     */
    private void simple(Block block, Function<Block, ModelFile> modelFile) {
        this.simpleBlock(block, modelFile.apply(block));
    }

    private void pane(Block block, ModelFile post, ModelFile side, ModelFile noside, ModelFile sideAlt, ModelFile nosideAlt) {
        this.getMultipartBuilder(block)
            .part().modelFile(post).addModel().end()
            .part().modelFile(side).addModel().condition(FourWayBlock.NORTH, true).end()
            .part().modelFile(side).rotationY(90).addModel().condition(FourWayBlock.EAST, true).end()
            .part().modelFile(sideAlt).addModel().condition(FourWayBlock.SOUTH, true).end()
            .part().modelFile(sideAlt).rotationY(90).addModel().condition(FourWayBlock.WEST, true).end()
            .part().modelFile(noside).addModel().condition(FourWayBlock.NORTH, false).end()
            .part().modelFile(nosideAlt).addModel().condition(FourWayBlock.EAST, false).end()
            .part().modelFile(nosideAlt).rotationY(90).addModel().condition(FourWayBlock.SOUTH, false).end()
            .part().modelFile(noside).rotationY(270).addModel().condition(FourWayBlock.WEST, false).end();
    }

    private void glassPane(Block block, Function<Block, ResourceLocation> paneTex, Function<Block, ResourceLocation> edgeTex) {
        this.pane(
            block,
            panePost(block, paneTex.apply(block), edgeTex.apply(block)),
            paneSide(block, paneTex.apply(block), edgeTex.apply(block)),
            paneNoside(block, paneTex.apply(block)),
            paneSideAlt(block, paneTex.apply(block), edgeTex.apply(block)),
            paneNosideAlt(block, paneTex.apply(block))
        );
    }

    private void hangingPlant(Block block, Function<Block, Pair<ModelFile, ModelFile>> modelFile) {
        Pair<ModelFile, ModelFile> result = modelFile.apply(block);
        ModelFile root = result.getFirst();
        ModelFile end = result.getSecond();

        this.getVariantBuilder(block)
            .partialState().with(HangingLeavesBlock.END, true).modelForState().modelFile(end).addModel()
            .partialState().with(HangingLeavesBlock.END, false).modelForState().modelFile(root).addModel();
    }

    private void facingOrient(Block block, Function<Block, ModelFile> modelFile, boolean uvLock) {
        ModelFile model = modelFile.apply(block);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Direction direction : Direction.values()) {
            int x = 0, y = 0;

            if (direction == Direction.DOWN)
                x = 180;
            else if (direction != Direction.UP) {
                x = 90;
                y = direction.getOpposite().get2DDataValue() * 90;
            }

            builder = builder.partialState().with(DirectionalPlantBlock.FACING, direction).modelForState().modelFile(model).uvLock(uvLock).rotationX(x).rotationY(y).addModel();
        }
    }

    private void nightReed(Block block, BiFunction<String, ResourceLocation, ModelFile> modelFile) {
        this.getVariantBuilder(block)
            .partialState().with(NightReedBlock.WATERLOGGED, false).with(NightReedBlock.END, false).modelForState().modelFile(modelFile.apply(this.name(block), this.blockTexture(block))).addModel()
            .partialState().with(NightReedBlock.WATERLOGGED, true).with(NightReedBlock.END, false).modelForState().modelFile(modelFile.apply(this.name(block, "flooded"), this.blockTexture(block, "flooded"))).addModel()
            .partialState().with(NightReedBlock.WATERLOGGED, false).with(NightReedBlock.END, true).modelForState().modelFile(modelFile.apply(this.name(block, "end"), this.blockTexture(block, "end"))).addModel()
            .partialState().with(NightReedBlock.WATERLOGGED, true).with(NightReedBlock.END, true).modelForState().modelFile(modelFile.apply(this.name(block, "flooded_end"), this.blockTexture(block, "flooded_end"))).addModel();
    }

    private void fibre(Block block, Function<Block, Pair<ModelFile, ModelFile>> modelFile) {
        Pair<ModelFile, ModelFile> result = modelFile.apply(block);
        ModelFile less = result.getFirst();
        ModelFile more = result.getSecond();

        this.getVariantBuilder(block)
            .partialState().with(FibreBlock.DENSE, true).modelForState().modelFile(more).addModel()
            .partialState().with(FibreBlock.DENSE, false).modelForState().modelFile(less).addModel();
    }

    private void growable(Block block, BiFunction<String, ResourceLocation, ModelFile> modelFile, BooleanProperty property) {
        ModelFile normal = modelFile.apply(this.name(block), this.blockTexture(block));
        ModelFile grown = modelFile.apply(this.name(block, "grown"), this.blockTexture(block, "grown"));

        this.getVariantBuilder(block)
            .partialState().with(property, true).modelForState().modelFile(grown).addModel()
            .partialState().with(property, false).modelForState().modelFile(normal).addModel();
    }

    private void growableRotY(Block block, BiFunction<String, ResourceLocation, ModelFile> modelFile, BooleanProperty property) {
        ModelFile normal = modelFile.apply(this.name(block), this.blockTexture(block));
        ModelFile grown = modelFile.apply(this.name(block, "grown"), this.blockTexture(block, "grown"));

        this.getVariantBuilder(block)
            .partialState().with(property, true).setModels(ConfiguredModel.allYRotations(grown, 0, false))
            .partialState().with(property, false).setModels(ConfiguredModel.allYRotations(normal, 0, false));
    }

    private void rotateY(Block block, Function<Block, ModelFile> modelFile) {
        ModelFile model = modelFile.apply(block);

        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.allYRotations(model, 0, false));
    }

    private void rotateXY(Block block, Function<Block, ModelFile> modelFile) {
        ModelFile model = modelFile.apply(block);

        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.allRotations(model, false));
    }

    private void doublePlant(Block block, BiFunction<String, ResourceLocation, ModelFile> modelFile) {
        ModelFile lower = modelFile.apply(
            this.name(block, "lower"), // name
            this.blockTexture(block, "lower") // texture
        );
        ModelFile upper = modelFile.apply(
            this.name(block, "upper"), // name
            this.blockTexture(block, "upper") // texture
        );

        this.getVariantBuilder(block)
            .partialState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER).modelForState().modelFile(lower).addModel()
            .partialState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(upper).addModel();
    }

    private void rotatedPillar(Block block, Function<Block, ModelFile> modelFile) {
        ModelFile model = modelFile.apply(block);

        this.getVariantBuilder(block)
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(model).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(model).rotationX(90).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X).modelForState().modelFile(model).rotationX(90).rotationY(90).addModel();
    }

    private void horizRotated(Block block, Function<Block, ModelFile> modelFile, boolean uvLock) {
        ModelFile model = modelFile.apply(block);

        this.getVariantBuilder(block)
            .partialState().with(HorizontalFacingBlock.FACING, Direction.NORTH).modelForState().modelFile(model).uvLock(uvLock).addModel()
            .partialState().with(HorizontalFacingBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(90).uvLock(uvLock).addModel()
            .partialState().with(HorizontalFacingBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(180).uvLock(uvLock).addModel()
            .partialState().with(HorizontalFacingBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(270).uvLock(uvLock).addModel();
    }

    private void shelf(Block block) {
        ModelFile fan = this.models().singleTexture(this.name(block, "fan", "shelf"), this.blockFolder("fan", true), "fan", this.blockTexture(block));
        ModelFile shelf = this.models().singleTexture(this.name(block), this.blockFolder("shelf", true), "shelf", this.blockTexture(block));

        this.getVariantBuilder(block)
            .partialState().with(ShroomShelfBlock.FACING, Direction.UP).modelForState().modelFile(fan).addModel()
            .partialState().with(ShroomShelfBlock.FACING, Direction.NORTH).modelForState().modelFile(shelf).addModel()
            .partialState().with(ShroomShelfBlock.FACING, Direction.EAST).modelForState().modelFile(shelf).rotationY(90).addModel()
            .partialState().with(ShroomShelfBlock.FACING, Direction.SOUTH).modelForState().modelFile(shelf).rotationY(180).addModel()
            .partialState().with(ShroomShelfBlock.FACING, Direction.WEST).modelForState().modelFile(shelf).rotationY(270).addModel();
    }

    private void shroomCap(Block block) {
        StateContainer<Block, BlockState> states = block.getStateDefinition();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);

        ResourceLocation capTex = this.blockTexture(block);
        ResourceLocation innerTex = this.blockTexture(block, "inner");

        for (BlockState state : states.getPossibleStates()) {
            boolean down, up, north, south, east, west;

            ResourceLocation downTex = (down = state.getValue(ShroomCapBlock.DOWN)) ? capTex : innerTex;
            ResourceLocation upTex = (up = state.getValue(ShroomCapBlock.UP)) ? capTex : innerTex;
            ResourceLocation northTex = (north = state.getValue(ShroomCapBlock.NORTH)) ? capTex : innerTex;
            ResourceLocation southTex = (south = state.getValue(ShroomCapBlock.SOUTH)) ? capTex : innerTex;
            ResourceLocation eastTex = (east = state.getValue(ShroomCapBlock.EAST)) ? capTex : innerTex;
            ResourceLocation westTex = (west = state.getValue(ShroomCapBlock.WEST)) ? capTex : innerTex;

            String side = "";
            if (!state.getValue(ShroomCapBlock.UP)) side += "u";
            if (!state.getValue(ShroomCapBlock.DOWN)) side += "d";
            if (!state.getValue(ShroomCapBlock.NORTH)) side += "n";
            if (!state.getValue(ShroomCapBlock.EAST)) side += "e";
            if (!state.getValue(ShroomCapBlock.SOUTH)) side += "s";
            if (!state.getValue(ShroomCapBlock.WEST)) side += "w";

            ModelFile model = this.models().cube(this.name(block, side), downTex, upTex, northTex, southTex, eastTex, westTex).texture("particle", innerTex);
            builder = builder.partialState()
                             .with(ShroomCapBlock.UP, up)
                             .with(ShroomCapBlock.DOWN, down)
                             .with(ShroomCapBlock.NORTH, north)
                             .with(ShroomCapBlock.SOUTH, south)
                             .with(ShroomCapBlock.EAST, east)
                             .with(ShroomCapBlock.WEST, west)
                             .modelForState().modelFile(model).addModel();
        }
    }

    /*
     * MODEL GENERATION
     */

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides.
     * <pre>
     * {
     *     "parent": "block/cube_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with its texture) to use for the model.
     * @return A {@code block/cube_all} model file for the data generator.
     *
     * @see BlockStateProvider#cubeAll(Block)
     */
    @Override
    public ModelFile cubeAll(Block block) {
        return super.cubeAll(block);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides.
     * <pre>
     * {
     *     "parent": "block/cube_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block   The block's name to use for the model.
     * @param texture The texture to use for the model.
     * @return A {@code block/cube_all} model file for the data generator.
     *
     * @see #cubeAll(String, ResourceLocation)
     */
    private ModelFile cubeAll(Block block, ResourceLocation texture) {
        return this.cubeAll(this.name(block), texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides.
     * <pre>
     * {
     *     "parent": "block/cube_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param name    The name to use for the model.
     * @param texture The texture to use for the model.
     * @return A {@code block/cube_all} model file for the data generator.
     */
    private ModelFile cubeAll(String name, ResourceLocation texture) {
        return this.models().cubeAll(name, texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, allowing for foliage coloring
     * in leaves.
     * <pre>
     * {
     *     "parent": "block/leaves",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with its texture) to use for the model.
     * @return A {@code block/leaves} model file for the data generator.
     *
     * @see #leaves(Block, ResourceLocation)
     * @see #leaves(String, ResourceLocation)
     */
    private ModelFile leaves(Block block) {
        return this.leaves(block, this.blockTexture(block));
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, allowing for foliage coloring
     * in leaves.
     * <pre>
     * {
     *     "parent": "block/leaves",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block   The block's name to use for the model.
     * @param texture The texture to use for the model.
     * @return A {@code block/leaves} model file for the data generator.
     *
     * @see #leaves(String, ResourceLocation)
     */
    private ModelFile leaves(Block block, ResourceLocation texture) {
        return this.leaves(this.name(block), texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, allowing for foliage coloring
     * in leaves.
     * <pre>
     * {
     *     "parent": "block/leaves",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param name    The name to use for the model.
     * @param texture The texture to use for the model.
     * @return A {@code block/leaves} model file for the data generator.
     */
    private ModelFile leaves(String name, ResourceLocation texture) {
        return this.models().singleTexture(name, this.blockFolder("leaves", false), "all", texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, but mirroring the texture.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with its texture) to use in the model.
     * @return A {@code block/cube_mirrored_all} model file for the data generator.
     *
     * @see #cubeMirroredAll(Block, ResourceLocation)
     * @see #cubeMirroredAll(String, ResourceLocation)
     */
    private ModelFile cubeMirroredAll(Block block) {
        return this.cubeMirroredAll(block, this.blockTexture(block));
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, but mirroring the texture.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param block   The block's name to use in the model.
     * @param texture The texture to use in the model.
     * @return A {@code block/cube_mirrored_all} model file for the data generator.
     */
    private ModelFile cubeMirroredAll(Block block, ResourceLocation texture) {
        return this.cubeMirroredAll(this.name(block), texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with the same texture on all sides, but mirroring the texture.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param name    The name to use in the model.
     * @param texture The texture to use in the model.
     * @return A {@code block/cube_mirrored_all} model file for the data generator.
     */
    private ModelFile cubeMirroredAll(String name, ResourceLocation texture) {
        return this.models().singleTexture(name, this.blockFolder("cube_mirrored_all", false), "all", texture);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top and bottom ({@code end}), and another texture
     * on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined side and end textures) to use in the model.
     * @return A {@code block/cube_column} model file for the data generator.
     *
     * @see #cubeColumn(Block, ResourceLocation, ResourceLocation)
     * @see #cubeColumn(String, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeColumn(Block block) {
        return this.cubeColumn(
            block,
            this.blockTexture(block, "side"),
            this.blockTexture(block, "end")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top and bottom ({@code end}), and another texture
     * on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name to use in the model.
     * @param side  The side texture to use in the model.
     * @param end   The end texture to use in the model.
     * @return A {@code block/cube_column} model file for the data generator.
     *
     * @see #cubeColumn(String, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeColumn(Block block, ResourceLocation side, ResourceLocation end) {
        return this.cubeColumn(this.name(block), side, end);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top and bottom ({@code end}), and another texture
     * on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param name The name to use in the model.
     * @param side The side texture to use in the model.
     * @param end  The end texture to use in the model.
     * @return A {@code block/cube_column} model file for the data generator.
     */
    private ModelFile cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return this.models().cubeColumn(name, side, end);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on north and south ({@code end}), and another
     * texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column_horizontal",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined side and end textures) to use in the model.
     * @return A {@code block/cube_column_horizontal} model file for the data generator.
     *
     * @see #cubeColumnHoriz(Block, ResourceLocation, ResourceLocation)
     * @see #cubeColumnHoriz(String, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeColumnHoriz(Block block) {
        return this.cubeColumnHoriz(
            block,
            this.blockTexture(block, "side"),
            this.blockTexture(block, "end")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on north and south ({@code end}), and another
     * texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column_horizontal",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name to use in the model.
     * @param side  The side texture to use in the model.
     * @param end   The end texture to use in the model.
     * @return A {@code block/cube_column_horizontal} model file for the data generator.
     *
     * @see #cubeColumnHoriz(String, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeColumnHoriz(Block block, ResourceLocation side, ResourceLocation end) {
        return this.cubeColumnHoriz(this.name(block), side, end);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on north and south ({@code end}), and another
     * texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_column_horizontal",
     *     "textures": {
     *         "side": "...",
     *         "end": "..."
     *     }
     * }
     * </pre>
     *
     * @param name The name to use in the model.
     * @param side The side texture to use in the model.
     * @param end  The end texture to use in the model.
     * @return A {@code block/cube_column_horizontal} model file for the data generator.
     */
    private ModelFile cubeColumnHoriz(String name, ResourceLocation side, ResourceLocation end) {
        return this.models().cubeColumnHorizontal(name, side, end);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top ({@code top}), one on bottom ({@code
     * bottom}), and a texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_bottom_top",
     *     "textures": {
     *         "side": "...",
     *         "bottom": "...",
     *         "top": "..."
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefinied side, bottom, and top textures) to use in the model.
     * @return A {@code block/cube_buttom_top} model file for the data generator.
     *
     * @see #cubeBottomTop(Block, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #cubeBottomTop(String, ResourceLocation, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeBottomTop(Block block) {
        return this.cubeBottomTop(
            block,
            this.blockTexture(block, "side"),
            this.blockTexture(block, "bottom"),
            this.blockTexture(block, "top")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top ({@code top}), one on bottom ({@code
     * bottom}), and a texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_bottom_top",
     *     "textures": {
     *         "side": "...",
     *         "bottom": "...",
     *         "top": "..."
     *     }
     * }
     * </pre>
     *
     * @param block  The block's name to use in the model.
     * @param bottom The bottom texture to use in the model.
     * @param top    The top texture to use in the model.
     * @param side   The side texture to use in the model.
     * @return A {@code block/cube_buttom_top} model file for the data generator.
     *
     * @see #cubeBottomTop(String, ResourceLocation, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeBottomTop(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.cubeBottomTop(this.name(block), side, bottom, top);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on top ({@code top}), one on bottom ({@code
     * bottom}), and a texture on other sides ({@code side}).
     * <pre>
     * {
     *     "parent": "block/cube_bottom_top",
     *     "textures": {
     *         "side": "...",
     *         "bottom": "...",
     *         "top": "..."
     *     }
     * }
     * </pre>
     *
     * @param name   The name to use in the model.
     * @param bottom The bottom texture to use in the model.
     * @param top    The top texture to use in the model.
     * @param side   The side texture to use in the model.
     * @return A {@code block/cube_buttom_top} model file for the data generator.
     */
    private ModelFile cubeBottomTop(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.models().cubeBottomTop(name, side, bottom, top);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined down, up, north, south, east, and west textures) to use in the
     *              model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cube(Block, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     * @see #cube(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     */
    private ModelFile cube(Block block) {
        return this.cube(
            block,
            this.blockTexture(block, "down"),
            this.blockTexture(block, "up"),
            this.blockTexture(block, "north"),
            this.blockTexture(block, "south"),
            this.blockTexture(block, "east"),
            this.blockTexture(block, "west")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param block The block's name to use in the model.
     * @param down  The down texture to use in the model.
     * @param up    The up texture to use in the model.
     * @param north The north texture to use in the model.
     * @param south The south texture to use in the model.
     * @param east  The east texture to use in the model.
     * @param west  The west texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cube(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     */
    private ModelFile cube(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return this.cube(this.name(block), down, up, north, south, east, west);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param name  The name to use in the model.
     * @param down  The down texture to use in the model.
     * @param up    The up texture to use in the model.
     * @param north The north texture to use in the model.
     * @param south The south texture to use in the model.
     * @param east  The east texture to use in the model.
     * @param west  The west texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     */
    private ModelFile cube(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return this.models().cube(name, down, up, north, south, east, west);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side, mirroring the textures.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined down, up, north, south, east, and west textures) to use in the
     *              model.
     * @return A {@code block/cube_mirrored} model file for the data generator.
     *
     * @see #cubeMirrored(Block, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation, ResourceLocation)
     * @see #cubeMirrored(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeMirrored(Block block) {
        return this.cubeMirrored(
            block,
            this.blockTexture(block, "down"),
            this.blockTexture(block, "up"),
            this.blockTexture(block, "north"),
            this.blockTexture(block, "south"),
            this.blockTexture(block, "east"),
            this.blockTexture(block, "west")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side, mirroring the textures.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param block The block's name to use in the model.
     * @param down  The down texture to use in the model.
     * @param up    The up texture to use in the model.
     * @param north The north texture to use in the model.
     * @param south The south texture to use in the model.
     * @param east  The east texture to use in the model.
     * @param west  The west texture to use in the model.
     * @return A {@code block/cube_mirrored} model file for the data generator.
     *
     * @see #cubeMirrored(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeMirrored(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return this.cubeMirrored(this.name(block), down, up, north, south, east, west);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a separate texture on each side.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "..."
     *         "up": "...",
     *         "north": "...",
     *         "south": "...",
     *         "east": "...",
     *         "west": "...",
     *     }
     * }
     * </pre>
     *
     * @param name  The name to use in the model.
     * @param down  The down texture to use in the model.
     * @param up    The up texture to use in the model.
     * @param north The north texture to use in the model.
     * @param south The south texture to use in the model.
     * @param east  The east texture to use in the model.
     * @param west  The west texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     */
    private ModelFile cubeMirrored(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return this.models().withExistingParent(name, this.blockFolder("cube_mirrored", false))
                   .texture("down", down)
                   .texture("up", up)
                   .texture("north", north)
                   .texture("south", south)
                   .texture("east", east)
                   .texture("west", west);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, texture on horizontal sides, and
     * separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}"
     *         "north": "{front}",
     *         "south": "{side}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined front, side, top, and bottom textures) to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cubeFrontSided(Block, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #cubeFrontSided(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeFrontSided(Block block) {
        return this.cubeFrontSided(
            this.name(block),
            this.blockTexture(block, "front"),
            this.blockTexture(block, "side"),
            this.blockTexture(block, "bottom"),
            this.blockTexture(block, "top")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, texture on horizontal sides, and
     * separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}"
     *         "north": "{front}",
     *         "south": "{side}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param block  The block's name to use in the model.
     * @param front  The front (north) texture to use in the model.
     * @param side   The side (east, south, west) texture to use in the model.
     * @param top    The top (up) texture to use in the model.
     * @param bottom The bottom (down) texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cubeFrontSided(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     */
    private ModelFile cubeFrontSided(Block block, ResourceLocation front, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.cubeFrontSided(this.name(block), front, side, bottom, top);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, texture on horizontal sides, and
     * separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}"
     *         "north": "{front}",
     *         "south": "{side}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param name   The name to use in the model.
     * @param front  The front (north) texture to use in the model.
     * @param side   The side (east, south, west) texture to use in the model.
     * @param top    The top (up) texture to use in the model.
     * @param bottom The bottom (down) texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     */
    private ModelFile cubeFrontSided(String name, ResourceLocation front, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.models().cube(name, bottom, top, front, side, side, side).texture("particle", side);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, a texture on the south side, a
     * shared texture on east and west side, and separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}",
     *         "north": "{front}",
     *         "south": "{back}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param block The block's name (with predefined front, side, bottom, top, and back textures) to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cubeFrontBackSided(Block, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     * @see #cubeFrontBackSided(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     */
    public ModelFile cubeFrontBackSided(Block block) {
        return this.cubeFrontBackSided(
            this.name(block),
            this.blockTexture(block, "front"),
            this.blockTexture(block, "side"),
            this.blockTexture(block, "bottom"),
            this.blockTexture(block, "top"),
            this.blockTexture(block, "back")
        );
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, a texture on the south side, a
     * shared texture on east and west side, and separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}",
     *         "north": "{front}",
     *         "south": "{back}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param block  The block's name to use in the model.
     * @param front  The front (north) texture to use in the model.
     * @param side   The side (east, west) texture to use in the model.
     * @param bottom The bottom (down) texture to use in the model.
     * @param top    The top (up) texture to use in the model.
     * @param back   The back (south) texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     *
     * @see #cubeFrontBackSided(String, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation,
     *     ResourceLocation)
     */
    public ModelFile cubeFrontBackSided(Block block, ResourceLocation front, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation back) {
        return this.cubeFrontBackSided(this.name(block), front, side, bottom, top, back);
    }

    /**
     * Generates a {@link ModelFile} of a full block with a texture on the north side, a texture on the south side, a
     * shared texture on east and west side, and separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "down": "{bottom}",
     *         "up": "{top}",
     *         "north": "{front}",
     *         "south": "{back}",
     *         "east": "{side}",
     *         "west": "{side}"
     *     }
     * }
     * </pre>
     *
     * @param name   The name to use in the model.
     * @param front  The front (north) texture to use in the model.
     * @param back   The back (south) texture to use in the model.
     * @param side   The side (east, west) texture to use in the model.
     * @param top    The top (up) texture to use in the model.
     * @param bottom The bottom (down) texture to use in the model.
     * @return A {@code block/cube} model file for the data generator.
     */
    public ModelFile cubeFrontBackSided(String name, ResourceLocation front, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation back) {
        return this.models().cube(name, bottom, top, front, back, side, side).texture("particle", side);
    }

    private ModelFile grassBlock(Block block) {
        return this.grassBlock(
            block,
            this.blockTexture(block, "side"),
            this.blockTexture(block, "bottom"),
            this.blockTexture(block, "top"),
            this.blockTexture(block, "overlay")
        );
    }

    private ModelFile grassBlock(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation overlay) {
        return this.grassBlock(this.name(block), side, bottom, top, overlay);
    }

    private ModelFile grassBlock(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation overlay) {
        return this.models().withExistingParent(name, this.blockFolder("grass_block", true))
                   .texture("top", top)
                   .texture("bottom", bottom)
                   .texture("side", side)
                   .texture("overlay", overlay);
    }

    /**
     * Since hanging leaves require both root and end block states, this method generates both of those and then uses a
     * {@link Pair} to return both of them.
     *
     * @param block The block to create hanging leaves models for.
     * @return The pair with both of the block state files, the first being the root and the second being the end.
     */
    private Pair<ModelFile, ModelFile> hangingLeaves(Block block) {
        ModelFile hangingLeaves = this.models().withExistingParent(this.name(block), this.blockFolder("hanging_leaves", true))
                                      .texture("inner", this.blockTexture(block, "inner"))
                                      .texture("outer", this.blockTexture(block, "outer"));
        ModelFile hangingLeavesEnd = this.models().withExistingParent(this.name(block, "end"), this.blockFolder("hanging_leaves_end", true))
                                         .texture("inner", this.blockTexture(block, "inner"))
                                         .texture("end", this.blockTexture(block, "end"));

        return Pair.of(hangingLeaves, hangingLeavesEnd);
    }

    private ModelFile cross(Block block) {
        return this.cross(this.name(block), this.blockTexture(block));
    }

    private ModelFile cross(String name, ResourceLocation texture) {
        return this.models().cross(name, texture);
    }

    private ModelFile tintedCross(Block block) {
        return this.tintedCross(this.name(block), this.blockTexture(block));
    }

    private ModelFile tintedCross(String name, ResourceLocation texture) {
        return this.models().singleTexture(name, this.blockFolder("tinted_cross", false), "cross", texture);
    }

    private ModelFile singleLayerPlant(Block block) {
        return this.models().singleTexture(this.name(block), this.blockFolder("layered_plant_1", true), "layer1", this.blockTexture(block));
    }

    private Pair<ModelFile, ModelFile> doubleLayerPlant(Block block) {
        ModelFile less = this.models().singleTexture(this.name(block), this.blockFolder("layered_plant_1", true), "layer1", this.blockTexture(block));
        ModelFile more = this.models().withExistingParent(this.name(block, "dense"), this.blockFolder("layered_plant_2", true))
                             .texture("layer1", this.blockTexture(block, "dense")).texture("layer2", this.blockTexture(block));

        return Pair.of(less, more);
    }

    private ModelFile crystalotus(Block block) {
        return this.models().withExistingParent(this.name(block), this.blockFolder("crystalotus_base", true))
                   .texture("leaf", this.blockTexture(block, "leaf"))
                   .texture("core", this.blockTexture(block, "core"));
    }

    private ModelFile flatPlant(Block block) {
        return this.flatPlant(this.name(block), this.blockTexture(block));
    }

    private ModelFile flatPlant(String name, ResourceLocation texture) {
        return this.models().singleTexture(name, this.blockFolder("flat_plant", true), "plant", texture);
    }

    private ModelFile moss(Block block) {
        return this.moss(this.name(block), this.blockTexture(block));
    }

    private ModelFile moss(String name, ResourceLocation texture) {
        return this.models().singleTexture(name, this.blockFolder("moss", true), "texture", texture);
    }

    private ModelFile paneNoside(Block block, ResourceLocation texture) {
        return this.models().withExistingParent(this.name(block, "noside"), this.blockFolder("pane_noside", true))
                   .texture("particle", texture)
                   .texture("pane", texture);
    }

    private ModelFile paneNosideAlt(Block block, ResourceLocation texture) {
        return this.models().withExistingParent(this.name(block, "noside_alt"), this.blockFolder("pane_noside_alt", true))
                   .texture("particle", texture)
                   .texture("pane", texture);
    }

    private ModelFile paneSide(Block block, ResourceLocation paneTex, ResourceLocation edgeTex) {
        return this.models().withExistingParent(this.name(block, "side"), this.blockFolder("pane_side", true))
                   .texture("particle", paneTex)
                   .texture("edge", edgeTex)
                   .texture("pane", paneTex);
    }

    private ModelFile paneSideAlt(Block block, ResourceLocation paneTex, ResourceLocation edgeTex) {
        return this.models().withExistingParent(this.name(block, "side_alt"), this.blockFolder("pane_side_alt", true))
                   .texture("particle", paneTex)
                   .texture("edge", edgeTex)
                   .texture("pane", paneTex);
    }

    private ModelFile panePost(Block block, ResourceLocation paneTex, ResourceLocation edgeTex) {
        return this.models().withExistingParent(this.name(block, "post"), this.blockFolder("pane_post", true))
                   .texture("particle", paneTex)
                   .texture("edge", edgeTex);
    }

    /*
     * HELPER METHODS
     */

    /**
     * Gets the name of a {@link Block} for use in data generation.
     *
     * @param block The block to get the name of.
     * @return The name of the {@link Block} to be used.
     *
     * @see BlockStateProvider#name(Block)
     */
    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName(), String.format("Registry name for %s was null!", block.toString())).getPath();
    }

    /**
     * Gets the name of a {@link Block} for use in data generation and appends a given {@link String} to the end of it.
     *
     * @param block  The block to get the name of.
     * @param append The string to append to the end of the name.
     * @return The name of the {@link Block} to be used.
     *
     * @see #name(Block)
     */
    private String name(Block block, @Nullable String append) {
        String name = this.name(block);
        return append(name, append);
    }

    /**
     * Gets the name of a {@link Block} for use in data generation, removed a given suffix, and appends a given {@link
     * String}.
     *
     * @param block        The block to get the name of.
     * @param append       The string to append to the end of the name.
     * @param removeSuffix The suffix to remove from the original block name.
     * @return The name of the {@link Block} to be used.
     *
     * @see #name(Block, String)
     * @see #name(Block)
     */
    private String name(Block block, @Nullable String append, String removeSuffix) {
        String name = this.name(block);

        removeSuffix = "_" + removeSuffix;
        if (name.endsWith(removeSuffix)) {
            name = name.substring(0, name.length() - removeSuffix.length());
        }

        return append(name, append);
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture.
     *
     * @param block The block to get the {@link ResourceLocation} of.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see BlockStateProvider#blockTexture(Block)
     */
    @Override
    public ResourceLocation blockTexture(Block block) {
        return super.blockTexture(block);
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture and appends a given {@link String} to
     * the end of it.
     *
     * @param block  The block to get the {@link ResourceLocation} of.
     * @param append The string to append to the end of the new {@link ResourceLocation}.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #blockTexture(Block)
     */
    private ResourceLocation blockTexture(Block block, @Nullable String append) {
        return new ResourceLocation(append(this.blockTexture(block).toString(), append));
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture, removes a given suffix, and appends a
     * given {@link String}.
     *
     * @param block        The block to get the {@link ResourceLocation} of.
     * @param append       The string to append to the end of the new {@link ResourceLocation}.
     * @param removeSuffix The suffix to remove from the original {@link ResourceLocation} of the block.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #blockTexture(Block, String)
     * @see BlockStateProvider#blockTexture(Block)
     */
    private ResourceLocation blockTexture(Block block, @Nullable String append, String removeSuffix) {
        String texture = this.blockTexture(block).toString();

        removeSuffix = "_" + removeSuffix;
        if (texture.endsWith(removeSuffix)) {
            texture = texture.substring(0, texture.length() - removeSuffix.length());
        }

        return new ResourceLocation(append(texture, append));
    }

    private ResourceLocation blockFolder(String name, boolean mod) {
        Function<String, ResourceLocation> locator = mod ? this::modLoc : this::mcLoc;
        return locator.apply(String.format("%s/%s", BLOCK_FOLDER, name));
    }

    static String append(String first, @Nullable String second) {
        return second != null && !second.isEmpty() ? String.format("%s_%s", first, second) : first;
    }
}
