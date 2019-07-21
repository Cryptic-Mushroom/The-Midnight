package com.mushroom.midnight.common.json.recipe;

import com.mushroom.midnight.common.json.ingredient.IJsonIngredient;
import com.mushroom.midnight.common.json.ingredient.Result;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ShapelessCraftingRecipe implements IJsonRecipe {
    private final String type;
    private IJsonIngredient[] ingredients;
    private final Result result;
    private final transient String name;

    public ShapelessCraftingRecipe(Block resultBlock) {
        this(resultBlock, 1);
    }

    public ShapelessCraftingRecipe(Block resultBlock, int count) {
        this(resultBlock.asItem(), count);
    }

    public ShapelessCraftingRecipe(Item resultItem) {
        this(resultItem, 1);
    }

    public ShapelessCraftingRecipe(Item resultItem, int count) {
        this(resultItem.getRegistryName().getPath(), resultItem, count);
    }

    public ShapelessCraftingRecipe(String name, Item resultItem, int count) {
        this.type = getType();
        this.result = new Result(resultItem, count);
        this.name = name;
    }

    public ShapelessCraftingRecipe withIngredients(IJsonIngredient... ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return "minecraft:crafting_shapeless";
    }
}
