package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import midnight.common.entity.MnEntities;
import midnight.common.entity.creature.CrystalBugEntity;

import java.util.List;
import java.util.Random;

public class BloomCrystalBlock extends CrystalBlock {
    public BloomCrystalBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.tick(state, world, pos, random);

        if (random.nextFloat() > 0.005F) {
            return;
        }

        List<CrystalBugEntity> crystalBugs = world.getEntitiesWithinAABB(CrystalBugEntity.class, new AxisAlignedBB(pos).grow(6.0, 4.0, 6.0));
        if (!crystalBugs.isEmpty()) {
            return;
        }

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;
        float yaw = world.rand.nextFloat() * 360.0F;

        Entity entity = MnEntities.CRYSTAL_BUG.create(world);
        if (entity != null) {
            entity.setPositionAndRotation(x, y, z, yaw, 0f);
            world.addEntity(entity);
        }
    }
}
