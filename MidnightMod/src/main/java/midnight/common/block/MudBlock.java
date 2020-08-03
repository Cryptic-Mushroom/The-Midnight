package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import midnight.common.registry.MidnightTags;
import midnight.common.registry.MnSounds;

@SuppressWarnings("deprecation")
public class MudBlock extends SoilBlock {
    private static final SoundType MUD = new SoundType(1.0F, 1.0F, MnSounds.MUD_DIG, MnSounds.MUD_STEP, MnSounds.MUD_DIG, MnSounds.MUD_DIG, MnSounds.MUD_STEP);
    private static final VoxelShape COLLISION_SHAPE = makeCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public MudBlock(Properties properties) {
        super(properties.sound(MUD), false);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.getType().isContained(MidnightTags.EntityTypes.IGNORE_MUD)) {
            entity.setMotion(entity.getMotion().mul(0.6d, 1d, 0.6d));
        }
    }
}
