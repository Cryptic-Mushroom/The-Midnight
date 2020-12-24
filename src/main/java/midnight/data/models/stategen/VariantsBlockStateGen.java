package midnight.data.models.stategen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import midnight.data.models.modelgen.ModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class VariantsBlockStateGen implements StateGen {
    private final Map<String, ModelInfo[]> variants = new HashMap<>();

    @Override
    public JsonElement makeJson(Identifier id, Block block) {
        JsonObject root = new JsonObject();

        JsonObject variants = new JsonObject();
        for (Map.Entry<String, ModelInfo[]> variant : this.variants.entrySet()) {
            JsonElement variantJson = ModelInfo.makeJson(variant.getValue());
            variants.add(variant.getKey(), variantJson);
        }
        root.add("variants", variants);
        return root;
    }

    @Override
    public void getModels(BiConsumer<String, ModelGen> consumer) {
        for (ModelInfo[] infos : variants.values()) {
            for (ModelInfo info : infos) {
                info.getModels(consumer);
            }
        }
    }

    public VariantsBlockStateGen variant(String variant, ModelInfo... models) {
        variants.put(variant, models.clone());
        return this;
    }

    public static VariantsBlockStateGen variants(String variant, ModelInfo... models) {
        return new VariantsBlockStateGen().variant(variant, models);
    }

    public static VariantsBlockStateGen variants(ModelInfo... models) {
        return new VariantsBlockStateGen().variant("", models);
    }

    public static VariantsBlockStateGen variants() {
        return new VariantsBlockStateGen();
    }
}
