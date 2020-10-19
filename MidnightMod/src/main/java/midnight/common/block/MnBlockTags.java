/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public abstract class MnBlockTags {
    public static final ITag.INamedTag<Block> DEAD_WOOD_LOGS = BlockTags.makeWrapperTag("midnight:dead_wood_logs");
    public static final ITag.INamedTag<Block> SHADOWROOT_LOGS = BlockTags.makeWrapperTag("midnight:shadowroot_logs");
    public static final ITag.INamedTag<Block> DARK_WILLOW_LOGS = BlockTags.makeWrapperTag("midnight:dark_willow_logs");

    private MnBlockTags() {
    }
}
