/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
 */

package midnight.common.item;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public abstract class MnItemTags {
    public static final ITag.INamedTag<Item> DEAD_WOOD_LOGS = ItemTags.makeWrapperTag("midnight:dead_wood_logs");
    public static final ITag.INamedTag<Item> SHADOWROOT_LOGS = ItemTags.makeWrapperTag("midnight:shadowroot_logs");
    public static final ITag.INamedTag<Item> DARK_WILLOW_LOGS = ItemTags.makeWrapperTag("midnight:dark_willow_logs");

    private MnItemTags() {
    }
}
