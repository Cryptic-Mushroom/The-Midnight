package com.mushroom.midnight.common.json.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public abstract class AbstractCookingRecipe extends AbstractSingleIngredientRecipe {
    private final float experience;
    private final int cookingtime;

    public AbstractCookingRecipe(Block ingredientBlock, Item resultItem, int count, float experience, int cookingtime) {
        this(ingredientBlock.asItem(), resultItem, count, experience, cookingtime);
    }

    public AbstractCookingRecipe(Item ingredientItem, Item resultItem, int count, float experience, int cookingtime) {
        super(ingredientItem, resultItem, count);
        this.experience = experience;
        this.cookingtime = cookingtime;
    }
}
