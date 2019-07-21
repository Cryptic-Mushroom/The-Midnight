package com.mushroom.midnight.common.json.ingredient;

import net.minecraft.item.Item;

public class Result {
    private final String item;
    private final int count;

    public Result(Item item, int count) {
        this.item = item.getRegistryName().toString();
        this.count = count;
    }
}
