package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class ThrownGeodeEntity extends ThrowableEntity implements IRendersAsItem {
    private static final byte POPPED_STATE_ID = 3;

    public ThrownGeodeEntity(EntityType<? extends ThrownGeodeEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownGeodeEntity(World world, double x, double y, double z) {
        super(MidnightEntities.THROWN_GEODE, x, y, z, world);
    }

    public ThrownGeodeEntity(World world, LivingEntity thrower) {
        super(MidnightEntities.THROWN_GEODE, thrower, world);
    }

    public ThrownGeodeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.THROWN_GEODE, world);
    }

    @Override
    protected void registerData() {
        // TODO ?
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == POPPED_STATE_ID) {
            this.spawnPopParticles();
        }
    }

    private void spawnPopParticles() {
        this.world.playSound(this.posX, this.posY, this.posZ, MidnightSounds.EGG_CRACKED, this.getSoundCategory(), 1f, this.rand.nextFloat() * 0.2f + 0.85f, false);

        for (int i = 0; i < 8; i++) {
            double velX = (this.rand.nextDouble() - 0.5) * 0.1;
            double velY = (this.rand.nextDouble() - 0.5) * 0.1;
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(MidnightItems.GEODE)), this.posX, this.posY + 0.1, this.posZ, velX, 0.1, velY);
        }

        for (int i = 0; i < 2; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * 0.4;
            double offsetY = (this.rand.nextDouble() - 0.5) * 0.4;
            double offsetZ = (this.rand.nextDouble() - 0.5) * 0.4;
            MidnightParticles.FURNACE_FLAME.spawn(this.world, this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            ((EntityRayTraceResult)result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1f);
        }

        if (!this.world.isRemote) {
            if (canBreakOn(result)) {
                ServerPlayerEntity player = this.owner instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.owner : null;
                if (player != null) {
                    MidnightCriterion.THROWN_GEODE.trigger(player);
                }
                dropLootWhenBroken(player);
                this.world.setEntityState(this, POPPED_STATE_ID);

            } else {
                MidnightUtil.spawnItemStack(this.world, getPosition(), MidnightItems.GEODE);
            }

            this.remove();
        }
    }

    private boolean canBreakOn(RayTraceResult result) {
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return false;
        }
        BlockState impactedState = this.world.getBlockState(((BlockRayTraceResult)result).getPos());
        return impactedState.getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON;
    }

    private void dropLootWhenBroken(@Nullable ServerPlayerEntity player) {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) this.world)
                .withParameter(LootParameters.THIS_ENTITY, this)
                .withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.GENERIC).withParameter(LootParameters.POSITION, getPosition());
        if (player != null) {
            builder = builder.withParameter(LootParameters.KILLER_ENTITY, player).withLuck(player.getLuck());
        }

        LootTable loottable = this.world.getServer().getLootTableManager().getLootTableFromLocation(MidnightLootTables.LOOT_TABLE_THROWN_GEODE);
        for (ItemStack itemstack : loottable.generate(builder.build(LootParameterSets.ENTITY))) {
            this.entityDropItem(itemstack, 0.1f);
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(MidnightItems.GEODE);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
