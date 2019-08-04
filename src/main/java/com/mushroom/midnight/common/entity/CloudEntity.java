package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.registry.MidnightEntities;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CloudEntity extends Entity {
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(CloudEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(CloudEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.createKey(CloudEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> PARTICLE = EntityDataManager.createKey(CloudEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PARTICLE_PARAM = EntityDataManager.createKey(CloudEntity.class, DataSerializers.VARINT);
    private Potion potion = Potions.EMPTY;
    private final List<EffectInstance> effects = new ArrayList<>();
    private final Map<Entity, Integer> reapplicationDelayMap = new HashMap<>();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private boolean colorSet;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    private LivingEntity owner;
    private UUID ownerUniqueId;
    private boolean allowTeleport = false;

    public CloudEntity(EntityType<CloudEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        setRadius(3f);
    }

    public CloudEntity(World world, double x, double y, double z) {
        this(MidnightEntities.CLOUD, world);
        setPosition(x, y, z);
    }

    public CloudEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.CLOUD, world);
    }

    public CloudEntity setAllowTeleport() {
        this.allowTeleport = true;
        return this;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(COLOR, 0);
        this.dataManager.register(RADIUS, 0.5f);
        this.dataManager.register(IGNORE_RADIUS, false);
        this.dataManager.register(PARTICLE, MidnightParticles.getDefaultParticle().ordinal());
        this.dataManager.register(PARTICLE_PARAM, 0);
    }

    @Override
    public void recalculateSize() {
        double x = this.posX;
        double y = this.posY;
        double z = this.posZ;
        super.recalculateSize();
        setPosition(x, y, z);
    }

    public CloudEntity setRadius(float radius) {
        if (!this.world.isRemote) {
            this.dataManager.set(RADIUS, radius);
        }
        return this;
    }

    public float getRadius() {
        return this.dataManager.get(RADIUS);
    }

    public CloudEntity setPotion(Potion potion) {
        this.potion = potion;
        if (!this.colorSet) {
            updateFixedColor();
        }
        return this;
    }

    private void updateFixedColor() {
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.dataManager.set(COLOR, 0);
        } else {
            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.effects)));
        }
    }

    public CloudEntity addEffect(EffectInstance effect) {
        this.effects.add(effect);
        if (!this.colorSet) {
            updateFixedColor();
        }
        return this;
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    public CloudEntity setColor(int color) {
        this.colorSet = true;
        this.dataManager.set(COLOR, color);
        return this;
    }

    public MidnightParticles getParticle() {
        return MidnightParticles.fromId(this.dataManager.get(PARTICLE));
    }

    public CloudEntity setParticle(MidnightParticles particle) {
        this.dataManager.set(PARTICLE, particle.ordinal());
        return this;
    }

    public int getParticleParam() {
        return this.dataManager.get(PARTICLE_PARAM);
    }

    public CloudEntity setParticleParam(int param) {
        this.dataManager.set(PARTICLE_PARAM, param);
        return this;
    }

    protected void setIgnoreRadius(boolean ignoreRadius) {
        this.dataManager.set(IGNORE_RADIUS, ignoreRadius);
    }

    public boolean shouldIgnoreRadius() {
        return this.dataManager.get(IGNORE_RADIUS);
    }

    public int getDuration() {
        return this.duration;
    }

    public CloudEntity setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = shouldIgnoreRadius();
        float f = getRadius();
        if (this.world.isRemote) {
            MidnightParticles particle = getParticle();
            if (flag) {
                if (this.rand.nextBoolean()) {
                    for (int i = 0; i < 2; ++i) {
                        float f1 = this.rand.nextFloat() * ((float) Math.PI * 2f);
                        float f2 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2f;
                        float f3 = MathHelper.cos(f1) * f2;
                        float f4 = MathHelper.sin(f1) * f2;
                        particle.spawn(this.world, this.posX + (double) f3, this.posY, this.posZ + (double) f4, 0d, 0d, 0d, getParticleParam());
                    }
                }
            } else {
                float f5 = (float) Math.PI * f * f;
                for (int k1 = 0; (float) k1 < f5; ++k1) {
                    float f6 = this.rand.nextFloat() * ((float) Math.PI * 2f);
                    float f7 = MathHelper.sqrt(this.rand.nextFloat()) * f;
                    float f8 = MathHelper.cos(f6) * f7;
                    float f9 = MathHelper.sin(f6) * f7;
                    particle.spawn(this.world, this.posX + (double) f8, this.posY, this.posZ + (double) f9, (0.5d - this.rand.nextDouble()) * 0.15d, 0.009999999776482582d, (0.5d - this.rand.nextDouble()) * 0.15d, getParticleParam());
                }
            }
        } else {
            if (this.ticksExisted >= this.waitTime + this.duration) {
                remove();
                return;
            }
            boolean flag1 = this.ticksExisted < this.waitTime;
            if (flag != flag1) {
                setIgnoreRadius(flag1);
            }
            if (flag1) {
                return;
            }
            if (this.radiusPerTick != 0f) {
                f += this.radiusPerTick;
                if (f < 0.5f) {
                    remove();
                    return;
                }
                setRadius(f);
            }
            if (this.ticksExisted % 5 == 0) {
                this.reapplicationDelayMap.entrySet().removeIf(entry -> this.ticksExisted >= entry.getValue());
                List<EffectInstance> potions = new ArrayList<>();
                for (EffectInstance effect : this.potion.getEffects()) {
                    potions.add(new EffectInstance(effect.getPotion(), effect.getDuration() / 4, effect.getAmplifier(), effect.isAmbient(), effect.doesShowParticles()));
                }
                potions.addAll(this.effects);
                if (potions.isEmpty()) {
                    this.reapplicationDelayMap.clear();
                } else {
                    List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox());
                    if (!list.isEmpty()) {
                        for (LivingEntity entity : list) {
                            if (!this.reapplicationDelayMap.containsKey(entity) && entity.canBeHitWithPotion()) {
                                double d0 = entity.posX - this.posX;
                                double d1 = entity.posZ - this.posZ;
                                double d2 = d0 * d0 + d1 * d1;
                                if (d2 <= (double) (f * f)) {
                                    this.reapplicationDelayMap.put(entity, this.ticksExisted + this.reapplicationDelay);
                                    for (EffectInstance effect : potions) {
                                        if (effect.getPotion().isInstant()) {
                                            effect.getPotion().affectEntity(this, getOwner(), entity, effect.getAmplifier(), 0.5d);
                                        } else {
                                            entity.addPotionEffect(new EffectInstance(effect));
                                        }
                                    }
                                    if (this.radiusOnUse != 0f) {
                                        f += this.radiusOnUse;
                                        if (f < 0.5f) {
                                            remove();
                                            return;
                                        }
                                        setRadius(f);
                                    }
                                    if (this.durationOnUse != 0) {
                                        this.duration += this.durationOnUse;
                                        if (this.duration <= 0) {
                                            remove();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // TODO could be a move
        if (this.allowTeleport && !this.world.isRemote && isAlive() && this.world.getGameTime() % 40 == 0) {
            List<Entity> list = this.world.getEntitiesInAABBexcluding(getOwner(), getBoundingBox().grow(5d), p -> p instanceof LivingEntity && p.isAlive());
            if (!list.isEmpty()) {
                Entity entity = list.get(this.world.rand.nextInt(list.size()));
                setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
            }
        }
    }

    public CloudEntity setRadiusOnUse(float radiusOnUseIn) {
        this.radiusOnUse = radiusOnUseIn;
        return this;
    }

    public CloudEntity setRadiusPerTick(float radiusPerTickIn) {
        this.radiusPerTick = radiusPerTickIn;
        return this;
    }

    public CloudEntity setWaitTime(int waitTimeIn) {
        this.waitTime = waitTimeIn;
        return this;
    }

    public CloudEntity setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUniqueId = owner == null ? null : owner.getUniqueID();
        return this;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUniqueId != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld) this.world).getEntityByUuid(this.ownerUniqueId);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity) entity;
            }
        }
        return this.owner;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.ticksExisted = compound.getInt("Age");
        this.duration = compound.getInt("Duration");
        this.waitTime = compound.getInt("WaitTime");
        this.reapplicationDelay = compound.getInt("ReapplicationDelay");
        this.durationOnUse = compound.getInt("DurationOnUse");
        this.radiusOnUse = compound.getFloat("RadiusOnUse");
        this.radiusPerTick = compound.getFloat("RadiusPerTick");
        setRadius(compound.getFloat("Radius"));
        this.ownerUniqueId = compound.getUniqueId("OwnerUUID");
        if (compound.contains("particle_id", Constants.NBT.TAG_INT)) {
            setParticle(MidnightParticles.fromId(compound.getInt("particle_id")));
        }
        if (compound.contains("particle_param", Constants.NBT.TAG_INT)) {
            setParticleParam(compound.getInt("particle_param"));
        }
        if (compound.contains("Color", Constants.NBT.TAG_ANY_NUMERIC)) {
            setColor(compound.getInt("Color"));
        }
        if (compound.contains("Potion", Constants.NBT.TAG_STRING)) {
            setPotion(PotionUtils.getPotionTypeFromNBT(compound));
        }
        if (compound.contains("Effects", Constants.NBT.TAG_LIST)) {
            ListNBT list = compound.getList("Effects", Constants.NBT.TAG_COMPOUND);
            this.effects.clear();
            for (int i = 0; i < list.size(); ++i) {
                EffectInstance effectInstance = EffectInstance.read(list.getCompound(i));
                if (effectInstance != null) {
                    addEffect(effectInstance);
                }
            }
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Age", this.ticksExisted);
        compound.putInt("Duration", this.duration);
        compound.putInt("WaitTime", this.waitTime);
        compound.putInt("ReapplicationDelay", this.reapplicationDelay);
        compound.putInt("DurationOnUse", this.durationOnUse);
        compound.putFloat("RadiusOnUse", this.radiusOnUse);
        compound.putFloat("RadiusPerTick", this.radiusPerTick);
        compound.putFloat("Radius", getRadius());
        compound.putInt("particle_id", getParticle().ordinal());
        compound.putInt("particle_param", getParticleParam());
        if (this.ownerUniqueId != null) {
            compound.putUniqueId("OwnerUUID", this.ownerUniqueId);
        }
        if (this.colorSet) {
            compound.putInt("Color", getColor());
        }
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compound.putString("Potion", ForgeRegistries.POTION_TYPES.getKey(this.potion).toString());
        }
        if (!this.effects.isEmpty()) {
            ListNBT list = new ListNBT();
            for (EffectInstance effect : this.effects) {
                list.add(effect.write(new CompoundNBT()));
            }
            compound.put("Effects", list);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (RADIUS.equals(key)) {
            setRadius(getRadius());
            recalculateSize();
        }
        super.notifyDataManagerChange(key);
    }

    @Override
    public PushReaction getPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.flexible(getRadius() * 2f, 0.5f);
    }
}
