package com.mushroom.midnight.common.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void onInit() {
        GameRegistry.addSmelting(ModItems.RAW_SUAVIS, new ItemStack(ModItems.COOK_SUAVIS), 0.1F);
        GameRegistry.addSmelting(ModBlocks.TENEBRUM_ORE, new ItemStack(ModItems.TENEBRUM_INGOT), 0.5F);
        GameRegistry.addSmelting(ModBlocks.NAGRILITE_ORE, new ItemStack(ModItems.NAGRILITE_INGOT), 0.5F);
        GameRegistry.addSmelting(ModBlocks.EBONYS_ORE, new ItemStack(ModItems.EBONYS), 0.5F);
    }
}
