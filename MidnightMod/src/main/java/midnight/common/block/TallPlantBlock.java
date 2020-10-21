/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class TallPlantBlock extends DoublePlantBlock {
    private VoxelShape hitboxLo = VoxelShapes.fullCube();
    private VoxelShape hitboxHi = VoxelShapes.fullCube();
    private OffsetType offsetType = OffsetType.NONE;
    private PlantType plantType = PlantType.PLAINS;

    public TallPlantBlock(Properties props) {
        super(props);
    }

    /**
     * Set the hitboxes of this plant to the given shapes. Subclasses may override this method to change behavior or,
     * when they don't support it, throw an {@link UnsupportedOperationException}.
     *
     * @param lo The hitbox for the lower part of this plant
     * @param hi The hitbox for the upper part of this plant
     * @return This instance for method chaining
     *
     * @throws UnsupportedOperationException When the specified plant does not support setting the hitboxes
     * @since 0.6.0
     */
    public TallPlantBlock hitbox(VoxelShape lo, VoxelShape hi) {
        this.hitboxLo = lo;
        this.hitboxHi = hi;
        return this;
    }

    /**
     * Set the hitboxes of this plant to the given boundaries. This generates a square shape with diameter {@code size},
     * extruded from the ground by {@code height} size units, where 1 size unit is 1/16 block (1 pixel). When the height
     * of the shape is less than 16 size units, it will assign a shape only to the lower part of this plant, and give
     * the upper part an empty shape. If the height is more than 16 size units, the shape is split so that and the lower
     * and upper part are assigned the shapes such that their union resembles the desired shape. Subclasses may override
     * to alter the shape that is generated, for example a plant that generates on the ceiling will instead create a
     * shape that extrudes from the ceiling instead of the floor. Plants that have other dynamic hitboxes may choose to
     * throw an {@link UnsupportedOperationException} if they do not support setting hitboxes.
     *
     * @param size   The diameter of the base square of the shape
     * @param height The height of the shape
     * @return This instance for method chaining
     *
     * @throws UnsupportedOperationException When the specified plant does not support setting the hitboxes
     * @since 0.6.0
     */
    public TallPlantBlock hitbox(double size, double height) {
        double radius = size / 2;
        if (height < 16) {
            hitbox(makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, height, 8 + radius), VoxelShapes.empty());
        } else {
            hitbox(
                makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, 16, 8 + radius),
                makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, height - 16, 8 + radius)
            );
        }
        return this;
    }

    /**
     * Sets the offset type of this plant. Depending on the offset type, this causes the plant to be randomly
     * repositioned while rendering. This also causes the hitbox to be randomly repositioned to match the actual place
     * of the plant. The following options are available:
     * <ul>
     * <li>{@link OffsetType#NONE NONE}: Does not create random rendering offset.</li>
     * <li>{@link OffsetType#XZ XZ}: Creates random offset along the X and Z axis.</li>
     * <li>{@link OffsetType#XYZ XYZ}: Creates random offset along the X, Y and Z axis. Note that the Y offset is less
     * than the X and Z offset.</li>
     * </ul>
     * Plants that do not support random offset may choose to throw an {@link UnsupportedOperationException}.
     *
     * @param offsetType The desired offset type
     * @return This instance for method chaining
     *
     * @throws UnsupportedOperationException When the specified plant has a predetermined offset type or does not
     *                                       support offsetting
     * @since 0.6.0
     */
    public TallPlantBlock offset(OffsetType offsetType) {
        this.offsetType = offsetType;
        return this;
    }

    /**
     * Sets a specific {@link PlantType} for this plant. By default, the plant type is {@link MnPlantTypes#MIDNIGHT},
     * this method allows you to change that.
     *
     * @param type The desired plant type
     * @return This instance for method chaining
     *
     * @since 0.6.0
     */
    public TallPlantBlock plantType(PlantType type) {
        this.plantType = type;
        return this;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock() instanceof NightDirtBlock || super.isValidGround(state, world, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vector3d off = state.getOffset(world, pos);
        return (state.get(HALF) == DoubleBlockHalf.LOWER ? hitboxLo : hitboxHi).withOffset(off.x, off.y, off.z);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
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
