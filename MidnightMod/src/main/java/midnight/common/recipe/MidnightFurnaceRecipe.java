package midnight.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import midnight.common.block.MnBlocks;

public class MidnightFurnaceRecipe extends AbstractCookingRecipe {
    public MidnightFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(MidnightRecipeTypes.SMELTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(MnBlocks.NIGHTSTONE_FURNACE);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return MnRecipeSerializers.SMELTING;
    }
}
