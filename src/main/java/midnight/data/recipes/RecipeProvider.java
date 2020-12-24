package midnight.data.recipes;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.EnterBlockCriterion;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class RecipeProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator root;

    public RecipeProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) {
        Path path = this.root.getOutput();
        Set<Identifier> set = Sets.newHashSet();
        generate(provider -> {
            if (!set.add(provider.getRecipeId())) {
                throw new IllegalStateException("Duplicate recipe " + provider.getRecipeId());
            } else {
                saveRecipe(
                    cache, provider.toJson(),
                    path.resolve(
                        "data/" + provider.getRecipeId().getNamespace() +
                            "/recipes/" + provider.getRecipeId().getPath() + ".json"
                    )
                );
                JsonObject advJson = provider.toAdvancementJson();
                if (advJson != null) {
                    saveRecipeAdvancement(
                        cache, advJson,
                        path.resolve(
                            "data/" + provider.getRecipeId().getNamespace() +
                                "/advancements/" + provider.getAdvancementId().getPath() + ".json"
                        )
                    );
                }
            }
        });
        saveRecipeAdvancement(
            cache,
            Advancement.Task.create()
                            .criterion("impossible", new ImpossibleCriterion.Conditions())
                            .toJson(),
            path.resolve("data/minecraft/advancements/recipes/root.json")
        );
    }

    private static void saveRecipe(DataCache cache, JsonObject json, Path path) {
        try {
            String jsonTxt = GSON.toJson(json);
            String hash = SHA1.hashUnencodedChars(jsonTxt).toString();
            if (!Objects.equals(cache.getOldSha1(path), hash) || !Files.exists(path)) {
                Files.createDirectories(path.getParent());
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(jsonTxt);
                }
            }

            cache.updateSha1(path, hash);
        } catch (IOException exc) {
            LOGGER.error("Couldn't save recipe {}", path, exc);
        }

    }

    private static void saveRecipeAdvancement(DataCache dataCache, JsonObject jsonObject, Path path) {
        try {
            String jsonTxt = GSON.toJson(jsonObject);
            String hash = SHA1.hashUnencodedChars(jsonTxt).toString();
            if (!Objects.equals(dataCache.getOldSha1(path), hash) || !Files.exists(path)) {
                Files.createDirectories(path.getParent());
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(jsonTxt);
                }
            }

            dataCache.updateSha1(path, hash);
        } catch (IOException exc) {
            LOGGER.error("Couldn't save recipe advancement {}", path, exc);
        }

    }

    protected abstract void generate(Consumer<RecipeJsonProvider> consumer);

    protected static EnterBlockCriterion.Conditions inFluid(Block block) {
        return new EnterBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, block, StatePredicate.ANY);
    }

    protected static InventoryChangedCriterion.Conditions hasItem(ItemConvertible itemConvertible) {
        return hasItems(ItemPredicate.Builder.create().item(itemConvertible).build());
    }

    protected static InventoryChangedCriterion.Conditions hasItems(Tag<Item> tag) {
        return hasItems(ItemPredicate.Builder.create().tag(tag).build());
    }

    protected static InventoryChangedCriterion.Conditions hasItems(ItemPredicate... itemPredicates) {
        return new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, itemPredicates);
    }
}
