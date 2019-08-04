package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RiftTraveller implements ICapabilityProvider {
    private RiftEntity enteredRift;

    private int cooldown;

    public void update(Entity entity) {
        if (this.cooldown > 0) {
            AxisAlignedBB bounds = entity.getBoundingBox();
            if (entity.world.getEntitiesWithinAABB(RiftEntity.class, bounds).isEmpty()) {
                this.cooldown--;
            }
        }

        if (entity.world instanceof ServerWorld) {
            if (this.enteredRift != null && this.isReady()) {
                MidnightTeleporter.INSTANCE.teleport(entity, this.enteredRift);
                this.enteredRift = null;
            }
        }
    }

    public void enterRift(RiftEntity rift) {
        if (!this.isReady()) return;
        this.enteredRift = rift;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isReady() {
        return this.cooldown <= 0;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return capability == Midnight.RIFT_TRAVELLER_CAP ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
    }
}
