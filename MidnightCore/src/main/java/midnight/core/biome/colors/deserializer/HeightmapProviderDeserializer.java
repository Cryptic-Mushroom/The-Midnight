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
import midnight.core.biome.colors.provider.HeightmapColorProvider;

public class HeightmapProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has("above")) throw ctx.exception("Missing required 'above'");
            if (!obj.has("below")) throw ctx.exception("Missing required 'below'");
            if (!obj.has("type")) throw ctx.exception("Missing required 'type'");

            IColorProvider above = ctx.silentDeserialize(obj.get("above"), "above");
            IColorProvider below = ctx.silentDeserialize(obj.get("below"), "below");

            String type = obj.get("type").getAsString();

            HeightmapColorProvider.HeightProvider provider;
            try {
                provider = HeightmapColorProvider.HeightProvider.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException exc) {
                throw ctx.exception("'" + type + "' is not a valid height provider");
            }

            int lo = 1, hi = 1;

            if (obj.has("range") && obj.get("range").isJsonPrimitive()) {
                int s = obj.get("range").getAsInt();
                lo = -s / 2;
                hi = s / 2;
            }

            if (obj.has("offset") && obj.get("offset").isJsonPrimitive()) {
                int s = obj.get("offset").getAsInt();
                lo += s;
                hi += s;
            }

            if (obj.has("low") && obj.get("low").isJsonPrimitive()) {
                lo = obj.get("low").getAsInt();
            }

            if (obj.has("high") && obj.get("high").isJsonPrimitive()) {
                hi = obj.get("high").getAsInt();
            }

            return new HeightmapColorProvider(lo, hi, above, below, provider);
        } else {
            throw ctx.exception("Expected an object");
        }
    }
}
