package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IntegrationJEI implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		// blacklisted items
		IIngredientBlacklist blackList = jeiHelpers.getIngredientBlacklist();
		blackList.addIngredientToBlacklist(new ItemStack(ModBlocks.DARK_WATER));
		blackList.addIngredientToBlacklist(new ItemStack(ModBlocks.MIASMA));
		blackList.addIngredientToBlacklist(new ItemStack(ModItems.ADVANCEMENT_RIFT));
	}
}
