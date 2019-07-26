package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.data.Triggers;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public final class MidnightPlantRecipes extends MidnightRecipeProvider {
    public MidnightPlantRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.GLOB_FUNGUS_STEM)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.GLOB_FUNGUS_HAND)
                .addCriterion("has_hand", Triggers.hasItem(MidnightItems.GLOB_FUNGUS_HAND))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.SUAVIS)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.RAW_SUAVIS)
                .addCriterion("has_suavis", Triggers.hasItem(MidnightItems.RAW_SUAVIS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BLADESHROOM_SPORES)
                .addIngredient(MidnightItems.BLADESHROOM_CAP)
                .addCriterion("has_cap", Triggers.hasItem(MidnightItems.BLADESHROOM_CAP))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.UNSTABLE_SEEDS)
                .addIngredient(MidnightTags.Items.UNSTABLE_FRUITS)
                .addCriterion("has_fruit", Triggers.hasItem(MidnightTags.Items.UNSTABLE_FRUITS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.RAW_SUAVIS, 4)
                .addIngredient(MidnightBlocks.SUAVIS)
                .addCriterion("has_suavis", Triggers.hasItem(MidnightBlocks.SUAVIS))
                .build(consumer);

        this.addFungiPowderRecipes(consumer);
    }

    private void addFungiPowderRecipes(Consumer<IFinishedRecipe> consumer) {
        // bogshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.BOGSHROOM_HAT)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.BOGSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.BOGSHROOM_SHELF)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_BOGSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_double"));

        // dewshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DEWSHROOM_HAT)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.DEWSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.DEWSHROOM_SHELF)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_DEWSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.DEWSHROOM_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_roots"));

        // nightshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_HAT)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.NIGHTSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_SHELF)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_NIGHTSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_roots"));

        // viridshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_HAT)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.VIRIDSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_SHELF)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_VIRIDSHROOM)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_ROOTS)
                .addCriterion("has_fungi", Triggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_roots"));
    }
}
