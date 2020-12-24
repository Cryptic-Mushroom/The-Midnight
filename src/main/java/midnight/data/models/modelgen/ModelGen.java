/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.modelgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

/**
 * Basic generator for a block or item model.
 */
@FunctionalInterface
public interface ModelGen {
    ModelGen EMPTY = name -> new JsonObject();

    /**
     * Generates a JSON element to be exported as a model.
     *
     * @param name The name of the model, under which it will be exported
     */
    JsonElement makeJson(Identifier name);
}
