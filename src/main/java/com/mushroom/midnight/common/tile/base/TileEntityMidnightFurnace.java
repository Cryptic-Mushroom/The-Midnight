package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.block.BlockMidnightFurnace;
import com.mushroom.midnight.common.container.ContainerMidnightFurnace;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.MathHelper;

public class TileEntityMidnightFurnace extends TileEntityFurnace {

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        currentItemBurnTime = TileEntityMidnightFurnace.getItemBurnTime(furnaceItemStacks.get(1));
    }

    @Override
    public String getName() {
        return hasCustomName() ? furnaceCustomName : "tile.midnight.midnight_furnace.name";
    }

    @Override
    public ContainerMidnightFurnace createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerMidnightFurnace(playerIn.inventory, this);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 1) {
            ItemStack itemstack = this.furnaceItemStacks.get(1);
            return TileEntityMidnightFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
        return index != 2;
    }

    public static boolean isItemFuel(ItemStack stack) {
        return TileEntityMidnightFurnace.getItemBurnTime(stack) > 0;
    }

    public static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
            if (burnTime >= 0) {
                return burnTime;
            }
            Item item = stack.getItem();
            if (item == Item.getItemFromBlock(ModBlocks.SHADOWROOT_SLAB) || item == Item.getItemFromBlock(ModBlocks.DARK_WILLOW_SLAB) || item == Item.getItemFromBlock(ModBlocks.DEAD_WOOD_SLAB)) {
                return 150;
            } else if (item == Item.getItemFromBlock(ModBlocks.DARK_PEARL_BLOCK)) {
                return 16000;
            } else if (item == ModItems.DARK_STICK) {
                return 100;
            } else if (item == ModItems.DARK_PEARL) {
                return 1600;
            } else {
                return TileEntityFurnace.getItemBurnTime(stack);
            }
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
                    furnaceBurnTime = TileEntityMidnightFurnace.getItemBurnTime(itemstack);
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
