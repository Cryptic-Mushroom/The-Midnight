package com.mushroom.midnight.common.json.ingredient;

import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

public class IngredientTag implements IJsonIngredient {
    private final String tag;

    public IngredientTag(String tagString) {
        this.tag = tagString;
    }

    public IngredientTag(Tag<Item> tag) {
        this.tag = tag.getId().toString();
    }

    @Override
    public String getIngredientString() {
        return this.tag;
    }
}
