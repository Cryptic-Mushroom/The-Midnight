package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

public class EntityReference<T extends Entity> {
    private final World world;

    @Nullable
    private UUID entityId;

    @Nullable
    private WeakReference<T> cachedEntity;

    private long lastLookupTime;

    public EntityReference(World world) {
        this.world = world;
    }

    public void set(T entity) {
        this.entityId = entity.getUniqueID();
        this.cachedEntity = new WeakReference<>(entity);
    }

    public Optional<T> deref(boolean forceLoad) {
        if (!(this.world instanceof ServerWorld)) throw new IllegalStateException("Cannot dereference entity reference on client");

        if (this.entityId == null) {
            return Optional.empty();
        }

        T cached = this.getCachedEntity();
        if (cached != null && !cached.isAlive()) {
            this.cachedEntity = null;
            return Optional.empty();
        }

        if (cached != null) {
            return Optional.of(cached);
        }

        long totalWorldTime = this.world.getGameTime();
        if (forceLoad || totalWorldTime - this.lastLookupTime > 20) {
            Entity entity = ((ServerWorld) this.world).getEntityByUuid(this.entityId);
            if (entity != null) {
                this.updateCachedEntity(entity);
            }
            this.lastLookupTime = totalWorldTime;
        }

        return Optional.ofNullable(this.getCachedEntity());
    }

    public boolean isPresent() {
        return this.deref(false).isPresent();
    }

    @SuppressWarnings("unchecked")
    private void updateCachedEntity(Entity entity) {
        try {
            this.cachedEntity = new WeakReference<>((T) entity);
        } catch (ClassCastException e) {
            Midnight.LOGGER.warn("Entity contained reference to entity of unexpected type! {}", entity);
            this.cachedEntity = null;
        }
    }

    @Nullable
    private T getCachedEntity() {
        return this.cachedEntity != null ? this.cachedEntity.get() : null;
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        if (this.entityId != null) {
            compound.putUniqueId("uuid", this.entityId);
        }
        return compound;
    }

    public void deserialize(CompoundNBT compound) {
        if (compound.hasUniqueId("uuid")) {
            this.entityId = compound.getUniqueId("uuid");
        } else {
            this.entityId = null;
        }
    }
}
