/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.OptionalLong;

@MethodsReturnNonnullByDefault
public class MnDimensionType extends DimensionType {
    public static Builder builder() {
        return new Builder();
    }

    /**
     * This constructor is used to create the dimension type to use for a dimension.
     *
     * @param fixedTime   An optinal long containing a fixed time for the dimension to use. If you prefer not to use a
     *                    fixed time, use {@link OptionalLong#empty()}.
     * @param skylight    Whether or not to use skylight in this dimension.
     * @param ceiling     Whether or not this dimension has a ceiling.
     * @param ultrawarm   Whether or not this dimension is ultra warm.
     * @param natural     Whether or not this dimension is natural.
     * @param coordsScale The coordinates scale (in comparison to the Overworld) for this dimension.
     * @param dragon      Whether or not a dragon can spawn in this dimension.
     * @param piglins     Whether or not piglins are safe in this dimension.
     * @param bed         Whether or not you can use a bed in this dimension.
     * @param anchor      Whether or not you can use a respawn anchor in this dimension..
     * @param raid        Whether or not raids are allowed to occur in this dimension.
     * @param height      The block height limit of this dimension.
     * @param magnifier   The biome magnifier to use for this dimension.
     * @param infiniburn  The infiniburn tag to use for this dimension.
     * @param sky         The sky renderer type to use for this dimension.
     * @param lighting    The default ambient lighting to have for this dimension.
     */
    private MnDimensionType(OptionalLong fixedTime, boolean skylight, boolean ceiling, boolean ultrawarm, boolean natural, double coordsScale, boolean dragon, boolean piglins, boolean bed, boolean anchor, boolean raid, int height, IBiomeMagnifier magnifier, ResourceLocation infiniburn, ResourceLocation sky, float lighting) {
        super(fixedTime, skylight, ceiling, ultrawarm, natural, coordsScale, dragon, piglins, bed, anchor, raid, height, magnifier, infiniburn, sky, lighting);
    }

    /*
     * PLEASE NOTE THAT I'M USING MOJMAP NAMES FOR THESE VARIABLES. I'LL PUT IN JAVADOC COMMENTS WHAT THEIR MCP NAMES
     * WERE FOR CONVENIENCE.
     */
    public static class Builder {
        private OptionalLong fixedTime;
        private boolean skylight;
        private boolean ceiling;
        private boolean ultrawarm;
        private boolean natural;
        private double coordsScale;
        private boolean dragon;
        private boolean piglins;
        private boolean bed;
        private boolean anchor;
        private boolean raid;
        private int height;
        private IBiomeMagnifier magnifier;
        private ResourceLocation infiniburn;
        private ResourceLocation sky;
        private float lighting;

        public Builder() {
            this.fixedTime = OptionalLong.empty();
            this.skylight = false;
            this.ceiling = false;
            this.ultrawarm = false;
            this.natural = false;
            this.coordsScale = 1.0;
            this.dragon = false;
            this.piglins = false;
            this.bed = false;
            this.anchor = false;
            this.raid = false;
            this.height = 256;
            this.magnifier = FuzzedBiomeMagnifier.INSTANCE;
            this.infiniburn = null;
            this.sky = null;
            this.lighting = 0.0F;
        }

        public MnDimensionType build() {
            return new MnDimensionType(
                this.fixedTime,
                this.skylight,
                this.ceiling,
                this.ultrawarm,
                this.natural,
                this.coordsScale,
                this.dragon,
                this.piglins,
                this.bed,
                this.anchor,
                this.raid,
                this.height,
                this.magnifier,
                nullCheck(this.infiniburn, "infiniburn"),
                nullCheck(this.sky, "effects location"),
                this.lighting
            );
        }

        public Builder fixedTime(long time) {
            this.fixedTime = OptionalLong.of(time);
            return this;
        }

        public Builder skylight(boolean skylight) {
            this.skylight = skylight;
            return this;
        }

        public Builder ceiling(boolean ceiling) {
            this.ceiling = ceiling;
            return this;
        }

        public Builder ultrawarm(boolean ultrawarm) {
            this.ultrawarm = ultrawarm;
            return this;
        }

        public Builder natural(boolean natural) {
            this.natural = natural;
            return this;
        }

        public Builder coordsScale(double coordsScale) {
            this.coordsScale = coordsScale;
            return this;
        }

        public Builder dragon(boolean dragon) {
            this.dragon = dragon;
            return this;
        }

        public Builder piglins(boolean piglins) {
            this.piglins = piglins;
            return this;
        }

        public Builder bed(boolean bed) {
            this.bed = bed;
            return this;
        }

        public Builder anchor(boolean anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder raid(boolean raid) {
            this.raid = raid;
            return this;
        }

        public Builder height(int height) {
            this.height = heightCheck(height);
            return this;
        }

        public Builder magnifier(@Nonnull IBiomeMagnifier magnifier) {
            this.magnifier = nullCheck(magnifier, "biome zoomer");
            return this;
        }

        public Builder infiniburn(@Nonnull ResourceLocation infiniburn) {
            this.infiniburn = nullCheck(infiniburn, "infiniburn");
            return this;
        }

        public Builder sky(ResourceLocation sky) {
            this.sky = nullCheck(sky, "effects location");
            return this;
        }

        public Builder lighting(float lighting) {
            this.lighting = lighting;
            return this;
        }

        private static int heightCheck(int height) {
            if (height < 1) {
                throw new IllegalArgumentException("Dimension height cannot be less than 1!");
            }

            return height;
        }

        private static <T> T nullCheck(T object, String name) {
            return Objects.requireNonNull(object, String.format("Value %s cannot be null for dimension type!", name));
        }
    }
}
