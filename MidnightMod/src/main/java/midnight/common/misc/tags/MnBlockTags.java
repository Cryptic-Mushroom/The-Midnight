/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.misc.tags;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public final class MnBlockTags {
    public static final ITag.INamedTag<Block> DEAD_WOOD_LOGS = BlockTags.makeWrapperTag("midnight:dead_wood_logs");
    public static final ITag.INamedTag<Block> SHADOWROOT_LOGS = BlockTags.makeWrapperTag("midnight:shadowroot_logs");
    public static final ITag.INamedTag<Block> DARK_WILLOW_LOGS = BlockTags.makeWrapperTag("midnight:dark_willow_logs");
    public static final ITag.INamedTag<Block> SHROOM_STEMS = BlockTags.makeWrapperTag("midnight:shroom_stems");
    public static final ITag.INamedTag<Block> SHROOM_CAPS = BlockTags.makeWrapperTag("midnight:shroom_caps");
    public static final ITag.INamedTag<Block> SHROOM_SHELVES = BlockTags.makeWrapperTag("midnight:shroom_shelves");
    public static final ITag.INamedTag<Block> SHROOM_PLANKS = BlockTags.makeWrapperTag("midnight:shroom_planks");
    public static final ITag.INamedTag<Block> SHROOM_ROOTS = BlockTags.makeWrapperTag("midnight:shroom_planks");
    public static final ITag.INamedTag<Block> SHROOMS = BlockTags.makeWrapperTag("midnight:shrooms");
    public static final ITag.INamedTag<Block> SMALL_SHROOMS = BlockTags.makeWrapperTag("midnight:small_shrooms");
    public static final ITag.INamedTag<Block> TALL_SHROOMS = BlockTags.makeWrapperTag("midnight:tall_shrooms");
    public static final ITag.INamedTag<Block> GLOB_STEMS = BlockTags.makeWrapperTag("midnight:glob_stems");
    public static final ITag.INamedTag<Block> CRYSTALOTUS_GROWABLE = BlockTags.makeWrapperTag("midnight:crystalotus_growable");
    public static final ITag.INamedTag<Block> ORES = BlockTags.makeWrapperTag("midnight:ores");
    public static final ITag.INamedTag<Block> MINERAL_BLOCKS = BlockTags.makeWrapperTag("midnight:mineral_blocks");
    public static final ITag.INamedTag<Block> SOILS = BlockTags.makeWrapperTag("midnight:soils");
    public static final ITag.INamedTag<Block> WET_SOILS = BlockTags.makeWrapperTag("midnight:wet_soils");
    public static final ITag.INamedTag<Block> SMALL_PLANTS = BlockTags.makeWrapperTag("midnight:small_plants");
    public static final ITag.INamedTag<Block> TALL_PLANTS = BlockTags.makeWrapperTag("midnight:tall_plants");
    public static final ITag.INamedTag<Block> PLANTS = BlockTags.makeWrapperTag("midnight:plants");
    public static final ITag.INamedTag<Block> NIGHT_REED_GROWABLE = BlockTags.makeWrapperTag("midnight:night_reed_growable");

    private MnBlockTags() {
    }
}
