/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import mcp.MethodsReturnNonnullByDefault;
import midnight.common.Midnight;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.OptionalLong;

@MethodsReturnNonnullByDefault
public class MnDimensionType extends DimensionType {
    private final OptionalLong fixedTime;
    private final ResourceLocation infiniburn;
    private final ResourceLocation effectsLocation;
    private final float ambientLight;

    public static Builder builder() {
        return new Builder();
    }

    /**
     * This constructor is used to create the dimension type to use for a dimension. It is intentionally private; please
     * use a {@link MnDimensionType.Builder}.
     *
     * @param fixedTime          An optinal long containing a fixed time for the dimension to use. If you prefer not to
     *                           use a fixed time, use {@link OptionalLong#empty()}.
     * @param hasSkylight        Whether or not to use skylight in this dimension.
     * @param hasCeiling         Whether or not this dimension has a ceiling.
     * @param ultraWarm          Whether or not this dimension is ultra warm.
     * @param natural            Whether or not this dimension is natural.
     * @param coordinateScale    The coordinates scale (in comparison to the Overworld) for this dimension.
     * @param createDragonFight  Whether or not a dragon can spawn in this dimension.
     * @param piglinSafe         Whether or not piglins are safe in this dimension.
     * @param bedWorks           Whether or not you can use a bed in this dimension.
     * @param respawnAnchorWorks Whether or not you can use a respawn anchor in this dimension..
     * @param hasRaids           Whether or not raids are allowed to occur in this dimension.
     * @param logicalHeight      The block height limit of this dimension.
     * @param biomeZoomer        The biome magnifier to use for this dimension.
     * @param infiniburn         The infiniburn tag to use for this dimension.
     * @param effectsLocation    The sky renderer type to use for this dimension.
     * @param ambientLight       The default ambient lighting to have for this dimension.
     * @see Builder#Builder()
     */
    private MnDimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean createDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int logicalHeight, IBiomeMagnifier biomeZoomer, ResourceLocation infiniburn, ResourceLocation effectsLocation, float ambientLight) {
        super(fixedTime, hasSkylight, hasCeiling, ultraWarm, natural, coordinateScale, createDragonFight, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, logicalHeight, biomeZoomer, infiniburn, effectsLocation, ambientLight);
        this.fixedTime = fixedTime;
        this.infiniburn = infiniburn;
        this.effectsLocation = effectsLocation;
        this.ambientLight = ambientLight;
    }

    /**
     * This builder provides an easier way to create midnight dimension types by specifying which values need to be
     * changed from  their default, if any. All of the attributes are set to {@code 0}, {@code false}, or {@code null}
     * except for {@link #fixedTime}, {@link #coordinateScale} {@link #logicalHeight}, and {@link #biomeZoomer} which
     * are all set to {@link OptionalLong#empty()}, {@code 1.0}, {@code 256}, and {@link FuzzedBiomeMagnifier#INSTANCE}
     * respectively.
     *
     * @author Jonathing
     * @since 0.6.0
     */
    public static class Builder {
        private OptionalLong fixedTime;
        private boolean hasSkylight;
        private boolean hasCeiling;
        private boolean ultraWarm;
        private boolean natural;
        private double coordinateScale;
        private boolean createDragonFight;
        private boolean piglinSafe;
        private boolean bedWorks;
        private boolean respawnAnchorWorks;
        private boolean hasRaids;
        private int logicalHeight;
        private IBiomeMagnifier biomeZoomer;
        private ResourceLocation infiniburn;
        private ResourceLocation effectsLocation;
        private float ambientLight;

        private Builder(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean createDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int logicalHeight, IBiomeMagnifier biomeZoomer, ResourceLocation infiniburn, ResourceLocation effectsLocation, float ambientLight) {
            this.fixedTime = fixedTime;
            this.hasSkylight = hasSkylight;
            this.hasCeiling = hasCeiling;
            this.ultraWarm = ultraWarm;
            this.natural = natural;
            this.coordinateScale = coordinateScale;
            this.createDragonFight = createDragonFight;
            this.piglinSafe = piglinSafe;
            this.bedWorks = bedWorks;
            this.respawnAnchorWorks = respawnAnchorWorks;
            this.hasRaids = hasRaids;
            this.logicalHeight = logicalHeight;
            this.biomeZoomer = biomeZoomer;
            this.infiniburn = infiniburn;
            this.effectsLocation = effectsLocation;
            this.ambientLight = ambientLight;
        }

        /**
         * This constructor creates a new builder with the default values mentioned in the documentation for the {@link
         * Builder} class.
         *
         * @see Builder
         */
        public Builder() {
            this(
                OptionalLong.empty(),
                false,
                false,
                false,
                false,
                1.0,
                false,
                false,
                false,
                false,
                false,
                256,
                FuzzedBiomeMagnifier.INSTANCE,
                BlockTags.INFINIBURN_OVERWORLD.getName(),
                DimensionType.OVERWORLD_EFFECTS,
                0.0F
            );
        }

        /**
         * This constructor creates a new builder with a given {@link MnDimensionType}. The benefit of using this over
         * {@link #Builder(DimensionType)} is that our dimension types keep track of fixed time, infiniburn, effects
         * location, and the ambient light level.
         *
         * @param type The midnight dimension type to base this builder off of.
         */
        public Builder(MnDimensionType type) {
            this(
                type.fixedTime,
                type.hasSkyLight(),
                type.hasCeiling(),
                type.ultraWarm(),
                type.natural(),
                type.coordinateScale(),
                type.createDragonFight(),
                type.piglinSafe(),
                type.bedWorks(),
                type.respawnAnchorWorks(),
                type.hasRaids(),
                type.logicalHeight(),
                type.getBiomeZoomer(),
                type.infiniburn,
                type.effectsLocation,
                type.ambientLight
            );
        }

        /**
         * This constructor creates a new builder with a given {@link DimensionType}. Fixed time, infiniburn, effects
         * location, and the ambient light level are not copied over.
         *
         * @param type The dimension type to base this builder off of.
         */
        public Builder(DimensionType type) {
            this(
                OptionalLong.empty(),
                type.hasSkyLight(),
                type.hasCeiling(),
                type.ultraWarm(),
                type.natural(),
                type.coordinateScale(),
                type.createDragonFight(),
                type.piglinSafe(),
                type.bedWorks(),
                type.respawnAnchorWorks(),
                type.hasRaids(),
                type.logicalHeight(),
                type.getBiomeZoomer(),
                BlockTags.INFINIBURN_OVERWORLD.getName(),
                DimensionType.OVERWORLD_EFFECTS,
                0.0F
            );
        }

        /**
         * This method takes all of the {@link Builder}'s attributes and uses them to create a new {@link
         * MnDimensionType}.
         *
         * @return The newly-built {@link MnDimensionType}.
         */
        public MnDimensionType build() {
            return new MnDimensionType(
                this.fixedTime,
                this.hasSkylight,
                this.hasCeiling,
                this.ultraWarm,
                this.natural,
                this.coordinateScale,
                this.createDragonFight,
                this.piglinSafe,
                this.bedWorks,
                this.respawnAnchorWorks,
                this.hasRaids,
                this.logicalHeight,
                this.biomeZoomer,
                this.infiniburn,
                this.effectsLocation,
                this.ambientLight
            );
        }

        public Builder fixedTime(long time) {
            this.fixedTime = OptionalLong.of(time);
            return this;
        }

        public Builder hasSkylight(boolean hasSkylight) {
            this.hasSkylight = hasSkylight;
            return this;
        }

        public Builder hasCeiling(boolean hasCeiling) {
            this.hasCeiling = hasCeiling;
            return this;
        }

        public Builder ultraWarm(boolean ultraWarm) {
            this.ultraWarm = ultraWarm;
            return this;
        }

        public Builder natural(boolean natural) {
            this.natural = natural;
            return this;
        }

        public Builder coordinateScale(double coordinateScale) {
            this.coordinateScale = coordinateScale;
            return this;
        }

        public Builder createDragonFight(boolean createDragonFight) {
            this.createDragonFight = createDragonFight;
            return this;
        }

        public Builder piglinSafe(boolean piglinSafe) {
            this.piglinSafe = piglinSafe;
            return this;
        }

        public Builder bedWorks(boolean bedWorks) {
            this.bedWorks = bedWorks;
            return this;
        }

        public Builder respawnAnchorWorks(boolean respawnAnchorWorks) {
            this.respawnAnchorWorks = respawnAnchorWorks;
            return this;
        }

        public Builder hasRaids(boolean hasRaids) {
            this.hasRaids = hasRaids;
            return this;
        }

        public Builder logicalHeight(int logicalHeight) {
            this.logicalHeight = heightCheck(logicalHeight);
            return this;
        }

        public Builder biomeZoomer(@Nonnull IBiomeMagnifier biomeZoomer) {
            this.biomeZoomer = nullCheck(biomeZoomer, "biome zoomer");
            return this;
        }

        public Builder infiniburn(@Nonnull ResourceLocation infiniburn) {
            this.infiniburn = nullCheck(infiniburn, "infiniburn");
            return this;
        }

        public Builder infiniburn(@Nonnull String infiniburn) {
            return infiniburn(Midnight.id(infiniburn));
        }

        public Builder effectsLocation(@Nonnull ResourceLocation effectsLocation) {
            this.effectsLocation = nullCheck(effectsLocation, "effects location");
            return this;
        }

        public Builder effectsLocation(@Nonnull String effectsLocation) {
            return effectsLocation(Midnight.id(effectsLocation));
        }

        public Builder ambientLight(float ambientLight) {
            this.ambientLight = ambientLight;
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
