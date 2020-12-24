/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.stategen;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.state.property.Property;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A multipart block state selector.
 */
public class Selector {
    private final boolean or;
    private final Map<String, String> conditions = Maps.newHashMap();

    private Selector(boolean or) {
        this.or = or;
    }

    /**
     * Adds a property condition to this selector
     *
     * @param property The property name
     * @param values   The allowed values, separated by {@code |}
     */
    public Selector condition(String property, String values) {
        conditions.put(property, values);
        return this;
    }

    /**
     * Adds a property condition to this selector
     *
     * @param property The property
     * @param values   The allowed values
     */
    @SafeVarargs
    public final <T extends Comparable<T>> Selector condition(Property<T> property, T... values) {
        return condition(
            property.getName(),
            Stream.of(values)
                  .map(property::name)
                  .distinct()
                  .collect(Collectors.joining("|"))
        );
    }

    private JsonObject getSelectorJson() {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, String> condition : conditions.entrySet()) {
            object.addProperty(condition.getKey(), condition.getValue());
        }
        return object;
    }

    /**
     * Converts this selector to JSON format
     */
    public JsonObject getJson() {
        JsonObject selector = getSelectorJson();
        if (or) {
            JsonObject out = new JsonObject();
            out.add("OR", selector);
        }
        return selector;
    }

    /**
     * Creates an {@code OR}-selector: only one of the defined conditions has to pass in order to apply a model.
     */
    public static Selector or() {
        return new Selector(true);
    }

    /**
     * Creates an {@code AND}-selector: all the defined conditions have to pass in order to apply a model.
     */
    public static Selector and() {
        return new Selector(false);
    }
}
