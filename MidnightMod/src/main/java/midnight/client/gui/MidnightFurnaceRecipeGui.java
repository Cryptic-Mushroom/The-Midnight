package midnight.client.gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import midnight.common.tileentity.MidnightFurnaceTileEntity;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class MidnightFurnaceRecipeGui extends AbstractRecipeBookGui {
    @Override
    protected boolean func_212962_b() {
        return this.recipeBook.isFurnaceFilteringCraftable();
    }

    @Override
    protected void func_212959_a(boolean p_212959_1_) {
        this.recipeBook.setFurnaceFilteringCraftable(p_212959_1_);
    }

    @Override
    protected boolean func_212963_d() {
        return this.recipeBook.isFurnaceGuiOpen();
    }

    @Override
    protected void func_212957_c(boolean p_212957_1_) {
        this.recipeBook.setFurnaceGuiOpen(p_212957_1_);
    }

    @Override
    protected ITextComponent func_230479_g_() {
        return new TranslationTextComponent("gui.recipebook.toggleRecipes.smeltable");
    }

    @Override
    protected Set<Item> func_212958_h() {
        return MidnightFurnaceTileEntity.getBurnTimes().keySet();
    }
}