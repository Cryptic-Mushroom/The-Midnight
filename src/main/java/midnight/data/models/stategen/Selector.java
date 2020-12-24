package midnight.data.models.stategen;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.state.property.Property;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Selector {
    private final boolean or;
    private final Map<String, String> conditions = Maps.newHashMap();

    private Selector(boolean or) {
        this.or = or;
    }

    public Selector condition(String property, String values) {
        conditions.put(property, values);
        return this;
    }

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

    public JsonObject getJson() {
        JsonObject selector = getSelectorJson();
        if (or) {
            JsonObject out = new JsonObject();
            out.add("OR", selector);
        }
        return selector;
    }

    public static Selector or() {
        return new Selector(true);
    }

    public static Selector and() {
        return new Selector(false);
    }
}
