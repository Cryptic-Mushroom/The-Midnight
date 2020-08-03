package midnight.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class CacheContainer extends Container {
    private static final int DEFAULT_SIZE = 4;
    private final IInventory inventory;

    protected CacheContainer(ContainerType<CacheContainer> type, int id, PlayerInventory playerInventory, IInventory inventory) {
        super(type, id);
        assertInventorySize(inventory, DEFAULT_SIZE);
        this.inventory = inventory;
        inventory.openInventory(playerInventory.player);
        for (int k = 0; k < DEFAULT_SIZE; ++k) {
            addSlot(new Slot(inventory, k, 26 + k * 36, 23));
        }
        setPlayerSlots(playerInventory);
    }

    public CacheContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        this(MnContainers.CACHE, id, playerInventory, inventory);
    }

    public CacheContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(DEFAULT_SIZE));
    }

    private void setPlayerSlots(PlayerInventory playerInventory) {
        int i = (1 - 4) * 18;
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 101 + l * 18 + i));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 159 + i));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return this.inventory.isUsableByPlayer(player);
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.inventory.closeInventory(player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int inventSize = this.inventory.getSizeInventory();
            if (index < inventSize) {
                if (!this.mergeItemStack(itemstack1, inventSize, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, inventSize, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
