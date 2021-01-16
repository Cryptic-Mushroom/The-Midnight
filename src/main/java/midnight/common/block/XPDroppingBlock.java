/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class XPDroppingBlock extends OreBlock {
    private final int minxp;
    private final int maxxp;

    public XPDroppingBlock(Settings props, int minxp, int maxxp) {
        super(props);
        this.minxp = minxp;
        this.maxxp = maxxp;
    }

    @Override
    protected int getExperienceWhenMined(Random rng) {
        return MathHelper.nextInt(rng, minxp, maxxp);
    }
}
