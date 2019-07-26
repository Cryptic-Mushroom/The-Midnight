package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightGameRules;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RiftSpawnHandler {
    private static final double RIFT_SEPARATION = 64.0;
    private static final double PLAYER_SEPARATION = 16.0;

    private static final int REGION_RANGE = 64 >> 5;

    private static boolean warnedOverworldUnloaded;

    public static void update() {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        if (server == null) {
            return;
        }

        ServerWorld world = DimensionManager.getWorld(server, DimensionType.OVERWORLD, false, false);
        if (world == null) {
            if (!warnedOverworldUnloaded) {
                Midnight.LOGGER.warn("Overworld unloaded! Cannot spawn Midnight rifts");
                warnedOverworldUnloaded = true;
            }
            return;
        }

        if (!world.getGameRules().getBoolean(MidnightGameRules.DO_RIFT_SPAWNING)) {
            return;
        }

        World endpointWorld = DimensionManager.getWorld(server, MidnightDimensions.midnight(), false, false);
        if (!world.isDaytime()) {
            Random random = world.rand;
            Set<BlockPos> spawnRegions = collectPlayerRegions(world.getPlayers());

            if (endpointWorld != null) {
                spawnRegions.addAll(collectPlayerRegions(endpointWorld.getPlayers()));
            }

            for (BlockPos spawnRegion : spawnRegions) {
                if (random.nextInt(MidnightConfig.general.riftSpawnRarity.get()) == 0) {
                    BlockPos riftPosition = generateRiftPosition(random, spawnRegion);
                    if (endpointWorld != null && (world.getWorldBorder().contains(riftPosition) != endpointWorld.getWorldBorder().contains(riftPosition))) {
                        continue;
                    }
                    if (world.isBlockLoaded(riftPosition)) {
                        trySpawnRift(world, riftPosition);
                    } else if (endpointWorld != null && endpointWorld.isBlockLoaded(riftPosition)) {
                        trySpawnRift(endpointWorld, riftPosition);
                    }
                }
            }
        }
    }

    private static Set<BlockPos> collectPlayerRegions(List<? extends PlayerEntity> players) {
        Set<BlockPos> spawnRegions = new HashSet<>();

        for (PlayerEntity player : players) {
            int regionX = (int) player.posX >> 5;
            int regionY = (int) player.posY >> 5;
            int regionZ = (int) player.posZ >> 5;
            BlockPos.getAllInBox(
                    regionX - REGION_RANGE, regionY - REGION_RANGE, regionZ - REGION_RANGE,
                    regionX + REGION_RANGE, regionY + REGION_RANGE, regionZ + REGION_RANGE
            ).forEach(spawnRegions::add);
        }
        return spawnRegions;
    }

    private static void trySpawnRift(World world, BlockPos riftPosition) {
        if (world.isAirBlock(riftPosition)) {
            BlockPos correctedPosition = WorldUtil.findSurface(world, riftPosition, 6);
            if (correctedPosition == null || !canRiftSpawn(world, correctedPosition)) {
                return;
            }
            spawnRift(world, correctedPosition);
        }
    }

    private static void spawnRift(World world, BlockPos pos) {
        float yaw = world.rand.nextFloat() * 360.0F;
        RiftAttachment attachment = new RiftAttachment(pos, yaw);

        BridgeManager trackerHandler = GlobalBridgeManager.getServer();
        RiftBridge bridge = trackerHandler.createBridge(attachment);

        RiftEntity rift = MidnightEntities.RIFT.create(world);
        if (rift != null) {
            rift.acceptBridge(bridge);
            bridge.getAttachment().apply(rift);
            trackerHandler.addBridge(bridge);
            world.addEntity(rift);
        }
    }

    private static boolean canRiftSpawn(World world, BlockPos pos) {
        if (world.isPlayerWithin(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, PLAYER_SEPARATION)) {
            return false;
        }
        AxisAlignedBB riftBounds = new AxisAlignedBB(pos).grow(RIFT_SEPARATION);
        if (!world.getEntitiesWithinAABB(RiftEntity.class, riftBounds).isEmpty()) {
            return false;
        }
        AxisAlignedBB bounds = new AxisAlignedBB(pos).grow(1.0, 0.0, 1.0).expand(0.0, 1.0, 0.0);
        if (!world.areCollisionShapesEmpty(bounds)) {
            return false;
        }
        return MidnightUtil.isMidnightDimension(world) || (!world.containsAnyLiquid(bounds) && world.getLightFor(LightType.BLOCK, pos) < 4);
    }

    private static BlockPos generateRiftPosition(Random random, BlockPos region) {
        int x = (region.getX() << 5) + random.nextInt(32);
        int y = (region.getY() << 5) + random.nextInt(32);
        int z = (region.getZ() << 5) + random.nextInt(32);
        return new BlockPos(x, y, z);
    }
}
