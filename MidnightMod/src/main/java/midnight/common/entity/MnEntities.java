package midnight.common.entity;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import midnight.MidnightInfo;
import midnight.common.entity.creature.CrystalBugEntity;
import midnight.common.entity.projectile.BladeshroomCapEntity;
import midnight.common.entity.projectile.ThrownGeodeEntity;
import midnight.common.registry.RegistryManager;

import java.util.Random;

@ObjectHolder("midnight")
public class MnEntities {
    public static final EntityType<CrystalBugEntity> CRYSTAL_BUG = register("crystal_bug", EntityType.Builder.create(CrystalBugEntity::new, MnEntityClassifications.MIDNIGHT_AMBIENT)
                                                                                                             .setTrackingRange(80)
                                                                                                             .setUpdateInterval(3)
                                                                                                             .setShouldReceiveVelocityUpdates(false)
                                                                                                             .size(0.2f, 0.2f)
                                                                                                             .build(MidnightInfo.MODID + ":crystal_bug"));
    public static final EntityType<BladeshroomCapEntity> BLADESHROOM_CAP = register("bladeshroom_cap", EntityType.Builder.<BladeshroomCapEntity>create(BladeshroomCapEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(BladeshroomCapEntity::new)
            .size(0.5f, 0.5f)
            .build(MidnightInfo.MODID + ":bladeshroom_cap"));
    public static final EntityType<ThrownGeodeEntity> THROWN_GEODE = register("thrown_geode", EntityType.Builder.<ThrownGeodeEntity>create(ThrownGeodeEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(ThrownGeodeEntity::new)
            .size(0.5f, 0.5f)
            .build(MidnightInfo.MODID + ":thrown_geode"));

    private static <E extends EntityType<?>> E register(String id, E entitytype) {
        RegistryManager.ENTITY_TYPES.register(id, entitytype);
        return entitytype;
    }

    public static void init() {
        EntitySpawnPlacementRegistry.register(CRYSTAL_BUG, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MnEntities::mobCondition);
        GlobalEntityTypeAttributes.put(CRYSTAL_BUG, CrystalBugEntity.registerAttributes().func_233813_a_());
    }

    private static boolean mobCondition(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getWorld().getBlockState(blockpos).canEntitySpawn(world, blockpos, entityType);
    }
}
