/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class UpsideDownPlantBlock extends MnPlantBlock {
    protected UpsideDownPlantBlock(Properties props) {
        super(props);
    }

    @Override
    public MnPlantBlock setPlantHitbox(double size, double height) {
        double radius = size / 2;
        setHitbox(makeCuboidShape(8 - radius, 16 - height, 8 - radius, 8 + radius, 16, 8 + radius));
        return this;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos up = pos.up();
        if (state.getBlock() == this)
            return world.getBlockState(up).canSustainPlant(world, up, Direction.DOWN, this);
        return isValidGround(world.getBlockState(up), world, up);
    }
}
