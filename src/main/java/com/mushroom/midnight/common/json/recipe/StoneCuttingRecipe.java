package com.mushroom.midnight.common.json.recipe;

import net.minecraft.block.Block;

public class StoneCuttingRecipe extends AbstractSingleIngredientRecipe {

    public StoneCuttingRecipe(Block ingredientBlock, Block resultBlock) {
        this(ingredientBlock, resultBlock, 1);
    }

    public StoneCuttingRecipe(Block ingredientBlock, Block resultBlock, int count) {
        super(ingredientBlock.asItem(), resultBlock.asItem(), count);
    }

    @Override
    public String getName() {
        return super.getName() + "_from_" + getIngredient().split(":")[1];
    }

    @Override
    public String getType() {
        return "minecraft:stonecutting";
    }
}
