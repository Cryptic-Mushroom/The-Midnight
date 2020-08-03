package midnight.common.registry;

import com.google.common.collect.Maps;
import net.minecraftforge.common.ToolType;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public abstract class AbstractItemBuilder<T extends AbstractItemBuilder<T, I>, I extends Item> {
    protected int stackSize = 64;
    protected int durability = 0;
    protected Item container = null;
    protected ItemGroup group = null;
    protected Rarity rarity = Rarity.COMMON;
    protected Food food = null;
    protected boolean resistsFire = false;
    protected boolean canRepair = false;
    protected Map<ToolType, Integer> toolClasses = Maps.newHashMap();
    protected Supplier<Callable<ItemStackTileEntityRenderer>> teRenderer = null;
    protected final List<Consumer<? super I>> itemProcess = new ArrayList<>();

    /**
     * Sets the maximum stack size for this item. Default is 64.
     */
    public T maxStackSize(int size) {
        stackSize = size;
        return (T) this;
    }

    /**
     * Sets the maximum durability for this item. Setting this to 0 will make an item have no durability. Default is 0.
     */
    public T maxDurability(int max) {
        durability = max;
        return (T) this;
    }

    /**
     * Sets a container item for this item, for use in crafting. For example, a bucket of water has an empty bucket as
     * container item. Default is null.
     */
    public T container(Item container) {
        this.container = container;
        return (T) this;
    }

    /**
     * Sets the item group/creative tab this item appears in. Default is null.
     */
    public T group(ItemGroup group) {
        this.group = group;
        return (T) this;
    }

    /**
     * Sets the {@link Rarity} of this item. Default is {@link Rarity#COMMON}.
     */
    public T rarity(Rarity rarity) {
        this.rarity = rarity;
        return (T) this;
    }

    /**
     * Sets the {@link Food} values of this item. Null indicates this item is no food. Default is null.
     */
    public T food(Food food) {
        this.food = food;
        return (T) this;
    }

    /**
     * Sets whether this item resists fire and lava damage. Example, netherite tools have this set to true. Default is
     * false.
     */
    public T resistsFire(boolean resistsFire) {
        this.resistsFire = resistsFire;
        return (T) this;
    }

    /**
     * Sets whether this item is repairable. Default is false.
     */
    public T canRepair(boolean canRepair) {
        this.canRepair = canRepair;
        return (T) this;
    }

    /**
     * Adds a tool type to this item, making it a tool.
     *
     * @param type The tool type of this item.
     * @param tier The tier of tool this item is.
     */
    public T tool(ToolType type, int tier) {
        this.toolClasses.put(type, tier);
        return (T) this;
    }

    /**
     * Sets a special tile entity renderer for this item.
     */
    public T teRenderer(Supplier<Callable<ItemStackTileEntityRenderer>> teRendererCallableSupplier) {
        teRenderer = teRendererCallableSupplier;
        return (T) this;
    }

    /**
     * Adds a post-processor to process the item right before creation.
     */
    public T processItem(Consumer<? super I> processor) {
        this.itemProcess.add(processor);
        return (T) this;
    }

    /**
     * Makes the {@link Item.Properties} instance with the configurations in this instance.
     */
    protected Item.Properties makeItemProps() {
        Item.Properties props = new Item.Properties();
        props.maxStackSize(stackSize);
        props.maxDamage(durability);
        props.containerItem(container);
        props.group(group);
        props.rarity(rarity);
        props.food(food);
        if (resistsFire) props.isBurnable();
        if (!canRepair) props.setNoRepair();
        for (Map.Entry<ToolType, Integer> toolTier : toolClasses.entrySet()) {
            props.addToolType(toolTier.getKey(), toolTier.getValue());
        }
        props.setISTER(teRenderer);
        return props;
    }
}
