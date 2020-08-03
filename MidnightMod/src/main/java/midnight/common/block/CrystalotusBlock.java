package midnight.common.block;

import net.minecraftforge.common.ToolType;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import midnight.common.registry.MidnightTags;

public class CrystalotusBlock extends BushBlock {
    private static final VoxelShape BOUNDS = makeCuboidShape(1.0, 0.0, 1.0, 15.0, 12.0, 15.0);
    private static final VoxelShape COLLISION_BOX = makeCuboidShape(6.0, 0.0, 6.0, 10.0, 3.0, 10.0);

    public CrystalotusBlock() {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(2f, 0f).sound(SoundType.GLASS).tickRandomly().setLightLevel(blockState -> 3).harvestTool(ToolType.PICKAXE).harvestLevel(1));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock().isIn(MidnightTags.Blocks.PLANTABLE_GROUNDS);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLISION_BOX;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
}
