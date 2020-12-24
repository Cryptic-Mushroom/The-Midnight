/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.tags;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;
import java.util.function.Function;

/**
 * Generator for item tag JSONs. This can copy entries from block tags.
 */
public class MnItemTagsProvider extends AbstractTagProvider<Item> {
    private final Function<Tag.Identified<Block>, Tag.Builder> builderGetter;

    public MnItemTagsProvider(DataGenerator gen, MnBlockTagsProvider blockTags) {
        super(gen, Registry.ITEM);
        this.builderGetter = blockTags::getTagBuilder;
    }

    @Override
    protected void configure() {
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.LOGS, ItemTags.LOGS);

        //
        //
        //  REGISTER ITEM TAGS HERE
        //
        //
    }


    protected void copy(Tag.Identified<Block> blockTag, Tag.Identified<Item> itemTag) {
        Tag.Builder itemBuilder = method_27169(itemTag);
        Tag.Builder blockBuilder = builderGetter.apply(blockTag);
        blockBuilder.streamEntries().forEach(itemBuilder::add);
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight/ItemTags";
    }
}
