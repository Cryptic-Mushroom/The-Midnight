/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.common.misc.tags;

import midnight.common.Midnight;
import midnight.core.util.MnObjects;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;

public final class MnEntityTypeTags {
    public static final ITag.INamedTag<EntityType<?>> IGNORE_MUD = tag("ignore_mud");

    private static ITag.INamedTag<EntityType<?>> tag(String id) {
        id = Midnight.resStr(id);
        ITag.INamedTag<EntityType<?>> tag = EntityTypeTags.register(id);
        MnObjects.addEntityTypeTag(id, tag);
        return tag;
    }

    private MnEntityTypeTags() {
    }
}
