/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.misc.tags;

import midnight.common.Midnight;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;

public final class MnFluidTags {

    private static ITag.INamedTag<Fluid> tag(String id) {
        id = Midnight.idStr(id);
        ITag.INamedTag<Fluid> tag = FluidTags.makeWrapperTag(id);
        return tag;
    }

    private MnFluidTags() {
    }
}
