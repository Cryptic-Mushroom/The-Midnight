package midnight.common.inventory;

import com.google.common.collect.Lists;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import midnight.common.recipe.MidnightRecipeBookCategories;
import midnight.common.recipe.MidnightRecipeTypes;
import midnight.common.tileentity.MidnightFurnaceTileEntity;

import java.util.List;

public class MidnightFurnaceContainer extends AbstractFurnaceContainer {
    public MidnightFurnaceContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
        super(MnContainers.FURNACE, MidnightRecipeTypes.SMELTING, p_i50082_1_, p_i50082_2_);
    }

    public MidnightFurnaceContainer(int p_i50083_1_, PlayerInventory p_i50083_2_, IInventory p_i50083_3_, IIntArray p_i50083_4_) {
        super(MnContainers.FURNACE, MidnightRecipeTypes.SMELTING, p_i50083_1_, p_i50083_2_, p_i50083_3_, p_i50083_4_);
    }

    @Override
    protected boolean isFuel(ItemStack p_217058_1_) {
        return MidnightFurnaceTileEntity.isFuel(p_217058_1_);
    }

    @Override
    public List<RecipeBookCategories> getRecipeBookCategories() {
        return Lists.newArrayList(
                MidnightRecipeBookCategories.FURNACE_SEARCH,
                MidnightRecipeBookCategories.FURNACE_FOOD,
                MidnightRecipeBookCategories.FURNACE_BLOCKS,
                MidnightRecipeBookCategories.FURNACE_MISC
        );
    }
}