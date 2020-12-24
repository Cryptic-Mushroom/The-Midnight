/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.stategen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.data.models.modelgen.ModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A generator for variants based block state definitions.
 */
public class VariantsBlockStateGen implements StateGen {
    private final Map<String, ModelInfo[]> variants = new HashMap<>();

    @Override
    public JsonElement makeJson(Identifier id, Block block) {
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
    public void getModels(BiConsumer<String, ModelGen> consumer) {
        for (ModelInfo[] infos : variants.values()) {
            for (ModelInfo info : infos) {
                info.getModels(consumer);
            }
        }
    }

    /**
     * Adds a variant to this generator
     *
     * @param variant The variant selector, properties with values, separated by {@code ,} ({@code
     *                property1=value1,property2=value2,...}
     * @param models  A list of randomly chosen models
     */
    public VariantsBlockStateGen variant(String variant, ModelInfo... models) {
        variants.put(variant, models.clone());
        return this;
    }

    /**
     * Creates a variant state generator and adds one variant to it
     *
     * @param variant The variant selector, properties with values, separated by {@code ,} ({@code
     *                property1=value1,property2=value2,...}
     * @param models  A list of randomly chosen models
     */
    public static VariantsBlockStateGen variants(String variant, ModelInfo... models) {
        return new VariantsBlockStateGen().variant(variant, models);
    }

    /**
     * Creates a variant state generator and adds one variant to it that matches all states
     *
     * @param models A list of randomly chosen models
     */
    public static VariantsBlockStateGen variants(ModelInfo... models) {
        return new VariantsBlockStateGen().variant("", models);
    }

    /**
     * Creates a variant state generator without variants
     */
    public static VariantsBlockStateGen variants() {
        return new VariantsBlockStateGen();
    }
}
