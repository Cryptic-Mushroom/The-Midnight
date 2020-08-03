package midnight.data.blockstates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectorBlockstateGen implements IBlockstateGen {
    private final Map<String, ModelVariant[]> variants = new LinkedHashMap<>();

    private SelectorBlockstateGen() {
    }

    public SelectorBlockstateGen variant(String selector, ModelVariant... variants) {
        this.variants.put(selector, variants);
        return this;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SelectorBlockstateGen variant(BlockState state, ModelVariant... variants) {
        String selector = state.getProperties() // getProperties()
                               .stream()
                               .map(prop -> prop.getName() + "=" + ((Property) prop).getName(state.get(prop)))
                               .collect(Collectors.joining(","));
        this.variants.put(selector, variants);
        return this;
    }

    @Override
    public JsonElement makeJson(ResourceLocation id, Block block) {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, ModelVariant[]> variant : variants.entrySet()) {
            object.add(variant.getKey(), ModelVariant.makeJson(variant.getValue()));
        }
        JsonObject out = new JsonObject();
        out.add("variants", object);
        return out;
    }

    public static SelectorBlockstateGen create(ModelVariant... variants) {
        return new SelectorBlockstateGen().variant("", variants);
    }

    public static SelectorBlockstateGen create(String selector, ModelVariant... variants) {
        return new SelectorBlockstateGen().variant(selector, variants);
    }

    public static SelectorBlockstateGen create(BlockState selector, ModelVariant... variants) {
        return new SelectorBlockstateGen().variant(selector, variants);
    }
}
