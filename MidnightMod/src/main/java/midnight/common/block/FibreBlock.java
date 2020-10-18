/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class FibreBlock extends MnPlantBlock {
    public static final BooleanProperty DENSE = MnBlockStateProperties.DENSE;

    public FibreBlock(Properties props) {
        super(props);
        setPlantHitbox(16, 1.5);

        setDefaultState(getStateContainer().getBaseState().with(DENSE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DENSE);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        return stack.getItem() == asItem() && ctx.getFace() == Direction.UP && !state.get(DENSE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        BlockState state = world.getBlockState(pos);
        if (state.isIn(this) && ctx.getFace() == Direction.UP && !state.get(DENSE)) {
            return getDefaultState().with(DENSE, true);
        }
        return getDefaultState();
    }
}
