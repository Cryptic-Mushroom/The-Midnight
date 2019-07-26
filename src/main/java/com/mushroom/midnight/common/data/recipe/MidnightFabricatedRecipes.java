package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.data.Triggers;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public final class MidnightFabricatedRecipes extends MidnightRecipeProvider {
    public MidnightFabricatedRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightItems.BOGSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.BOGSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.BOGSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.VIRIDSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.VIRIDSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.VIRIDSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.DEWSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.DEWSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.DEWSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.NIGHTSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.NIGHTSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.NIGHTSHROOM_POWDER))
                .build(consumer);
    }
}
