package com.mushroom.midnight.common.json.recipe;

import com.mushroom.midnight.common.json.ingredient.IJsonIngredient;
import com.mushroom.midnight.common.json.ingredient.IngredientItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractSingleIngredientRecipe implements IJsonRecipe {
    private final String type;
    private final IJsonIngredient ingredient;
    private final String result;
    private final int count;
    private final transient String name;

    AbstractSingleIngredientRecipe(Item ingredientItem, Item resultItem, int count) {
        this.type = getType();
        this.ingredient = new IngredientItem(ingredientItem);
        ResourceLocation registryName = resultItem.getRegistryName();
        this.result = registryName.toString();
        this.name = registryName.getPath();
        this.count = count;
    }

    @Override
    public abstract String getType();

    public String getIngredient() {
        return this.ingredient.getIngredientString();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
