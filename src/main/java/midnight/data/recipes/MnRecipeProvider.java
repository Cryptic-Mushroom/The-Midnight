/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.data.recipes;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.function.Consumer;

/**
 * Generator for ALL recipes, except stonecutting recipes. Stonecutting recipes go into {@link
 * MnStonecuttingRecipeProvider}.
 */
public class MnRecipeProvider extends RecipeProvider {
    private Consumer<RecipeJsonProvider> consumer;

    public MnRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generate(Consumer<RecipeJsonProvider> consumer) {
        this.consumer = consumer;
        generic2x2("nightstone_bricks_2x2", MnBlocks.NIGHTSTONE, MnBlocks.NIGHTSTONE_BRICKS, 4);
        generic2x2("trenchstone_bricks_2x2", MnBlocks.TRENCHSTONE, MnBlocks.TRENCHSTONE_BRICKS, 4);
        // generic2x2("shroombricks_2x2", MnItems.ROCKSHROOM_CLUMP, MnBlocks.SHROOMBRICKS, 1);
    }

    private void generic2x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("##")
                               .pattern("##")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void shapeless(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapelessRecipeJsonFactory.create(to, count)
                                  .input(from)
                                  .criterion("has_ingredient", hasItem(from))
                                  .offerTo(consumer, Midnight.id(id));
    }

    private void fence(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .input('/', Items.STICK)
                               .pattern("#/#")
                               .pattern("#/#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void generic1x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#")
                               .pattern("#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void generic1x3(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#")
                               .pattern("#")
                               .pattern("#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void generic3x1(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void generic3x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("###")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void stairs(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#  ")
                               .pattern("## ")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void step(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#  ")
                               .pattern("## ")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, Midnight.id(id));
    }

    private void smelting(String id, ItemConvertible from, ItemConvertible to, double xp) {
        CookingRecipeJsonFactory.createSmelting(Ingredient.ofItems(from), to, (float) xp, 200)
                                .criterion("has_ingredient", hasItem(from))
                                .offerTo(consumer, Midnight.id(id));
    }

    @Override
    public String getName() {
        return "Midnight/Recipes";
    }
}
