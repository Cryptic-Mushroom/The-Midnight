/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class MnPlantBlock extends BushBlock {
    private VoxelShape hitbox = VoxelShapes.fullCube();
    private OffsetType offsetType = OffsetType.NONE;

    protected MnPlantBlock(Properties props) {
        super(props);
    }

    public MnPlantBlock setHitbox(VoxelShape hitbox) {
        this.hitbox = hitbox;
        return this;
    }

    public MnPlantBlock setPlantHitbox(double size, double height) {
        double radius = size / 2;
        setHitbox(makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, height, 8 + radius));
        return this;
    }

    public MnPlantBlock setOffsetType(OffsetType offsetType) {
        this.offsetType = offsetType;
        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return hitbox;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock() instanceof NightDirtBlock || state.isIn(MnBlocks.NIGHT_MYCELIUM) || super.isValidGround(state, world, pos);
    }

    @Override
    public OffsetType getOffsetType() {
        return offsetType;
    }
}
