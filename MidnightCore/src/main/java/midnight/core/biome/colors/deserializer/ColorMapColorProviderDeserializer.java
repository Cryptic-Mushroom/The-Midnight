/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.deserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.core.biome.colors.ColorDeserializeContext;
import midnight.core.biome.colors.ColorDeserializeException;
import midnight.core.biome.colors.IColorProvider;
import midnight.core.biome.colors.IColorProviderDeserializer;
import midnight.core.biome.colors.provider.ColormapColorProvider;
import midnight.core.biome.colors.provider.ErrorColorProvider;
import net.minecraft.util.ResourceLocation;

public class ColorMapColorProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has("texture")) throw ctx.exception("Missing required 'texture'");
            if (!obj.get("texture").isJsonPrimitive()) throw ctx.exception("'texture' must be a string");
            if (!obj.getAsJsonPrimitive("texture").isString()) throw ctx.exception("'texture' must be a string");

            if (!obj.has("x")) throw ctx.exception("Missing required 'x'");
            if (!obj.get("x").isJsonPrimitive()) throw ctx.exception("'x' must be a number");
            if (!obj.getAsJsonPrimitive("x").isNumber()) throw ctx.exception("'x' must be a number");

            if (!obj.has("y")) throw ctx.exception("Missing required 'y'");
            if (!obj.get("y").isJsonPrimitive()) throw ctx.exception("'y' must be a number");
            if (!obj.getAsJsonPrimitive("y").isNumber()) throw ctx.exception("'y' must be a number");

            ResourceLocation loc = ctx.resLoc(obj.get("texture").getAsString());
            float x = obj.get("x").getAsFloat();
            float y = obj.get("y").getAsFloat();

            IColorProvider fallback = ErrorColorProvider.INSTANCE;

            if (obj.has("fallback")) {
                fallback = ctx.deserialize(obj.get("fallback"));
            }

            return new ColormapColorProvider(loc, x, y, fallback);
        } else {
            throw ctx.exception("Expected an object");
        }
    }
}
