/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.core.mixin;

import midnight.common.block.MidnightBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMakerMixin {
    @Inject(
        method = "getCommonNodeType",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void onGetCommonNodeType(BlockView world, BlockPos pos, CallbackInfoReturnable<PathNodeType> info) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof MidnightBlock) {
            PathNodeType pathType = ((MidnightBlock) block).getPathNodeType(state, world, pos);
            if (pathType != null) {
                info.setReturnValue(pathType);
            }
        }
    }
}
