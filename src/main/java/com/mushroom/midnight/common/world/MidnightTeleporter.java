package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.entity.RiftEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.Heightmap;

public class MidnightTeleporter {
    public static final MidnightTeleporter INSTANCE = new MidnightTeleporter();

    public static final int COOLDOWN = 40;

    private MidnightTeleporter() {
    }

    public void teleport(Entity entity, RiftEntity originRift) {
        RiftBridge bridge = originRift.getBridge();
        if (bridge == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Bridge not present on portal {}", originRift);
            return;
        }

        RiftEntity endpointRift = bridge.computeEndpoint(originRift.getEndpointDimension());
        if (endpointRift == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Endpoint not present from portal {}", originRift);
            return;
        }

        Vec3d placementPos = this.findPlacementPos(entity, endpointRift);

        ServerWorld endpointWorld = (ServerWorld) endpointRift.world;

        Entity teleportedEntity = this.teleportEntity(entity, endpointWorld, placementPos);

        teleportedEntity.fallDistance = 0.0F;
        teleportedEntity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(traveller -> traveller.setCooldown(COOLDOWN));
    }

    private Entity teleportEntity(Entity entity, ServerWorld endpointWorld, Vec3d endpoint) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.teleport(endpointWorld, endpoint.x, endpoint.y, endpoint.z, entity.rotationYaw, entity.rotationPitch);
            return player;
        }

        entity.detach();
        entity.dimension = endpointWorld.dimension.getType();

        Entity teleportedEntity = entity.getType().create(endpointWorld);
        if (teleportedEntity == null) {
            return entity;
        }

        teleportedEntity.copyDataFromOld(entity);
        teleportedEntity.setLocationAndAngles(endpoint.x, endpoint.y, endpoint.z, entity.rotationYaw, entity.rotationPitch);
        teleportedEntity.setRotationYawHead(entity.rotationYaw);
        endpointWorld.func_217460_e(teleportedEntity);

        return teleportedEntity;
    }

    private Vec3d findPlacementPos(Entity entity, RiftEntity endpointRift) {
        float angle = (float) Math.toRadians(entity.rotationYaw);
        float displacementX = -MathHelper.sin(angle) * endpointRift.getWidth() / 2.0F;
        float displacementZ = MathHelper.cos(angle) * endpointRift.getWidth() / 2.0F;

        Vec3d placementPos = new Vec3d(endpointRift.posX + displacementX, endpointRift.posY + 0.5, endpointRift.posZ + displacementZ);
        if (!endpointRift.world.checkBlockCollision(this.getEntityBoundAt(entity, placementPos))) {
            return placementPos;
        }

        BlockPos placementBlockPos = new BlockPos(placementPos.x, placementPos.y, placementPos.z);
        BlockPos minPos = placementBlockPos.add(-2, -2, -2);
        BlockPos maxPos = placementBlockPos.add(2, 2, 2);
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            Vec3d originPos = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            AxisAlignedBB entityBound = this.getEntityBoundAt(entity, originPos);
            if (entityBound.intersects(endpointRift.getBoundingBox())) {
                continue;
            }
            if (!endpointRift.world.checkBlockCollision(entityBound)) {
                return originPos;
            }
        }

        BlockPos surface = endpointRift.world.getHeight(Heightmap.Type.MOTION_BLOCKING, placementBlockPos);
        return new Vec3d(placementPos.x + 0.5, surface.getY() + 0.5, placementPos.z + 0.5);
    }

    private AxisAlignedBB getEntityBoundAt(Entity entity, Vec3d pos) {
        float halfWidth = entity.getWidth() / 2.0F;
        return new AxisAlignedBB(
                pos.x - halfWidth, pos.y, pos.z - halfWidth,
                pos.x + halfWidth, pos.y + entity.getHeight(), pos.z + halfWidth
        );
    }
}
