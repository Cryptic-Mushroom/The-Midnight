package com.mushroom.midnight.common.entity;

import com.google.common.collect.ImmutableSet;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.RiftParticleSystem;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.world.BridgeManager;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RiftEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final int OPEN_TIME = 20;
    public static final int CLOSE_SPEED = 2;
    public static final int CLOSE_TIME = OPEN_TIME / CLOSE_SPEED;

    public static final int UNSTABLE_TIME = 110;

    public static final int LIFETIME = 4000;

    public static final float PULL_RADIUS = 8.0F;
    public static final float PULL_INTENSITY = 5.0F;

    public static final double MAX_PULL_VELOCITY = 1.2;

    private RiftGeometry geometry;

    private RiftBridge bridge;

    private RiftParticleSystem particleSystem;

    private boolean wasStable = true;

    private int spawnedRifters = 0, failedSpawn = 0;

    private boolean invalid;

    public RiftEntity(EntityType<RiftEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        this.initGeometry(new Random().nextLong());
        if (world.isRemote) {
            this.particleSystem = new RiftParticleSystem(this);
        }
    }

    public RiftEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.RIFT, world);
    }

    private void initGeometry(long seed) {
        this.geometry = new RiftGeometry(seed, this.getWidth(), this.getHeight());
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        if (this.invalid) {
            this.remove();
            return;
        }

        RiftBridge bridge = this.getBridge();

        super.tick();

        if (!this.world.isRemote) {
            if (!bridge.exists) {
                this.remove();
                return;
            }

            if (!MidnightUtil.isMidnightDimension(this.world)) {
                this.updateOverworldBehavior();
            }

            if (bridge.open.getTimer() >= OPEN_TIME && !this.wasUsed()) {
                this.teleportEntities();
            }

            if (this.wasStable && bridge.unstable.get()) {
                this.playSound(MidnightSounds.RIFT_UNSTABLE, 1.0F, 1.0F);
                this.wasStable = false;
            }
        } else if (this.particleSystem != null) {
            this.particleSystem.updateParticles(this.world.rand);
        }
    }

    public void acceptBridge(RiftBridge riftBridge) {
        this.bridge = riftBridge;
        this.bridge.accept(this);
    }

    private void updateOverworldBehavior() {
        if (this.world.isDaytime() || this.world.getLightFor(LightType.BLOCK, this.getPosition()) > 5) {
            this.getBridge().unstable.set(true);
        }

        if (this.isUnstable() && !this.wasUsed()) {
            this.pullEntities();
        }

        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            if (!this.isUnstable() && MidnightConfig.general.rifterSpawnRarity.get() > 0 && this.spawnedRifters < MidnightConfig.general.maxRifterByRift.get() && this.world.rand.nextInt(MidnightConfig.general.rifterSpawnRarity.get()) == 0) {
                if (trySpawnRifter()) {
                    this.spawnedRifters++;
                    this.failedSpawn = 0;
                } else {
                    if (++failedSpawn > 5) {
                        this.spawnedRifters = MidnightConfig.general.maxRifterByRift.get();
                    }
                }
            }
        }
    }

    private boolean trySpawnRifter() {
        float theta = (float) (this.world.rand.nextFloat() * Math.PI * 2.0F);
        float offsetX = -MathHelper.sin(theta) * this.getWidth() * 0.9F;
        float offsetZ = MathHelper.cos(theta) * this.getWidth() * 0.9F;

        MobEntity entity;
        boolean isHunter = rand.nextFloat() < 0.01f;
        if (isHunter) {
            entity = MidnightEntities.HUNTER.create(this.world);
        } else {
            entity = MidnightEntities.RIFTER.create(this.world);
        }
        if (entity == null) {
            return false;
        }
        if (!isHunter) {
            ((RifterEntity) entity).spawnedThroughRift = true;
        }
        entity.setPositionAndRotation(this.posX + offsetX, this.posY, this.posZ + offsetZ, (float) Math.toDegrees(theta), 0.0F);

        if (entity.isNotColliding(this.world)) {
            this.world.addEntity(entity);
            if (isHunter) {
                spawnedRifters = MidnightConfig.general.maxRifterByRift.get();
            }
            return true;
        }
        return false;
    }

    private void pullEntities() {
        double pullIntensity = this.getPullIntensity();

        AxisAlignedBB pullBounds = this.getBoundingBox().grow(PULL_RADIUS);
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, pullBounds);
        for (Entity entity : entities) {
            if (!(entity instanceof RiftEntity)) {
                this.pullEntity(pullIntensity, entity);
            }
        }
    }

    public void pullEntity(double pullIntensity, Entity entity) {
        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.isFlying) {
            return;
        }

        double deltaX = this.posX - entity.posX;
        double deltaY = (this.posY + this.getHeight() / 2.0F) - (entity.posY + entity.getHeight() / 2.0F);
        double deltaZ = this.posZ - entity.posZ;

        double distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        if (distanceSq > 0.0) {
            double distance = Math.sqrt(distanceSq);
            double intensity = MathHelper.clamp(pullIntensity / distanceSq, 0.0F, 1.0F);

            double accX = (deltaX / distance) * intensity;
            double accY = (deltaY / distance) * intensity;
            double accZ = (deltaZ / distance) * intensity;

            Vec3d newMotion = entity.getMotion().add(accX, accY, accZ);
            if (newMotion.lengthSquared() > MAX_PULL_VELOCITY * MAX_PULL_VELOCITY) {
                newMotion.normalize();
                newMotion.scale(MAX_PULL_VELOCITY);
            }

            entity.setMotion(newMotion);

            entity.fallDistance = 0.0F;
        }
    }

    public double getPullIntensity() {
        if (this.wasUsed() || !this.isBridgeValid()) {
            return 0.0;
        }
        return Math.pow((double) this.getBridge().unstable.getTimer() / UNSTABLE_TIME, 5.0) * PULL_INTENSITY;
    }

    private void teleportEntities() {
        AxisAlignedBB bounds = this.getBoundingBox().grow(-0.4, 0.0, -0.4);
        List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, bounds, entity -> {
            if (entity.isPassenger() || (entity instanceof PlayerEntity && entity.isSpectator())) {
                return false;
            }
            return entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).map(cooldownCap -> cooldownCap.isReady() && !(entity instanceof RiftEntity)).orElse(false);
        });

        Set<RiftTravelEntry> recursedEntities = this.getRecursedTravelers(entities);

        for (RiftTravelEntry entry : recursedEntities) {
            Entity entity = entry.getEntity();
            if (entity instanceof IRiftTraveler) {
                ((IRiftTraveler) entity).onEnterRift(this);
            }
            entity.detach();

            if (this.shouldTravelThroughRift(entity)) {
                entry.travel(this);
            } else {
                entity.remove();
            }
        }
    }

    private boolean shouldTravelThroughRift(Entity entity) {
        return this.isEndpointLoaded() || entity instanceof PlayerEntity;
    }

    private Set<RiftTravelEntry> getRecursedTravelers(List<Entity> entities) {
        if (entities.isEmpty()) {
            return ImmutableSet.of();
        }
        Set<RiftTravelEntry> recursedEntities = new HashSet<>();
        for (Entity entity : entities) {
            if (entity instanceof IRiftTraveler) {
                IRiftTraveler traveler = (IRiftTraveler) entity;
                recursedEntities.add(traveler.createTravelEntry(this));
                recursedEntities.addAll(traveler.getAdditionalTravelers(this));
            } else {
                recursedEntities.add(new RiftTravelEntry(entity));
                for (Entity passenger : entity.getRecursivePassengers()) {
                    recursedEntities.add(new RiftTravelEntry(passenger));
                }
            }
        }
        return recursedEntities;
    }

    public boolean isOpen() {
        if (!this.isBridgeValid()) {
            return false;
        }
        return this.getBridge().open.get();
    }

    public boolean isUnstable() {
        if (!this.isBridgeValid()) {
            return false;
        }
        return this.getBridge().unstable.get();
    }

    public boolean wasUsed() {
        if (!this.isBridgeValid()) {
            return false;
        }
        return this.getBridge().used;
    }

    public RiftGeometry getGeometry() {
        return this.geometry;
    }

    @Nullable
    public RiftParticleSystem getParticleSystem() {
        return this.particleSystem;
    }

    public boolean isBridgeValid() {
        return this.bridge != null;
    }

    public RiftBridge getBridge() {
        if (!this.world.isRemote && this.bridge == null) {
            this.bridge = this.createBridge();
        }
        return this.bridge;
    }

    private RiftBridge createBridge() {
        BridgeManager trackerHandler = GlobalBridgeManager.getServer();

        RiftAttachment attachment = new RiftAttachment(this.getPosition(), this.rotationYaw);
        RiftBridge bridge = trackerHandler.createBridge(attachment);
        bridge.accept(this);
        trackerHandler.addBridge(bridge);

        return bridge;
    }

    public DimensionType getEndpointDimension() {
        if (MidnightUtil.isMidnightDimension(this.world)) {
            return DimensionType.OVERWORLD;
        } else {
            return MidnightDimensions.midnight();
        }
    }

    public boolean isEndpointLoaded() {
        RiftBridge bridge = this.getBridge();
        if (bridge == null) {
            return false;
        }
        return bridge.isEndpointLoaded(this.getEndpointDimension());
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putLong("geometry_seed", this.geometry.getSeed());
        compound.putInt("spawned_rifters", this.spawnedRifters);
        compound.putInt("failed_spawn", this.failedSpawn);
        if (this.bridge != null) {
            compound.putInt("bridge_id", this.bridge.getId());
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.initGeometry(compound.getLong("geometry_seed"));
        this.spawnedRifters = compound.getInt("spawned_rifters");
        this.failedSpawn = compound.getInt("failed_spawn");
        if (compound.contains("bridge_id")) {
            this.invalid = this.initBridge(compound.getInt("bridge_id"));
        }
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeBoolean(this.invalid);

        if (!this.invalid) {
            buffer.writeLong(this.geometry.getSeed());

            RiftBridge bridge = this.getBridge();
            buffer.writeInt(bridge.getId());
        }
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        this.invalid = buffer.readBoolean();
        if (!this.invalid) {
            this.initGeometry(buffer.readLong());
            this.initBridge(buffer.readInt());
        }
    }

    private boolean initBridge(int bridgeId) {
        BridgeManager tracker = GlobalBridgeManager.get(this.world.isRemote);

        RiftBridge bridge = tracker.getBridge(bridgeId);
        if (bridge == null) {
            if (this.world.isRemote) {
                bridge = new RiftBridge(bridgeId, new RiftAttachment(this.getPosition(), this.rotationYaw));
                GlobalBridgeManager.getClient().addBridge(bridge);
            } else {
                return true;
            }
        }

        this.acceptBridge(bridge);

        return false;
    }
}
