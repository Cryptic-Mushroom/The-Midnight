package midnight.common.recipe;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.IRecipeSerializer;
import midnight.common.registry.RegistryManager;

@ObjectHolder("midnight")
public class MnRecipeSerializers {
    public static final MidnightCookingRecipeSerializer<MidnightFurnaceRecipe> SMELTING = register("smelting", new MidnightCookingRecipeSerializer<>(MidnightFurnaceRecipe::new, 200));

    private static <R extends IRecipeSerializer<?>> R register(String id, R recipe) {
        RegistryManager.RECIPE_SERIALIZERS.register(id, recipe);
        return recipe;
    }
}
