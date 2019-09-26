package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.entity.CloudEntity;
import com.mushroom.midnight.common.entity.creature.*;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.entity.projectile.NovaSpikeEntity;
import com.mushroom.midnight.common.entity.projectile.SporeBombEntity;
import com.mushroom.midnight.common.entity.projectile.ThrownGeodeEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Random;

import static com.mushroom.midnight.Midnight.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(MODID)
public class MidnightEntities {
    public static final EntityType<RifterEntity> RIFTER = EntityType.Builder.create(RifterEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 1.8f)
            .build(MODID + ":rifter");
    public static final EntityType<HunterEntity> HUNTER = EntityType.Builder.create(HunterEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(2)
            .setShouldReceiveVelocityUpdates(true)
            .size(1f, 1f)
            .build(MODID + ":hunter");
    public static final EntityType<BladeshroomCapEntity> BLADESHROOM_CAP = EntityType.Builder.<BladeshroomCapEntity>create(BladeshroomCapEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(BladeshroomCapEntity::new)
            .size(0.5f, 0.5f)
            .build(MODID + ":bladeshroom_cap");
    public static final EntityType<NovaEntity> NOVA = EntityType.Builder.create(NovaEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(2)
            .setShouldReceiveVelocityUpdates(true)
            .size(1.2f, 1.8f)
            .immuneToFire()
            .build(MODID + ":nova");
    public static final EntityType<NovaSpikeEntity> NOVA_SPIKE = EntityType.Builder.<NovaSpikeEntity>create(NovaSpikeEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(NovaSpikeEntity::new)
            .size(0.4f, 0.4f)
            .build(MODID + ":nova_spike");
    public static final EntityType<CrystalBugEntity> CRYSTAL_BUG = EntityType.Builder.create(CrystalBugEntity::new, MIDNIGHT_AMBIENT)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(false)
            .size(0.2f, 0.2f)
            .build(MODID + ":crystal_bug");
    public static final EntityType<PenumbrianEntity> PENUMBRIAN = EntityType.Builder.create(PenumbrianEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.5f, 0.5f)
            .build(MODID + ":penumbrian");
    public static final EntityType<TreeHopperEntity> TREE_HOPPER = EntityType.Builder.create(TreeHopperEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.5f, 0.5f)
            .build(MODID + ":tree_hopper");
    public static final EntityType<StingerEntity> STINGER = EntityType.Builder.create(StingerEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.2f, 0.2f)
            .build(MODID + ":stinger");
    public static final EntityType<NightStagEntity> NIGHTSTAG = EntityType.Builder.create(NightStagEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.9f, 1.87f)
            .build(MODID + ":nightstag");
    public static final EntityType<DeceitfulSnapperEntity> DECEITFUL_SNAPPER = EntityType.Builder.create(DeceitfulSnapperEntity::new, EntityClassification.WATER_CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.4f, 0.4f)
            .build(MODID + ":deceitful_snapper");
    public static final EntityType<BulbAnglerEntity> BULB_ANGLER = EntityType.Builder.create(BulbAnglerEntity::new, EntityClassification.WATER_CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 0.6f)
            .build(MODID + ":bulb_angler");
    public static final EntityType<SkulkEntity> SKULK = EntityType.Builder.create(SkulkEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 0.6f)
            .build(MODID + ":skulk");
    public static final EntityType<ShadeSquirrelEntity> SHADE_SQUIRREL = EntityType.Builder.create(ShadeSquirrelEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 0.75f)
            .build(MODID + ":shade_squirrel");
    public static final EntityType<ThrownGeodeEntity> THROWN_GEODE = EntityType.Builder.<ThrownGeodeEntity>create(ThrownGeodeEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(ThrownGeodeEntity::new)
            .size(0.5f, 0.5f)
            .build(MODID + ":thrown_geode");
    public static final EntityType<SporeBombEntity> SPORE_BOMB = EntityType.Builder.<SporeBombEntity>create(SporeBombEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(SporeBombEntity::new)
            .size(0.5f, 0.5f)
            .build(MODID + ":spore_bomb");
    public static final EntityType<CloudEntity> CLOUD = EntityType.Builder.<CloudEntity>create(CloudEntity::new, EntityClassification.MISC)
            .setTrackingRange(160)
            .setUpdateInterval(Integer.MAX_VALUE)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(CloudEntity::new)
            .immuneToFire()
            .size(6f, 0.5f)
            .build(MODID + ":cloud");

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        RIFTER.setRegistryName(MODID, "rifter");
        event.getRegistry().register(RIFTER);
        HUNTER.setRegistryName(MODID, "hunter");
        event.getRegistry().register(HUNTER);
        BLADESHROOM_CAP.setRegistryName(MODID, "bladeshroom_cap");
        event.getRegistry().register(BLADESHROOM_CAP);
        NOVA.setRegistryName(MODID, "nova");
        event.getRegistry().register(NOVA);
        NOVA_SPIKE.setRegistryName(MODID, "nova_spike");
        event.getRegistry().register(NOVA_SPIKE);
        CRYSTAL_BUG.setRegistryName(MODID, "crystal_bug");
        event.getRegistry().register(CRYSTAL_BUG);
        //PENUMBRIAN.setRegistryName(MODID, "penumbrian");
        //event.getRegistry().register(PENUMBRIAN);
        //TREE_HOPPER.setRegistryName(MODID, "tree_hopper");
        //event.getRegistry().register(TREE_HOPPER);
        STINGER.setRegistryName(MODID, "stinger");
        event.getRegistry().register(STINGER);
        NIGHTSTAG.setRegistryName(MODID, "nightstag");
        event.getRegistry().register(NIGHTSTAG);
        DECEITFUL_SNAPPER.setRegistryName(MODID, "deceitful_snapper");
        event.getRegistry().register(DECEITFUL_SNAPPER);
        BULB_ANGLER.setRegistryName(MODID, "bulb_angler");
        event.getRegistry().register(BULB_ANGLER);
        SKULK.setRegistryName(MODID, "skulk");
        event.getRegistry().register(SKULK);
//        SHADE_SQUIRREL.setRegistryName(MODID, "shade_squirrel");
//        event.getRegistry().register(SHADE_SQUIRREL);
        THROWN_GEODE.setRegistryName(MODID, "thrown_geode");
        event.getRegistry().register(THROWN_GEODE);
        SPORE_BOMB.setRegistryName(MODID, "spore_bomb");
        event.getRegistry().register(SPORE_BOMB);
        CLOUD.setRegistryName(MODID, "cloud");
        event.getRegistry().register(CLOUD);

        EntitySpawnPlacementRegistry.register(HUNTER, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::monsterCondition);
        EntitySpawnPlacementRegistry.register(CRYSTAL_BUG, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::mobCondition);
        EntitySpawnPlacementRegistry.register(NOVA, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::monsterCondition);
        EntitySpawnPlacementRegistry.register(DECEITFUL_SNAPPER, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::fishCondition);
        EntitySpawnPlacementRegistry.register(BULB_ANGLER, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::fishCondition);

        EntitySpawnPlacementRegistry.register(RIFTER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::monsterCondition);

        EntitySpawnPlacementRegistry.register(STINGER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::mobCondition);
        EntitySpawnPlacementRegistry.register(SKULK, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::mobCondition);
        EntitySpawnPlacementRegistry.register(NIGHTSTAG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MidnightEntities::mobCondition);
    }

    private static boolean fishCondition(EntityType<? extends AbstractFishEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).getBlock() == MidnightBlocks.DARK_WATER && (world.getBlockState(pos.down()).getBlock() == MidnightBlocks.DARK_WATER || world.getBlockState(pos.up()).getBlock() == MidnightBlocks.DARK_WATER);
    }

    private static boolean monsterCondition(EntityType<? extends MonsterEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && mobCondition(entityType, world, spawnReason, pos, random); // && lightCondition(world, pos, random)
    }

    private static boolean mobCondition(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getWorld().getBlockState(blockpos).canEntitySpawn(world, blockpos, entityType);
    }

    // unused
    private static boolean lightCondition(IWorld world, BlockPos pos, Random random) {
        if (world.getLightFor(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = world.getWorld().isThundering() ? world.getNeighborAwareLightSubtracted(pos, 10) : world.getLight(pos);
            return i <= random.nextInt(8);
        }
    }
}
