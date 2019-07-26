package com.mushroom.midnight.common.data.recipe;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public final class MidnightMaterialRecipes extends MidnightRecipeProvider {
    public MidnightMaterialRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        StandardRecipes recipes = StandardRecipes.into(consumer);

        recipes.ofMaterial(MidnightItems.ROCKSHROOM_CLUMP)
                .addHelmet(MidnightItems.ROCKSHROOM_HELMET)
                .addChestPlate(MidnightItems.ROCKSHROOM_CHESTPLATE)
                .addLeggings(MidnightItems.ROCKSHROOM_LEGGINGS)
                .addBoots(MidnightItems.ROCKSHROOM_BOOTS);

        recipes.ofMaterial(MidnightItems.NAGRILITE_INGOT)
                .addPressurePlate(MidnightBlocks.NAGRILITE_PRESSURE_PLATE)
                .addPickaxe(MidnightItems.NAGRILITE_PICKAXE)
                .addAxe(MidnightItems.NAGRILITE_AXE)
                .addSword(MidnightItems.NAGRILITE_SWORD)
                .addShovel(MidnightItems.NAGRILITE_SHOVEL)
                .addHoe(MidnightItems.NAGRILITE_HOE)
                .addStorageBlock(MidnightBlocks.NAGRILITE_BLOCK)
                .addNugget(MidnightItems.NAGRILITE_NUGGET);

        recipes.ofMaterial(MidnightItems.TENEBRUM_INGOT)
                .addDoor(MidnightBlocks.TENEBRUM_DOOR)
                .addPressurePlate(MidnightBlocks.TENEBRUM_PRESSURE_PLATE)
                .addTrapDoor(MidnightBlocks.TENEBRUM_TRAPDOOR)
                .addPickaxe(MidnightItems.TENEBRUM_PICKAXE)
                .addAxe(MidnightItems.TENEBRUM_AXE)
                .addSword(MidnightItems.TENEBRUM_SWORD)
                .addShovel(MidnightItems.TENEBRUM_SHOVEL)
                .addHoe(MidnightItems.TENEBRUM_HOE)
                .addHelmet(MidnightItems.TENEBRUM_HELMET)
                .addChestPlate(MidnightItems.TENEBRUM_CHESTPLATE)
                .addLeggings(MidnightItems.TENEBRUM_LEGGINGS)
                .addBoots(MidnightItems.TENEBRUM_BOOTS)
                .addStorageBlock(MidnightBlocks.TENEBRUM_BLOCK)
                .addNugget(MidnightItems.TENEBRUM_NUGGET);

        recipes.ofMaterial(MidnightItems.EBONITE)
                .addPickaxe(MidnightItems.EBONITE_PICKAXE)
                .addAxe(MidnightItems.EBONITE_AXE)
                .addSword(MidnightItems.EBONITE_SWORD)
                .addShovel(MidnightItems.EBONITE_SHOVEL)
                .addHoe(MidnightItems.EBONITE_HOE)
                .addStorageBlock(MidnightBlocks.EBONITE_BLOCK);

        recipes.ofMaterial(MidnightItems.DARK_PEARL)
                .addStorageBlock(MidnightBlocks.DARK_PEARL_BLOCK);

        recipes.addIngot(MidnightBlocks.NAGRILITE_ORE, MidnightItems.NAGRILITE_INGOT);
        recipes.addIngot(MidnightBlocks.TENEBRUM_ORE, MidnightItems.TENEBRUM_INGOT);
        recipes.addIngot(MidnightBlocks.EBONITE_ORE, MidnightItems.EBONITE);
    }
}
