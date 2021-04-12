/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.misc.tags.MnBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CrystalotusBlock extends PlantBlock {
    private static final VoxelShape SELECTION_HITBOX = box(2, 0, 2, 14, 2, 14);
    private static final VoxelShape COLLISION_HITBOX = box(6.5, 0, 6.5, 9.5, 2, 9.5);

    protected CrystalotusBlock(Properties props) {
        super(props);
        plantType(MnPlantTypes.CRYSTALOTUS);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return COLLISION_HITBOX;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SELECTION_HITBOX;
    }

    /**
     * @deprecated Not effective
     */
    @Override
    @Deprecated
    public PlantBlock hitbox(VoxelShape hitbox) {
        throw new UnsupportedOperationException("hitbox has no effect");
    }

    /**
     * @deprecated Not effective
     */
    @Override
    @Deprecated
    public PlantBlock hitbox(double size, double height) {
        throw new UnsupportedOperationException("hitbox has no effect");
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
        return state.is(MnBlockTags.CRYSTALOTUS_GROWABLE);
    }
}
