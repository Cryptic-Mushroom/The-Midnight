package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockMidnightFurnace;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class TileEntityMidnightFurnace extends TileEntityFurnace {
    
    @Override
    public String getName() {
        return hasCustomName() ? furnaceCustomName : "tile.midnight.midnight_furnace.name";
    }

    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == Item.getItemFromBlock(ModBlocks.SHADOWROOT_SLAB) || item == Item.getItemFromBlock(ModBlocks.DARK_WILLOW_SLAB) || item == Item.getItemFromBlock(ModBlocks.DEAD_WOOD_SLAB)) {
            event.setBurnTime(150);
        } else if (item == ModItems.DARK_STICK) {
            event.setBurnTime(100);
        } else if (item == ModItems.DARK_PEARL) {
            event.setBurnTime(1600);
        } else if (event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.DARK_PEARL_BLOCK)) {
            event.setBurnTime(16000);
        }
    }

    @Override
    public void update() {
        boolean flag = isBurning();
        boolean flag1 = false;
        if (isBurning()) {
            --furnaceBurnTime;
        }
        if (!world.isRemote) {
            ItemStack itemstack = furnaceItemStacks.get(1);
            if (isBurning() || !itemstack.isEmpty() && !(furnaceItemStacks.get(0)).isEmpty()) {
                if (!isBurning() && canSmelt()) {
                    furnaceBurnTime = getItemBurnTime(itemstack);
                    currentItemBurnTime = furnaceBurnTime;
                    if (isBurning()) {
                        flag1 = true;
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }
                if (isBurning() && canSmelt()) {
                    ++cookTime;

                    if (cookTime == totalCookTime) {
                        cookTime = 0;
                        totalCookTime = getCookTime(furnaceItemStacks.get(0));
                        smeltItem();
                        flag1 = true;
                    }
                } else {
                    cookTime = 0;
                }
            } else if (!isBurning() && cookTime > 0) {
                cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
            }
            if (flag != isBurning()) {
                flag1 = true;
                BlockMidnightFurnace.setState(isBurning(), world, pos);
            }
        }
        if (flag1) {
            markDirty();
        }
    }

    private boolean canSmelt() {
        if ((furnaceItemStacks.get(0)).isEmpty()) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks.get(0));
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = furnaceItemStacks.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
}
