/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// TODO Since this is in Midnight Core, we might want to document this.
public class BiomeColoring {
    public static final Codec<BiomeColoring> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("grass")
                     .orElse(0x506C78)
                     .forGetter(BiomeColoring::getGrassColor),
            Codec.INT.fieldOf("water")
                     .orElse(0x22517D)
                     .forGetter(BiomeColoring::getWaterColor),
            Codec.INT.fieldOf("shadowroot")
                     .orElse(0x3A3154)
                     .forGetter(BiomeColoring::getWaterColor)
        ).apply(instance, instance.stable(BiomeColoring::new))
    );

    private final int grassColor;
    private final int waterColor;
    private final int shadowrootColor;

    private BiomeColoring(int grassColor, int waterColor, int shadowrootColor) {
        this.grassColor = grassColor;
        this.waterColor = waterColor;
        this.shadowrootColor = shadowrootColor;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getWaterColor() {
        return waterColor;
    }

    public int getShadowrootColor() {
        return shadowrootColor;
    }

    public static class Builder {
        private int grassColor = 0x506C78;
        private int waterColor = 0x22517D;
        private int shadowrootColor = 0x3A3154;

        public Builder grassColor(int grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public Builder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        public Builder shadowrootColor(int shadowrootColor) {
            this.shadowrootColor = shadowrootColor;
            return this;
        }

        public BiomeColoring build() {
            return new BiomeColoring(grassColor, waterColor, shadowrootColor);
        }
    }
}
