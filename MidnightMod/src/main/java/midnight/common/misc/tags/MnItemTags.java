/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.misc.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class MnItemTags {
    public static final ITag.INamedTag<Item> DEAD_WOOD_LOGS = ItemTags.makeWrapperTag("midnight:dead_wood_logs");
    public static final ITag.INamedTag<Item> SHADOWROOT_LOGS = ItemTags.makeWrapperTag("midnight:shadowroot_logs");
    public static final ITag.INamedTag<Item> DARK_WILLOW_LOGS = ItemTags.makeWrapperTag("midnight:dark_willow_logs");
    public static final ITag.INamedTag<Item> SHROOM_STEMS = ItemTags.makeWrapperTag("midnight:shroom_stems");
    public static final ITag.INamedTag<Item> SHROOM_CAPS = ItemTags.makeWrapperTag("midnight:shroom_caps");
    public static final ITag.INamedTag<Item> SHROOM_SHELVES = ItemTags.makeWrapperTag("midnight:shroom_shelves");
    public static final ITag.INamedTag<Item> SHROOM_PLANKS = ItemTags.makeWrapperTag("midnight:shroom_planks");
    public static final ITag.INamedTag<Item> SHROOM_ROOTS = ItemTags.makeWrapperTag("midnight:shroom_planks");
    public static final ITag.INamedTag<Item> SHROOMS = ItemTags.makeWrapperTag("midnight:shrooms");
    public static final ITag.INamedTag<Item> SMALL_SHROOMS = ItemTags.makeWrapperTag("midnight:small_shrooms");
    public static final ITag.INamedTag<Item> TALL_SHROOMS = ItemTags.makeWrapperTag("midnight:tall_shrooms");
    public static final ITag.INamedTag<Item> GLOB_STEMS = ItemTags.makeWrapperTag("midnight:glob_stems");
    public static final ITag.INamedTag<Item> ORES = ItemTags.makeWrapperTag("midnight:ores");
    public static final ITag.INamedTag<Item> MINERAL_BLOCKS = ItemTags.makeWrapperTag("midnight:mineral_blocks");
    public static final ITag.INamedTag<Item> MINERALS = ItemTags.makeWrapperTag("midnight:minerals");
    public static final ITag.INamedTag<Item> SOILS = ItemTags.makeWrapperTag("midnight:soils");
    public static final ITag.INamedTag<Item> WET_SOILS = ItemTags.makeWrapperTag("midnight:wet_soils");
    public static final ITag.INamedTag<Item> SMALL_PLANTS = ItemTags.makeWrapperTag("midnight:small_plants");
    public static final ITag.INamedTag<Item> TALL_PLANTS = ItemTags.makeWrapperTag("midnight:tall_plants");
    public static final ITag.INamedTag<Item> PLANTS = ItemTags.makeWrapperTag("midnight:plants");

    private MnItemTags() {
    }
}
