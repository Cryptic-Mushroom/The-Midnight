/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.IColorProvider;
import midnight.core.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class BiomeSpecificColorProvider implements IColorProvider {
    private final HashMap<Biome, IColorProvider> colors = new HashMap<>();
    private final IColorProvider defaultColor;
    private final IColorProvider itemColor;
    private final int radius;

    public BiomeSpecificColorProvider(IColorProvider defaultColor, IColorProvider itemColor, int radius) {
        this.defaultColor = defaultColor;
        this.itemColor = itemColor;
        this.radius = radius;
    }

    public void addColor(Biome biome, IColorProvider provider) {
        colors.put(biome, provider);
    }

    @Override
    public int getColor(IWorld world, BlockPos pos) {
        if (world == null) return itemColor.getColor(null, pos);

        int radius = this.radius;
        if (radius <= -1) radius = Minecraft.getInstance().gameSettings.biomeBlendRadius;

        if (radius == 0) {
            Biome biome = world.getBiome(pos);
            return colors.getOrDefault(biome, defaultColor).getColor(world, pos);
        } else {
            float r = 0, g = 0, b = 0;
            int total = 0;

            BlockPos.Mutable mpos = new BlockPos.Mutable();

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    mpos.setPos(pos).move(x, 0, z);
                    Biome biome = world.getBiome(mpos);
                    int color = colors.getOrDefault(biome, defaultColor).getColor(world, pos);

                    r += ColorUtil.redf(color);
                    g += ColorUtil.greenf(color);
                    b += ColorUtil.bluef(color);
                    total++;
                }
            }

            r /= total;
            g /= total;
            b /= total;
            return ColorUtil.rgb(r, g, b);
        }
    }

    @Override
    public void initForSeed(long seed) {
        for (IColorProvider provider : colors.values()) {
            provider.initForSeed(seed);
        }
        defaultColor.initForSeed(seed);
        itemColor.initForSeed(seed);
    }
}
