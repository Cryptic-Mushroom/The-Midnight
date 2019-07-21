package com.mushroom.midnight.common.json.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlastingRecipe extends AbstractCookingRecipe {

    public BlastingRecipe(Block ingredientBlock, Block resultBlock, int count, float experience, int cookingtime) {
        this(ingredientBlock, resultBlock.asItem(), count, experience, cookingtime);
    }

    public BlastingRecipe(Block ingredientBlock, Item resultItem, int count, float experience, int cookingtime) {
        super(ingredientBlock, resultItem, count, experience, cookingtime);
    }

    @Override
    public String getType() {
        return "minecraft:blasting";
    }
}
