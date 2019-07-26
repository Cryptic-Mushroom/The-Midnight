package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.data.Triggers;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public final class MidnightStoneRecipes extends MidnightRecipeProvider {
    public MidnightStoneRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        StandardRecipes recipes = StandardRecipes.into(consumer);

        recipes.ofMaterial(MidnightBlocks.NIGHTSTONE)
                .addButton(MidnightBlocks.NIGHTSTONE_BUTTON)
                .addPressurePlate(MidnightBlocks.NIGHTSTONE_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.NIGHTSTONE_SLAB)
                .addStoneStairs(MidnightBlocks.NIGHTSTONE_STAIRS)
                .addWall(MidnightBlocks.NIGHTSTONE_WALL)
                .addPickaxe(MidnightItems.NIGHTSTONE_PICKAXE)
                .addAxe(MidnightItems.NIGHTSTONE_AXE)
                .addSword(MidnightItems.NIGHTSTONE_SWORD)
                .addShovel(MidnightItems.NIGHTSTONE_SHOVEL)
                .addHoe(MidnightItems.NIGHTSTONE_HOE);

        recipes.ofMaterial(MidnightBlocks.NIGHTSTONE_BRICKS)
                .addStoneSlab(MidnightBlocks.NIGHTSTONE_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.NIGHTSTONE_BRICK_STAIRS)
                .addWall(MidnightBlocks.NIGHTSTONE_BRICK_WALL);

        recipes.ofMaterial(MidnightBlocks.TRENCHSTONE)
                .addButton(MidnightBlocks.TRENCHSTONE_BUTTON)
                .addPressurePlate(MidnightBlocks.TRENCHSTONE_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.TRENCHSTONE_SLAB)
                .addStoneStairs(MidnightBlocks.TRENCHSTONE_STAIRS)
                .addWall(MidnightBlocks.TRENCHSTONE_WALL);

        recipes.ofMaterial(MidnightBlocks.TRENCHSTONE_BRICKS)
                .addStoneSlab(MidnightBlocks.TRENCHSTONE_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.TRENCHSTONE_BRICK_STAIRS)
                .addWall(MidnightBlocks.TRENCHSTONE_BRICK_WALL);

        recipes.ofMaterial(MidnightBlocks.ROCKSHROOM_BRICKS)
                .addButton(MidnightBlocks.ROCKSHROOM_BRICK_BUTTON)
                .addPressurePlate(MidnightBlocks.ROCKSHROOM_BRICK_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.ROCKSHROOM_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.ROCKSHROOM_BRICK_STAIRS)
                .addWall(MidnightBlocks.ROCKSHROOM_BRICK_WALL);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ROCKSHROOM)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.ROCKSHROOM_CLUMP)
                .addCriterion("has_rockshroom_clump", Triggers.hasItems(MidnightItems.ROCKSHROOM_CLUMP, MidnightBlocks.ROCKSHROOM))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .addCriterion("has_item", Triggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.NIGHTSTONE_BRICKS)
                .addCriterion("has_item", Triggers.hasItem(MidnightBlocks.NIGHTSTONE_BRICKS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.TRENCHSTONE)
                .addCriterion("has_item", Triggers.hasItem(MidnightBlocks.TRENCHSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.ROCKSHROOM)
                .addCriterion("has_item", Triggers.hasItem(MidnightBlocks.ROCKSHROOM))
                .build(consumer);

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE), MidnightBlocks.NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", Triggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_to_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE), MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", Triggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_to_chiseled_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE_BRICKS), MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", Triggers.hasItem(MidnightBlocks.NIGHTSTONE_BRICKS))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_bricks_to_chiseled_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.TRENCHSTONE), MidnightBlocks.TRENCHSTONE_BRICKS)
                .func_218643_a("has_item", Triggers.hasItem(MidnightBlocks.TRENCHSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "trenchstone_to_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.ROCKSHROOM), MidnightBlocks.ROCKSHROOM_BRICKS)
                .func_218643_a("has_item", Triggers.hasItem(MidnightBlocks.ROCKSHROOM))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "rockshroom_to_bricks"));
    }
}
