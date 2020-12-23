/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.data.models.stategen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.data.models.modelgen.IModelGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VariantBlockStateGen implements IBlockStateGen {

    private static final Function<Map.Entry<Property<?>, Comparable<?>>, String> PROPERTY_MAP_PRINTER = new Function<Map.Entry<Property<?>, Comparable<?>>, String>() {
        @Override
        public String apply(Map.Entry<Property<?>, Comparable<?>> entry) {
            if (entry == null) {
                return "<NULL>";
            } else {
                Property<?> prop = entry.getKey();
                return prop.getName() + "=" + nameValue(prop, entry.getValue());
            }
        }

        @SuppressWarnings("unchecked")
        private <T extends Comparable<T>> String nameValue(Property<T> prop, Comparable<?> val) {
            return prop.getName((T) val);
        }
    };

    private final Map<String, ModelInfo[]> variants = new HashMap<>();

    @Override
    public JsonElement makeJson(ResourceLocation id, Block block) {
        JsonObject root = new JsonObject();

        JsonObject variants = new JsonObject();
        for (Map.Entry<String, ModelInfo[]> variant : this.variants.entrySet()) {
            JsonElement variantJson = ModelInfo.makeJson(variant.getValue());
            variants.add(variant.getKey(), variantJson);
        }
        root.add("variants", variants);
        return root;
    }

    @Override
    public void getModels(BiConsumer<String, IModelGen> consumer) {
        for (ModelInfo[] infos : variants.values()) {
            for (ModelInfo info : infos) {
                info.getModels(consumer);
            }
        }
    }

    public VariantBlockStateGen variant(String variant, ModelInfo... models) {
        variants.put(variant, models);
        return this;
    }

    public VariantBlockStateGen variant(BlockState variant, ModelInfo... models) {
        String p = variant.getValues()
                          .entrySet()
                          .stream()
                          .map(PROPERTY_MAP_PRINTER)
                          .collect(Collectors.joining(","));
        variant(p, models);
        return this;
    }


    public static VariantBlockStateGen create(String variant, ModelInfo... models) {
        return new VariantBlockStateGen().variant(variant, models);
    }

    public static VariantBlockStateGen create(ModelInfo... models) {
        return new VariantBlockStateGen().variant("", models);
    }
}
