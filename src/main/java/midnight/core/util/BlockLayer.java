/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.util;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Replacement for the removed {@code BlockRenderLayer}. We use this in block builders to dynamically set the block
 * render layer and delegate that to the {@link RenderTypeLookup} when available.
 *
 * @author Shadew
 * @since 0.6.0
 */
public enum BlockLayer {
    SOLID,
    CUTOUT,
    CUTOUT_MIPPED,
    TRANSLUCENT;

    /**
     * @return The corresponding {@link RenderType} for this render layer.
     */
    @OnlyIn(Dist.CLIENT)
    public RenderType getRenderType() {
        switch (this) {
            default:
            case SOLID:
                return RenderType.solid();
            case CUTOUT:
                return RenderType.cutout();
            case CUTOUT_MIPPED:
                return RenderType.cutoutMipped();
            case TRANSLUCENT:
                return RenderType.translucent();
        }
    }

    /**
     * @param type The render type to get the {@link BlockLayer} from.
     * @return The render layer corresponding to the specified {@link RenderType}, or {@link #SOLID} if not found.
     */
    @OnlyIn(Dist.CLIENT)
    public static BlockLayer getFromRenderType(RenderType type) {
        if (type == RenderType.solid()) return SOLID;
        if (type == RenderType.cutout()) return CUTOUT;
        if (type == RenderType.cutoutMipped()) return CUTOUT_MIPPED;
        if (type == RenderType.translucent()) return TRANSLUCENT;
        return SOLID;
    }
}
