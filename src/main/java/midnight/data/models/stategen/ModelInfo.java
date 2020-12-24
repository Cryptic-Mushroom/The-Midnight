/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.stategen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.data.models.modelgen.ModelGen;

import java.util.function.BiConsumer;

/**
 * Generator for some generic model information inside a block state definition, i.e. the model, it's rotation and some
 * other properties.
 */
public final class ModelInfo {
    private final String model;
    private ModelGen modelgen;
    private int x;
    private int y;
    private boolean uvlock;
    private int weight = 1;

    private ModelInfo(String model) {
        this.model = model;
    }

    private ModelInfo modelGen(ModelGen modelgen) {
        this.modelgen = modelgen;
        return this;
    }

    /**
     * Apply rotation to the model, i.e. {@code x} and {@code y} properties in JSON.
     *
     * @param x The X rotation, must be 0, 90, 180 or 270
     * @param y The Y rotation, must be 0, 90, 180 or 270
     */
    public ModelInfo rotate(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Enable or disable UV lock on the model (when the model itself enables UV lock this has no effect), i.e. the
     * {@code uvlock} property in JSON.
     */
    public ModelInfo uvlock(boolean uvlock) {
        this.uvlock = uvlock;
        return this;
    }

    /**
     * Add a weight for this model, for use in randomly selecting from multiple models, i.e. the {@code weight} property
     * in JSON.
     */
    public ModelInfo weight(int weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Accepts any {@link ModelGen} belonging to the model of the model information by feeding it to the given
     * consumer.
     */
    public void getModels(BiConsumer<String, ModelGen> consumer) {
        if (modelgen != null) {
            consumer.accept(model, modelgen);
        }
    }

    /**
     * Generates the JSON for this model info.
     *
     * @param withWeight Whether the {@code weight} property should be included
     * @return The generated JSON object
     */
    public JsonObject makeJson(boolean withWeight) {
        JsonObject obj = new JsonObject();
        obj.addProperty("model", model);
        if (x != 0) {
            obj.addProperty("x", x);
        }
        if (y != 0) {
            obj.addProperty("y", y);
        }
        if (uvlock) {
            obj.addProperty("uvlock", true);
        }
        if (withWeight && weight != 1) {
            obj.addProperty("weight", weight);
        }
        return obj;
    }

    /**
     * Makes the JSON of multiple model info's. When one model info is specified, it generates a JSON object with that
     * model info, excluding the {@code weight} property. In any other case it will return an array with a JSON object
     * for each model info, including the {@code weight} property.
     */
    public static JsonElement makeJson(ModelInfo... infos) {
        if (infos.length == 0) return new JsonArray();
        if (infos.length == 1) return infos[0].makeJson(false);
        JsonArray arr = new JsonArray();
        for (ModelInfo variant : infos) {
            arr.add(variant.makeJson(true));
        }
        return arr;
    }

    /**
     * Creates a model info with the given model
     */
    public static ModelInfo create(String model) {
        return new ModelInfo(model);
    }

    /**
     * Creates a model info with the given model, and a {@link ModelGen} that generates the model of this model info.
     * The given model generator may be null.
     */
    public static ModelInfo create(String model, ModelGen gen) {
        return new ModelInfo(model).modelGen(gen);
    }
}
