package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class CrystalBulbSpearEntity extends TridentEntity {
    private static final DataParameter<ItemStack> ITEMSTACK = EntityDataManager.createKey(CrystalBulbSpearEntity.class, DataSerializers.ITEMSTACK);

    private ItemStack thrownStack = new ItemStack(MidnightItems.CRYSTAL_BLUB_SPEAR);
    private boolean dealtDamage;

    public CrystalBulbSpearEntity(EntityType<? extends CrystalBulbSpearEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public CrystalBulbSpearEntity(World p_i48790_1_, LivingEntity p_i48790_2_, ItemStack p_i48790_3_) {
        this(p_i48790_1_, p_i48790_2_.posX, p_i48790_2_.posY + (double)p_i48790_2_.getEyeHeight() - (double)0.1F, p_i48790_2_.posZ);
        this.thrownStack = p_i48790_3_.copy();
        this.setRenderStack(this.thrownStack);
        this.setShooter(p_i48790_2_);
        if (p_i48790_2_ instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public CrystalBulbSpearEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        this(MidnightEntities.CRYSTAL_BLUB_SPEAR, p_i48791_1_);
        this.setPosition(p_i48791_2_,p_i48791_4_,p_i48791_6_);
    }

    public CrystalBulbSpearEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.CRYSTAL_BLUB_SPEAR, world);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ITEMSTACK, ItemStack.EMPTY);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {

            for (int i = 0; i < 10; i++) {
                double d0 = (double) this.getPosition().getX() + 2.0D - (double) (rand.nextFloat() * 4.0F);
                double d1 = (double) this.getPosition().getY() + 2.0D - (double) (rand.nextFloat() * 4.0F);
                double d2 = (double) this.getPosition().getZ() + 2.0D - (double) (rand.nextFloat() * 4.0F);
                world.addParticle(ParticleTypes.END_ROD, d0, d1, d2, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D);
            }
        }
        super.handleStatusUpdate(id);
    }


    protected ItemStack getArrowStack() {
        return this.thrownStack.copy();
    }

    public ItemStack getRenderStack() {
        return this.dataManager.get(ITEMSTACK);
    }

    //for render
    public void setRenderStack(ItemStack stack) {
        this.dataManager.set(ITEMSTACK, stack);
    }

    protected EntityRayTraceResult func_213866_a(Vec3d p_213866_1_, Vec3d p_213866_2_) {
        return this.dealtDamage ? null : super.func_213866_a(p_213866_1_, p_213866_2_);
    }

    protected void func_213868_a(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = 5.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;
            f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource = DamageSource.causeTridentDamage(this, (Entity) (entity1 == null ? this : entity1));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.attackEntityFrom(damagesource, f) && entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity) entity;
            if (entity1 instanceof LivingEntity) {
                EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
                EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity1);
            }

            this.world.setEntityState(this, (byte) 4);

            for (LivingEntity shockedEntity : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(2.0D, 2.0D, 2.0D))) {
                if (shockedEntity != entity1 && (!(shockedEntity instanceof ArmorStandEntity) || !((ArmorStandEntity) shockedEntity).hasMarker()) && this.getDistanceSq(shockedEntity) < 14.0D) {
                    float f2 = 2.0F;

                    if (shockedEntity.isWet()) {
                        f2 += 4.0F;
                    }

                    shockedEntity.attackEntityFrom(new EntityDamageSource("shocked", entity1) {
                        @Override
                        public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
                            return new TranslationTextComponent("death." + Midnight.MODID + "." + damageType, entity.getDisplayName());
                        }
                    }.setDamageIsAbsolute(), f2);
                }
            }

            this.arrowHit(livingentity1);
        }

        this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.thrownStack)) {
            BlockPos blockpos = entity.getPosition();
            if (this.world.isSkyLightMax(blockpos)) {
                LightningBoltEntity lightningboltentity = new LightningBoltEntity(this.world, (double) blockpos.getX() + 0.5D, (double) blockpos.getY(), (double) blockpos.getZ() + 0.5D, false);
                lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
                ((ServerWorld) this.world).addLightningBolt(lightningboltentity);
                soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
                f1 = 5.0F;
            }
        }

        this.playSound(soundevent, f1, 1.0F);
    }

    protected SoundEvent func_213867_k() {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Spear", 10)) {
            this.thrownStack = ItemStack.read(compound.getCompound("Spear"));
            this.setRenderStack(this.thrownStack);
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Spear", this.thrownStack.write(new CompoundNBT()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }


    protected float getWaterDrag() {
        return 0.985F;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}