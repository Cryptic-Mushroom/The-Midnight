/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DeadLogFeatureConfig implements IFeatureConfig {
    public static final int BRANCHING_CHANCE_DEFAULT = 10;
    public static final int BRANCH_LENGTH_MAX_DEFAULT = 2;
    public static final int LENGTH_MIN_DEFAULT = 4;
    public static final int LENGTH_MAX_DEFAULT = 10;

    public static final Codec<DeadLogFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BlockStateProvider.CODEC
                .fieldOf("x_axis_log")
                .forGetter(cfg -> cfg.xAxisLog),
            BlockStateProvider.CODEC
                .fieldOf("z_axis_log")
                .forGetter(cfg -> cfg.zAxisLog),
            Codec.INT
                .fieldOf("branching_chance")
                .orElse(BRANCHING_CHANCE_DEFAULT)
                .forGetter(cfg -> cfg.branchingChance),
            Codec.INT
                .fieldOf("branch_length_max")
                .orElse(BRANCH_LENGTH_MAX_DEFAULT)
                .forGetter(cfg -> cfg.branchLengthMax),
            Codec.INT
                .fieldOf("length_min")
                .orElse(LENGTH_MIN_DEFAULT)
                .forGetter(cfg -> cfg.lengthMin),
            Codec.INT
                .fieldOf("length_max")
                .orElse(LENGTH_MAX_DEFAULT)
                .forGetter(cfg -> cfg.lengthMax)
        ).apply(instance, DeadLogFeatureConfig::new)
    );


    private final BlockStateProvider xAxisLog;
    private final BlockStateProvider zAxisLog;
    private int branchingChance = BRANCHING_CHANCE_DEFAULT;
    private int branchLengthMax = BRANCH_LENGTH_MAX_DEFAULT;
    private int lengthMin = LENGTH_MIN_DEFAULT;
    private int lengthMax = LENGTH_MAX_DEFAULT;

    public DeadLogFeatureConfig(BlockStateProvider xAxisLog, BlockStateProvider zAxisLog) {
        this.xAxisLog = xAxisLog;
        this.zAxisLog = zAxisLog;
    }

    // used in codec ^
    private DeadLogFeatureConfig(BlockStateProvider xAxisLog, BlockStateProvider zAxisLog,
                                 int branchingChance, int branchLengthMax, int lengthMin, int lengthMax) {
        this.xAxisLog = xAxisLog;
        this.zAxisLog = zAxisLog;
        this.branchingChance = branchingChance;
        this.branchLengthMax = branchLengthMax;
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }

    public BlockStateProvider getXAxisLog() {
        return xAxisLog;
    }

    public BlockStateProvider getZAxisLog() {
        return zAxisLog;
    }

    public DeadLogFeatureConfig setBranchingChance(int branchingChance) {
        this.branchingChance = branchingChance;
        return this;
    }

    public int getBranchingChance() {
        return branchingChance;
    }

    public DeadLogFeatureConfig setBranchLengthMax(int branchLengthMax) {
        this.branchLengthMax = branchLengthMax;
        return this;
    }

    public int getBranchLengthMax() {
        return branchLengthMax;
    }

    public DeadLogFeatureConfig setLengthMin(int lengthMin) {
        this.lengthMin = lengthMin;
        return this;
    }

    public int getLengthMin() {
        return lengthMin;
    }

    public DeadLogFeatureConfig setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
        return this;
    }

    public int getLengthMax() {
        return lengthMax;
    }
}
