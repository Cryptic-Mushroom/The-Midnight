package midnight.common.tileentity;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import midnight.common.inventory.CacheContainer;

import javax.annotation.Nullable;

public class CacheTileEntity extends LockableLootTileEntity implements INamedContainerProvider {
    private int inventSize = 4;
    private final NonNullList<ItemStack> cacheContents = NonNullList.withSize(inventSize, ItemStack.EMPTY);

    protected CacheTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public CacheTileEntity() {
        this(MnTileEntities.CACHE);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.midnight.viridshroom_stem_cache");
    }

    @Override
    public int getSizeInventory() {
        return this.inventSize;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.cacheContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.cacheContents.clear();
        this.cacheContents.addAll(items);
    }

    @Override
    public boolean isEmpty() {
        return this.cacheContents.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public void read(BlockState stateIn, CompoundNBT compound) {
        super.read(stateIn, compound);
        this.cacheContents.clear();
        if (!checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.cacheContents);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.cacheContents);
        }
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> new InvWrapper(this)).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return createMenu(id, playerInventory, playerInventory.player);
    }

    @Override
    @Nullable
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        if (canOpen(player)) {
            fillWithLoot(playerInventory.player);
            return new CacheContainer(id, playerInventory, this);
        } else {
            return null;
        }
    }
}
