package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.common.entity.util.RiftEntityReference;
import com.mushroom.midnight.common.entity.util.ToggleAnimation;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.util.BitFlags;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;

public class RiftBridge {
    public final ToggleAnimation open = new ToggleAnimation(RiftEntity.OPEN_TIME);
    public final ToggleAnimation unstable = new ToggleAnimation(RiftEntity.UNSTABLE_TIME);
    public boolean used;

    public boolean exists = true;

    private final int id;
    private RiftAttachment attachment;

    private boolean sentOpen;
    private boolean sentUnstable;
    private boolean sentUsed;

    private int ticks;

    private int prevOpenTimer;
    private int prevUnstableTimer;

    private final BridgeTracker tracker = new BridgeTracker(this);

    private final RiftEntityReference source = new RiftEntityReference();
    private final RiftEntityReference target = new RiftEntityReference();

    public RiftBridge(int id, RiftAttachment attachment) {
        this.id = id;
        this.attachment = attachment;

        this.open.set(true);
    }

    public void tickTimers() {
        this.prevOpenTimer = this.open.getTimer();
        this.prevUnstableTimer = this.unstable.getTimer();

        this.ticks++;

        if (this.open.get()) {
            this.open.setRate(1);
        } else {
            this.open.setRate(RiftEntity.CLOSE_SPEED);
        }

        this.open.update();
        this.unstable.update();
    }

    public boolean tickState() {
        this.tracker.update();

        this.trySpawnEndpoint(DimensionType.OVERWORLD);
        this.trySpawnEndpoint(MidnightDimensions.midnight());

        if (this.unstable.get()) {
            if (this.unstable.getTimer() >= RiftEntity.UNSTABLE_TIME && this.open.get()) {
                this.open.set(false);
            }
        } else if (this.ticks > RiftEntity.LIFETIME) {
            this.unstable.set(true);
        }

        RiftEntity source = this.getSource();
        if (source != null && !source.isAlive()) {
            return true;
        }

        RiftEntity target = this.getTarget();
        if (target != null && !target.isAlive()) {
            return true;
        }

        return !this.open.get() && this.open.getTimer() <= 0;
    }

    private void trySpawnEndpoint(DimensionType endpointDimension) {
        RiftEntityReference endpointReference = this.getEndpointReference(endpointDimension);
        if (!endpointReference.hasReference()) {
            World world = DimensionManager.getWorld(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER), endpointDimension, false, false);
            if (world != null && world.isBlockLoaded(this.attachment.getPos())) {
                this.spawnRiftEntity(world);
            }
        }
    }

    private void spawnRiftEntity(World world) {
        RiftEntity rift = MidnightEntities.RIFT.create(world);
        if (rift != null) {
            rift.acceptBridge(this);
            RiftAttachment surfaceAttachment = this.attachment.fixedToSurface(world);
            surfaceAttachment.apply(rift);
            // Force the chunk we're spawning in to be loaded
            world.getChunk(rift.getPosition());
            world.addEntity(rift);
        }
    }

    public RiftEntity computeEndpoint(DimensionType endpointDimension) {
        RiftEntityReference endpointReference = this.getEndpointReference(endpointDimension);
        if (!endpointReference.hasReference()) {
            MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            this.spawnRiftEntity(server.getWorld(endpointDimension));
        }
        RiftEntity rift = endpointReference.compute();
        if (rift == null) {
            MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            this.spawnRiftEntity(server.getWorld(endpointDimension));
        }
        return endpointReference.get();
    }

    public boolean isEndpointLoaded(DimensionType endpointDimension) {
        ServerWorld world = DimensionManager.getWorld(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER), endpointDimension, false, false);
        return world != null && world.isBlockLoaded(this.attachment.getPos());
    }

    public void writeState(ByteBuf buffer) {
        buffer.writeByte(new BitFlags()
                .withBit(0, this.open.get())
                .withBit(1, this.unstable.get())
                .withBit(2, this.used)
                .toInner()
        );

        buffer.writeInt((this.ticks & 0xFFFF) << 16
                | (this.open.getTimer() & 0xFF) << 8
                | this.unstable.getTimer() & 0xFF
        );
    }

    public void handleState(ByteBuf buffer) {
        BitFlags flags = new BitFlags(buffer.readByte());

        this.open.set(flags.getBit(0));
        this.unstable.set(flags.getBit(1));
        this.used = flags.getBit(2);

        long packedTimers = buffer.readUnsignedInt();

        this.ticks = (int) ((packedTimers >> 16) & 0xFFFF);
        this.open.setTimer((int) ((packedTimers >> 8) & 0xFF));
        this.unstable.setTimer((int) (packedTimers & 0xFF));
    }

    public void clearDirt() {
        this.sentOpen = this.open.get();
        this.sentUnstable = this.unstable.get();
        this.sentUsed = this.used;
    }

    public boolean isDirty() {
        return this.sentOpen != this.open.get()
                || this.sentUnstable != this.unstable.get()
                || this.sentUsed != this.used;
    }

    public int getId() {
        return this.id;
    }

    public void setAttachment(RiftAttachment attachment) {
        this.attachment = attachment;
    }

    public RiftAttachment getAttachment() {
        return this.attachment;
    }

    public float getOpenAnimation(float partialTicks) {
        return this.prevOpenTimer + (this.open.getTimer() - this.prevOpenTimer) * partialTicks;
    }

    public float getUnstableAnimation(float partialTicks) {
        return this.prevUnstableTimer + (this.unstable.getTimer() - this.prevUnstableTimer) * partialTicks;
    }

    public int getTimeUntilClose() {
        if (this.unstable.get()) {
            return 0;
        }
        return Math.max(RiftEntity.LIFETIME - this.ticks, 0);
    }

    public void accept(RiftEntity rift) {
        DimensionType endpointDimension = rift.world.dimension.getType();
        this.getEndpointReference(endpointDimension).set(rift);
    }

    private RiftEntityReference getEndpointReference(DimensionType endpointDimension) {
        return endpointDimension == MidnightDimensions.midnight() ? this.target : this.source;
    }

    public void close() {
        this.used = true;
        this.unstable.set(true);
    }

    public BridgeTracker getTracker() {
        return this.tracker;
    }

    @Nullable
    public RiftEntity getSource() {
        return this.source.get();
    }

    @Nullable
    public RiftEntity getTarget() {
        return this.target.get();
    }

    public void remove() {
        this.exists = false;
        this.prevOpenTimer = this.open.getTimer();
        this.prevUnstableTimer = this.unstable.getTimer();
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        compound.putInt("id", this.id);
        compound.put("attachment", this.attachment.serialize(new CompoundNBT()));

        compound.put("open", this.open.serialize(new CompoundNBT()));
        compound.put("unstable", this.unstable.serialize(new CompoundNBT()));
        compound.putBoolean("used", this.used);
        compound.putInt("ticks", this.ticks);

        compound.put("source", this.source.serialize(new CompoundNBT()));
        compound.put("target", this.target.serialize(new CompoundNBT()));

        return compound;
    }

    public static RiftBridge deserialize(CompoundNBT compound) {
        int id = compound.getInt("id");
        RiftAttachment attachment = RiftAttachment.deserialize(compound.getCompound("attachment"));

        RiftBridge bridge = new RiftBridge(id, attachment);
        bridge.open.deserialize(compound.getCompound("open"));
        bridge.unstable.deserialize(compound.getCompound("unstable"));
        bridge.used = compound.getBoolean("used");
        bridge.ticks = compound.getInt("ticks");

        bridge.source.deserialize(compound.getCompound("source"));
        bridge.target.deserialize(compound.getCompound("target"));

        return bridge;
    }

    @Override
    public String toString() {
        return "RiftBridge{open=" + this.open + ", unstable=" + this.unstable + ", exists=" + this.exists + ", id=" + this.id + '}';
    }
}
