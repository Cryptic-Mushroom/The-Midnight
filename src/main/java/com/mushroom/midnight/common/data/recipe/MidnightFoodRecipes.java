package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public final class MidnightFoodRecipes extends MidnightRecipeProvider {
    public MidnightFoodRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        StandardRecipes recipes = StandardRecipes.into(consumer);

        recipes.addFood(MidnightItems.HUNTER_WING, MidnightItems.COOKED_HUNTER_WING);
        recipes.addFood(MidnightItems.RAW_STAG_FLANK, MidnightItems.COOKED_STAG_FLANK);
        recipes.addFood(MidnightBlocks.STINGER_EGG, MidnightItems.COOKED_STINGER_EGG);
        recipes.addFood(MidnightItems.RAW_SUAVIS, MidnightItems.COOKED_SUAVIS);
    }
}
