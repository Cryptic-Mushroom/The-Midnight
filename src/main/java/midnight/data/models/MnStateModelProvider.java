package midnight.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import midnight.data.models.modelgen.ModelGen;
import midnight.data.models.stategen.StateGen;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MnStateModelProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder()
                                         .setPrettyPrinting()
                                         .disableHtmlEscaping()
                                         .create();

    private final DataGenerator datagen;

    private final Map<Block, StateGen> blockStateData = new HashMap<>();
    private final Map<Item, ModelGen> itemModelData = new HashMap<>();
    private final Map<String, ModelGen> blockModelData = new HashMap<>();

    public MnStateModelProvider(DataGenerator datagen) {
        this.datagen = datagen;
    }

    @Override
    public void run(DataCache cache) {
        blockStateData.clear();
        blockModelData.clear();
        itemModelData.clear();

        BlockStateTable.registerBlockStates((block, stategen) -> {
            blockStateData.put(block, stategen);
            stategen.getModels(blockModelData::put);
        });
        ItemModelTable.registerItemModels(itemModelData::put);

        Path path = datagen.getOutput();
        blockStateData.forEach((block, state) -> {
            Identifier id = Registry.BLOCK.getId(block);
            assert id != null;

            Path out = getPath(path, id, "blockstates");

            try {
                DataProvider.writeToPath(GSON, cache, state.makeJson(id, block), out);
            } catch (IOException exc) {
                LOGGER.error("Couldn't save blockstate {}", out, exc);
            }
        });

        itemModelData.forEach((item, model) -> {
            Identifier id = Registry.ITEM.getId(item);
            assert id != null;

            Path out = getPath(path, id, "models/item");

            try {
                DataProvider.writeToPath(GSON, cache, model.makeJson(id), out);
            } catch (IOException exc) {
                LOGGER.error("Couldn't save item model {}", out);
            }
        });

        blockModelData.forEach((name, model) -> {
            Identifier id = new Identifier(name);

            Path out = getPath(path, id, "models");

            try {
                DataProvider.writeToPath(GSON, cache, model.makeJson(id), out);
            } catch (IOException exc) {
                LOGGER.error("Couldn't save block model {}", out);
            }
        });
    }

    @Override
    public String getName() {
        return "Midnight/StatesModels";
    }

    private static Path getPath(Path path, Identifier id, String folder) {
        return path.resolve(String.format("assets/%s/%s/%s.json", id.getNamespace(), folder, id.getPath()));
    }
}
