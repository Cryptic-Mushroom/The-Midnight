/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
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
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;

public class MnBlockTagsProvider extends TagsProvider<Block> {
    @SuppressWarnings("deprecation") // We need Registry.BLOCK. Sorry Forge...
    public MnBlockTagsProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Registry.BLOCK, "midnight", helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Blocks.DIRT)
            .addTag(MnBlockTags.SOILS);

        tag(Tags.Blocks.ORES)
            .addTag(MnBlockTags.ORES);

        tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTag(MnBlockTags.MINERAL_BLOCKS);



        tag(BlockTags.ENDERMAN_HOLDABLE)
            .add(MnBlocks.NIGHTSTONE)
            .add(MnBlocks.NIGHT_MYCELIUM)
            .add(MnBlocks.STRANGE_SAND)
            .addTag(MnBlockTags.SOILS)
            .addTag(MnBlockTags.SMALL_PLANTS)
            .addTag(MnBlockTags.SHROOM_ROOTS);

        tag(BlockTags.LOGS_THAT_BURN)
            .addTag(MnBlockTags.DARK_WILLOW_LOGS)
            .addTag(MnBlockTags.SHADOWROOT_LOGS)
            .addTag(MnBlockTags.DEAD_WOOD_LOGS);

        tag(BlockTags.LEAVES)
            .add(MnBlocks.DARK_WILLOW_LEAVES)
            .add(MnBlocks.SHADOWROOT_LEAVES);

        tag(BlockTags.PLANKS)
            .add(MnBlocks.DEAD_WOOD_PLANKS)
            .add(MnBlocks.SHADOWROOT_PLANKS)
            .add(MnBlocks.DARK_WILLOW_PLANKS)
            .addTag(MnBlockTags.SHROOM_PLANKS);



        tag(MnBlockTags.ORES)
            .add(MnBlocks.DARK_PEARL_ORE)
            .add(MnBlocks.TENEBRUM_ORE)
            .add(MnBlocks.NAGRILITE_ORE)
            .add(MnBlocks.EBONITE_ORE)
            .add(MnBlocks.VIRILUX_ORE);

        tag(MnBlockTags.MINERAL_BLOCKS)
            .add(MnBlocks.DARK_PEARL_BLOCK)
            .add(MnBlocks.TENEBRUM_BLOCK)
            .add(MnBlocks.NAGRILITE_BLOCK)
            .add(MnBlocks.EBONITE_BLOCK)
            .add(MnBlocks.VIRILUX_BLOCK);

        tag(MnBlockTags.SOILS)
            .add(MnBlocks.NIGHT_DIRT)
            .add(MnBlocks.COARSE_NIGHT_DIRT)
            .add(MnBlocks.NIGHT_GRASS_BLOCK)
            .addTag(MnBlockTags.WET_SOILS);

        tag(MnBlockTags.WET_SOILS)
            .add(MnBlocks.DECEITFUL_MUD)
            .add(MnBlocks.DECEITFUL_PEAT);

        tag(MnBlockTags.CRYSTALOTUS_GROWABLE)
            .add(MnBlocks.NIGHTSTONE)
            .add(MnBlocks.TRENCHSTONE)
            .add(MnBlocks.NIGHT_MYCELIUM)
            .add(MnBlocks.ROUXE_ROCK)
            .add(MnBlocks.BLOOMCRYSTAL_ROCK)
            .add(MnBlocks.NIGHTSTONE_BRICKS)
            .add(MnBlocks.TRENCHSTONE_BRICKS)
            .addTag(MnBlockTags.ORES)
            .addTag(MnBlockTags.SOILS);

        tag(MnBlockTags.NIGHT_REED_GROWABLE)
            .add(MnBlocks.STRANGE_SAND)
            .addTag(MnBlockTags.SOILS);

        tag(MnBlockTags.DARK_WILLOW_LOGS)
            .add(MnBlocks.DARK_WILLOW_LOG)
            .add(MnBlocks.DARK_WILLOW_WOOD)
            .add(MnBlocks.STRIPPED_DARK_WILLOW_LOG)
            .add(MnBlocks.STRIPPED_DARK_WILLOW_WOOD);

        tag(MnBlockTags.SHADOWROOT_LOGS)
            .add(MnBlocks.SHADOWROOT_LOG)
            .add(MnBlocks.SHADOWROOT_WOOD)
            .add(MnBlocks.STRIPPED_SHADOWROOT_LOG)
            .add(MnBlocks.STRIPPED_SHADOWROOT_WOOD);

        tag(MnBlockTags.DEAD_WOOD_LOGS)
            .add(MnBlocks.DEAD_WOOD_LOG)
            .add(MnBlocks.DEAD_WOOD)
            .add(MnBlocks.STRIPPED_DEAD_WOOD_LOG)
            .add(MnBlocks.STRIPPED_DEAD_WOOD);

        tag(MnBlockTags.GLOB_STEMS)
            .add(MnBlocks.GLOB_FUNGUS_STEM)
            .add(MnBlocks.GLOB_FUNGUS_HYPHAE)
            .add(MnBlocks.INFESTED_GLOB_FUNGUS_STEM);

        tag(MnBlockTags.SHROOM_STEMS)
            .add(MnBlocks.NIGHTSHROOM_STEM)
            .add(MnBlocks.DEWSHROOM_STEM)
            .add(MnBlocks.VIRIDSHROOM_STEM)
            .add(MnBlocks.BOGSHROOM_STEM)
            .add(MnBlocks.MOONSHROOM_STEM);

        tag(MnBlockTags.SHROOM_CAPS)
            .add(MnBlocks.NIGHTSHROOM_CAP)
            .add(MnBlocks.DEWSHROOM_CAP)
            .add(MnBlocks.VIRIDSHROOM_CAP)
            .add(MnBlocks.BOGSHROOM_CAP)
            .add(MnBlocks.MOONSHROOM_CAP);

        tag(MnBlockTags.SHROOM_SHELVES)
            .add(MnBlocks.NIGHTSHROOM_SHELF)
            .add(MnBlocks.DEWSHROOM_SHELF)
            .add(MnBlocks.VIRIDSHROOM_SHELF)
            .add(MnBlocks.BOGSHROOM_SHELF)
            .add(MnBlocks.MOONSHROOM_SHELF);

        tag(MnBlockTags.SHROOM_PLANKS)
            .add(MnBlocks.NIGHTSHROOM_PLANKS)
            .add(MnBlocks.DEWSHROOM_PLANKS)
            .add(MnBlocks.VIRIDSHROOM_PLANKS)
            .add(MnBlocks.BOGSHROOM_PLANKS)
            .add(MnBlocks.MOONSHROOM_PLANKS);

        tag(MnBlockTags.SHROOM_ROOTS)
            .add(MnBlocks.NIGHTSHROOM_ROOTS, MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS)
            .add(MnBlocks.DEWSHROOM_ROOTS, MnBlocks.FLOWERING_DEWSHROOM_ROOTS)
            .add(MnBlocks.VIRIDSHROOM_ROOTS, MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS)
            .add(MnBlocks.MOONSHROOM_ROOTS, MnBlocks.FLOWERING_MOONSHROOM_ROOTS);

        tag(MnBlockTags.SHROOMS)
            .addTag(MnBlockTags.TALL_SHROOMS)
            .addTag(MnBlockTags.SMALL_SHROOMS);

        tag(MnBlockTags.TALL_SHROOMS)
            .add(MnBlocks.TALL_NIGHTSHROOM)
            .add(MnBlocks.TALL_DEWSHROOM)
            .add(MnBlocks.TALL_VIRIDSHROOM)
            .add(MnBlocks.TALL_BOGSHROOM)
            .add(MnBlocks.TALL_MOONSHROOM)
            .add(MnBlocks.TALL_MISTSHROOM);

        tag(MnBlockTags.SMALL_SHROOMS)
            .add(MnBlocks.NIGHTSHROOM)
            .add(MnBlocks.DEWSHROOM)
            .add(MnBlocks.VIRIDSHROOM)
            .add(MnBlocks.BOGSHROOM)
            .add(MnBlocks.MOONSHROOM)
            .add(MnBlocks.MISTSHROOM);

        tag(MnBlockTags.SMALL_PLANTS)
            .add(MnBlocks.BOGWEED)
            .add(MnBlocks.VIOLEAF)
            .add(MnBlocks.FINGERED_GRASS)
            .add(MnBlocks.LUMEN_BUD)
            .add(MnBlocks.RUNEBUSH)
            .add(MnBlocks.GHOST_PLANT)
            .add(MnBlocks.TENDRILWEED)
            .add(MnBlocks.GLOB_FUNGUS)
            .addTag(MnBlockTags.SMALL_SHROOMS);

        tag(MnBlockTags.TALL_PLANTS)
            .add(MnBlocks.TALL_LUMEN_BUD)
            .addTag(MnBlockTags.TALL_SHROOMS);

        tag(MnBlockTags.PLANTS)
            .addTag(MnBlockTags.TALL_PLANTS)
            .addTag(MnBlockTags.SMALL_PLANTS);
    }

    protected ITag.Builder getBuilder(ITag.INamedTag<Block> namedTag) {
        return builders.computeIfAbsent(namedTag.getName(), id -> new ITag.Builder());
    }

    @Override
    protected Path getPath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight - Block tags";
    }
}
