package com.mushroom.midnight.common.json;

import com.google.common.collect.ImmutableList;
import com.google.gson.GsonBuilder;
import com.mushroom.midnight.common.json.ingredient.IngredientItem;
import com.mushroom.midnight.common.json.ingredient.IngredientTag;
import com.mushroom.midnight.common.json.recipe.BlastingRecipe;
import com.mushroom.midnight.common.json.recipe.CampfireRecipe;
import com.mushroom.midnight.common.json.recipe.IJsonRecipe;
import com.mushroom.midnight.common.json.recipe.ShapedCraftingRecipe;
import com.mushroom.midnight.common.json.recipe.ShapelessCraftingRecipe;
import com.mushroom.midnight.common.json.recipe.SmeltingRecipe;
import com.mushroom.midnight.common.json.recipe.SmokingRecipe;
import com.mushroom.midnight.common.json.recipe.StoneCuttingRecipe;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.command.CommandSource;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.mushroom.midnight.Midnight.LOGGER;

public class GenRecipes {
    public static int genRecipes(CommandSource source) {
        File baseFolder = new File("json/data/midnight/recipes");

        new CommonRecipes(new IngredientItem(MidnightBlocks.DARK_WILLOW_PLANKS))
                .withButton(MidnightBlocks.DARK_WILLOW_BUTTON)
                .withChest(MidnightBlocks.DARK_WILLOW_CHEST)
                .withCraftingTable(MidnightBlocks.DARK_WILLOW_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.DARK_WILLOW_DOOR)
                .withFence(MidnightBlocks.DARK_WILLOW_FENCE)
                .withFenceGate(MidnightBlocks.DARK_WILLOW_FENCE_GATE)
                .withLadder(MidnightBlocks.DARK_WILLOW_LADDER)
                .withPressurePlate(MidnightBlocks.DARK_WILLOW_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.DARK_WILLOW_SLAB)
                .withStairs(MidnightBlocks.DARK_WILLOW_STAIRS)
                .withTrapDoor(MidnightBlocks.DARK_WILLOW_TRAPDOOR)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.SHADOWROOT_PLANKS))
                .withButton(MidnightBlocks.SHADOWROOT_BUTTON)
                .withChest(MidnightBlocks.SHADOWROOT_CHEST)
                .withCraftingTable(MidnightBlocks.SHADOWROOT_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.SHADOWROOT_DOOR)
                .withFence(MidnightBlocks.SHADOWROOT_FENCE)
                .withFenceGate(MidnightBlocks.SHADOWROOT_FENCE_GATE)
                .withLadder(MidnightBlocks.SHADOWROOT_LADDER)
                .withPressurePlate(MidnightBlocks.SHADOWROOT_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.SHADOWROOT_SLAB)
                .withStairs(MidnightBlocks.SHADOWROOT_STAIRS)
                .withTrapDoor(MidnightBlocks.SHADOWROOT_TRAPDOOR)
                .withPickaxe(MidnightItems.SHADOWROOT_PICKAXE)
                .withAxe(MidnightItems.SHADOWROOT_AXE)
                .withSword(MidnightItems.SHADOWROOT_SWORD)
                .withShovel(MidnightItems.SHADOWROOT_SHOVEL)
                .withHoe(MidnightItems.SHADOWROOT_HOE)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.DEAD_WOOD_PLANKS))
                .withButton(MidnightBlocks.DEAD_WOOD_BUTTON)
                .withChest(MidnightBlocks.DEAD_WOOD_CHEST)
                .withCraftingTable(MidnightBlocks.DEAD_WOOD_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.DEAD_WOOD_DOOR)
                .withFence(MidnightBlocks.DEAD_WOOD_FENCE)
                .withFenceGate(MidnightBlocks.DEAD_WOOD_FENCE_GATE)
                .withLadder(MidnightBlocks.DEAD_WOOD_LADDER)
                .withPressurePlate(MidnightBlocks.DEAD_WOOD_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.DEAD_WOOD_SLAB)
                .withStairs(MidnightBlocks.DEAD_WOOD_STAIRS)
                .withTrapDoor(MidnightBlocks.DEAD_WOOD_TRAPDOOR)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.NIGHTSHROOM_PLANKS))
                .withButton(MidnightBlocks.NIGHTSHROOM_BUTTON)
                .withChest(MidnightBlocks.NIGHTSHROOM_CHEST)
                .withCraftingTable(MidnightBlocks.NIGHTSHROOM_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.NIGHTSHROOM_DOOR)
                .withFence(MidnightBlocks.NIGHTSHROOM_FENCE)
                .withFenceGate(MidnightBlocks.NIGHTSHROOM_FENCE_GATE)
                .withLadder(MidnightBlocks.NIGHTSHROOM_LADDER)
                .withPressurePlate(MidnightBlocks.NIGHTSHROOM_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.NIGHTSHROOM_SLAB)
                .withStairs(MidnightBlocks.NIGHTSHROOM_STAIRS)
                .withTrapDoor(MidnightBlocks.NIGHTSHROOM_TRAPDOOR)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.DEWSHROOM_PLANKS))
                .withButton(MidnightBlocks.DEWSHROOM_BUTTON)
                .withChest(MidnightBlocks.DEWSHROOM_CHEST)
                .withCraftingTable(MidnightBlocks.DEWSHROOM_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.DEWSHROOM_DOOR)
                .withFence(MidnightBlocks.DEWSHROOM_FENCE)
                .withFenceGate(MidnightBlocks.DEWSHROOM_FENCE_GATE)
                .withLadder(MidnightBlocks.DEWSHROOM_LADDER)
                .withPressurePlate(MidnightBlocks.DEWSHROOM_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.DEWSHROOM_SLAB)
                .withStairs(MidnightBlocks.DEWSHROOM_STAIRS)
                .withTrapDoor(MidnightBlocks.DEWSHROOM_TRAPDOOR)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.VIRIDSHROOM_PLANKS))
                .withButton(MidnightBlocks.VIRIDSHROOM_BUTTON)
                .withChest(MidnightBlocks.VIRIDSHROOM_CHEST)
                .withCraftingTable(MidnightBlocks.VIRIDSHROOM_CRAFTING_TABLE)
                .withDoor(MidnightBlocks.VIRIDSHROOM_DOOR)
                .withFence(MidnightBlocks.VIRIDSHROOM_FENCE)
                .withFenceGate(MidnightBlocks.VIRIDSHROOM_FENCE_GATE)
                .withLadder(MidnightBlocks.VIRIDSHROOM_LADDER)
                .withPressurePlate(MidnightBlocks.VIRIDSHROOM_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.VIRIDSHROOM_SLAB)
                .withStairs(MidnightBlocks.VIRIDSHROOM_STAIRS)
                .withTrapDoor(MidnightBlocks.VIRIDSHROOM_TRAPDOOR)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.NIGHTSTONE))
                .withButton(MidnightBlocks.NIGHTSTONE_BUTTON)
                .withPressurePlate(MidnightBlocks.NIGHTSTONE_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.NIGHTSTONE_SLAB)
                .withStairs(MidnightBlocks.NIGHTSTONE_STAIRS)
                .withWall(MidnightBlocks.NIGHTSTONE_WALL)
                .withPickaxe(MidnightItems.NIGHTSTONE_PICKAXE)
                .withAxe(MidnightItems.NIGHTSTONE_AXE)
                .withSword(MidnightItems.NIGHTSTONE_SWORD)
                .withShovel(MidnightItems.NIGHTSTONE_SHOVEL)
                .withHoe(MidnightItems.NIGHTSTONE_HOE)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.NIGHTSTONE_BRICKS))
                .withSlab(MidnightBlocks.NIGHTSTONE_BRICK_SLAB)
                .withStairs(MidnightBlocks.NIGHTSTONE_BRICK_STAIRS)
                .withWall(MidnightBlocks.NIGHTSTONE_BRICK_WALL)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.TRENCHSTONE))
                .withButton(MidnightBlocks.TRENCHSTONE_BUTTON)
                .withPressurePlate(MidnightBlocks.TRENCHSTONE_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.TRENCHSTONE_SLAB)
                .withStairs(MidnightBlocks.TRENCHSTONE_STAIRS)
                .withWall(MidnightBlocks.TRENCHSTONE_WALL)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.TRENCHSTONE_BRICKS))
                .withSlab(MidnightBlocks.TRENCHSTONE_BRICK_SLAB)
                .withStairs(MidnightBlocks.TRENCHSTONE_BRICK_STAIRS)
                .withWall(MidnightBlocks.TRENCHSTONE_BRICK_WALL)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightBlocks.ROCKSHROOM_BRICKS))
                .withButton(MidnightBlocks.ROCKSHROOM_BRICK_BUTTON)
                .withPressurePlate(MidnightBlocks.ROCKSHROOM_BRICK_PRESSURE_PLATE)
                .withSlab(MidnightBlocks.ROCKSHROOM_BRICK_SLAB)
                .withStairs(MidnightBlocks.ROCKSHROOM_BRICK_STAIRS)
                .withWall(MidnightBlocks.ROCKSHROOM_BRICK_WALL)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightItems.ROCKSHROOM_CLUMP))
                .withHelmet(MidnightItems.ROCKSHROOM_HELMET)
                .withChestPlate(MidnightItems.ROCKSHROOM_CHESTPLATE)
                .withLeggings(MidnightItems.ROCKSHROOM_LEGGINGS)
                .withBoots(MidnightItems.ROCKSHROOM_BOOTS)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightItems.NAGRILITE_INGOT))
                .withPressurePlate(MidnightBlocks.NAGRILITE_PRESSURE_PLATE)
                .withPickaxe(MidnightItems.NAGRILITE_PICKAXE)
                .withAxe(MidnightItems.NAGRILITE_AXE)
                .withSword(MidnightItems.NAGRILITE_SWORD)
                .withShovel(MidnightItems.NAGRILITE_SHOVEL)
                .withHoe(MidnightItems.NAGRILITE_HOE)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightItems.TENEBRUM_INGOT))
                .withDoor(MidnightBlocks.TENEBRUM_DOOR)
                .withPressurePlate(MidnightBlocks.TENEBRUM_PRESSURE_PLATE)
                .withTrapDoor(MidnightBlocks.TENEBRUM_TRAPDOOR)
                .withPickaxe(MidnightItems.TENEBRUM_PICKAXE)
                .withAxe(MidnightItems.TENEBRUM_AXE)
                .withSword(MidnightItems.TENEBRUM_SWORD)
                .withShovel(MidnightItems.TENEBRUM_SHOVEL)
                .withHoe(MidnightItems.TENEBRUM_HOE)
                .withHelmet(MidnightItems.TENEBRUM_HELMET)
                .withChestPlate(MidnightItems.TENEBRUM_CHESTPLATE)
                .withLeggings(MidnightItems.TENEBRUM_LEGGINGS)
                .withBoots(MidnightItems.TENEBRUM_BOOTS)
                .generate();

        new CommonRecipes(new IngredientItem(MidnightItems.EBONITE))
                .withPickaxe(MidnightItems.EBONITE_PICKAXE)
                .withAxe(MidnightItems.EBONITE_AXE)
                .withSword(MidnightItems.EBONITE_SWORD)
                .withShovel(MidnightItems.EBONITE_SHOVEL)
                .withHoe(MidnightItems.EBONITE_HOE)
                .generate();

        File smeltingFolder = new File(baseFolder, "smelting");
        if (!makeDirs(smeltingFolder)) {
            return 1;
        }
        List<SmeltingRecipe> smeltingRecipes = getSmeltingRecipes();
        for (SmeltingRecipe jsonRecipe : smeltingRecipes) {
            saveAsJson(new File(smeltingFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        File stoneCuttingFolder = new File(baseFolder, "stonecutting");
        if (!makeDirs(stoneCuttingFolder)) {
            return 1;
        }
        List<StoneCuttingRecipe> stoneCuttingRecipes = getStoneCuttingRecipes();
        for (StoneCuttingRecipe jsonRecipe : stoneCuttingRecipes) {
            saveAsJson(new File(stoneCuttingFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        File blastingFolder = new File(baseFolder, "blasting");
        if (!makeDirs(blastingFolder)) {
            return 1;
        }
        List<BlastingRecipe> blastingRecipes = getBlastingRecipes();
        for (BlastingRecipe jsonRecipe : blastingRecipes) {
            saveAsJson(new File(blastingFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        File campfireFolder = new File(baseFolder, "campfire_cooking");
        if (!makeDirs(campfireFolder)) {
            return 1;
        }
        List<CampfireRecipe> campfireRecipes = getCampfireRecipes();
        for (CampfireRecipe jsonRecipe : campfireRecipes) {
            saveAsJson(new File(campfireFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        File smokingFolder = new File(baseFolder, "smoking");
        if (!makeDirs(smokingFolder)) {
            return 1;
        }
        List<SmokingRecipe> smokingRecipes = getSmokingRecipes();
        for (SmokingRecipe jsonRecipe : smokingRecipes) {
            saveAsJson(new File(smokingFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        List<ShapelessCraftingRecipe> shapelessCraftingRecipes = getShapelessCraftingRecipes();
        for (ShapelessCraftingRecipe jsonRecipe : shapelessCraftingRecipes) {
            saveAsJson(new File(baseFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        List<ShapedCraftingRecipe> shapedCraftingRecipes = getShapedCraftingRecipes();
        for (ShapedCraftingRecipe jsonRecipe : shapedCraftingRecipes) {
            saveAsJson(new File(baseFolder, jsonRecipe.getName() + ".json"), jsonRecipe);
        }

        LOGGER.info("All jsons have been generated " + a);
        return 1;
    }

    private static List<ShapedCraftingRecipe> getShapedCraftingRecipes() {
        ImmutableList.Builder<ShapedCraftingRecipe> list = new ImmutableList.Builder<>();
        list.add(new ShapedCraftingRecipe(MidnightBlocks.ARCHAIC_GLASS, 8)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.ARCHAIC_SHARD)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.ARCHAIC_GLASS_PANE, 16)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.ARCHAIC_GLASS)))
                .withPattern("AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.BOGSHROOM_SPORE_BOMB)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.BOGSHROOM_POWDER)), Pair.of('B', new IngredientItem(MidnightItems.DARK_PEARL)))
                .withPattern(" A ", "ABA", " A ")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.VIRIDSHROOM_SPORE_BOMB)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.VIRIDSHROOM_POWDER)), Pair.of('B', new IngredientItem(MidnightItems.DARK_PEARL)))
                .withPattern(" A ", "ABA", " A ")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.DEWSHROOM_SPORE_BOMB)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.DEWSHROOM_POWDER)), Pair.of('B', new IngredientItem(MidnightItems.DARK_PEARL)))
                .withPattern(" A ", "ABA", " A ")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.NIGHTSHROOM_SPORE_BOMB)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.NIGHTSHROOM_POWDER)), Pair.of('B', new IngredientItem(MidnightItems.DARK_PEARL)))
                .withPattern(" A ", "ABA", " A ")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, 4)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.NIGHTSTONE)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS, 4)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.NIGHTSTONE_BRICKS)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, 4)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.TRENCHSTONE)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.ROCKSHROOM)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.ROCKSHROOM_CLUMP)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, 4)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.ROCKSHROOM)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.COARSE_DIRT, 4)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.DIRT)), Pair.of('B', new IngredientItem(MidnightBlocks.DECEITFUL_PEAT)))
                .withPattern("AB", "BA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.DARK_PEARL_BLOCK)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.DARK_PEARL)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.NAGRILITE_BLOCK)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.NAGRILITE_INGOT)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.TENEBRUM_BLOCK)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.TENEBRUM_INGOT)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.EBONITE_BLOCK)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.EBONITE)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.NAGRILITE_INGOT)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.NAGRILITE_NUGGET)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.TENEBRUM_INGOT)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.TENEBRUM_NUGGET)))
                .withPattern("AAA", "AAA", "AAA")
        );
        list.add(new ShapedCraftingRecipe(MidnightItems.DARK_STICK, 4)
                .withIngredients(Pair.of('A', new IngredientTag(MidnightTags.Items.MIDNIGHT_PLANKS)))
                .withPattern("A", "A")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.GLOB_FUNGUS_STEM)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.GLOB_FUNGUS_HAND)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.SUAVIS)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.RAW_SUAVIS)))
                .withPattern("AA", "AA")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.MIDNIGHT_LEVER)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightItems.DARK_STICK)), Pair.of('B', new IngredientItem(MidnightBlocks.NIGHTSTONE)))
                .withPattern("A", "B")
        );
        list.add(new ShapedCraftingRecipe(MidnightBlocks.NIGHTSTONE_FURNACE)
                .withIngredients(Pair.of('A', new IngredientItem(MidnightBlocks.NIGHTSTONE)))
                .withPattern("AAA", "A A", "AAA")
        );
        return list.build();
    }

    private static List<ShapelessCraftingRecipe> getShapelessCraftingRecipes() {
        ImmutableList.Builder<ShapelessCraftingRecipe> list = new ImmutableList.Builder<>();
        list.add(new ShapelessCraftingRecipe(MidnightItems.BLADESHROOM_SPORES).withIngredients(new IngredientItem(MidnightItems.BLADESHROOM_CAP)));
        list.add(new ShapelessCraftingRecipe(MidnightItems.UNSTABLE_SEEDS).withIngredients(new IngredientTag(MidnightTags.Items.UNSTABLE_FRUITS)));
        list.add(new ShapelessCraftingRecipe(MidnightItems.RAW_SUAVIS, 4).withIngredients(new IngredientItem(MidnightBlocks.SUAVIS)));

        list.add(new ShapelessCraftingRecipe("bogshroom_powder", MidnightItems.BOGSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.BOGSHROOM_HAT)));
        list.add(new ShapelessCraftingRecipe("bogshroom_powder2", MidnightItems.BOGSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.BOGSHROOM)));
        list.add(new ShapelessCraftingRecipe("bogshroom_powder3", MidnightItems.BOGSHROOM_POWDER, 3).withIngredients(new IngredientItem(MidnightBlocks.BOGSHROOM_SHELF)));
        list.add(new ShapelessCraftingRecipe("bogshroom_powder4", MidnightItems.BOGSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.DOUBLE_BOGSHROOM)));

        list.add(new ShapelessCraftingRecipe("dewshroom_powder", MidnightItems.DEWSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM_HAT)));
        list.add(new ShapelessCraftingRecipe("dewshroom_powder2", MidnightItems.DEWSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM)));
        list.add(new ShapelessCraftingRecipe("dewshroom_powder3", MidnightItems.DEWSHROOM_POWDER, 3).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM_SHELF)));
        list.add(new ShapelessCraftingRecipe("dewshroom_powder4", MidnightItems.DEWSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.DOUBLE_DEWSHROOM)));
        list.add(new ShapelessCraftingRecipe("dewshroom_powder5", MidnightItems.DEWSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS)));
        list.add(new ShapelessCraftingRecipe("dewshroom_powder6", MidnightItems.DEWSHROOM_POWDER, 1).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM_ROOTS)));

        list.add(new ShapelessCraftingRecipe("nightshroom_powder", MidnightItems.NIGHTSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM_HAT)));
        list.add(new ShapelessCraftingRecipe("nightshroom_powder2", MidnightItems.NIGHTSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM)));
        list.add(new ShapelessCraftingRecipe("nightshroom_powder3", MidnightItems.NIGHTSHROOM_POWDER, 3).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM_SHELF)));
        list.add(new ShapelessCraftingRecipe("nightshroom_powder4", MidnightItems.NIGHTSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.DOUBLE_NIGHTSHROOM)));
        list.add(new ShapelessCraftingRecipe("nightshroom_powder5", MidnightItems.NIGHTSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS)));
        list.add(new ShapelessCraftingRecipe("nightshroom_powder6", MidnightItems.NIGHTSHROOM_POWDER, 1).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM_ROOTS)));

        list.add(new ShapelessCraftingRecipe("viridshroom_powder", MidnightItems.VIRIDSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM_HAT)));
        list.add(new ShapelessCraftingRecipe("viridshroom_powder2", MidnightItems.VIRIDSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM)));
        list.add(new ShapelessCraftingRecipe("viridshroom_powder3", MidnightItems.VIRIDSHROOM_POWDER, 3).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM_SHELF)));
        list.add(new ShapelessCraftingRecipe("viridshroom_powder4", MidnightItems.VIRIDSHROOM_POWDER, 4).withIngredients(new IngredientItem(MidnightBlocks.DOUBLE_VIRIDSHROOM)));
        list.add(new ShapelessCraftingRecipe("viridshroom_powder5", MidnightItems.VIRIDSHROOM_POWDER, 2).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS)));
        list.add(new ShapelessCraftingRecipe("viridshroom_powder6", MidnightItems.VIRIDSHROOM_POWDER, 1).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM_ROOTS)));

        list.add(new ShapelessCraftingRecipe(MidnightBlocks.BOGSHROOM_SPORCH, 2).withIngredients(new IngredientItem(MidnightItems.BOGSHROOM_POWDER), new IngredientItem(MidnightItems.DARK_STICK)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.VIRIDSHROOM_SPORCH, 2).withIngredients(new IngredientItem(MidnightItems.VIRIDSHROOM_POWDER), new IngredientItem(MidnightItems.DARK_STICK)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.DEWSHROOM_SPORCH, 2).withIngredients(new IngredientItem(MidnightItems.DEWSHROOM_POWDER), new IngredientItem(MidnightItems.DARK_STICK)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.NIGHTSHROOM_SPORCH, 2).withIngredients(new IngredientItem(MidnightItems.NIGHTSHROOM_POWDER), new IngredientItem(MidnightItems.DARK_STICK)));

        list.add(new ShapelessCraftingRecipe(MidnightItems.DARK_PEARL, 9).withIngredients(new IngredientItem(MidnightBlocks.DARK_PEARL_BLOCK)));
        list.add(new ShapelessCraftingRecipe("nagrilite_block_disassemble", MidnightItems.NAGRILITE_INGOT, 9).withIngredients(new IngredientItem(MidnightBlocks.NAGRILITE_BLOCK)));
        list.add(new ShapelessCraftingRecipe("tenebrum_block_disassemble", MidnightItems.TENEBRUM_INGOT, 9).withIngredients(new IngredientItem(MidnightBlocks.TENEBRUM_BLOCK)));
        list.add(new ShapelessCraftingRecipe(MidnightItems.EBONITE, 9).withIngredients(new IngredientItem(MidnightBlocks.EBONITE_BLOCK)));

        list.add(new ShapelessCraftingRecipe(MidnightItems.NAGRILITE_NUGGET, 9).withIngredients(new IngredientItem(MidnightItems.NAGRILITE_INGOT)));
        list.add(new ShapelessCraftingRecipe(MidnightItems.TENEBRUM_NUGGET, 9).withIngredients(new IngredientItem(MidnightItems.TENEBRUM_INGOT)));

        list.add(new ShapelessCraftingRecipe(MidnightBlocks.DARK_WILLOW_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.DARK_WILLOW_LOG)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.SHADOWROOT_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.SHADOWROOT_LOG)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.DEAD_WOOD_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.DEAD_WOOD_LOG)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.NIGHTSHROOM_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.NIGHTSHROOM_STEM)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.DEWSHROOM_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.DEWSHROOM_STEM)));
        list.add(new ShapelessCraftingRecipe(MidnightBlocks.VIRIDSHROOM_PLANKS, 4).withIngredients(new IngredientItem(MidnightBlocks.VIRIDSHROOM_STEM)));
        return list.build();
    }

    private static int a = 0;

    public static boolean saveAsJson(File file, IJsonRecipe recipe) {
        a++;
        if (file.exists()) {
            file.delete();
        }
        try {
            if (file.createNewFile()) {
                FileWriter fw = new FileWriter(file);
                fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(recipe));
                fw.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean makeDirs(File file) {
        if (!file.exists() && !file.mkdirs()) {
            LOGGER.warn("Impossible to create the folder : " + file.getPath());
            return false;
        }
        return true;
    }

    private static List<BlastingRecipe> getBlastingRecipes() {
        ImmutableList.Builder<BlastingRecipe> list = new ImmutableList.Builder<>();
        list.add(new BlastingRecipe(MidnightBlocks.ARCHAIC_ORE, MidnightItems.ARCHAIC_SHARD, 1, 1f, 100));
        list.add(new BlastingRecipe(MidnightBlocks.EBONITE_ORE, MidnightItems.EBONITE, 1, 1f, 100));
        list.add(new BlastingRecipe(MidnightBlocks.NAGRILITE_ORE, MidnightItems.NAGRILITE_INGOT, 1, 1f, 100));
        list.add(new BlastingRecipe(MidnightBlocks.TENEBRUM_ORE, MidnightItems.TENEBRUM_INGOT, 1, 1f, 100));
        return list.build();
    }

    private static List<CampfireRecipe> getCampfireRecipes() {
        ImmutableList.Builder<CampfireRecipe> list = new ImmutableList.Builder<>();
        list.add(new CampfireRecipe(MidnightItems.HUNTER_WING, MidnightItems.COOKED_HUNTER_WING, 1, 0.35f, 400));
        list.add(new CampfireRecipe(MidnightItems.RAW_STAG_FLANK, MidnightItems.COOKED_STAG_FLANK, 1, 0.35f, 400));
        list.add(new CampfireRecipe(MidnightBlocks.STINGER_EGG, MidnightItems.COOKED_STINGER_EGG, 1, 0.35f, 400));
        list.add(new CampfireRecipe(MidnightItems.RAW_SUAVIS, MidnightItems.COOKED_SUAVIS, 1, 0.35f, 400));
        return list.build();
    }

    private static List<SmokingRecipe> getSmokingRecipes() {
        ImmutableList.Builder<SmokingRecipe> list = new ImmutableList.Builder<>();
        list.add(new SmokingRecipe(MidnightItems.HUNTER_WING, MidnightItems.COOKED_HUNTER_WING, 1, 0.35f, 100));
        list.add(new SmokingRecipe(MidnightItems.RAW_STAG_FLANK, MidnightItems.COOKED_STAG_FLANK, 1, 0.35f, 100));
        list.add(new SmokingRecipe(MidnightBlocks.STINGER_EGG, MidnightItems.COOKED_STINGER_EGG, 1, 0.35f, 100));
        list.add(new SmokingRecipe(MidnightItems.RAW_SUAVIS, MidnightItems.COOKED_SUAVIS, 1, 0.35f, 100));
        return list.build();
    }

    private static List<SmeltingRecipe> getSmeltingRecipes() {
        ImmutableList.Builder<SmeltingRecipe> list = new ImmutableList.Builder<>();
        list.add(new SmeltingRecipe(MidnightBlocks.ARCHAIC_ORE, MidnightItems.ARCHAIC_SHARD, 1, 1f, 200));
        list.add(new SmeltingRecipe(MidnightBlocks.EBONITE_ORE, MidnightItems.EBONITE, 1, 1f, 200));
        list.add(new SmeltingRecipe(MidnightBlocks.NAGRILITE_ORE, MidnightItems.NAGRILITE_INGOT, 1, 1f, 200));
        list.add(new SmeltingRecipe(MidnightBlocks.TENEBRUM_ORE, MidnightItems.TENEBRUM_INGOT, 1, 1f, 200));

        list.add(new SmeltingRecipe(MidnightItems.HUNTER_WING, MidnightItems.COOKED_HUNTER_WING, 1, 0.35f, 200));
        list.add(new SmeltingRecipe(MidnightItems.RAW_STAG_FLANK, MidnightItems.COOKED_STAG_FLANK, 1, 0.35f, 200));
        list.add(new SmeltingRecipe(MidnightBlocks.STINGER_EGG, MidnightItems.COOKED_STINGER_EGG, 1, 0.35f, 200));
        list.add(new SmeltingRecipe(MidnightItems.RAW_SUAVIS, MidnightItems.COOKED_SUAVIS, 1, 0.35f, 200));
        return list.build();
    }

    private static List<StoneCuttingRecipe> getStoneCuttingRecipes() {
        ImmutableList.Builder<StoneCuttingRecipe> list = new ImmutableList.Builder<>();
        // stairs
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_BRICK_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, MidnightBlocks.NIGHTSTONE_BRICK_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_BRICK_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, MidnightBlocks.TRENCHSTONE_BRICK_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM, MidnightBlocks.ROCKSHROOM_BRICK_STAIRS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, MidnightBlocks.ROCKSHROOM_BRICK_STAIRS));
        // slabs
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_BRICK_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, MidnightBlocks.NIGHTSTONE_BRICK_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_BRICK_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, MidnightBlocks.TRENCHSTONE_BRICK_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM, MidnightBlocks.ROCKSHROOM_BRICK_SLAB, 2));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, MidnightBlocks.ROCKSHROOM_BRICK_SLAB, 2));
        // walls
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_BRICK_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, MidnightBlocks.NIGHTSTONE_BRICK_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_BRICK_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, MidnightBlocks.TRENCHSTONE_BRICK_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM, MidnightBlocks.ROCKSHROOM_BRICK_WALL));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, MidnightBlocks.ROCKSHROOM_BRICK_WALL));
        // bricks/chiseled
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.NIGHTSTONE_BRICKS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE, MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.TRENCHSTONE, MidnightBlocks.TRENCHSTONE_BRICKS));
        list.add(new StoneCuttingRecipe(MidnightBlocks.ROCKSHROOM, MidnightBlocks.ROCKSHROOM_BRICKS));
        return list.build();
    }
}
