/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.data.models;

import midnight.common.block.MnBlocks;
import midnight.data.models.modelgen.ModelGen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.InheritingModelGen.*;

/**
 * Collects and generates models of items (including block items).
 */
public final class ItemModelTable {
    private static BiConsumer<Item, ModelGen> consumer;

    public static void registerItemModels(BiConsumer<Item, ModelGen> c) {
        consumer = c;

        register(MnBlocks.NIGHTSTONE, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.NIGHT_BEDROCK, item -> inherit(name(item, "block/%s")));
        register(MnBlocks.TRENCHSTONE, item -> inherit(name(item, "block/%s")));
    }



    private static void register(ItemConvertible provider, Function<Item, ModelGen> genFactory) {
        Item item = provider.asItem();
        ModelGen gen = genFactory.apply(item);
        consumer.accept(item, gen);
    }

    private static String name(Item item, String nameFormat) {
        Identifier id = Registry.ITEM.getId(item);
        assert id != null;

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Item item) {
        Identifier id = Registry.ITEM.getId(item);
        assert id != null;
        return id.toString();
    }

    private static String name(Item item, String nameFormat, String omitSuffix) {
        Identifier id = Registry.ITEM.getId(item);
        assert id != null;

        String path = id.getPath();
        if (path.endsWith(omitSuffix)) {
            path = path.substring(0, path.length() - omitSuffix.length());
        }

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, path));
    }

    private ItemModelTable() {
    }
}
