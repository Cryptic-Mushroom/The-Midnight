/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
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
    protected void onEntityHit(EntityRayTraceResult rtr) {
        super.onEntityHit(rtr);

        // Damage entities
        rtr.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, getOwner()), 0);

        // Spawn geode item
        ItemEntity itemEntity = new ItemEntity(world, getX(), getY(), getZ(), getItem());
        world.addEntity(itemEntity);

        // Kill self, we're item now
        remove();
    }

    @Override
    protected void onBlockHit(BlockRayTraceResult rtr) {
        super.onBlockHit(rtr);

        BlockState state = world.getBlockState(rtr.getPos());

        // Sound to play on hit - if the geode did not break open it is the block's hit sound but louder
        SoundEvent sound = state.getSoundType(world, rtr.getPos(), null).getHitSound();
        float pitch = state.getSoundType(world, rtr.getPos(), null).getPitch();
        float volume = 1.7f;

        if (GeodeHardMaterials.isHard(state)) {

            // Causes break particles to appear
            world.setEntityState(this, (byte) 3);

            // If pear cracks, play the crack sound instead
            sound = MnSoundEvents.ENTITY_GEODE_BREAK;
            pitch = rand.nextFloat() * 0.3f + 0.85f;
            volume = 1;

            // Spawn loot
            MinecraftServer server = world.getServer();
            if (server != null) {
                LootTable loot = server.getLootTableManager().getLootTableFromLocation(MnLootTables.GAMEPLAY_GEODE);
                LootContext.Builder ctxbuilder = new LootContext.Builder((ServerWorld) world)
                                                     .withParameter(LootParameters.ORIGIN, getPositionVec())
                                                     .withParameter(LootParameters.THIS_ENTITY, this)
                                                     .withParameter(LootParameters.BLOCK_STATE, state)
                                                     .withRandom(rand);

                // Spawn item stacks
                List<ItemStack> stacks = loot.generate(ctxbuilder.build(MnLootParameterSets.GEODE));
                for (ItemStack stack : stacks) {
                    world.addEntity(new ItemEntity(world, getX(), getY(), getZ(), stack));
                }
            }
        } else {
            // If we did not break, spawn ourselves
            ItemEntity itemEntity = new ItemEntity(world, getX(), getY(), getZ(), getItem());
            world.addEntity(itemEntity);
        }

        // Play sound
        world.playSound(getX(), getY(), getZ(), sound, SoundCategory.PLAYERS, volume, pitch, false);

        // Kill self, we're item now or we broke open
        remove();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                world.addParticle(
                    new ItemParticleData(ParticleTypes.ITEM, getItem()),
                    getX(), getY(), getZ(),
                    (rand.nextDouble() - 0.5) * 0.08,
                    (rand.nextDouble() - 0.5) * 0.08,
                    (rand.nextDouble() - 0.5) * 0.08
                );
            }
        }
    }
}
