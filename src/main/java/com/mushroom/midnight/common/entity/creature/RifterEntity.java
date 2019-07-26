package com.mushroom.midnight.common.entity.creature;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.entity.IRiftTraveler;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.RiftTravelEntry;
import com.mushroom.midnight.common.entity.TargetIdleTracker;
import com.mushroom.midnight.common.entity.task.RifterCaptureGoalGoal;
import com.mushroom.midnight.common.entity.task.RifterKeepNearRiftGoal;
import com.mushroom.midnight.common.entity.task.RifterMeleeGoal;
import com.mushroom.midnight.common.entity.task.RifterReturnGoal;
import com.mushroom.midnight.common.entity.task.RifterTeleportGoal;
import com.mushroom.midnight.common.entity.task.RifterTransportGoal;
import com.mushroom.midnight.common.entity.util.DragSolver;
import com.mushroom.midnight.common.entity.util.EntityReference;
import com.mushroom.midnight.common.event.RifterCaptureEvent;
import com.mushroom.midnight.common.event.RifterReleaseEvent;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.network.CaptureEntityMessage;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class RifterEntity extends MonsterEntity implements IRiftTraveler, IEntityAdditionalSpawnData {
    public static final float HOME_SCALE_MODIFIER = 1.4F;

    private static final UUID SPEED_MODIFIER_ID = UUID.fromString("3b8cda1f-c11d-478b-98b1-6144940c7ba1");
    private static final AttributeModifier HOME_SPEED_MODIFIER = new AttributeModifier(SPEED_MODIFIER_ID, "home_speed_modifier", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final UUID ARMOR_MODIFIER_ID = UUID.fromString("8cea53c5-1b5c-4b7c-9c86-192bf255c3d4");
    private static final AttributeModifier HOME_ARMOR_MODIFIER = new AttributeModifier(ARMOR_MODIFIER_ID, "home_armor_modifier", 2.0, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final UUID ATTACK_MODIFIER_ID = UUID.fromString("0e13d84c-52ed-4335-a284-49596533f445");
    private static final AttributeModifier HOME_ATTACK_MODIFIER = new AttributeModifier(ATTACK_MODIFIER_ID, "home_attack_modifier", 3.0, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static final int CAPTURE_COOLDOWN = 15;

    private static final double RIFT_SEARCH_RADIUS = 48.0;
    private static final float DROP_DAMAGE_THRESHOLD = 2.0F;

    private final EntityReference<RiftEntity> homeRift;
    private final DragSolver dragSolver;

    private final TargetIdleTracker targetIdleTracker = new TargetIdleTracker(this, 3.0);

    public int captureCooldown;

    public boolean spawnedThroughRift;

    private LivingEntity capturedEntity;

    public RifterEntity(EntityType<? extends RifterEntity> entityType, World world) {
        super(entityType, world);
        this.homeRift = new EntityReference<>(world);
        this.dragSolver = new DragSolver(this);

        float scaleModifier = MidnightUtil.isMidnightDimension(world) ? HOME_SCALE_MODIFIER : 1.0F;
        stepHeight = 1f;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return getPosition().getY() > world.getSeaLevel();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(0, new RifterReturnGoal(this, 1.3));

        this.goalSelector.addGoal(1, new RifterKeepNearRiftGoal(this, 1.0));
        this.goalSelector.addGoal(1, new RifterTeleportGoal(this));

        this.goalSelector.addGoal(2, new RifterTransportGoal(this, 1.0));
        this.goalSelector.addGoal(3, new RifterCaptureGoalGoal(this, 1.0));
        this.goalSelector.addGoal(4, new RifterMeleeGoal(this, 1.0));

        this.goalSelector.addGoal(4, new OpenDoorGoal(this, false));

        this.goalSelector.addGoal(4, new LookAtWithoutMovingGoal(this, LivingEntity.class, 8.0F, 0.2f));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4, 0.005F));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 2, true, false, this::shouldAttack));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 4, true, false, this::shouldAttack));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0);
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            this.targetIdleTracker.update();

            this.applyHomeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, HOME_SPEED_MODIFIER);
            this.applyHomeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, HOME_ATTACK_MODIFIER);
            this.applyHomeModifier(SharedMonsterAttributes.ARMOR, HOME_ARMOR_MODIFIER);

            if (this.capturedEntity != null && !this.capturedEntity.isAlive()) {
                this.setCapturedEntity(null);
            }

            if (this.captureCooldown > 0) {
                this.captureCooldown--;
            }

            if (!MidnightUtil.isMidnightDimension(this.world)) {
                this.updateHomeRift();
                if (this.ticksExisted % 20 == 0 && !this.homeRift.isPresent()) {
                    this.attackEntityFrom(DamageSource.OUT_OF_WORLD, 2.0F);
                }
            }
        }

        if (!this.world.isRemote || Midnight.PROXY.isClientPlayer(this.capturedEntity)) {
            this.dragSolver.solveDrag();
        }

        super.livingTick();
    }

    public int getTargetIdleTime() {
        return this.targetIdleTracker.getIdleTime();
    }

    public boolean shouldCapture() {
        if (MidnightUtil.isMidnightDimension(this.world)) {
            return false;
        }
        return this.homeRift.isPresent();
    }

    private void applyHomeModifier(IAttribute attribute, AttributeModifier modifier) {
        IAttributeInstance instance = this.getAttribute(attribute);
        boolean home = MidnightUtil.isMidnightDimension(this.world);
        if (home != instance.hasModifier(modifier)) {
            if (home) {
                instance.applyModifier(modifier);
            } else {
                instance.removeModifier(modifier);
            }
        }
    }

    private void updateHomeRift() {
        if (this.ticksExisted % 20 == 0 && !this.homeRift.isPresent()) {
            AxisAlignedBB searchBounds = this.getBoundingBox().grow(RIFT_SEARCH_RADIUS);
            List<RiftEntity> rifts = this.world.getEntitiesWithinAABB(RiftEntity.class, searchBounds);
            if (!rifts.isEmpty()) {
                rifts.sort(Comparator.comparingDouble(this::getDistanceSq));
                this.homeRift.set(rifts.get(0));
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity trueSource = source.getTrueSource();

        if (super.attackEntityFrom(source, amount)) {
            if (trueSource instanceof LivingEntity && this.shouldAttack(trueSource)) {
                if (this.shouldChangeTarget(this.getAttackTarget(), (LivingEntity) trueSource)) {
                    this.setAttackTarget((LivingEntity) trueSource);
                }
            }

            if (amount > DROP_DAMAGE_THRESHOLD) {
                this.setCapturedEntity(null);
            }

            return true;
        }

        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 1.5F;
    }

    private boolean shouldAttack(Entity entity) {
        if (entity == null || RifterCapturable.isCaptured(entity)) {
            return false;
        }
        if (entity instanceof AnimalEntity) {
            return !MidnightUtil.isMidnightDimension(entity.world);
        }
        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).isSleeping()) {
            return false;
        }
        return !(entity instanceof RifterEntity);
    }

    private boolean shouldChangeTarget(@Nullable LivingEntity from, LivingEntity to) {
        if (from == null) {
            return true;
        }
        if (to instanceof PlayerEntity && !(from instanceof PlayerEntity)) {
            return true;
        }
        return to.getHealth() > from.getHealth();
    }

    public void setCapturedEntity(@Nullable LivingEntity capturedEntity) {
        this.captureCooldown = CAPTURE_COOLDOWN;

        if (MinecraftForge.EVENT_BUS.post(new RifterReleaseEvent(this, this.capturedEntity))) {
            return;
        }

        if (this.capturedEntity != null) {
            this.resetCapturedEntity(this.capturedEntity);
        }

        if (MinecraftForge.EVENT_BUS.post(new RifterCaptureEvent(this, capturedEntity))) {
            return;
        }

        this.capturedEntity = capturedEntity;
        this.dragSolver.setDragged(capturedEntity);

        if (capturedEntity != null) {
            this.initCapturedEntity(capturedEntity);
        }

        if (!this.world.isRemote) {
            CaptureEntityMessage message = new CaptureEntityMessage(this, capturedEntity);
            Midnight.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), message);
        }
    }

    private void initCapturedEntity(LivingEntity capturedEntity) {
        capturedEntity.getCapability(Midnight.RIFTER_CAPTURABLE_CAP, null)
                .ifPresent(capturable -> capturable.setCaptured(true));

        capturedEntity.addPotionEffect(new EffectInstance(MidnightEffects.STUNNED, 200, 1, false, false));
        capturedEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 2, false, false));
    }

    private void resetCapturedEntity(LivingEntity capturedEntity) {
        capturedEntity.getCapability(Midnight.RIFTER_CAPTURABLE_CAP, null)
                .ifPresent(capturable -> capturable.setCaptured(false));
    }

    public LivingEntity getCapturedEntity() {
        return this.capturedEntity;
    }

    public boolean hasCaptured() {
        return this.capturedEntity != null;
    }

    public EntityReference<RiftEntity> getHomeRift() {
        return this.homeRift;
    }

    public DragSolver getDragSolver() {
        return this.dragSolver;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MidnightSounds.RIFTER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.RIFTER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.RIFTER_DEATH;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("home_rift", this.homeRift.serialize(new CompoundNBT()));
        compound.putBoolean("spawned_through_rift", this.spawnedThroughRift);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.homeRift.deserialize(compound.getCompound("home_rift"));
        this.spawnedThroughRift = compound.getBoolean("spawned_through_rift");
    }

    @Override
    public void onEnterRift(RiftEntity rift) {
        if (this.capturedEntity != null) {
            this.setCapturedEntity(null);
        }
    }

    @Override
    public RiftTravelEntry createTravelEntry(RiftEntity rift) {
        return this.createTravelEntry(this, rift);
    }

    @Override
    public Collection<RiftTravelEntry> getAdditionalTravelers(RiftEntity rift) {
        if (this.capturedEntity != null) {
            return ImmutableList.of(this.createTravelEntry(this.capturedEntity, rift));
        }
        return Collections.emptyList();
    }

    private RiftTravelEntry createTravelEntry(Entity entity, RiftEntity rift) {
        return new RiftTravelEntry(entity);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(this.capturedEntity != null ? this.capturedEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        int capturedId = buffer.readInt();
        if (capturedId != -1) {
            Entity entity = this.world.getEntityByID(capturedId);
            if (entity instanceof LivingEntity) {
                this.setCapturedEntity((LivingEntity) entity);
            }
        }
    }
}
