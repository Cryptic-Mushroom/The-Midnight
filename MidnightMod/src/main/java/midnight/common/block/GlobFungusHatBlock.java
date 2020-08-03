package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class GlobFungusHatBlock extends Block {

    public GlobFungusHatBlock() {
        super(Properties.create(Material.WOOD, MaterialColor.MAGENTA).hardnessAndResistance(0.2f, 0f).sound(SoundType.SLIME).setLightLevel(blockstate -> 2));
    }

    // TODO What are these methods?

    // random.nextInt(5) == 0 -> drop 1 GLOB_FUNGUS (+SilkTouch)

    /*@Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, ILightReader worldIn, BlockPos pos) {
        return worldIn.getCombinedLight(pos, 13);
    }

    @Override
    //getAmbientOcclusionLightValue
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0f;
    }
*/
    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSneaking()) {
            super.onFallenUpon(world, pos, entity, fallDistance);
        } else {
            entity.onLivingFall(fallDistance, 0f);
        }
    }

    @Override
    public void onLanded(IBlockReader world, Entity entity) {
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
        } else if (entity.getMotion().y < 0d) {
            entity.setMotion(entity.getMotion().x, -entity.getMotion().y, entity.getMotion().z);
            if (!(entity instanceof LivingEntity)) {
                entity.setMotion(entity.getMotion().mul(1d, 0.8d, 1d));
            }
        }
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (Math.abs(entity.getMotion().y) < 0.1d && !entity.isSneaking()) {
            double d0 = 0.4d + Math.abs(entity.getMotion().y) * 0.2d;
            entity.setMotion(entity.getMotion().mul(d0, 1d, d0));
        }
        super.onEntityWalk(world, pos, entity);
    }
}
