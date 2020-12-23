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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ShearableBlock extends Block {
    private final Supplier<Block> sheared;

    public ShearableBlock(Properties props, Supplier<Block> sheared) {
        super(props);
        this.sheared = sheared;
    }

    @Override
    public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rtr) {
        ItemStack usedItem = player.getHeldItem(hand);
        if (usedItem.getItem() == Items.SHEARS) {
            world.setBlockState(pos, sheared.get().getDefaultState());
            usedItem.damageItem(1, player, p -> {});
            world.playSound(null, pos, SoundEvents.BLOCK_WART_BLOCK_HIT, SoundCategory.BLOCKS, 1, 1);

            return ActionResultType.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, rtr);
    }
}
