package midnight.data.models;

import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import midnight.data.models.modelgen.IModelGen;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.InheritingModelGen.*;

public final class ItemModelTable {
    private static BiConsumer<Item, IModelGen> consumer;

    public static void registerItemModels(BiConsumer<Item, IModelGen> c) {
        consumer = c;

        register(MnBlocks.NIGHT_STONE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_BEDROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TRENCHSTONE, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_GRASS_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.COARSE_NIGHT_DIRT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_MUD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DECEITFUL_PEAT, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRANGE_SAND, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.NIGHT_GRASS, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_NIGHT_GRASS, item -> generated(name(item, "block/%s_upper")));

        register(MnBlocks.GHOST_PLANT, item -> generated(name(item, "block/%s")));
        register(MnBlocks.GHOST_PLANT_LEAF, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.GHOST_PLANT_STEM, item -> inherit(name(item, "block/%s")));

        register(MnBlocks.DEAD_WOOD_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DEAD_WOOD_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DEAD_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_WOOD_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DEAD_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.SHADOWROOT_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_SHADOWROOT_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_SHADOWROOT_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_LEAVES, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.SHADOWROOT_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_LOG, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.STRIPPED_DARK_WILLOW_WOOD, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_LEAVES, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.HANGING_DARK_WILLOW_LEAVES, item -> generated(name(item, "block/%s_item")));
        register(MnBlocks.DARK_WILLOW_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.DARK_WILLOW_SAPLING, item -> generated(name(item, "block/%s")));

        register(MnBlocks.NIGHTSHROOM_CAP, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_STEM, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_PLANKS, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM, item -> generated(name(item, "block/%s")));
        register(MnBlocks.NIGHTSHROOM_FIBRE, item -> generated(name(item, "block/%s")));
        register(MnBlocks.TALL_NIGHTSHROOM, item -> generated(name(item, "block/%s_upper")));

        register(MnItems.DARK_STICK, item -> generated(name(item, "item/%s")));
    }



    private static void register(IItemProvider provider, Function<Item, IModelGen> genFactory) {
        Item item = provider.asItem();
        IModelGen gen = genFactory.apply(item);
        consumer.accept(item, gen);
    }

    private static String name(Item item, String nameFormat) {
        ResourceLocation id = item.getRegistryName();
        assert id != null;

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Item item) {
        ResourceLocation id = item.getRegistryName();
        assert id != null;
        return id.toString();
    }

    private ItemModelTable() {
    }
}
