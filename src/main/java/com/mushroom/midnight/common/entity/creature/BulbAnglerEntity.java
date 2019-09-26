package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.task.FindEatableFood;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class BulbAnglerEntity extends AbstractFishEntity {
    private static final Predicate<Entity> canEatEntity = (p_213470_0_) -> {
        if (!(p_213470_0_ instanceof LivingEntity)) {
            return false;
        } else {
            LivingEntity livingentity = (LivingEntity)p_213470_0_;
            return livingentity.getLastAttackedEntity() != null && livingentity.getLastAttackedEntityTime() < livingentity.ticksExisted + 400;
        }
    };

    public BulbAnglerEntity(EntityType<? extends BulbAnglerEntity> entityType, World world) {
        super(entityType, world);
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
        this.goalSelector.addGoal(2, new FindEatableFood(this,new ItemStack(MidnightItems.DECEITFUL_SNAPPER), 1.15D){
            @Override
            public void resetTask() {
                super.resetTask();
                heal(2.0F);
                playSound(SoundEvents.ENTITY_GENERIC_EAT,0.65F,1.0F);
            }
        });
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.15D, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.05D, 45));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DeceitfulSnapperEntity.class, 10,true, false, canEatEntity::test));

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
}
