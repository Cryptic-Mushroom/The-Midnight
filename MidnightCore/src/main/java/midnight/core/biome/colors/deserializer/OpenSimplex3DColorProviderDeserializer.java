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
import midnight.core.biome.colors.provider.OpenSimplex3DColorProvider;

public class OpenSimplex3DColorProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has("a")) throw ctx.exception("Missing required 'a'");
            if (!obj.has("b")) throw ctx.exception("Missing required 'b'");

            IColorProvider a = ctx.silentDeserialize(obj.get("a"), "a");
            IColorProvider b = ctx.silentDeserialize(obj.get("b"), "b");

            int octaves = 1;

            double sizeX = 1, sizeY = 1, sizeZ = 1;

            if (obj.has("size") && obj.get("size").isJsonPrimitive()) {
                double s = obj.get("size").getAsDouble();
                sizeX = s;
                sizeY = s;
                sizeZ = s;
            }

            if (obj.has("sizeX") && obj.get("sizeX").isJsonPrimitive()) {
                sizeX = obj.get("sizeX").getAsDouble();
            }

            if (obj.has("sizeY") && obj.get("sizeY").isJsonPrimitive()) {
                sizeY = obj.get("sizeY").getAsDouble();
            }

            if (obj.has("sizeZ") && obj.get("sizeZ").isJsonPrimitive()) {
                sizeZ = obj.get("sizeZ").getAsDouble();
            }

            if (obj.has("octaves") && obj.get("octaves").isJsonPrimitive()) {
                octaves = obj.get("octaves").getAsInt();
            }

            return new OpenSimplex3DColorProvider(a, b, octaves, sizeX, sizeY, sizeZ);
        } else {
            throw ctx.exception("Expected an object");
        }
    }
}
