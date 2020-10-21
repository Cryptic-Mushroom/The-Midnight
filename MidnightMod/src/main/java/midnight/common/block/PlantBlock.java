/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class PlantBlock extends BushBlock {
    private VoxelShape hitbox = VoxelShapes.fullCube();
    private OffsetType offsetType = OffsetType.NONE;
    private PlantType plantType = MnPlantTypes.MIDNIGHT;

    protected PlantBlock(Properties props) {
        super(props);
    }

    public PlantBlock hitbox(VoxelShape hitbox) {
        this.hitbox = hitbox;
        return this;
    }

    public PlantBlock hitbox(double size, double height) {
        double radius = size / 2;
        hitbox(makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, height, 8 + radius));
        return this;
    }

    public PlantBlock offset(OffsetType offsetType) {
        this.offsetType = offsetType;
        return this;
    }

    public PlantBlock plantType(PlantType type) {
        this.plantType = type;
        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vector3d off = state.getOffset(world, pos);
        return hitbox.withOffset(off.x, off.y, off.z);
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

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return plantType;
    }
}
