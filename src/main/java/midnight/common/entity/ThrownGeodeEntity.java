/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.entity;

import midnight.api.util.GeodeHardMaterials;
import midnight.common.item.MnItems;
import midnight.common.misc.MnLootParameterSets;
import midnight.common.misc.MnLootTables;
import midnight.common.misc.MnSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ThrownGeodeEntity extends FlyingItemEntity {
    public ThrownGeodeEntity(EntityType<? extends ThrownGeodeEntity> type, World world) {
        super(type, world);
    }

    public ThrownGeodeEntity(World world) {
        super(MnEntityTypes.THROWN_GEODE, world);
    }

    public ThrownGeodeEntity(double x, double y, double z, World world) {
        super(MnEntityTypes.THROWN_GEODE, x, y, z, world);
    }

    public ThrownGeodeEntity(LivingEntity thrower, World world) {
        super(MnEntityTypes.THROWN_GEODE, thrower, world);
    }

    @Override
    protected Item getDefaultItem() {
        return MnItems.GEODE;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult rtr) {
        super.onHitEntity(rtr);

        // Damage entities
        rtr.getEntity().hurt(DamageSource.thrown(this, getOwner()), 0);

        // Spawn geode item
        ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), getItem());
        level.addFreshEntity(itemEntity);

        // Kill self, we're item now
        remove();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult rtr) {
        super.onHitBlock(rtr);

        BlockState state = level.getBlockState(rtr.getBlockPos());

        // Sound to play on hit - if the geode did not break open it is the block's hit sound but louder
        SoundEvent sound = state.getSoundType(level, rtr.getBlockPos(), null).getHitSound();
        float pitch = state.getSoundType(level, rtr.getBlockPos(), null).getPitch();
        float volume = 1.7f;

        if (GeodeHardMaterials.isHard(state)) {

            // Causes break particles to appear
            level.broadcastEntityEvent(this, (byte) 3);

            // If pear cracks, play the crack sound instead
            sound = MnSoundEvents.ENTITY_GEODE_BREAK;
            pitch = random.nextFloat() * 0.3f + 0.85f;
            volume = 1;

            // Spawn loot
            MinecraftServer server = level.getServer();
            if (server != null) {
                LootTable loot = server.getLootTables().get(MnLootTables.GAMEPLAY_GEODE);
                LootContext.Builder ctxbuilder = new LootContext.Builder((ServerWorld) level)
                                                     .withParameter(LootParameters.ORIGIN, position())
                                                     .withParameter(LootParameters.THIS_ENTITY, this)
                                                     .withParameter(LootParameters.BLOCK_STATE, state)
                                                     .withRandom(random);

                // Spawn item stacks
                List<ItemStack> stacks = loot.getRandomItems(ctxbuilder.create(MnLootParameterSets.GEODE));
                for (ItemStack stack : stacks) {
                    level.addFreshEntity(new ItemEntity(level, getX(), getY(), getZ(), stack));
                }
            }
        } else {
            // If we did not break, spawn ourselves
            ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), getItem());
            level.addFreshEntity(itemEntity);
        }

        // Play sound
        level.playLocalSound(getX(), getY(), getZ(), sound, SoundCategory.PLAYERS, volume, pitch, false);

        // Kill self, we're item now or we broke open
        remove();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                level.addParticle(
                    new ItemParticleData(ParticleTypes.ITEM, getItem()),
                    getX(), getY(), getZ(),
                    (random.nextDouble() - 0.5) * 0.08,
                    (random.nextDouble() - 0.5) * 0.08,
                    (random.nextDouble() - 0.5) * 0.08
                );
            }
        }
    }
}
