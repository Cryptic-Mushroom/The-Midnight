/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.loottables;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Parent of all loot table generators, which actually exports all generated loot tables as JSON files.
 */
public class MnLootTablesProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator root;
    private final List<Pair<Supplier<Consumer<BiConsumer<Identifier, LootTable.Builder>>>, LootContextType>> lootTypeGenerators;

    public MnLootTablesProvider(DataGenerator dataGenerator) {
        lootTypeGenerators = ImmutableList.of(
            Pair.of(MnBlockLootTables::new, LootContextTypes.BLOCK)
        );
        root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) {
        Path path = root.getOutput();
        Map<Identifier, LootTable> map = Maps.newHashMap();

        lootTypeGenerators.forEach(pair -> pair.getFirst().get().accept((id, builder) -> {
            if (map.put(id, builder.type(pair.getSecond()).build()) != null) {
                throw new IllegalStateException("Duplicate loot table " + id);
            }
        }));

        map.forEach((identifierx, lootTable) -> {
            Path output = getOutput(path, identifierx);

            try {
                DataProvider.writeToPath(GSON, cache, LootManager.toJson(lootTable), output);
            } catch (IOException exc) {
                LOGGER.error("Couldn't save loot table {}", output, exc);
            }
        });
    }

    private static Path getOutput(Path rootOutput, Identifier lootTableId) {
        return rootOutput.resolve("data/" + lootTableId.getNamespace() + "/loot_tables/" + lootTableId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight/LootTables";
    }
}
