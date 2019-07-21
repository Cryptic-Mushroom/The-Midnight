package com.mushroom.midnight.common.json;

import com.mushroom.midnight.common.json.ingredient.IJsonIngredient;
import com.mushroom.midnight.common.json.ingredient.IngredientItem;
import com.mushroom.midnight.common.json.recipe.IJsonRecipe;
import com.mushroom.midnight.common.json.recipe.ShapedCraftingRecipe;
import com.mushroom.midnight.common.json.recipe.ShapelessCraftingRecipe;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonRecipes {
    private final File baseFolder = new File("json/data/midnight/recipes");
    private final List<IJsonRecipe> recipeList = new ArrayList<>();
    private final IJsonIngredient blockIngredient;
    private final IJsonIngredient stickIngredient = new IngredientItem(MidnightItems.DARK_STICK);

    CommonRecipes(IJsonIngredient blockIngredient) {
        this.blockIngredient = blockIngredient;
    }

    public CommonRecipes withButton(Block button) {
        makeRecipe(button);
        return this;
    }

    public CommonRecipes withChest(Block chest) {
        makeRecipe(chest, "AAA", "A A", "AAA");
        return this;
    }

    public CommonRecipes withCraftingTable(Block craftingTable) {
        makeRecipe(craftingTable, "AA", "AA");
        return this;
    }

    public CommonRecipes withDoor(Block door) {
        makeRecipe(door, 3, "AA", "AA", "AA");
        return this;
    }

    public CommonRecipes withFence(Block fence) {
        makeRecipe(fence, 3, "ABA", "ABA");
        return this;
    }

    public CommonRecipes withFenceGate(Block fenceGate) {
        makeRecipe(fenceGate, "BAB", "BAB");
        return this;
    }

    public CommonRecipes withLadder(Block ladder) {
        makeRecipe(ladder, 3, "B B", "BAB", "B B");
        return this;
    }

    public CommonRecipes withPressurePlate(Block pressurePlate) {
        makeRecipe(pressurePlate, "AA");
        return this;
    }

    public CommonRecipes withSlab(Block slab) {
        makeRecipe(slab, 6, "AAA");
        return this;
    }

    public CommonRecipes withStairs(Block stairs) {
        makeRecipe(stairs, 4, "A  ", "AA ", "AAA");
        return this;
    }

    public CommonRecipes withTrapDoor(Block trapDoor) {
        makeRecipe(trapDoor, 6, "AAA", "AAA");
        return this;
    }

    public CommonRecipes withWall(Block wall) {
        makeRecipe(wall, 6, "AAA", "AAA");
        return this;
    }

    public CommonRecipes withPickaxe(Item pickaxe) {
        makeRecipe(pickaxe, "AAA", " B ", " B ");
        return this;
    }

    public CommonRecipes withAxe(Item axe) {
        makeRecipe(axe, "AA", "BA", "B ");
        return this;
    }

    public CommonRecipes withSword(Item sword) {
        makeRecipe(sword, "A", "A", "B");
        return this;
    }

    public CommonRecipes withShovel(Item shovel) {
        makeRecipe(shovel, "A", "B", "B");
        return this;
    }

    public CommonRecipes withHoe(Item hoe) {
        makeRecipe(hoe, "AA", "B ", "B ");
        return this;
    }

    public CommonRecipes withHelmet(Item helmet) {
        makeRecipe(helmet, "AAA", "A A");
        return this;
    }

    public CommonRecipes withChestPlate(Item chestPlate) {
        makeRecipe(chestPlate, "A A", "AAA", "AAA");
        return this;
    }

    public CommonRecipes withLeggings(Item leggings) {
        makeRecipe(leggings, "AAA", "A A", "A A");
        return this;
    }

    public CommonRecipes withBoots(Item boots) {
        makeRecipe(boots, "A A", "A A");
        return this;
    }

    public void generate() {
        if (GenRecipes.makeDirs(this.baseFolder)) {
            for (IJsonRecipe jsonRecipe : this.recipeList) {
                GenRecipes.saveAsJson(new File(this.baseFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
            }
        }
    }

    private void makeRecipe(Block block, String... pattern) {
        makeRecipe(block, 1, pattern);
    }

    private void makeRecipe(Block block, int count, String... pattern) {
        makeRecipe(block.asItem(), count, pattern);
    }

    private void makeRecipe(Item item, String... pattern) {
        makeRecipe(item, 1, pattern);
    }

    @SuppressWarnings("unchecked")
    private void makeRecipe(Item item, int count, String... pattern) {
        IJsonRecipe recipe;
        if (pattern.length > 0) {
            List<Pair> pairs = new ArrayList<>();
            pairs.add(Pair.of('A', this.blockIngredient));
            if (Arrays.stream(pattern).anyMatch(p -> p.indexOf('B') >= 0)) {
                pairs.add(Pair.of('B', this.stickIngredient));
            }
            recipe = new ShapedCraftingRecipe(item, count).withPattern(pattern).withIngredients(pairs.toArray(new Pair[0]));
        } else {
            recipe = new ShapelessCraftingRecipe(item, count).withIngredients(this.blockIngredient);
        }
        this.recipeList.add(recipe);
    }
}
