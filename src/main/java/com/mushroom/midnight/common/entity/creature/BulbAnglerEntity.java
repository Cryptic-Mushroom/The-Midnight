package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.task.FindEatableFood;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Predicate;

public class BulbAnglerEntity extends AbstractFishEntity {
    private static final Predicate<Entity> canEatEntity = (p_213470_0_) -> {
        if (!(p_213470_0_ instanceof LivingEntity)) {
            return false;
        } else {
            LivingEntity livingentity = (LivingEntity) p_213470_0_;
            return livingentity.getLastAttackedEntity() != null && livingentity.getLastAttackedEntityTime() < livingentity.ticksExisted + 100;
        }
    };
    private int eatTicks;

    public BulbAnglerEntity(EntityType<? extends BulbAnglerEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    //TODO Change FishBucket
    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 3;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FindEatableFood(this, new ItemStack(MidnightItems.DECEITFUL_SNAPPER), 1.15D));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.15D, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.05D, 45));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DeceitfulSnapperEntity.class, 4, true, false, canEatEntity::test));

    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3 + this.world.rand.nextInt(3);
    }

    //TODO replase to bulb angler sound
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.SNAPPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.SNAPPER_DEATH;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public void livingTick() {
        if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            ++this.eatTicks;
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (this.canEatItem(itemstack)) {
                if (this.eatTicks > 200) {
                    ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
                    }

                    this.heal(2.0F);

                    this.eatTicks = 0;
                } else if (this.eatTicks > 160 && this.rand.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemstack), 1.0F, 1.0F);
                    this.world.setEntityState(this, (byte) 45);
                }
            }
        }
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 45) {
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemstack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double) this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).rotatePitch(-this.rotationPitch * ((float) Math.PI / 180F)).rotateYaw(-this.rotationYaw * ((float) Math.PI / 180F));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), this.posX + this.getLookVec().x / 2.0D, this.posY, this.posZ + this.getLookVec().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
            }
        } else {
            super.handleStatusUpdate(id);
        }

    }

    private boolean canEatItem(ItemStack itemstack) {
        return itemstack.getItem() == MidnightItems.DECEITFUL_SNAPPER;
    }

    private void spitOutItem(ItemStack stackIn) {
        if (!stackIn.isEmpty() && !this.world.isRemote) {
            ItemEntity itementity = new ItemEntity(this.world, this.posX + this.getLookVec().x, this.posY + 1.0D, this.posZ + this.getLookVec().z, stackIn);
            itementity.setPickupDelay(40);
            itementity.setThrowerId(this.getUniqueID());
            this.world.addEntity(itementity);
        }
    }

    private void spawnItem(ItemStack stackIn) {
        ItemEntity itementity = new ItemEntity(this.world, this.posX, this.posY, this.posZ, stackIn);
        this.world.addEntity(itementity);
    }

    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
     * better.
     */
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (!this.canEatItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) && this.canEatItem(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.spawnItem(itemstack.split(i - 1));
            }

            this.spitOutItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.split(1));
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
            this.onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.remove();
            this.eatTicks = 0;
        }

    }
}
