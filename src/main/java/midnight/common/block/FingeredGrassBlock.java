/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.api.util.INightGrassColorModifying;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class FingeredGrassBlock extends PlantBlock implements INightGrassColorModifying {
    protected FingeredGrassBlock(Properties props) {
        super(props);
    }

    @Override
    public int getColorModifier(IWorld world, BlockPos pos, BlockState state) {
        return 0x004B7A;
    }
}
