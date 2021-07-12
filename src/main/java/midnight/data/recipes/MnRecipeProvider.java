/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.data.recipes;

import midnight.MnInfo;
import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public class MnRecipeProvider extends RecipeProvider {
    private Consumer<IFinishedRecipe> consumer;

    public MnRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return String.format("%s - Recipes", MnInfo.NAME);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;

        generic2x2("nightstone_bricks_2x2", MnBlocks.NIGHTSTONE, MnBlocks.NIGHTSTONE_BRICKS, 4);
        generic2x2("trenchstone_bricks_2x2", MnBlocks.TRENCHSTONE, MnBlocks.TRENCHSTONE_BRICKS, 4);
        generic2x2("shroombricks_2x2", MnItems.ROCKSHROOM_CLUMP, MnBlocks.SHROOMBRICKS, 1);

        generic2x2("archaic_glass_2x2", MnItems.ARCHAIC_SHARD, MnBlocks.ARCHAIC_GLASS, 2);

        generic1x2("dark_stick_from_dead_wood", MnBlocks.DEAD_WOOD_PLANKS, MnItems.DARK_STICK, 4);
        generic1x2("dark_stick_from_dark_willow", MnBlocks.DARK_WILLOW_PLANKS, MnItems.DARK_STICK, 4);
        generic1x2("dark_stick_from_shadowroot", MnBlocks.SHADOWROOT_PLANKS, MnItems.DARK_STICK, 4);

        generic2x2("dead_wood_2x2", MnBlocks.DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD, 3);
        generic2x2("stripped_dead_wood_2x2", MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnBlocks.STRIPPED_DEAD_WOOD, 3);
        shapeless("dead_wood_planks_from_wood", MnBlocks.DEAD_WOOD, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_log", MnBlocks.DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_stripped_wood", MnBlocks.STRIPPED_DEAD_WOOD, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_stripped_log", MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD_PLANKS, 4);

        generic2x2("shadowroot_wood_2x2", MnBlocks.SHADOWROOT_LOG, MnBlocks.SHADOWROOT_WOOD, 3);
        generic2x2("stripped_shadowroot_wood_2x2", MnBlocks.STRIPPED_SHADOWROOT_LOG, MnBlocks.STRIPPED_SHADOWROOT_WOOD, 3);
        shapeless("shadowroot_planks_from_wood", MnBlocks.SHADOWROOT_WOOD, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_log", MnBlocks.SHADOWROOT_LOG, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_stripped_wood", MnBlocks.STRIPPED_SHADOWROOT_WOOD, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_stripped_log", MnBlocks.STRIPPED_SHADOWROOT_LOG, MnBlocks.SHADOWROOT_PLANKS, 4);

        generic2x2("dark_willow_wood_2x2", MnBlocks.DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_WOOD, 3);
        generic2x2("stripped_dark_willow_wood_2x2", MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnBlocks.STRIPPED_DARK_WILLOW_WOOD, 3);
        shapeless("dark_willow_planks_from_wood", MnBlocks.DARK_WILLOW_WOOD, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_log", MnBlocks.DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_stripped_wood", MnBlocks.STRIPPED_DARK_WILLOW_WOOD, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_stripped_log", MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_PLANKS, 4);

        shapeless("nightshroom_planks_from_stem", MnBlocks.NIGHTSHROOM_STEM, MnBlocks.NIGHTSHROOM_PLANKS, 4);
        shapeless("nightshroom_powder_from_cap", MnBlocks.NIGHTSHROOM_CAP, MnItems.NIGHTSHROOM_POWDER, 4);
        shapeless("nightshroom_powder_from_shroom", MnBlocks.NIGHTSHROOM, MnItems.NIGHTSHROOM_POWDER, 1);
        shapeless("nightshroom_powder_from_tall_shroom", MnBlocks.TALL_NIGHTSHROOM, MnItems.NIGHTSHROOM_POWDER, 2);
        shapeless("nightshroom_powder_from_shelf", MnBlocks.NIGHTSHROOM_SHELF, MnItems.NIGHTSHROOM_POWDER, 1);

        shapeless("dewshroom_planks_from_stem", MnBlocks.DEWSHROOM_STEM, MnBlocks.DEWSHROOM_PLANKS, 4);
        shapeless("dewshroom_powder_from_cap", MnBlocks.DEWSHROOM_CAP, MnItems.DEWSHROOM_POWDER, 4);
        shapeless("dewshroom_powder_from_shroom", MnBlocks.DEWSHROOM, MnItems.DEWSHROOM_POWDER, 1);
        shapeless("dewshroom_powder_from_tall_shroom", MnBlocks.TALL_DEWSHROOM, MnItems.DEWSHROOM_POWDER, 2);
        shapeless("dewshroom_powder_from_shelf", MnBlocks.DEWSHROOM_SHELF, MnItems.DEWSHROOM_POWDER, 1);

        shapeless("viridshroom_planks_from_stem", MnBlocks.VIRIDSHROOM_STEM, MnBlocks.VIRIDSHROOM_PLANKS, 4);
        shapeless("viridshroom_powder_from_cap", MnBlocks.VIRIDSHROOM_CAP, MnItems.VIRIDSHROOM_POWDER, 4);
        shapeless("viridshroom_powder_from_shroom", MnBlocks.VIRIDSHROOM, MnItems.VIRIDSHROOM_POWDER, 1);
        shapeless("viridshroom_powder_from_tall_shroom", MnBlocks.TALL_VIRIDSHROOM, MnItems.VIRIDSHROOM_POWDER, 2);
        shapeless("viridshroom_powder_from_shelf", MnBlocks.VIRIDSHROOM_SHELF, MnItems.VIRIDSHROOM_POWDER, 1);

        shapeless("moonshroom_planks_from_stem", MnBlocks.MOONSHROOM_STEM, MnBlocks.MOONSHROOM_PLANKS, 4);
        shapeless("moonshroom_powder_from_cap", MnBlocks.MOONSHROOM_CAP, MnItems.MOONSHROOM_POWDER, 4);
        shapeless("moonshroom_powder_from_shroom", MnBlocks.MOONSHROOM, MnItems.MOONSHROOM_POWDER, 1);
        shapeless("moonshroom_powder_from_tall_shroom", MnBlocks.TALL_MOONSHROOM, MnItems.MOONSHROOM_POWDER, 2);
        shapeless("moonshroom_powder_from_shelf", MnBlocks.MOONSHROOM_SHELF, MnItems.MOONSHROOM_POWDER, 1);

        shapeless("bogshroom_planks_from_stem", MnBlocks.BOGSHROOM_STEM, MnBlocks.BOGSHROOM_PLANKS, 4);
        shapeless("bogshroom_powder_from_cap", MnBlocks.BOGSHROOM_CAP, MnItems.BOGSHROOM_POWDER, 4);
        shapeless("bogshroom_powder_from_shroom", MnBlocks.BOGSHROOM, MnItems.BOGSHROOM_POWDER, 1);
        shapeless("bogshroom_powder_from_tall_shroom", MnBlocks.TALL_BOGSHROOM, MnItems.BOGSHROOM_POWDER, 2);
        shapeless("bogshroom_powder_from_shelf", MnBlocks.BOGSHROOM_SHELF, MnItems.BOGSHROOM_POWDER, 1);

        generic3x3("glob_fungus_thatch_3x3", MnItems.GLOB_FUNGUS_HAND, MnBlocks.GLOB_FUNGUS_THATCH, 1);
        shapeless("glob_fungus_hand_from_thatch", MnBlocks.GLOB_FUNGUS_THATCH, MnItems.GLOB_FUNGUS_HAND, 9);
        generic2x2("glob_fungus_hyphae_2x2", MnBlocks.GLOB_FUNGUS_STEM, MnBlocks.GLOB_FUNGUS_HYPHAE, 3);
        generic2x2("glob_fungus_stem_2x2", MnItems.GLOB_FUNGUS_HAND, MnBlocks.GLOB_FUNGUS_STEM, 1);

        generic3x3("reed_thatch_3x3", MnBlocks.NIGHT_REED, MnBlocks.REED_THATCH, 1);
        shapeless("night_reed_from_thatch", MnBlocks.REED_THATCH, MnBlocks.NIGHT_REED, 9);
        generic2x2("cut_reed_thatch_2x2", MnBlocks.REED_THATCH, MnBlocks.CUT_REED_THATCH, 4);

        shapeless("dark_pearl_from_block", MnBlocks.DARK_PEARL_BLOCK, MnItems.DARK_PEARL, 9);
        generic3x3("dark_pearl_block_3x3", MnItems.DARK_PEARL, MnBlocks.DARK_PEARL_BLOCK, 1);
        blasting("dark_pearl_from_ore", MnBlocks.DARK_PEARL_ORE, MnItems.DARK_PEARL, 0.7);

        shapeless("tenebrum_ingot_from_block", MnBlocks.TENEBRUM_BLOCK, MnItems.TENEBRUM_INGOT, 9);
        generic3x3("tenebrum_block_3x3", MnItems.TENEBRUM_INGOT, MnBlocks.TENEBRUM_BLOCK, 1);
        shapeless("tenebrum_nugget_from_ingot", MnItems.TENEBRUM_INGOT, MnItems.TENEBRUM_NUGGET, 9);
        generic3x3("tenebrum_ingot_3x3", MnItems.TENEBRUM_NUGGET, MnItems.TENEBRUM_INGOT, 1);
        blasting("tenebrum_ingot_from_ore", MnBlocks.TENEBRUM_ORE, MnItems.TENEBRUM_INGOT, 0.9);

        shapeless("nagrilite_ingot_from_block", MnBlocks.NAGRILITE_BLOCK, MnItems.NAGRILITE_INGOT, 9);
        generic3x3("nagrilite_block_3x3", MnItems.NAGRILITE_INGOT, MnBlocks.NAGRILITE_BLOCK, 1);
        shapeless("nagrilite_nugget_from_ingot", MnItems.NAGRILITE_INGOT, MnItems.NAGRILITE_NUGGET, 9);
        generic3x3("nagrilite_ingot_3x3", MnItems.NAGRILITE_NUGGET, MnItems.NAGRILITE_INGOT, 1);
        blasting("nagrilite_ingot_from_ore", MnBlocks.NAGRILITE_ORE, MnItems.NAGRILITE_INGOT, 0.7);

        shapeless("ebonite_from_block", MnBlocks.EBONITE_BLOCK, MnItems.EBONITE, 9);
        generic3x3("ebonite_block_3x3", MnItems.EBONITE, MnBlocks.EBONITE_BLOCK, 1);
        blasting("ebonite_from_ore", MnBlocks.EBONITE_ORE, MnItems.EBONITE, 0.7);

        shapeless("virilux_from_block", MnBlocks.VIRILUX_BLOCK, MnItems.VIRILUX, 9);
        generic3x3("virilux_block_3x3", MnItems.VIRILUX, MnBlocks.VIRILUX_BLOCK, 1);
        blasting("virilux_from_ore", MnBlocks.VIRILUX_ORE, MnItems.VIRILUX, 0.7);

        cooking("cooked_suavis_from_raw", MnItems.RAW_SUAVIS, MnItems.COOKED_SUAVIS, 0.35);
    }

    private void generic3x3(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("###")
                           .pattern("###")
                           .pattern("###")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void generic2x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("##")
                           .pattern("##")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void shapeless(String id, IItemProvider from, IItemProvider to, int count) {
        ShapelessRecipeBuilder.shapeless(to, count)
                              .requires(from)
                              .unlockedBy("has_ingredient", has(from))
                              .save(consumer, Midnight.id(id));
    }

    private void shapeless(String id, Tag<Item> tag, IItemProvider to, int count) {
        ShapelessRecipeBuilder.shapeless(to, count)
                              .requires(tag)
                              .unlockedBy("has_ingredient", has(tag))
                              .save(consumer, Midnight.id(id));
    }

    private void fence(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .define('/', Items.STICK)
                           .pattern("#/#")
                           .pattern("#/#")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void generic1x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("#")
                           .pattern("#")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void generic1x3(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("#")
                           .pattern("#")
                           .pattern("#")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void generic3x1(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("###")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void generic3x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("###")
                           .pattern("###")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void stairs(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("#  ")
                           .pattern("## ")
                           .pattern("###")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void step(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shaped(to, count)
                           .define('#', from)
                           .pattern("#  ")
                           .pattern("## ")
                           .unlockedBy("has_ingredient", has(from))
                           .save(consumer, Midnight.id(id));
    }

    private void smelting(String id, IItemProvider from, IItemProvider to, double xp) {
        CookingRecipeBuilder.smelting(Ingredient.of(from), to, (float) xp, 200)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id));
    }

    private void cooking(String id, IItemProvider from, IItemProvider to, double xp) {
        CookingRecipeBuilder.smelting(Ingredient.of(from), to, (float) xp, 200)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id));
        CookingRecipeBuilder.cooking(Ingredient.of(from), to, (float) xp, 100, IRecipeSerializer.SMOKING_RECIPE)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id + "_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(from), to, (float) xp, 600, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id + "_campfire"));
    }

    private void blasting(String id, IItemProvider from, IItemProvider to, double xp) {
        CookingRecipeBuilder.smelting(Ingredient.of(from), to, (float) xp, 200)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id));
        CookingRecipeBuilder.cooking(Ingredient.of(from), to, (float) xp, 100, IRecipeSerializer.BLASTING_RECIPE)
                            .unlockedBy("has_ingredient", has(from))
                            .save(consumer, Midnight.id(id + "_blasting"));
    }
}
