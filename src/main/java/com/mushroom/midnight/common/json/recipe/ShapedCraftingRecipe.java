package com.mushroom.midnight.common.json.recipe;

import com.mushroom.midnight.common.json.ingredient.IJsonIngredient;
import com.mushroom.midnight.common.json.ingredient.Result;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class ShapedCraftingRecipe implements IJsonRecipe {
    private final String type;
    private String[] pattern;
    private final Map<Character, IJsonIngredient> key = new HashMap<>();
    private final Result result;
    private final transient String name;

    public ShapedCraftingRecipe(Block resultBlock) {
        this(resultBlock, 1);
    }

    public ShapedCraftingRecipe(Block resultBlock, int count) {
        this(resultBlock.asItem(), count);
    }

    public ShapedCraftingRecipe(Item resultItem) {
        this(resultItem, 1);
    }

    public ShapedCraftingRecipe(Item resultItem, int count) {
        this.type = getType();
        this.result = new Result(resultItem, count);
        this.name = resultItem.getRegistryName().getPath();
    }

    public ShapedCraftingRecipe withPattern(String... pattern) {
        this.pattern = pattern;
        return this;
    }

    @SafeVarargs
    public final ShapedCraftingRecipe withIngredients(Pair<Character, IJsonIngredient>... pairs) {
        for (Pair<Character, IJsonIngredient> pair : pairs) {
            this.key.put(pair.getLeft(), pair.getRight());
        }
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return "minecraft:crafting_shaped";
    }
}
