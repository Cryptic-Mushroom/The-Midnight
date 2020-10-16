package midnight.common.item;

import midnight.common.Midnight;
import midnight.common.item.group.MnItemCategory;
import midnight.common.item.group.MnItemGroup;
import midnight.core.util.IRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

/**
 * This class registers and stores the list of Midnight items.
 *
 * @version 0.6.0
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public final class MnItems {
    public static final Item DARK_STICK = inj();
    public static final Item DARK_PEARL = inj();
    public static final Item GEODE = inj();

    private MnItems() {
    }

    public static void registerItems(IRegistry<Item> registry) {
        registry.registerAll(
            item("dark_stick", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("dark_pearl", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC),
            item("geode", MnItemCategory.COMMON_ITEMS, MnItemGroup.MISC)
        );
    }

    private static Item item(String id, MnItemCategory cat, Item item) {
        item.setRegistryName(Midnight.resLoc(id));
        cat.add(item);
        return item;
    }

    private static Item item(String id, MnItemCategory cat, Item.Properties properties) {
        return item(id, cat, new Item(properties));
    }

    private static Item item(String id, MnItemCategory cat, ItemGroup group) {
        return item(id, cat, new Item.Properties().group(group));
    }

    private static Item edible(String id, MnItemCategory cat, ItemGroup group, Food food) {
        return item(id, cat, new Item.Properties().group(group).food(food));
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T extends Item> T inj() {
        return null;
    }
}
