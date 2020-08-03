package midnight.common.entity.projectile;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import midnight.common.entity.MnEntities;
import midnight.common.item.MnItems;
import midnight.common.particle.MnParticleTypes;
import midnight.common.registry.MidnightLootTables;
import midnight.common.registry.MnSounds;
import midnight.common.util.MidnightUtil;

import javax.annotation.Nullable;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class ThrownGeodeEntity extends ThrowableEntity implements IRendersAsItem {
    private static final byte POPPED_STATE_ID = 3;

    public ThrownGeodeEntity(World world) {
        super(MnEntities.THROWN_GEODE, world);
    }

    public ThrownGeodeEntity(EntityType<? extends ThrownGeodeEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownGeodeEntity(World world, double x, double y, double z) {
        super(MnEntities.THROWN_GEODE, x, y, z, world);
    }

    public ThrownGeodeEntity(World world, LivingEntity thrower) {
        super(MnEntities.THROWN_GEODE, thrower, world);
    }

    public ThrownGeodeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MnEntities.THROWN_GEODE, world);
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
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), MnSounds.EGG_CRACKED, this.getSoundCategory(), 1f, this.rand.nextFloat() * 0.2f + 0.85f, false);

        for (int i = 0; i < 8; i++) {
            double velX = (this.rand.nextDouble() - 0.5) * 0.1;
            double velY = (this.rand.nextDouble() - 0.5) * 0.1;
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(MnItems.GEODE)), this.getPosX(), this.getPosY() + 0.1, this.getPosZ(), velX, 0.1, velY);
        }

        for (int i = 0; i < 2; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * 0.4;
            double offsetY = (this.rand.nextDouble() - 0.5) * 0.4;
            double offsetZ = (this.rand.nextDouble() - 0.5) * 0.4;
            this.world.addParticle(MnParticleTypes.FURNACE_FLAME, this.getPosX() + offsetX, this.getPosY() + offsetY, this.getPosZ() + offsetZ, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            ((EntityRayTraceResult) result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 1f);
        }

        if (!this.world.isRemote) {
            if (canBreakOn(result)) {
                ServerPlayerEntity player = this.func_234616_v_() instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.func_234616_v_() : null;
                /*if (player != null) {
                    MidnightCriterion.THROWN_GEODE.trigger(player);
                }*/
                dropLootWhenBroken(player);
                this.world.setEntityState(this, POPPED_STATE_ID);

            } else {
                MidnightUtil.spawnItemStack(this.world, getPositionVec(), MnItems.GEODE);
            }

            this.remove();
        }
    }

    private boolean canBreakOn(RayTraceResult result) {
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return false;
        }
        BlockState impactedState = this.world.getBlockState(((BlockRayTraceResult) result).getPos());
        return impactedState.getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON;
    }

    private void dropLootWhenBroken(@Nullable ServerPlayerEntity player) {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) this.world)
                .withParameter(LootParameters.THIS_ENTITY, this)
                .withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.GENERIC).withParameter(LootParameters.POSITION, new BlockPos(getPositionVec()));
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
        return new ItemStack(MnItems.GEODE);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
