package midnight.common.tileentity;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import midnight.common.inventory.MidnightFurnaceContainer;
import midnight.common.item.MnItems;
import midnight.common.recipe.MidnightRecipeTypes;

import java.util.Map;

public class MidnightFurnaceTileEntity extends AbstractFurnaceTileEntity {

    protected MidnightFurnaceTileEntity(TileEntityType<?> entityType) {
        super(entityType, MidnightRecipeTypes.SMELTING);
    }

    public MidnightFurnaceTileEntity() {
        this(MnTileEntities.MIDNIGHT_FURNACE);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.midnight.nightstone_furnace");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new MidnightFurnaceContainer(id, player, this, this.furnaceData);
    }

    public static Map<Item, Integer> getBurnTimes() {
        Map<Item, Integer> burnTimes = Maps.newLinkedHashMap();
        addItemBurnTime(burnTimes, MnItems.DARK_PEARL, 1600);
        return burnTimes;
    }

    @SuppressWarnings("unused")
    private static void addItemTagBurnTime(Map<Item, Integer> map, Tag<Item> tag, int burnTime) {
        for (Item item : tag.getAllElements()) {
            map.put(item, burnTime);
        }
    }

    private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider item, int burnTime) {
        map.put(item.asItem(), burnTime);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack item = items.get(1);
            return isFuel(stack) || stack.getItem() == Items.BUCKET && item.getItem() != Items.BUCKET;
        }
    }

    @Override
    protected int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return getBurnTimes().getOrDefault(item, 0);
        }
    }

    public static boolean isFuel(ItemStack item) {
        return getBurnTimes().getOrDefault(item.getItem(), 0) > 0;
    }
}
