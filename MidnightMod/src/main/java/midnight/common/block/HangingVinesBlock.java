/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HangingVinesBlock extends MnPlantBlock {
    protected HangingVinesBlock(Properties props) {
        super(props);
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Block.doesSideFillSquare(state.getCollisionShape(world, pos), Direction.DOWN);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos abovePos = pos.up();
        return isValidGround(world.getBlockState(abovePos), world, abovePos);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
