/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;

public class MnFluidBlock extends FluidBlock {
    public MnFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }
}
