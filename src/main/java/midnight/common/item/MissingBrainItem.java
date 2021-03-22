/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 3 - 22
 */

package midnight.common.item;

import midnight.common.block.MnBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingBrainItem extends Item {
    public MissingBrainItem(Properties props) {
        super(props);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        if (state.isIn(MnBlocks.BRAINLESS_BLOCK)) {
            List<Direction> dirs = new ArrayList<>(Arrays.asList(Direction.values()));

            while (!dirs.isEmpty()) {
                Direction dir = dirs.remove(world.rand.nextInt(dirs.size()));

                BlockPos neighbor = pos.offset(dir);
                if (world.isAirBlock(neighbor)) {
                    if (!world.isRemote) {
                        world.setBlockState(neighbor, MnBlocks.BRAINLESS_BLOCK.getDefaultState(), 3);
                        if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                            context.getItem().shrink(1);
                        }
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }
}
