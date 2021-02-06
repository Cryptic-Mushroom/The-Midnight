/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.data.tags;

import midnight.common.block.MnBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

/**
 * Generator for block tag JSONs.
 */
public class MnBlockTagsProvider extends AbstractTagProvider<Block> {
    public MnBlockTagsProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }

    @Override
    protected void configure() {
        getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE)
            .add(MnBlocks.NIGHTSTONE)
            .add(MnBlocks.NIGHT_MYCELIUM)
            .add(MnBlocks.STRANGE_SAND);
        // .addTag(MnBlockTags.SOILS)
        // .addTag(MnBlockTags.SMALL_PLANTS)
        // .addTag(MnBlockTags.SHROOM_ROOTS);
    }

    protected Tag.Builder getTagBuilder(Tag.Identified<Block> identified) {
        return super.method_27169(identified);
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight/BlockTags";
    }
}
