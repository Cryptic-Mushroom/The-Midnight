/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class DeceitfulMudBlock extends NightDirtBlock implements MidnightBlock {
    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 14, 16);

    public DeceitfulMudBlock(Settings props) {
        super(props);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
//        if (!entity.isWet() && !MnEntityTypeTags.IGNORE_MUD.contains(entity.getType())) TODO Tags in Fabric
        if (!entity.isWet())
            entity.setVelocity(entity.getVelocity().multiply(0.5, 1, 0.5));
    }

    @Override
    public PathNodeType getPathNodeType(BlockState state, BlockView world, BlockPos pos) {
        return PathNodeType.STICKY_HONEY;
    }
}
