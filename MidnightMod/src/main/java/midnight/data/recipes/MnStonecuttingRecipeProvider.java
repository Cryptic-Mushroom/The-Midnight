/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.data.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import midnight.common.Midnight;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Recipe provider designed for generating stonecutter recipes. This provider automatically derives all other possible
 * stonecutting recipes from the registered recipes. For example, when rock can be stonecutted into rock bricks and rock
 * bricks can be stonecutted into rock bricks slabs, it automatically derives that rock can be turned into rock bricks
 * slabs and it will generate an extra recipe for that. This way we don't need to care about these 'recursive recipes',
 * which apparently aren't derived by Minecraft itself.
 */
public class MnStonecuttingRecipeProvider extends RecipeProvider {
    private Consumer<IFinishedRecipe> consumer;

    private final Map<Item, Map<Item, Integer>> recipes = Maps.newHashMap();
    private final Map<Item, List<Pair<Item, Integer>>> compiled = Maps.newHashMap();
    private final Set<String> flushed = Sets.newHashSet();

    public MnStonecuttingRecipeProvider(DataGenerator gen) {
        super(gen);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;

        // Add stonecutting recipes here

        compile();
        flush();
    }

    private void register(IItemProvider from, IItemProvider to) {
        register(from, to, 1);
    }

    private void register(IItemProvider from, IItemProvider to, int count) {
        Item fromItem = from.asItem();
        Item toItem = to.asItem();
        recipes.computeIfAbsent(fromItem, k -> Maps.newHashMap())
               .put(toItem, count);
    }

    private void compile() {
        for(Item item : recipes.keySet()) {
            List<Pair<Item, Integer>> results = Lists.newArrayList();
            compile(item, 1, results);
            compiled.put(item, results);
        }
    }

    private void compile(Item from, int countMul, List<Pair<Item, Integer>> out) {
        Map<Item, Integer> to = recipes.get(from);
        for(Map.Entry<Item, Integer> entry : to.entrySet()) {
            Item item = entry.getKey();
            int count = entry.getValue() * countMul;
            out.add(Pair.of(item, count));

            // Recursively walk recipes map
            // If you can stonecut stone to bricks and bricks to bricks slab, you can stonecut stone to bricks slab
            if(recipes.containsKey(item)) {
                compile(item, count, out);
            }
        }
    }

    private void flush() {
        for(Map.Entry<Item, List<Pair<Item, Integer>>> entry : compiled.entrySet()) {
            Item from = entry.getKey();

            List<Pair<Item, Integer>> results = entry.getValue();
            for(Pair<Item, Integer> result : results) {
                Item to = result.getFirst();
                int count = result.getSecond();

                String name = recipeName(from, to);

                // Sometimes multiple ways lead to the same item, meaning a recipe can be registered multiple times.
                // We track all the recipe IDs we flushed in a set so that we only register recipes that we didn't
                // register already. We can assume that when a recipe is registered twice, they are exactly the same and
                // the latter one can be safely omitted...
                if(!flushed.contains(name)) {
                    SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(from), to, count)
                                           .addCriterion(criterionName(from), hasItem(from))
                                           .build(consumer, Midnight.resLoc(name));
                    flushed.add(name);
                }
            }
        }
    }

    private static String criterionName(Item from) {
        ResourceLocation id = from.getRegistryName();
        assert id != null;

        return String.format("has_%s", id.getPath());
    }

    private static String recipeName(Item from, Item to) {
        ResourceLocation fromId = from.getRegistryName();
        ResourceLocation toId = to.getRegistryName();
        assert fromId != null && toId != null; // If they were null they wouldn't have been registered at all...

        return String.format("%s:%s_to_%s_stonecutting", toId.getNamespace(), fromId.getPath(), toId.getPath());
    }

    @Override
    public String getName() {
        return "Midnight - Stonecutting recipes";
    }
}
