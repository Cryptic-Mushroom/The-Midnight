/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.misc.tags;

import midnight.common.Midnight;
import midnight.core.util.MnObjects;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public final class MnBlockTags {
    public static final ITag.INamedTag<Block> DEAD_WOOD_LOGS = tag("dead_wood_logs");
    public static final ITag.INamedTag<Block> SHADOWROOT_LOGS = tag("shadowroot_logs");
    public static final ITag.INamedTag<Block> DARK_WILLOW_LOGS = tag("dark_willow_logs");
    public static final ITag.INamedTag<Block> SHROOM_STEMS = tag("shroom_stems");
    public static final ITag.INamedTag<Block> SHROOM_CAPS = tag("shroom_caps");
    public static final ITag.INamedTag<Block> SHROOM_SHELVES = tag("shroom_shelves");
    public static final ITag.INamedTag<Block> SHROOM_PLANKS = tag("shroom_planks");
    public static final ITag.INamedTag<Block> SHROOM_ROOTS = tag("shroom_planks");
    public static final ITag.INamedTag<Block> SHROOMS = tag("shrooms");
    public static final ITag.INamedTag<Block> SMALL_SHROOMS = tag("small_shrooms");
    public static final ITag.INamedTag<Block> TALL_SHROOMS = tag("tall_shrooms");
    public static final ITag.INamedTag<Block> GLOB_STEMS = tag("glob_stems");
    public static final ITag.INamedTag<Block> CRYSTALOTUS_GROWABLE = tag("crystalotus_growable");
    public static final ITag.INamedTag<Block> ORES = tag("ores");
    public static final ITag.INamedTag<Block> MINERAL_BLOCKS = tag("mineral_blocks");
    public static final ITag.INamedTag<Block> SOILS = tag("soils");
    public static final ITag.INamedTag<Block> WET_SOILS = tag("wet_soils");
    public static final ITag.INamedTag<Block> SMALL_PLANTS = tag("small_plants");
    public static final ITag.INamedTag<Block> TALL_PLANTS = tag("tall_plants");
    public static final ITag.INamedTag<Block> PLANTS = tag("plants");
    public static final ITag.INamedTag<Block> NIGHT_REED_GROWABLE = tag("night_reed_growable");

    private static ITag.INamedTag<Block> tag(String id) {
        id = Midnight.resStr(id);
        ITag.INamedTag<Block> tag = BlockTags.makeWrapperTag(id);
        MnObjects.addBlockTag(id, tag);
        return tag;
    }

    private MnBlockTags() {
    }
}
