package midnight.data.recipes;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public class MnRecipeProvider extends RecipeProvider {
    private Consumer<IFinishedRecipe> consumer;

    public MnRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;

        generic1x2("dark_stick_from_dead_wood", MnBlocks.DEAD_WOOD_PLANKS, MnItems.DARK_STICK, 4);
        generic1x2("dark_stick_from_dark_willow", MnBlocks.DARK_WILLOW_PLANKS, MnItems.DARK_STICK, 4);
        generic1x2("dark_stick_from_shadowroot", MnBlocks.SHADOWROOT_PLANKS, MnItems.DARK_STICK, 4);

        generic2x2("dead_wood_2x2", MnBlocks.DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD, 3);
        generic2x2("stripped_dead_wood_2x2", MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnBlocks.STRIPPED_DEAD_WOOD, 3);
        shapeless("dead_wood_planks_from_wood", MnBlocks.DEAD_WOOD, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_log", MnBlocks.DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_stripped_wood", MnBlocks.STRIPPED_DEAD_WOOD, MnBlocks.DEAD_WOOD_PLANKS, 4);
        shapeless("dead_wood_planks_from_stripped_log", MnBlocks.STRIPPED_DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD_PLANKS, 4);

        generic2x2("shadowroot_wood_2x2", MnBlocks.SHADOWROOT_LOG, MnBlocks.SHADOWROOT_WOOD, 3);
        generic2x2("stripped_shadowroot_wood_2x2", MnBlocks.STRIPPED_SHADOWROOT_LOG, MnBlocks.STRIPPED_SHADOWROOT_WOOD, 3);
        shapeless("shadowroot_planks_from_wood", MnBlocks.SHADOWROOT_WOOD, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_log", MnBlocks.SHADOWROOT_LOG, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_stripped_wood", MnBlocks.STRIPPED_SHADOWROOT_WOOD, MnBlocks.SHADOWROOT_PLANKS, 4);
        shapeless("shadowroot_planks_from_stripped_log", MnBlocks.STRIPPED_SHADOWROOT_LOG, MnBlocks.SHADOWROOT_PLANKS, 4);

        generic2x2("dark_willow_wood_2x2", MnBlocks.DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_WOOD, 3);
        generic2x2("stripped_dark_willow_wood_2x2", MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnBlocks.STRIPPED_DARK_WILLOW_WOOD, 3);
        shapeless("dark_willow_planks_from_wood", MnBlocks.DARK_WILLOW_WOOD, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_log", MnBlocks.DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_stripped_wood", MnBlocks.STRIPPED_DARK_WILLOW_WOOD, MnBlocks.DARK_WILLOW_PLANKS, 4);
        shapeless("dark_willow_planks_from_stripped_log", MnBlocks.STRIPPED_DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_PLANKS, 4);

        shapeless("nightshroom_planks_from_stem", MnBlocks.NIGHTSHROOM_STEM, MnBlocks.NIGHTSHROOM_PLANKS, 4);

        shapeless("dark_pearl_from_block", MnBlocks.DARK_PEARL_BLOCK, MnItems.DARK_PEARL, 9);
        generic3x3("dark_pearl_block_3x3", MnItems.DARK_PEARL, MnBlocks.DARK_PEARL_BLOCK, 1);
    }

    private void generic3x3(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("###")
                           .patternLine("###")
                           .patternLine("###")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void generic2x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("##")
                           .patternLine("##")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void shapeless(String id, IItemProvider from, IItemProvider to, int count) {
        ShapelessRecipeBuilder.shapelessRecipe(to, count)
                              .addIngredient(from)
                              .addCriterion("has_ingredient", hasItem(from))
                              .build(consumer, Midnight.resLoc(id));
    }

    private void shapeless(String id, Tag<Item> tag, IItemProvider to, int count) {
        ShapelessRecipeBuilder.shapelessRecipe(to, count)
                              .addIngredient(tag)
                              .addCriterion("has_ingredient", hasItem(tag))
                              .build(consumer, Midnight.resLoc(id));
    }

    private void fence(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .key('/', Items.STICK)
                           .patternLine("#/#")
                           .patternLine("#/#")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void generic1x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("#")
                           .patternLine("#")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void generic1x3(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("#")
                           .patternLine("#")
                           .patternLine("#")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void generic3x1(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("###")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void generic3x2(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("###")
                           .patternLine("###")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void stairs(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("#  ")
                           .patternLine("## ")
                           .patternLine("###")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void step(String id, IItemProvider from, IItemProvider to, int count) {
        ShapedRecipeBuilder.shapedRecipe(to, count)
                           .key('#', from)
                           .patternLine("#  ")
                           .patternLine("## ")
                           .addCriterion("has_ingredient", hasItem(from))
                           .build(consumer, Midnight.resLoc(id));
    }

    private void smelting(String id, IItemProvider from, IItemProvider to, double xp) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(from), to, (float) xp, 200)
                            .addCriterion("has_ingredient", hasItem(from))
                            .build(consumer, Midnight.resLoc(id));
    }

    @Override
    public String getName() {
        return "Midnight - Recipes";
    }
}
