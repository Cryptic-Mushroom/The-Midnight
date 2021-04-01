/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.misc.tags;

import midnight.common.Midnight;
import midnight.core.util.MnObjects;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class MnItemTags {
    public static final ITag.INamedTag<Item> DEAD_WOOD_LOGS = tag("dead_wood_logs");
    public static final ITag.INamedTag<Item> SHADOWROOT_LOGS = tag("shadowroot_logs");
    public static final ITag.INamedTag<Item> DARK_WILLOW_LOGS = tag("dark_willow_logs");
    public static final ITag.INamedTag<Item> SHROOM_STEMS = tag("shroom_stems");
    public static final ITag.INamedTag<Item> SHROOM_CAPS = tag("shroom_caps");
    public static final ITag.INamedTag<Item> SHROOM_SHELVES = tag("shroom_shelves");
    public static final ITag.INamedTag<Item> SHROOM_PLANKS = tag("shroom_planks");
    public static final ITag.INamedTag<Item> SHROOM_ROOTS = tag("shroom_planks");
    public static final ITag.INamedTag<Item> SHROOMS = tag("shrooms");
    public static final ITag.INamedTag<Item> SMALL_SHROOMS = tag("small_shrooms");
    public static final ITag.INamedTag<Item> TALL_SHROOMS = tag("tall_shrooms");
    public static final ITag.INamedTag<Item> GLOB_STEMS = tag("glob_stems");
    public static final ITag.INamedTag<Item> ORES = tag("ores");
    public static final ITag.INamedTag<Item> MINERAL_BLOCKS = tag("mineral_blocks");
    public static final ITag.INamedTag<Item> MINERALS = tag("minerals");
    public static final ITag.INamedTag<Item> SOILS = tag("soils");
    public static final ITag.INamedTag<Item> WET_SOILS = tag("wet_soils");
    public static final ITag.INamedTag<Item> SMALL_PLANTS = tag("small_plants");
    public static final ITag.INamedTag<Item> TALL_PLANTS = tag("tall_plants");
    public static final ITag.INamedTag<Item> PLANTS = tag("plants");

    private static ITag.INamedTag<Item> tag(String id) {
        id = Midnight.idStr(id);
        ITag.INamedTag<Item> tag = ItemTags.bind(id);
        MnObjects.addItemTag(id, tag);
        return tag;
    }

    private MnItemTags() {
    }
}
