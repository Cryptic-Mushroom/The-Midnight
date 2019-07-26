package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.UnstableBushBlock;
import com.mushroom.midnight.common.block.UnstableBushBloomedBlock;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.capability.AnimationCapability.Type;
import com.mushroom.midnight.common.entity.task.ChargeGoal;
import com.mushroom.midnight.common.entity.task.EatGrassGoal;
import com.mushroom.midnight.common.entity.task.NeutralGoal;
import com.mushroom.midnight.common.entity.task.SearchForBlockGoal;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.item.UnstableFruitItem;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;
import java.util.function.Predicate;

public class NightStagEntity extends AnimalEntity {
    private static final DataParameter<Integer> ANTLER_TYPE = EntityDataManager.createKey(NightStagEntity.class, DataSerializers.VARINT);
    public static final int MAX_ANTLER_TYPE = 9;
    private static final AttributeModifier CHILD_ATTACK_MALUS = new AttributeModifier(UUID.fromString("c0f32cda-a4fd-4fe4-8b3f-15612ef9a52f"), "nightstag_child_attack_malus", -2d, AttributeModifier.Operation.ADDITION);
    private static final int GROWING_TIME = -24000;
    private static final Predicate<BlockState> FRUIT_PREDICATE = p -> p.getBlock() instanceof UnstableBushBloomedBlock && p.get(UnstableBushBloomedBlock.HAS_FRUIT);
    private final AnimationCapability animCap = new AnimationCapability();
    private int temptTime = 400;

    public NightStagEntity(EntityType<? extends NightStagEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public AgeableEntity createChild(AgeableEntity entity) {
        NightStagEntity child = MidnightEntities.NIGHTSTAG.create(world);
        if (child != null) {
            child.setGrowingAge(GROWING_TIME);
            child.setAntlerType(((NightStagEntity) entity).getAntlerType());
        }
        return child;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        if (this.rand.nextInt(5) == 0) {
            setGrowingAge(GROWING_TIME);
        }
        if (MidnightUtil.isMidnightDimension(world)) {
            Biome biome = world.getBiome(getPosition());
            int random = this.rand.nextInt(3);
            if (biome == MidnightSurfaceBiomes.VIGILANT_FOREST || biome == MidnightSurfaceBiomes.HILLY_VIGILANT_FOREST) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 3 : 7);
            } else if (biome == MidnightSurfaceBiomes.NIGHT_PLAINS || biome == MidnightSurfaceBiomes.WARPED_FIELDS) {
                setAntlerType(random == 0 ? 0 : 1);
            } else if (biome == MidnightSurfaceBiomes.RUNEBUSH_GROVE) {
                setAntlerType(random == 0 ? 3 : 8);
            } else if (biome == MidnightSurfaceBiomes.DECEITFUL_BOG) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 2 : 4);
            } else if (biome == MidnightSurfaceBiomes.FUNGI_FOREST || biome == MidnightSurfaceBiomes.HILLY_FUNGI_FOREST) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 2 : 3);
            } else if (biome == MidnightSurfaceBiomes.CRYSTAL_SPIRES) {
                setAntlerType(random == 0 ? 5 : 6);
            } else if (biome == MidnightSurfaceBiomes.PHANTASMAL_VALLEY || biome == MidnightSurfaceBiomes.OBSCURED_PEAKS || biome == MidnightSurfaceBiomes.OBSCURED_PLATEAU || biome == MidnightSurfaceBiomes.BLACK_RIDGE) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 5 : 7);
            } else {
                setAntlerType(this.rand.nextInt(MAX_ANTLER_TYPE));
            }
        } else {
            setAntlerType(this.rand.nextInt(MAX_ANTLER_TYPE));
        }
        return livingdata;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ANTLER_TYPE, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("antler_type", getAntlerType());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("antler_type", Constants.NBT.TAG_INT)) {
            int antlerType = compound.getInt("antler_type");
            setAntlerType(antlerType >= 0 && antlerType < MAX_ANTLER_TYPE ? antlerType : 0);
        }
    }

    public void setAntlerType(int antlerType) {
        dataManager.set(ANTLER_TYPE, antlerType >= 0 && antlerType < MAX_ANTLER_TYPE ? antlerType : 0);
    }

    public int getAntlerType() {
        return dataManager.get(ANTLER_TYPE);
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MidnightSounds.NIGHTSTAG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.NIGHTSTAG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.NIGHTSTAG_DEATH;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState stateIn) {
        playSound(MidnightSounds.NIGHTSTAG_STEP, 0.15f, 1f);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        Block belowBlock = world.getBlockState(pos.down()).getBlock();
        return belowBlock == MidnightBlocks.GRASS_BLOCK || belowBlock == MidnightBlocks.NIGHTSTONE ? 10f : 9f - (world.getBrightness(pos) * 10f);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (getPosition().getY() <= worldIn.getSeaLevel()) {
            return false;
        }
        return super.canSpawn(worldIn, spawnReasonIn);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new NeutralGoal(this, new PanicGoal(this, 1.2d), true));
        this.goalSelector.addGoal(1, new NeutralGoal(this, new ChargeGoal(this, 1.2d, 200, 0.25f), false));
        this.goalSelector.addGoal(2, new NeutralGoal(this, new MeleeAttackGoal(this, 1d, false), false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1d, Ingredient.EMPTY, false) {
            @Override
            protected boolean isTempting(ItemStack stack) {
                return isBreedingItem(stack);
            }

            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && !this.closestPlayer.isCreative()) {
                    if (--temptTime < 0) {
                        temptTime = 400;
                        setAttackTarget(this.closestPlayer);
                    }
                }
                return valid;
            }
        });
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1d));
        this.goalSelector.addGoal(4, new SearchForBlockGoal(this, FRUIT_PREDICATE, 0.7d, 8, 200));
        this.goalSelector.addGoal(5, new EatGrassGoal(this, 40, false, FRUIT_PREDICATE) {
            @Override
            public boolean shouldExecute() {
                BlockPos currentPos;
                return super.shouldExecute() || ((currentPos = getPosition()).equals(getHomePosition()) && FRUIT_PREDICATE.test(world.getBlockState(currentPos)));
            }

            @Override
            protected void eatPlant(BlockState state, BlockPos pos) {
                this.owner.world.setBlockState(pos, MidnightBlocks.UNSTABLE_BUSH.getDefaultState().with(UnstableBushBlock.STAGE, UnstableBushBlock.MAX_STAGE), 2);
                if (isChild()) {
                    setGrowingAge(Math.min(getGrowingAge() + 5000, 0));
                }
                addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 0, false, true));
                addPotionEffect(new EffectInstance(MidnightEffects.UNSTABLE_FALL, 400, 0, false, true));
            }
        });
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7d, 0.005f));
        this.goalSelector.addGoal(7, new CurtseyGoal(this, PlayerEntity.class, 12f, 0.02f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this).setCallsForHelp(NightStagEntity.class), false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4d);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20d);
        getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1d);
    }

    @Override
    protected void onGrowingAdult() {
        IAttributeInstance attackAttrib = getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
            attackAttrib.removeModifier(CHILD_ATTACK_MALUS);
        }
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20d);
        getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1d);
        setHealth(20f);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        float damage = (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        if (animCap.getAnimationType() == Type.CHARGE) {
            damage *= 2f;
        }
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if (flag) {
            if (!isChild() && entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).addPotionEffect(new EffectInstance(MidnightEffects.DARKNESS, 200, 0, false, true));
            }
            applyEnchantments(this, entity);
            animCap.setAnimation(this, Type.ATTACK, 10);
        }
        return flag;
    }

    @Override
    public void swingArm(Hand hand) {
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier * 0.2f);
    }

    @Override
    public double getMountedYOffset() {
        return super.getHeight() * 0.9d;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() instanceof UnstableFruitItem;
    }

    @Override
    protected void consumeItemFromStack(PlayerEntity player, ItemStack stack) {
        if (isBreedingItem(stack)) {
            this.temptTime = 400;
            addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 0, false, true));
            addPotionEffect(new EffectInstance(MidnightEffects.UNSTABLE_FALL, 400, 0, false, true));
        }
        super.consumeItemFromStack(player, stack);
    }

    @Override
    public void eatGrassBonus() {
        heal(1f);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return isChild() ? 4 : 7;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.animCap.updateAnimation();
        if (!this.world.isRemote && isChild()) {
            IAttributeInstance attackAttrib = getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            if (!attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
                attackAttrib.applyModifier(CHILD_ATTACK_MALUS);
                getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10d);
                getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0d);
                setHealth(10f);
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return capability == Midnight.ANIMATION_CAP ? LazyOptional.of(() -> this.animCap).cast() : LazyOptional.empty();
    }

    public class CurtseyGoal extends LookAtGoal {

        CurtseyGoal(MobEntity entity, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chance) {
            super(entity, watchTargetClass, maxDistance, chance);
            setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            if (!isChild() && getAttackTarget() == null && getRNG().nextFloat() < 0.1f) {
                if (closestEntity instanceof ServerPlayerEntity && MidnightUtil.isNotFakePlayer(closestEntity)) {
                    MidnightCriterion.NIGHTSTAG_BOW[getAntlerType()].trigger((ServerPlayerEntity) closestEntity);
                }
                animCap.setAnimation(this.entity, Type.CURTSEY, 40);
            }
        }

        @Override
        public void resetTask() {
            super.resetTask();
            if (animCap.isAnimate()) {
                animCap.resetAnimation(entity);
            }
        }
    }
}
