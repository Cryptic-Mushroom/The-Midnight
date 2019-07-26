package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.data.Triggers;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public final class MidnightDecorativeRecipes extends MidnightRecipeProvider {
    public MidnightDecorativeRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ARCHAIC_GLASS, 8)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.ARCHAIC_SHARD)
                .addCriterion("has_shards", Triggers.hasItem(MidnightItems.ARCHAIC_SHARD))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ARCHAIC_GLASS_PANE, 16)
                .patternLine("###")
                .patternLine("###")
                .key('#', MidnightBlocks.ARCHAIC_GLASS)
                .addCriterion("has_shards", Triggers.hasItem(MidnightItems.ARCHAIC_SHARD))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.COARSE_DIRT, 4)
                .patternLine("#*")
                .patternLine("*#")
                .key('#', MidnightBlocks.DIRT)
                .key('*', MidnightBlocks.DECEITFUL_PEAT)
                .addCriterion("has_peat", Triggers.hasItem(MidnightBlocks.DECEITFUL_PEAT))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.MIDNIGHT_LEVER)
                .patternLine("|")
                .patternLine("#")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_nightstone", Triggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSTONE_FURNACE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("###")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .addCriterion("has_nightstone", Triggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.BOGSHROOM_SPORCH, 2)
                .key('*', MidnightItems.BOGSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.BOGSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.VIRIDSHROOM_SPORCH, 2)
                .key('*', MidnightItems.VIRIDSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.VIRIDSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.DEWSHROOM_SPORCH, 2)
                .key('*', MidnightItems.DEWSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.DEWSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSHROOM_SPORCH, 2)
                .key('*', MidnightItems.NIGHTSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", Triggers.hasItem(MidnightItems.NIGHTSHROOM_POWDER))
                .build(consumer);
    }
}
