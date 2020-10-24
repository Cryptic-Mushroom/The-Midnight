/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 24
 */

package midnight.data.models.stategen;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.data.models.modelgen.IModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.BiConsumer;

public class MultipartBlockStateGen implements IBlockStateGen {
    private final List<Part> parts = Lists.newArrayList();

    private MultipartBlockStateGen() {
    }

    @Override
    public JsonElement makeJson(ResourceLocation id, Block block) {
        JsonObject root = new JsonObject();
        JsonArray multipart = new JsonArray();

        for (Part part : parts) {
            multipart.add(part.getJson());
        }

        root.add("multipart", multipart);
        return root;
    }

    @Override
    public void getModels(BiConsumer<String, IModelGen> consumer) {
        for (Part part : parts) {
            for (ModelInfo model : part.models) {
                model.getModels(consumer);
            }
        }
    }

    public MultipartBlockStateGen part(ModelInfo... models) {
        parts.add(new Part(null, models));
        return this;
    }

    public MultipartBlockStateGen part(Selector sel, ModelInfo... models) {
        parts.add(new Part(sel, models));
        return this;
    }

    public static MultipartBlockStateGen multipart() {
        return new MultipartBlockStateGen();
    }

    private static class Part {
        final Selector selector;
        final ModelInfo[] models;

        Part(Selector selector, ModelInfo[] models) {
            this.selector = selector;
            this.models = models;
        }

        public JsonObject getJson() {
            JsonObject obj = new JsonObject();
            if (selector != null) {
                obj.add("when", selector.getJson());
            }
            obj.add("apply", ModelInfo.makeJson(models));
            return obj;
        }
    }
}
