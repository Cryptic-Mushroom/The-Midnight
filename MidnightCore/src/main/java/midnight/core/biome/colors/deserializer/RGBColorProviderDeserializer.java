/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import midnight.core.biome.colors.ColorDeserializeContext;
import midnight.core.biome.colors.ColorDeserializeException;
import midnight.core.biome.colors.IColorProvider;
import midnight.core.biome.colors.IColorProviderDeserializer;
import midnight.core.biome.colors.provider.SolidColorProvider;
import midnight.core.util.ColorUtil;

public class RGBColorProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            return parseArray(array, ctx, 0);
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String str = primitive.getAsString();
                return parseHex(str, ctx);
            } else if (primitive.isNumber()) {
                return parseNumber(primitive.getAsDouble(), ctx);
            } else if (primitive.isBoolean()) {
                return parseBoolean(primitive.getAsBoolean());
            } else {
                throw ctx.exception("Invalid json type");
            }
        } else if (element.isJsonNull()) {
            return new SolidColorProvider(0);
        } else if (element.isJsonObject()) {
            JsonObject object = new JsonObject();
            if (object.has("red") && object.has("green") && object.has("blue")) {
                return parseObject(object, "red", "green", "blue", ctx);
            } else if (object.has("r") && object.has("g") && object.has("b")) {
                return parseObject(object, "r", "g", "b", ctx);
            } else {
                throw ctx.exception("Object must have either the keys 'r', 'g', 'b' or 'red', 'green', 'blue'");
            }
        } else {
            throw ctx.exception("Invalid json type");
        }
    }

    private static double element(JsonElement element, ColorDeserializeContext ctx, String name, int limit) throws ColorDeserializeException {
        if (!element.isJsonPrimitive()) throw ctx.exception(name + " must be a number");
        if (!element.getAsJsonPrimitive().isNumber()) throw ctx.exception(name + " must be a number");
        double d = element.getAsJsonPrimitive().getAsDouble();
        if (d > limit || d < 0) throw ctx.exception(name + " not in range 0-" + limit);
        return d;
    }

    public static IColorProvider parseArray(JsonArray array, ColorDeserializeContext ctx, int offset) throws ColorDeserializeException {
        if (array.size() < 3 + offset)
            throw ctx.exception("RGB array must have at least " + (3 + offset) + " elements");
        double r = element(array.get(offset), ctx, "Red element", 255);
        double g = element(array.get(1 + offset), ctx, "Green element", 255);
        double b = element(array.get(2 + offset), ctx, "Blue element", 255);
        return new SolidColorProvider(ColorUtil.rgb(r / 255, g / 255, b / 255));
    }

    public static IColorProvider parseHex(String hex, ColorDeserializeContext ctx) throws ColorDeserializeException {
        Integer i = ColorUtil.parseHexString(hex);
        if (i == null) throw ctx.exception("Invalid hex code: '" + hex + "'");
        return new SolidColorProvider(i);
    }

    public static IColorProvider parseNumber(double num, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (num < 0 || num > 1) throw ctx.exception("Grayscale value not in range 0-1");
        return new SolidColorProvider(ColorUtil.grey(num));
    }

    public static IColorProvider parseBoolean(boolean bool) {
        return new SolidColorProvider(bool ? 0xffffff : 0);
    }

    public static IColorProvider parseObject(JsonObject object, String red, String grn, String blu, ColorDeserializeContext ctx) throws ColorDeserializeException {
        double r = element(object.get(red), ctx, "'" + red + "'", 1);
        double g = element(object.get(grn), ctx, "'" + grn + "'", 1);
        double b = element(object.get(blu), ctx, "'" + blu + "'", 1);
        return new SolidColorProvider(ColorUtil.rgb(r, g, b));
    }
}
