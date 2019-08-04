package com.mushroom.midnight.common.data.loot;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.ValidationResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class MidnightLootTableProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final LootParameterSet lootSet;

    public MidnightLootTableProvider(DataGenerator generator, LootParameterSet lootSet) {
        this.generator = generator;
        this.lootSet = lootSet;
    }

    protected abstract void addTables(LootConsumer consumer);

    @Override
    public void act(DirectoryCache cache) {
        Map<ResourceLocation, LootTable> tables = this.buildTables();
        ValidationResults validation = this.registerTables(tables);

        Multimap<String, String> problems = validation.getProblems();
        if (!problems.isEmpty()) {
            problems.forEach((path, problem) -> LOGGER.warn("Found validation problem in " + path + ": " + problem));
            throw new IllegalStateException("Failed to validate loot tables");
        }

        this.writeTables(cache, tables);
    }

    private Map<ResourceLocation, LootTable> buildTables() {
        Map<ResourceLocation, LootTable> tables = new HashMap<>();

        this.addTables((block, builder) -> {
            ResourceLocation identifier = block.getLootTable();
            LootTable table = builder.setParameterSet(this.lootSet).build();
            if (tables.put(identifier, table) != null) {
                throw new IllegalStateException("Duplicate loot table " + identifier);
            }
        });

        return tables;
    }

    private ValidationResults registerTables(Map<ResourceLocation, LootTable> tables) {
        ValidationResults validation = new ValidationResults();
        tables.forEach((identifier, lootTable) -> {
            LootTableManager.func_215302_a(validation, identifier, lootTable, tables::get);
        });
        return validation;
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
        return "Midnight Loot Tables";
    }
}
