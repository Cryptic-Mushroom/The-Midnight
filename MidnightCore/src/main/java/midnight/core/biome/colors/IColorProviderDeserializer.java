/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import com.google.gson.JsonElement;

@FunctionalInterface
public interface IColorProviderDeserializer {
    IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException;
}
