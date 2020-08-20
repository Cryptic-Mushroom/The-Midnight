package midnight.common.item;

import midnight.common.food.MnFoods;
import midnight.common.registry.ItemBuilder;
import midnight.common.registry.RegistryManager;
import net.minecraft.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public class MnItems {
    private static final MnItems.Factory<Item> ITEM = factory(
        () -> ItemBuilder.builder(Item::new)
                         .group(MnItemGroups.ITEMS)
    );
    private static final MnItems.Factory<Item> FOODITEM = factory(
        () -> ItemBuilder.builder(Item::new)
                         .group(MnItemGroups.ITEMS)
    );

    public static final Item BOGSHROOM_POWDER = ITEM.item("bogshroom_powder");
    public static final Item NIGHTSHROOM_POWDER = ITEM.item("nightshroom_powder");
    public static final Item DEWSHROOM_POWDER = ITEM.item("dewshroom_powder");
    public static final Item VIRIDSHROOM_POWDER = ITEM.item("viridshroom_powder");

    public static final Item GEODE = ITEM.item("geode");
    public static final Item DARK_PEARL = ITEM.item("dark_pearl");
    public static final Item DARK_STICK = ITEM.item("dark_stick");
    public static final Item ROCKSHROOM_CLUMP = ITEM.item("rockshroom_clump");
    public static final Item TENEBRUM_INGOT = ITEM.item("tenebrum_ingot");
    public static final Item TENEBRUM_NUGGET = ITEM.item("tenebrum_nugget");
    public static final Item NAGRILITE_INGOT = ITEM.item("nagrilite_ingot");
    public static final Item NAGRILITE_NUGGET = ITEM.item("nagrilite_nugget");
    public static final Item EBONITE = ITEM.item("ebonite");
    public static final Item ARCHAIC_SHARD = ITEM.item("archaic_shard");

    public static final Item RAW_SUAVIS = FOODITEM.item("raw_suavis", config -> config.food(MnFoods.RAW_SUAVIS));
    public static final Item COOKED_SUAVIS = FOODITEM.item("cooked_suavis", config -> config.food(MnFoods.COOKED_SUAVIS));


    private static <I extends Item> I register(String id, I block) {
        RegistryManager.ITEMS.register(id, block);
        return block;
    }

    private static <B extends Item> MnItems.Factory<B> factory(Supplier<ItemBuilder<B>> config) {
        return new MnItems.Factory<>(config);
    }

    private static class Factory<B extends Item> {
        private final Supplier<ItemBuilder<B>> builderSupplier;

        private Factory(Supplier<ItemBuilder<B>> builderSupplier) {
            this.builderSupplier = builderSupplier;
        }

        public B item(String id, Function<ItemBuilder<B>, ItemBuilder<B>> additionalConfig) {
            return register(id, additionalConfig.apply(builderSupplier.get()).makeItem());
        }

        public B item(String id) {
            return item(id, Function.identity());
        }
    }
}
