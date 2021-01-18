/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.data.tags;

import midnight.common.item.MnItems;
import midnight.common.misc.tags.MnBlockTags;
import midnight.common.misc.tags.MnItemTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;
import java.util.function.Function;

public class MnItemTagsProvider extends TagsProvider<Item> {
    private final Function<ITag.INamedTag<Block>, ITag.Builder> builderGetter;

    @SuppressWarnings("deprecation") // We need Registry.ITEM. Sorry Forge...
    public MnItemTagsProvider(DataGenerator gen, MnBlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(gen, Registry.ITEM, "midnight", helper);
        this.builderGetter = blockTags::getBuilder;
    }

    @Override
    protected void registerTags() {
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

        copy(MnBlockTags.MINERAL_BLOCKS, MnItemTags.MINERAL_BLOCKS);
        copy(MnBlockTags.ORES, MnItemTags.ORES);
        copy(MnBlockTags.SHADOWROOT_LOGS, MnItemTags.SHADOWROOT_LOGS);
        copy(MnBlockTags.DARK_WILLOW_LOGS, MnItemTags.DARK_WILLOW_LOGS);
        copy(MnBlockTags.DEAD_WOOD_LOGS, MnItemTags.DEAD_WOOD_LOGS);
        copy(MnBlockTags.SHROOM_STEMS, MnItemTags.SHROOM_STEMS);
        copy(MnBlockTags.SHROOM_CAPS, MnItemTags.SHROOM_CAPS);
        copy(MnBlockTags.SHROOM_SHELVES, MnItemTags.SHROOM_SHELVES);
        copy(MnBlockTags.SHROOM_PLANKS, MnItemTags.SHROOM_PLANKS);
        copy(MnBlockTags.SHROOM_ROOTS, MnItemTags.SHROOM_ROOTS);
        copy(MnBlockTags.SHROOMS, MnItemTags.SHROOMS);
        copy(MnBlockTags.TALL_SHROOMS, MnItemTags.TALL_SHROOMS);
        copy(MnBlockTags.SMALL_SHROOMS, MnItemTags.SMALL_SHROOMS);
        copy(MnBlockTags.PLANTS, MnItemTags.PLANTS);
        copy(MnBlockTags.TALL_PLANTS, MnItemTags.TALL_PLANTS);
        copy(MnBlockTags.SMALL_PLANTS, MnItemTags.SMALL_PLANTS);
        copy(MnBlockTags.GLOB_STEMS, MnItemTags.GLOB_STEMS);
        copy(MnBlockTags.WET_SOILS, MnItemTags.WET_SOILS);
        copy(MnBlockTags.SOILS, MnItemTags.SOILS);

        getOrCreateTagBuilder(MnItemTags.MINERALS)
            .add(MnItems.DARK_PEARL)
            .add(MnItems.TENEBRUM_INGOT)
            .add(MnItems.TENEBRUM_NUGGET)
            .add(MnItems.NAGRILITE_INGOT)
            .add(MnItems.NAGRILITE_NUGGET)
            .add(MnItems.EBONITE)
            .add(MnItems.VIRILUX);
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
