package com.mushroom.midnight.common.json.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CampfireRecipe extends AbstractCookingRecipe {

    public CampfireRecipe(Block ingredientBlock, Item resultItem, int count, float experience, int cookingtime) {
        super(ingredientBlock.asItem(), resultItem, count, experience, cookingtime);
    }

    public CampfireRecipe(Item ingredientItem, Item resultItem, int count, float experience, int cookingtime) {
        super(ingredientItem, resultItem, count, experience, cookingtime);
    }

    @Override
    public String getType() {
        return "minecraft:campfire_cooking";
    }
}
