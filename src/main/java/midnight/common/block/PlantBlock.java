/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
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

    /**
     * Set the hitbox of this plant to the given shape. Subclasses may override this method to change behavior or, when
     * they don't support it, throw an {@link UnsupportedOperationException}.
     *
     * @param hitbox The hitbox for this plant
     * @return This instance for method chaining
     *
     * @throws UnsupportedOperationException When the specified plant does not support setting the hitbox
     * @since 0.6.0
     */
    public PlantBlock hitbox(VoxelShape hitbox) {
        this.hitbox = hitbox;
        return this;
    }

    /**
     * Set the hitbox of this plant to the given boundaries. This generates a square shape with diameter {@code size},
     * extruded from the ground by {@code height} size units, where 1 size unit is 1/16 block (1 pixel). Subclasses may
     * override to alter the shape that is generated, for example a plant that generates on the ceiling will instead
     * create a shape that extrudes from the ceiling instead of the floor. Plants that have other dynamic hitboxes may
     * choose to throw an {@link UnsupportedOperationException} if they do not support setting hitboxes.
     *
     * @param size   The diameter of the base square of the shape
     * @param height The height of the shape
     * @return This instance for method chaining
     *
     * @throws UnsupportedOperationException When the specified plant does not support setting the hitbox
     * @since 0.6.0
     */
    public PlantBlock hitbox(double size, double height) {
        double radius = size / 2;
        hitbox(makeCuboidShape(8 - radius, 0, 8 - radius, 8 + radius, height, 8 + radius));
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
    public PlantBlock offset(OffsetType offsetType) {
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
