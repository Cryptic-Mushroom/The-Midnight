/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.data.tags;

import midnight.common.block.MnBlocks;
import midnight.common.misc.tags.MnBlockTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;

public class MnBlockTagsProvider extends TagsProvider<Block> {
    @SuppressWarnings("deprecation") // We need Registry.BLOCK. Sorry Forge...
    public MnBlockTagsProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }

    @Override
    protected void registerTags() {
        getOrCreateTagBuilder(Tags.Blocks.DIRT)
            .addTag(MnBlockTags.SOILS);

        getOrCreateTagBuilder(Tags.Blocks.ORES)
            .addTag(MnBlockTags.ORES);

        getOrCreateTagBuilder(Tags.Blocks.STORAGE_BLOCKS)
            .addTag(MnBlockTags.MINERAL_BLOCKS);



        getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE)
            .add(MnBlocks.NIGHTSTONE)
            .add(MnBlocks.TRENCHSTONE)
            .add(MnBlocks.NIGHT_MYCELIUM)
            .add(MnBlocks.STRANGE_SAND)
            .addTag(MnBlockTags.SOILS)
            .addTag(MnBlockTags.SMALL_PLANTS)
            .addTag(MnBlockTags.SHROOM_ROOTS);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
            .addTag(MnBlockTags.DARK_WILLOW_LOGS)
            .addTag(MnBlockTags.SHADOWROOT_LOGS)
            .addTag(MnBlockTags.DEAD_WOOD_LOGS);

        getOrCreateTagBuilder(BlockTags.LEAVES)
            .add(MnBlocks.DARK_WILLOW_LEAVES)
            .add(MnBlocks.SHADOWROOT_LEAVES);

        getOrCreateTagBuilder(BlockTags.PLANKS)
            .add(MnBlocks.DEAD_WOOD_PLANKS)
            .add(MnBlocks.SHADOWROOT_PLANKS)
            .add(MnBlocks.DARK_WILLOW_PLANKS)
            .addTag(MnBlockTags.SHROOM_PLANKS);



        getOrCreateTagBuilder(MnBlockTags.ORES)
            .add(MnBlocks.DARK_PEARL_ORE);

        getOrCreateTagBuilder(MnBlockTags.MINERAL_BLOCKS)
            .add(MnBlocks.DARK_PEARL_BLOCK);

        getOrCreateTagBuilder(MnBlockTags.SOILS)
            .add(MnBlocks.NIGHT_DIRT)
            .add(MnBlocks.COARSE_NIGHT_DIRT)
            .add(MnBlocks.NIGHT_GRASS_BLOCK)
            .addTag(MnBlockTags.WET_SOILS);

        getOrCreateTagBuilder(MnBlockTags.WET_SOILS)
            .add(MnBlocks.DECEITFUL_MUD)
            .add(MnBlocks.DECEITFUL_PEAT);

        getOrCreateTagBuilder(MnBlockTags.CRYSTALOTUS_GROWABLE)
            .add(MnBlocks.NIGHTSTONE)
            .add(MnBlocks.TRENCHSTONE)
            .add(MnBlocks.NIGHT_MYCELIUM)
            .addTag(MnBlockTags.ORES)
            .addTag(MnBlockTags.SOILS);

        getOrCreateTagBuilder(MnBlockTags.DARK_WILLOW_LOGS)
            .add(MnBlocks.DARK_WILLOW_LOG)
            .add(MnBlocks.DARK_WILLOW_WOOD)
            .add(MnBlocks.STRIPPED_DARK_WILLOW_LOG)
            .add(MnBlocks.STRIPPED_DARK_WILLOW_WOOD);

        getOrCreateTagBuilder(MnBlockTags.SHADOWROOT_LOGS)
            .add(MnBlocks.SHADOWROOT_LOG)
            .add(MnBlocks.SHADOWROOT_WOOD)
            .add(MnBlocks.STRIPPED_SHADOWROOT_LOG)
            .add(MnBlocks.STRIPPED_SHADOWROOT_WOOD);

        getOrCreateTagBuilder(MnBlockTags.DEAD_WOOD_LOGS)
            .add(MnBlocks.DEAD_WOOD_LOG)
            .add(MnBlocks.DEAD_WOOD)
            .add(MnBlocks.STRIPPED_DEAD_WOOD_LOG)
            .add(MnBlocks.STRIPPED_DEAD_WOOD);

        getOrCreateTagBuilder(MnBlockTags.GLOB_STEMS)
            .add(MnBlocks.GLOB_FUNGUS_STEM)
            .add(MnBlocks.GLOB_FUNGUS_HYPHAE)
            .add(MnBlocks.INFESTED_GLOB_FUNGUS_STEM);

        getOrCreateTagBuilder(MnBlockTags.SHROOM_STEMS)
            .add(MnBlocks.NIGHTSHROOM_STEM)
            .add(MnBlocks.DEWSHROOM_STEM)
            .add(MnBlocks.VIRIDSHROOM_STEM)
            .add(MnBlocks.BOGSHROOM_STEM)
            .add(MnBlocks.MOONSHROOM_STEM);

        getOrCreateTagBuilder(MnBlockTags.SHROOM_CAPS)
            .add(MnBlocks.NIGHTSHROOM_CAP)
            .add(MnBlocks.DEWSHROOM_CAP)
            .add(MnBlocks.VIRIDSHROOM_CAP)
            .add(MnBlocks.BOGSHROOM_CAP)
            .add(MnBlocks.MOONSHROOM_CAP);

        getOrCreateTagBuilder(MnBlockTags.SHROOM_SHELVES)
            .add(MnBlocks.NIGHTSHROOM_SHELF)
            .add(MnBlocks.DEWSHROOM_SHELF)
            .add(MnBlocks.VIRIDSHROOM_SHELF)
            .add(MnBlocks.BOGSHROOM_SHELF)
            .add(MnBlocks.MOONSHROOM_SHELF);

        getOrCreateTagBuilder(MnBlockTags.SHROOM_PLANKS)
            .add(MnBlocks.NIGHTSHROOM_PLANKS)
            .add(MnBlocks.DEWSHROOM_PLANKS)
            .add(MnBlocks.VIRIDSHROOM_PLANKS)
            .add(MnBlocks.BOGSHROOM_PLANKS)
            .add(MnBlocks.MOONSHROOM_PLANKS);

        getOrCreateTagBuilder(MnBlockTags.SHROOM_ROOTS)
            .add(MnBlocks.NIGHTSHROOM_ROOTS, MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS)
            .add(MnBlocks.DEWSHROOM_ROOTS, MnBlocks.FLOWERING_DEWSHROOM_ROOTS)
            .add(MnBlocks.VIRIDSHROOM_ROOTS, MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS)
            .add(MnBlocks.MOONSHROOM_ROOTS, MnBlocks.FLOWERING_MOONSHROOM_ROOTS);

        getOrCreateTagBuilder(MnBlockTags.SHROOMS)
            .addTag(MnBlockTags.TALL_SHROOMS)
            .addTag(MnBlockTags.SMALL_SHROOMS);

        getOrCreateTagBuilder(MnBlockTags.TALL_SHROOMS)
            .add(MnBlocks.TALL_NIGHTSHROOM)
            .add(MnBlocks.TALL_DEWSHROOM)
            .add(MnBlocks.TALL_VIRIDSHROOM)
            .add(MnBlocks.TALL_BOGSHROOM)
            .add(MnBlocks.TALL_MOONSHROOM)
            .add(MnBlocks.TALL_MISTSHROOM);

        getOrCreateTagBuilder(MnBlockTags.SMALL_SHROOMS)
            .add(MnBlocks.NIGHTSHROOM)
            .add(MnBlocks.DEWSHROOM)
            .add(MnBlocks.VIRIDSHROOM)
            .add(MnBlocks.BOGSHROOM)
            .add(MnBlocks.MOONSHROOM)
            .add(MnBlocks.MISTSHROOM);

        getOrCreateTagBuilder(MnBlockTags.SMALL_PLANTS)
            .add(MnBlocks.BOGWEED)
            .add(MnBlocks.VIOLEAF)
            .add(MnBlocks.FINGERED_GRASS)
            .add(MnBlocks.LUMEN_BUD)
            .add(MnBlocks.RUNEBUSH)
            .add(MnBlocks.GHOST_PLANT)
            .add(MnBlocks.TENDRILWEED)
            .add(MnBlocks.GLOB_FUNGUS)
            .addTag(MnBlockTags.SMALL_SHROOMS);

        getOrCreateTagBuilder(MnBlockTags.TALL_PLANTS)
            .add(MnBlocks.TALL_LUMEN_BUD)
            .addTag(MnBlockTags.TALL_SHROOMS);

        getOrCreateTagBuilder(MnBlockTags.PLANTS)
            .addTag(MnBlockTags.TALL_PLANTS)
            .addTag(MnBlockTags.SMALL_PLANTS);
    }

    protected ITag.Builder getBuilder(ITag.INamedTag<Block> namedTag) {
        return tagToBuilder.computeIfAbsent(namedTag.getId(), id -> new ITag.Builder());
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight - Block tags";
    }
}
