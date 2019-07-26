package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.data.Triggers;
import com.mushroom.midnight.common.registry.MidnightTags;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public final class StandardRecipes {
    private final Consumer<IFinishedRecipe> consumer;

    StandardRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;
    }

    public static StandardRecipes into(Consumer<IFinishedRecipe> consumer) {
        return new StandardRecipes(consumer);
    }

    public Material ofMaterial(IItemProvider material) {
        return new Material(this.consumer, material);
    }

    public StandardRecipes addPlanks(IItemProvider material, IItemProvider planks) {
        ShapelessRecipeBuilder.shapelessRecipe(planks, 4)
                .addIngredient(material)
                .addCriterion("has_log", Triggers.hasItem(material))
                .build(this.consumer);

        return this;
    }

    public StandardRecipes addFood(IItemProvider raw, IItemProvider cooked) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(cooked.asItem());

        CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(raw), cooked, 0.35F, 200)
                .func_218628_a("has_raw", Triggers.hasItem(raw))
                .func_218630_a(this.consumer);

        CookingRecipeBuilder.func_218631_a(Ingredient.fromItems(raw), cooked, 0.35F, 100, IRecipeSerializer.SMOKING)
                .func_218628_a("has_raw", Triggers.hasItem(raw))
                .func_218635_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_from_smoking"));

        CookingRecipeBuilder.func_218631_a(Ingredient.fromItems(raw), cooked, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING)
                .func_218628_a("has_raw", Triggers.hasItem(raw))
                .func_218635_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_from_campfire_cooking"));

        return this;
    }

    public StandardRecipes addIngot(IItemProvider ore, IItemProvider ingot) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(ingot.asItem());

        CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ore), ingot, 1.0F, 200)
                .func_218628_a("has_ore", Triggers.hasItem(ore))
                .func_218630_a(this.consumer);

        CookingRecipeBuilder.func_218633_b(Ingredient.fromItems(ore), ingot, 1.0F, 100)
                .func_218628_a("has_ore", Triggers.hasItem(ore))
                .func_218635_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_from_blasting"));

        return this;
    }

    public static class Material {
        private final Consumer<IFinishedRecipe> consumer;
        private final IItemProvider material;

        private Material(Consumer<IFinishedRecipe> consumer, IItemProvider material) {
            this.consumer = consumer;
            this.material = material;
        }

        public Material addButton(IItemProvider button) {
            ShapelessRecipeBuilder.shapelessRecipe(button)
                    .addIngredient(this.material)
                    .setGroup("midnight_buttons")
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addChest(IItemProvider chest) {
            ShapedRecipeBuilder.shapedRecipe(chest)
                    .patternLine("###")
                    .patternLine("# #")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addCraftingTable(IItemProvider craftingTable) {
            ShapedRecipeBuilder.shapedRecipe(craftingTable)
                    .patternLine("##")
                    .patternLine("##")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addDoor(IItemProvider door) {
            ShapedRecipeBuilder.shapedRecipe(door, 3)
                    .patternLine("##")
                    .patternLine("##")
                    .patternLine("##")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .setGroup("midnight_doors")
                    .build(this.consumer);

            return this;
        }

        public Material addFence(IItemProvider fence) {
            ShapedRecipeBuilder.shapedRecipe(fence, 3)
                    .patternLine("#|#")
                    .patternLine("#|#")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .setGroup("midnight_fences")
                    .build(this.consumer);

            return this;
        }

        public Material addFenceGate(IItemProvider fenceGate) {
            ShapedRecipeBuilder.shapedRecipe(fenceGate)
                    .patternLine("|#|")
                    .patternLine("|#|")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .setGroup("midnight_fence_gates")
                    .build(this.consumer);

            return this;
        }

        public Material addLadder(IItemProvider ladder) {
            ShapedRecipeBuilder.shapedRecipe(ladder, 3)
                    .patternLine("| |")
                    .patternLine("|#|")
                    .patternLine("| |")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addPressurePlate(IItemProvider pressurePlate) {
            ShapedRecipeBuilder.shapedRecipe(pressurePlate)
                    .patternLine("##")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addSlab(IItemProvider slab) {
            ShapedRecipeBuilder.shapedRecipe(slab, 6)
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addStoneSlab(IItemProvider slab) {
            this.addSlab(slab);

            ResourceLocation id = ForgeRegistries.ITEMS.getKey(slab.asItem());
            SingleItemRecipeBuilder.func_218644_a(Ingredient.fromItems(this.material), slab, 2)
                    .func_218643_a("has_item", Triggers.hasItem(this.material))
                    .func_218647_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_stonecutting"));

            return this;
        }

        public Material addStairs(IItemProvider stairs) {
            ShapedRecipeBuilder.shapedRecipe(stairs, 4)
                    .patternLine("#  ")
                    .patternLine("## ")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addStoneStairs(IItemProvider stairs) {
            this.addStairs(stairs);

            ResourceLocation id = ForgeRegistries.ITEMS.getKey(stairs.asItem());
            SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(this.material), stairs)
                    .func_218643_a("has_item", Triggers.hasItem(this.material))
                    .func_218647_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_stonecutting"));

            return this;
        }

        public Material addTrapDoor(IItemProvider trapDoor) {
            ShapedRecipeBuilder.shapedRecipe(trapDoor, 6)
                    .patternLine("###")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);
            return this;
        }

        public Material addWall(IItemProvider wall) {
            ShapedRecipeBuilder.shapedRecipe(wall, 6)
                    .patternLine("###")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            ResourceLocation id = ForgeRegistries.ITEMS.getKey(wall.asItem());

            SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(this.material), wall)
                    .func_218643_a("has_item", Triggers.hasItem(this.material))
                    .func_218647_a(this.consumer, MidnightUtil.transformPath(id, path -> path + "_stonecutting"));

            return this;
        }

        public Material addPickaxe(IItemProvider pickaxe) {
            ShapedRecipeBuilder.shapedRecipe(pickaxe)
                    .patternLine("###")
                    .patternLine(" | ")
                    .patternLine(" | ")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addAxe(IItemProvider axe) {
            ShapedRecipeBuilder.shapedRecipe(axe)
                    .patternLine("##")
                    .patternLine("|#")
                    .patternLine("| ")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addSword(IItemProvider sword) {
            ShapedRecipeBuilder.shapedRecipe(sword)
                    .patternLine("#")
                    .patternLine("#")
                    .patternLine("|")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);
            return this;
        }

        public Material addShovel(IItemProvider shovel) {
            ShapedRecipeBuilder.shapedRecipe(shovel)
                    .patternLine("#")
                    .patternLine("|")
                    .patternLine("|")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addHoe(IItemProvider hoe) {
            ShapedRecipeBuilder.shapedRecipe(hoe)
                    .patternLine("##")
                    .patternLine("| ")
                    .patternLine("| ")
                    .key('#', this.material)
                    .key('|', MidnightTags.Items.STICKS)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addHelmet(IItemProvider helmet) {
            ShapedRecipeBuilder.shapedRecipe(helmet)
                    .patternLine("###")
                    .patternLine("# #")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addChestPlate(IItemProvider chestPlate) {
            ShapedRecipeBuilder.shapedRecipe(chestPlate)
                    .patternLine("# #")
                    .patternLine("###")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addLeggings(IItemProvider leggings) {
            ShapedRecipeBuilder.shapedRecipe(leggings)
                    .patternLine("###")
                    .patternLine("# #")
                    .patternLine("# #")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addBoots(IItemProvider boots) {
            ShapedRecipeBuilder.shapedRecipe(boots)
                    .patternLine("# #")
                    .patternLine("# #")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }

        public Material addStorageBlock(IItemProvider block) {
            ShapedRecipeBuilder.shapedRecipe(block)
                    .patternLine("###")
                    .patternLine("###")
                    .patternLine("###")
                    .key('#', this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            ResourceLocation id = ForgeRegistries.ITEMS.getKey(this.material.asItem());

            ShapelessRecipeBuilder.shapelessRecipe(this.material, 9)
                    .addIngredient(block)
                    .addCriterion("has_item", Triggers.hasItem(block))
                    .build(this.consumer, MidnightUtil.transformPath(id, path -> path + "_from_block"));

            return this;
        }

        public Material addNugget(IItemProvider nugget) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(nugget.asItem());

            ShapedRecipeBuilder.shapedRecipe(this.material)
                    .patternLine("###")
                    .patternLine("###")
                    .patternLine("###")
                    .key('#', nugget)
                    .addCriterion("has_nugget", Triggers.hasItem(nugget))
                    .build(this.consumer, MidnightUtil.transformPath(id, path -> path + "_from_nuggets"));

            ShapelessRecipeBuilder.shapelessRecipe(nugget, 9)
                    .addIngredient(this.material)
                    .addCriterion("has_item", Triggers.hasItem(this.material))
                    .build(this.consumer);

            return this;
        }
    }
}
