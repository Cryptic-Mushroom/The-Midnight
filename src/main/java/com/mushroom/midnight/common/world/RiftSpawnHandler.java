package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RiftSpawnHandler {
    private static final double RIFT_SEPARATION = 64.0;
    private static final double PLAYER_SEPARATION = 16.0;

    private static final int REGION_SPAWN_CHANCE = 960;
    private static final int REGION_RANGE = 64 >> 5;

    private static boolean warnedOverworldUnloaded;

    public static void update() {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server == null) {
            return;
        }

        World world = DimensionManager.getWorld(0);
        if (world == null) {
            if (!warnedOverworldUnloaded) {
                Midnight.LOGGER.warn("Overworld unloaded! Cannot spawn Midnight rifts");
                warnedOverworldUnloaded = true;
            }
            return;
        }

        if (!world.getGameRules().getBoolean("doRiftSpawning")) {
            return;
        }

        World endpointWorld = DimensionManager.getWorld(ModDimensions.MIDNIGHT.getId());

        if (!world.isDaytime()) {
            Random random = world.rand;
            Set<BlockPos> spawnRegions = collectPlayerRegions(world.playerEntities);

            if (endpointWorld != null) {
                spawnRegions.addAll(collectPlayerRegions(endpointWorld.playerEntities));
            }

            for (BlockPos spawnRegion : spawnRegions) {
                if (random.nextInt(REGION_SPAWN_CHANCE) == 0) {
                    BlockPos riftPosition = generateRiftPosition(random, spawnRegion);
                    if (world.isBlockLoaded(riftPosition)) {
                        trySpawnRift(world, riftPosition);
                    } else if (endpointWorld != null && endpointWorld.isBlockLoaded(riftPosition)) {
                        trySpawnRift(endpointWorld, riftPosition);
                    }
                }
            }
        }
    }

    private static Set<BlockPos> collectPlayerRegions(List<EntityPlayer> players) {
        Set<BlockPos> spawnRegions = new HashSet<>();

        for (EntityPlayer player : players) {
            int regionX = (int) player.posX >> 5;
            int regionY = (int) player.posY >> 5;
            int regionZ = (int) player.posZ >> 5;
            for (BlockPos pos : BlockPos.getAllInBox(
                    regionX - REGION_RANGE, regionY - REGION_RANGE, regionZ - REGION_RANGE,
                    regionX + REGION_RANGE, regionY + REGION_RANGE, regionZ + REGION_RANGE
            )) {
                spawnRegions.add(pos);
            }
        }
        return spawnRegions;
    }

    private static void trySpawnRift(World world, BlockPos riftPosition) {
        if (world.isAirBlock(riftPosition)) {
            BlockPos correctedPosition = getCorrectedRiftPosition(world, riftPosition);
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

        EntityRift rift = new EntityRift(world);
        rift.acceptBridge(bridge);
        bridge.getAttachment().apply(rift);

        trackerHandler.addBridge(bridge);

        world.spawnEntity(rift);
    }

    private static boolean canRiftSpawn(World world, BlockPos pos) {
        if (world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, PLAYER_SEPARATION)) {
            return false;
        }

        AxisAlignedBB riftBounds = new AxisAlignedBB(pos).grow(RIFT_SEPARATION);
        if (!world.getEntitiesWithinAABB(EntityRift.class, riftBounds).isEmpty()) {
            return false;
        }

        AxisAlignedBB bounds = new AxisAlignedBB(pos).grow(1.0, 0.0, 1.0).expand(0.0, 1.0, 0.0);
        if (!world.getCollisionBoxes(null, bounds).isEmpty()) {
            return false;
        }

        if (Helper.isMidnightDimension(world)) {
            return true;
        } else {
            if (world.isMaterialInBB(bounds, Material.WATER) || world.isMaterialInBB(bounds, Material.LAVA)) {
                return false;
            }

            return world.getLightFor(EnumSkyBlock.BLOCK, pos) < 4;
        }
    }

    private static BlockPos generateRiftPosition(Random random, BlockPos region) {
        int x = (region.getX() << 5) + random.nextInt(32);
        int y = (region.getY() << 5) + random.nextInt(32);
        int z = (region.getZ() << 5) + random.nextInt(32);
        return new BlockPos(x, y, z);
    }

    @Nullable
    private static BlockPos getCorrectedRiftPosition(World world, BlockPos pos) {
        BlockPos surface = WorldUtil.findSurface(world, pos, 6);
        if (surface != null) {
            return surface;
        }
        return null;
    }
}
