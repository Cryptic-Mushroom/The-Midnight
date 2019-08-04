package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.entity.RiftEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

public class RiftEntityReference {
    private UUID entityId;
    private DimensionType dimension;

    private WeakReference<RiftEntity> rift;

    public void set(RiftEntity rift) {
        this.rift = new WeakReference<>(rift);
        this.entityId = rift.getUniqueID();
        this.dimension = rift.world.dimension.getType();
    }

    @Nullable
    public RiftEntity compute() {
        RiftEntity cached = this.get();
        ServerWorld world = DimensionManager.getWorld(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER), this.dimension, false, false);

        if (world != null && cached == null) {
            Optional<Entity> entity = world.getEntities()
                    .filter(e -> e instanceof RiftEntity)
                    .filter(e -> e.getUniqueID().equals(this.entityId))
                    .findFirst();

            if (entity.isPresent()) {
                RiftEntity rift = (RiftEntity) entity.get();
                this.rift = new WeakReference<>(rift);
                return rift;
            }
        }

        return cached;
    }

    @Nullable
    public RiftEntity get() {
        RiftEntity rift = this.rift != null ? this.rift.get() : null;
        if (rift != null && this.isInvalid(rift)) {
            this.rift = null;
            return null;
        }
        return rift;
    }

    private boolean isInvalid(RiftEntity rift) {
        if (!rift.world.isRemote) {
            return DimensionManager.getWorld(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER), rift.world.dimension.getType(), false, false) == null;
        }
        return false;
    }

    public boolean hasReference() {
        return this.entityId != null;
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        if (this.entityId != null) {
            compound.putUniqueId("id", this.entityId);
            compound.putString("dimension", DimensionType.getKey(this.dimension).toString());
        }
        return compound;
    }

    public void deserialize(CompoundNBT compound) {
        if (compound.hasUniqueId("id")) {
            this.entityId = compound.getUniqueId("id");
            this.dimension = DimensionType.byName(new ResourceLocation(compound.getString("dimension")));
        }
    }
}
