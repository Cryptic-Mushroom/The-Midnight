package com.mushroom.midnight.common.json.ingredient;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class IngredientItem implements IJsonIngredient {
    private final String item;

    public IngredientItem(Block block) {
        this.item = block.getRegistryName().toString();
    }

    public IngredientItem(Item item) {
        this.item = item.getRegistryName().toString();
    }

    @Override
    public String getIngredientString() {
        return this.item;
    }
}
