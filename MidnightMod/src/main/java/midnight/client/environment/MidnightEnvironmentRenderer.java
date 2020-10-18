/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.client.environment;

import midnight.common.Midnight;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;

public class MidnightEnvironmentRenderer extends DimensionRenderInfo {
    public static final MidnightEnvironmentRenderer INSTANCE = new MidnightEnvironmentRenderer(Float.NaN, true, FogType.NONE, false, true);

    public static void init() {
        BY_IDENTIFIER.put(Midnight.resLoc("midnight"), INSTANCE);
    }

    public MidnightEnvironmentRenderer(float cloudHeight, boolean alternateSkyColor, FogType type, boolean shouldRender, boolean darkened) {
        super(cloudHeight, alternateSkyColor, type, shouldRender, darkened);

        setCloudRenderHandler((ticks, partialTicks, matrixStack, world, mc, viewEntityX, viewEntityY, viewEntityZ) -> {
        });

        setSkyRenderHandler((ticks, partialTicks, matrixStack, world, mc) -> {
        });

        setWeatherRenderHandler((ticks, partialTicks, world, mc, lightmapIn, xIn, yIn, zIn) -> {
        });
    }

    @Override
    public Vector3d adjustSkyColor(Vector3d color, float partialTicks) {
        return color;
    }

    @Override
    public boolean useThickFog(int x, int z) {
        return false;
    }
}
