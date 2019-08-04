package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.BridgeCreateMessage;
import com.mushroom.midnight.common.network.BridgeRemovalMessage;
import com.mushroom.midnight.common.network.BridgeStateMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BridgeTracker {
    private final RiftBridge bridge;
    private final Set<ServerPlayerEntity> currentTrackingPlayers = new HashSet<>();

    public BridgeTracker(RiftBridge bridge) {
        this.bridge = bridge;
    }

    public void update() {
        if (this.bridge.isDirty() && !this.currentTrackingPlayers.isEmpty()) {
            this.sendToTracking(new BridgeStateMessage(this.bridge));
            this.bridge.clearDirt();
        }

        Collection<ServerPlayerEntity> trackingPlayers = this.collectTrackingPlayers();

        for (ServerPlayerEntity player : trackingPlayers) {
            if (!this.currentTrackingPlayers.contains(player)) {
                Midnight.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new BridgeCreateMessage(this.bridge));
            }
        }

        for (ServerPlayerEntity player : this.currentTrackingPlayers) {
            if (!trackingPlayers.contains(player)) {
                Midnight.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new BridgeRemovalMessage(this.bridge.getId()));
            }
        }

        this.currentTrackingPlayers.clear();
        this.currentTrackingPlayers.addAll(trackingPlayers);
    }

    public <M> void sendToTracking(M message) {
        for (ServerPlayerEntity player : this.currentTrackingPlayers) {
            Midnight.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
        }
    }

    private Collection<ServerPlayerEntity> collectTrackingPlayers() {
        Collection<ServerPlayerEntity> trackingPlayers = new ArrayList<>();

        RiftEntity source = this.bridge.getSource();
        if (source != null) {
            this.collectTrackingPlayers(trackingPlayers, source);
        }

        RiftEntity target = this.bridge.getTarget();
        if (target != null) {
            this.collectTrackingPlayers(trackingPlayers, target);
        }

        return trackingPlayers;
    }

    private void collectTrackingPlayers(Collection<ServerPlayerEntity> trackingPlayers, RiftEntity target) {
        if (!(target.world instanceof ServerWorld)) {
            return;
        }
        ServerWorld world = (ServerWorld) target.world;
        ChunkManager.EntityTracker tracker = world.getChunkProvider().chunkManager.entities.get(target.getEntityId());
        if (tracker != null) {
            trackingPlayers.addAll(tracker.trackingPlayers);
        }
    }
}
