/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
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

import net.minecraft.block.AbstractBlock.Properties;

@SuppressWarnings("deprecation")
public class FibreBlock extends PlantBlock {
    public static final BooleanProperty DENSE = MnBlockStateProperties.DENSE;

    public FibreBlock(Properties props) {
        super(props);
        hitbox(16, 1.5);

        registerDefaultState(getStateDefinition().any().setValue(DENSE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DENSE);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        return stack.getItem() == asItem() && ctx.getClickedFace() == Direction.UP && !state.getValue(DENSE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.is(this) && ctx.getClickedFace() == Direction.UP && !state.getValue(DENSE)) {
            return defaultBlockState().setValue(DENSE, true);
        }
        return defaultBlockState();
    }
}
