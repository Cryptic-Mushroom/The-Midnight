package midnight.common.recipe;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import midnight.common.util.EnumUtil;

public class MidnightRecipeBookCategories {
    public static final RecipeBookCategories FURNACE_SEARCH = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_SEARCH", new Class[] {ItemStack[].class}, (Object) new ItemStack[] {new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategories FURNACE_FOOD = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_FOOD", new Class[] {ItemStack[].class}, (Object) new ItemStack[] {new ItemStack(MnItems.RAW_STAG_FLANK)});
    public static final RecipeBookCategories FURNACE_BLOCKS = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_BLOCKS", new Class[] {ItemStack[].class}, (Object) new ItemStack[] {new ItemStack(MnBlocks.NIGHTSTONE)});
    public static final RecipeBookCategories FURNACE_MISC = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_MISC", new Class[] {ItemStack[].class}, (Object) new ItemStack[] {new ItemStack(MnItems.MIASMA_BUCKET), new ItemStack(MnItems.TENEBRUM_INGOT)});

    public static void justLoadClass() {
    }

}
