/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.data.tags;

import midnight.common.block.MnBlockTags;
import midnight.common.item.MnItemTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;
import java.util.function.Function;

public class MnItemTagsProvider extends TagsProvider<Item> {
    private final Function<ITag.INamedTag<Block>, ITag.Builder> builderGetter;

    @SuppressWarnings("deprecation") // We need Registry.ITEM. Sorry Forge...
    public MnItemTagsProvider(DataGenerator gen, MnBlockTagsProvider blockTags) {
        super(gen, Registry.ITEM);
        this.builderGetter = blockTags::getBuilder;
    }

    @Override
    protected void registerTags() {
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(MnBlockTags.SHADOWROOT_LOGS, MnItemTags.SHADOWROOT_LOGS);
        copy(MnBlockTags.DARK_WILLOW_LOGS, MnItemTags.DARK_WILLOW_LOGS);
        copy(MnBlockTags.DEAD_WOOD_LOGS, MnItemTags.DEAD_WOOD_LOGS);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
    }

    protected ITag.Builder getBuilder(ITag.INamedTag<Item> namedTag) {
        return tagToBuilder.computeIfAbsent(namedTag.getId(), id -> new ITag.Builder());
    }

    /**
     * Many block tags exist as item tags too, and those item tags usually have the same values as the block tags. This
     * function copies all entries from a block tag builder into an item tag builder.
     *
     * @param blockTag The block tag to copy from
     * @param itemTag  The item tag to copy to
     * @since 0.6.0
     */
    protected void copy(ITag.INamedTag<Block> blockTag, ITag.INamedTag<Item> itemTag) {
        ITag.Builder itemBuilder = getBuilder(itemTag);
        ITag.Builder blockBuilder = builderGetter.apply(blockTag);
        blockBuilder.streamEntries().forEach(itemBuilder::add);
    }

    @Override
    public String getName() {
        return "Midnight - Item tags";
    }
}
