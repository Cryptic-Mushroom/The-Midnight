package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public final class MidnightWoodenRecipes extends MidnightRecipeProvider {
    public MidnightWoodenRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        StandardRecipes recipes = StandardRecipes.into(consumer);

        recipes.ofMaterial(MidnightBlocks.DARK_WILLOW_PLANKS)
                .addButton(MidnightBlocks.DARK_WILLOW_BUTTON)
                .addChest(MidnightBlocks.DARK_WILLOW_CHEST)
                .addCraftingTable(MidnightBlocks.DARK_WILLOW_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DARK_WILLOW_DOOR)
                .addFence(MidnightBlocks.DARK_WILLOW_FENCE)
                .addFenceGate(MidnightBlocks.DARK_WILLOW_FENCE_GATE)
                .addLadder(MidnightBlocks.DARK_WILLOW_LADDER)
                .addPressurePlate(MidnightBlocks.DARK_WILLOW_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DARK_WILLOW_SLAB)
                .addStairs(MidnightBlocks.DARK_WILLOW_STAIRS)
                .addTrapDoor(MidnightBlocks.DARK_WILLOW_TRAPDOOR);

        recipes.ofMaterial(MidnightBlocks.SHADOWROOT_PLANKS)
                .addButton(MidnightBlocks.SHADOWROOT_BUTTON)
                .addChest(MidnightBlocks.SHADOWROOT_CHEST)
                .addCraftingTable(MidnightBlocks.SHADOWROOT_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.SHADOWROOT_DOOR)
                .addFence(MidnightBlocks.SHADOWROOT_FENCE)
                .addFenceGate(MidnightBlocks.SHADOWROOT_FENCE_GATE)
                .addLadder(MidnightBlocks.SHADOWROOT_LADDER)
                .addPressurePlate(MidnightBlocks.SHADOWROOT_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.SHADOWROOT_SLAB)
                .addStairs(MidnightBlocks.SHADOWROOT_STAIRS)
                .addTrapDoor(MidnightBlocks.SHADOWROOT_TRAPDOOR)
                .addPickaxe(MidnightItems.SHADOWROOT_PICKAXE)
                .addAxe(MidnightItems.SHADOWROOT_AXE)
                .addSword(MidnightItems.SHADOWROOT_SWORD)
                .addShovel(MidnightItems.SHADOWROOT_SHOVEL)
                .addHoe(MidnightItems.SHADOWROOT_HOE);

        recipes.ofMaterial(MidnightBlocks.DEAD_WOOD_PLANKS)
                .addButton(MidnightBlocks.DEAD_WOOD_BUTTON)
                .addChest(MidnightBlocks.DEAD_WOOD_CHEST)
                .addCraftingTable(MidnightBlocks.DEAD_WOOD_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DEAD_WOOD_DOOR)
                .addFence(MidnightBlocks.DEAD_WOOD_FENCE)
                .addFenceGate(MidnightBlocks.DEAD_WOOD_FENCE_GATE)
                .addLadder(MidnightBlocks.DEAD_WOOD_LADDER)
                .addPressurePlate(MidnightBlocks.DEAD_WOOD_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DEAD_WOOD_SLAB)
                .addStairs(MidnightBlocks.DEAD_WOOD_STAIRS)
                .addTrapDoor(MidnightBlocks.DEAD_WOOD_TRAPDOOR);

        recipes.ofMaterial(MidnightBlocks.NIGHTSHROOM_PLANKS)
                .addButton(MidnightBlocks.NIGHTSHROOM_BUTTON)
                .addChest(MidnightBlocks.NIGHTSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.NIGHTSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.NIGHTSHROOM_DOOR)
                .addFence(MidnightBlocks.NIGHTSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.NIGHTSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.NIGHTSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.NIGHTSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.NIGHTSHROOM_SLAB)
                .addStairs(MidnightBlocks.NIGHTSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.NIGHTSHROOM_TRAPDOOR);

        recipes.ofMaterial(MidnightBlocks.DEWSHROOM_PLANKS)
                .addButton(MidnightBlocks.DEWSHROOM_BUTTON)
                .addChest(MidnightBlocks.DEWSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.DEWSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DEWSHROOM_DOOR)
                .addFence(MidnightBlocks.DEWSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.DEWSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.DEWSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.DEWSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DEWSHROOM_SLAB)
                .addStairs(MidnightBlocks.DEWSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.DEWSHROOM_TRAPDOOR);

        recipes.ofMaterial(MidnightBlocks.VIRIDSHROOM_PLANKS)
                .addButton(MidnightBlocks.VIRIDSHROOM_BUTTON)
                .addChest(MidnightBlocks.VIRIDSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.VIRIDSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.VIRIDSHROOM_DOOR)
                .addFence(MidnightBlocks.VIRIDSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.VIRIDSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.VIRIDSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.VIRIDSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.VIRIDSHROOM_SLAB)
                .addStairs(MidnightBlocks.VIRIDSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.VIRIDSHROOM_TRAPDOOR);

        recipes.addPlanks(MidnightBlocks.DARK_WILLOW_LOG, MidnightBlocks.DARK_WILLOW_PLANKS);
        recipes.addPlanks(MidnightBlocks.SHADOWROOT_LOG, MidnightBlocks.SHADOWROOT_PLANKS);
        recipes.addPlanks(MidnightBlocks.DEAD_WOOD_LOG, MidnightBlocks.DEAD_WOOD_PLANKS);
        recipes.addPlanks(MidnightBlocks.NIGHTSHROOM_STEM, MidnightBlocks.NIGHTSHROOM_PLANKS);
        recipes.addPlanks(MidnightBlocks.DEWSHROOM_STEM, MidnightBlocks.DEWSHROOM_PLANKS);
        recipes.addPlanks(MidnightBlocks.VIRIDSHROOM_STEM, MidnightBlocks.VIRIDSHROOM_PLANKS);
    }
}
