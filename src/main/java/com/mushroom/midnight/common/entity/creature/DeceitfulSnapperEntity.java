package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.task.FishJumpGoal;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DeceitfulSnapperEntity extends AbstractFishEntity {

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
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FishJumpGoal(this, 60));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1D, false) {
        });
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 40) {
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
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

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
}
