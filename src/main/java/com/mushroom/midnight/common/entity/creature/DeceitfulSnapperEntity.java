package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DeceitfulSnapperEntity extends AbstractFishEntity {
    private BlockPos spawnPosition;

    public DeceitfulSnapperEntity(EntityType<? extends DeceitfulSnapperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(MidnightItems.DECEITFUL_SNAPPER_BUCKET);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (inWater) {
            if (spawnPosition == null || spawnPosition.distanceSq((double) (int) posX, (double) (int) posY, (double) (int) posZ, true) < 4d || rand.nextInt(30) == 0) {
                spawnPosition = new BlockPos((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(4) - 1, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
                if (spawnPosition.getY() < 1 || (spawnPosition.getX() == (int) posX && spawnPosition.getZ() == (int) posZ) || world.getBlockState(spawnPosition).getMaterial() != Material.WATER) {
                    spawnPosition = null;
                }
            }
            if (spawnPosition != null) {
                double d0 = (double) spawnPosition.getX() + 0.5d - posX;
                double d1 = (double) spawnPosition.getY() + 0.1d - posY;
                double d2 = (double) spawnPosition.getZ() + 0.5d - posZ;
                double addX = (Math.signum(d0) * 0.2d - getMotion().x) * 0.1d;
                double addY = (Math.signum(d1) * 0.2d - getMotion().y) * 0.1d;
                double addZ = (Math.signum(d2) * 0.2d - getMotion().z) * 0.1d;
                setMotion(getMotion().add(addX, addY, addZ));
                moveForward = 0.2f;
                rotationYaw += MathHelper.wrapDegrees((float) ((MathHelper.atan2(getMotion().z, getMotion().x) * (180d / Math.PI)) - 90f) - rotationYaw);
            }
            if (getMotion().y > 0.007f && (Math.abs(getMotion().x) > 0.007f && Math.abs(getMotion().z) > 0.007f) && world.rand.nextFloat() < 0.001f) {
                setMotion(getMotion().add(0f, 0.8f, 0f));
            }
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return MidnightLootTables.LOOT_TABLE_DECEITFUL_SNAPPER;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.SNAPPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.SNAPPER_DEATH;
    }
}