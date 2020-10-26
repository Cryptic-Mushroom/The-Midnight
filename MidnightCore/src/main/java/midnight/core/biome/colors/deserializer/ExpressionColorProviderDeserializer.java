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
import midnight.core.biome.colors.expression.DefaultFunctions;
import midnight.core.biome.colors.expression.Expression;
import midnight.core.biome.colors.expression.ExpressionParser;
import midnight.core.biome.colors.expression.ExpressionSyntaxException;
import midnight.core.biome.colors.provider.ExpressionColorProvider;

import java.util.HashMap;
import java.util.Map;

public class ExpressionColorProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize(JsonElement element, ColorDeserializeContext ctx) throws ColorDeserializeException {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has("expression"))
                throw ctx.exception("Missing required 'expression'");
            if (!obj.get("expression").isJsonPrimitive())
                throw ctx.exception("'expression' must be a string");
            if (!obj.getAsJsonPrimitive("expression").isString())
                throw ctx.exception("'expression' must be a string");

            String expr = obj.get("expression").getAsString();
            ExpressionParser parser = makeParser(expr);
            Expression expression;
            try {
                expression = parser.parse().threadLocal();
            } catch (ExpressionSyntaxException exc) {
                throw ctx.exception("Failed to parse expression:" + exc.getMessage(), exc);
            }

            HashMap<String, IColorProvider> inputs = new HashMap<>();

            if (obj.has("inputs") && obj.get("inputs").isJsonObject()) {
                JsonObject inputsObj = obj.getAsJsonObject("inputs");
                for (Map.Entry<String, JsonElement> entry : inputsObj.entrySet()) {
                    if (entry.getKey().equals("x")) {
                        throw ctx.exception("Input name 'x' is reserved");
                    }
                    if (entry.getKey().equals("y")) {
                        throw ctx.exception("Input name 'y' is reserved");
                    }
                    if (entry.getKey().equals("z")) {
                        throw ctx.exception("Input name 'z' is reserved");
                    }

                    inputs.put(entry.getKey(), ctx.silentDeserialize(entry.getValue(), entry.getKey()));
                }
            }

            HashMap<Integer, IColorProvider> inputIDs = new HashMap<>();
            int size = 0;

            for (Map.Entry<String, IColorProvider> entry : inputs.entrySet()) {
                int i = expression.indexOfVariable(entry.getKey());
                if (i > size) size = i;
                inputIDs.put(i, entry.getValue());
            }

            IColorProvider[] providers = new IColorProvider[size];

            for (Map.Entry<Integer, IColorProvider> entry : inputIDs.entrySet()) {
                providers[entry.getKey()] = entry.getValue();
            }

            return new ExpressionColorProvider(expression, providers);
        } else {
            throw ctx.exception("Expected an object");
        }
    }

    private static ExpressionParser makeParser(String expr) {
        ExpressionParser parser = new ExpressionParser(expr);
        DefaultFunctions.addDefaults(parser);
        return parser;
    }
}
