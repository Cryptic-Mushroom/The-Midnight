package midnight.common.entity;

import midnight.common.block.MnBlocks;
import midnight.common.entity.task.FindEatableFoodGoal;
import midnight.common.entity.task.NeutralGoal;
import midnight.common.entity.task.StealFoodGoal;
import midnight.common.item.MnItems;
import midnight.common.sound.MnSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SkulkEntity extends TameableEntity {
    private static final DataParameter<Boolean> STEALTH = EntityDataManager.createKey(SkulkEntity.class, DataSerializers.BOOLEAN);
    private int stealthCooldown = 0;
    private int eatTicks;

    public static final Predicate<ItemStack> canEatFood = food -> {
        return food.getItem().getFood() != null && !food.getItem().getFood().isMeat() && food.getItem() != MnItems.RAW_SUAVIS && food.getItem() != MnItems.COOKED_SUAVIS;
    };

    public static final Predicate<ItemStack> dislikeFood = food -> {
        return food.getItem() == MnItems.RAW_SUAVIS || food.getItem() == MnItems.COOKED_SUAVIS;
    };

    public static final Predicate<ItemStack> tameableFood = food -> {
        return false; //TODO REPLACE THIS
        //return food.getItem() == MidnightBlocks.VIRIDSHROOM.asItem() || food.getItem() == MidnightBlocks.NIGHTSHROOM.asItem() || food.getItem() == MidnightBlocks.BOGSHROOM.asItem() || food.getItem() == MidnightBlocks.DEWSHROOM.asItem();
    };

    public SkulkEntity(EntityType<? extends SkulkEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(STEALTH, Boolean.FALSE);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if(getPosition().getY() <= world.getSeaLevel()) {
            return false;
        }
        return super.canSpawn(worldIn, spawnReasonIn);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        setStealth(true);
        return livingdata;
    }

    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, this.sitGoal);
        this.goalSelector.addGoal(2, new NeutralGoal(this, new PanicGoal(this, 1d), true));
        this.goalSelector.addGoal(3, new NeutralGoal(this, new MeleeAttackGoal(this, 1d, false), false));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(5, new AvoidEntityGoal(this, LivingEntity.class, 8.0F, 1.6D, 1.6D) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute() && !isTamed();
                return valid && this.avoidTarget != null && (dislikeFood.test(this.avoidTarget.getHeldItem(Hand.MAIN_HAND)) || dislikeFood.test(this.avoidTarget.getHeldItem(Hand.OFF_HAND)));
            }

            @Override
            public void startExecuting() {
                super.startExecuting();
                setStealth(false);
            }

            @Override
            public void resetTask() {
                super.resetTask();
                setStealth(true);
            }
        });
        this.goalSelector.addGoal(5, new FindEatableFoodGoal(this, this::canEatItem, 1.15D));

        this.goalSelector.addGoal(6, new StealFoodGoal(this, 1d, 0.005f));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1d, 0.005f) {
        });
        this.goalSelector.addGoal(8, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 8f, 0.02f));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if(valid && canStealth() && !isTamed()) {
                    setStealth(true);
                }
                return valid;
            }
        });
        this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this), false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1d);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        float damage = (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if(flag) {
            applyEnchantments(this, entity);
        }
        setStealth(false);
        return flag;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if(itemstack.getItem() instanceof SpawnEggItem) {
            return super.processInteract(player, hand);
        } else if(this.world.isRemote) {
            return this.isOwner(player) || tameableFood.test(itemstack);
        } else {
            if(this.isTamed()) {
                if(item.isFood() && canEatFood.test(itemstack) && this.getHealth() < this.getMaxHealth() && this.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
                    if(!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    ItemStack stack = itemstack.copy();

                    stack.setCount(1);

                    this.setHeldItem(Hand.MAIN_HAND, stack);
                    return true;
                }

                if(this.isOwner(player) && !tameableFood.test(itemstack)) {
                    this.sitGoal.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                }
            } else if(tameableFood.test(itemstack)) {
                if(!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if(this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.sitGoal.setSitting(true);
                    this.setStealth(false);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.world.setEntityState(this, (byte) 6);
                }

                return true;
            }

            return super.processInteract(player, hand);
        }
    }

    @Override
    public void swingArm(Hand hand) {
    }

    @Override
    public void livingTick() {
        if(!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            ++this.eatTicks;
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if(this.canEatItem(itemstack)) {
                if(this.eatTicks > 200) {
                    ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
                    if(!itemstack1.isEmpty()) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
                    }

                    this.heal(2.0F);

                    this.eatTicks = 0;
                } else if(this.eatTicks > 160 && this.rand.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemstack), 1.0F, 1.0F);
                    this.world.setEntityState(this, (byte) 45);
                }
            }
        }

        super.livingTick();
        if(stealthCooldown > 0) {
            stealthCooldown--;
        }
    }

    public boolean isStealth() {
        return dataManager.get(STEALTH);
    }

    public boolean canStealth() {
        return stealthCooldown <= 0;
    }

    public void setStealth(boolean flag) {
        dataManager.set(STEALTH, flag);
        stealthCooldown = 60;
    }

    @Override
    public float getAIMoveSpeed() {
        return super.getAIMoveSpeed() * (isStealth() ? 0.3f : 1f);
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
        setStealth(false);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if(!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if(target instanceof SkulkEntity) {
                SkulkEntity wolfentity = (SkulkEntity) target;
                return !wolfentity.isTamed() || wolfentity.getOwner() != owner;
            } else if(target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if(target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("stealth", isStealth());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if(compound.contains("stealth", Constants.NBT.TAG_BYTE)) {
            setStealth(compound.getBoolean("stealth"));
        }
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntitySize size) {
        return super.getStandingEyeHeight(pose, size) * 0.5f;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if(id == 45) {
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if(!itemstack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3d vec3d = new Vec3d(((double) this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D).rotatePitch(-this.rotationPitch * ((float) Math.PI / 180F)).rotateYaw(-this.rotationYaw * ((float) Math.PI / 180F));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), this.getPosX() + this.getLookVec().x / 2.0D, this.getPosY(), this.getPosZ() + this.getLookVec().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
            }
        } else {
            super.handleStatusUpdate(id);
        }

    }

    private boolean canEatItem(ItemStack itemstack) {
        return canEatFood.test(itemstack);
    }

    private void spitOutItem(ItemStack stackIn) {
        if(!stackIn.isEmpty() && !this.world.isRemote) {
            ItemEntity itementity = new ItemEntity(this.world, this.getPosX() + this.getLookVec().x, this.getPosY() + 1.0D, this.getPosZ() + this.getLookVec().z, stackIn);
            itementity.setPickupDelay(40);
            itementity.setThrowerId(this.getUniqueID());
            this.world.addEntity(itementity);
        }
    }

    private void spawnItem(ItemStack stackIn) {
        ItemEntity itementity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), stackIn);
        this.world.addEntity(itementity);
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if(!this.canEatItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) && this.canEatItem(itemstack) && (!isTamed() || this.getHealth() < this.getMaxHealth() && isTamed())) {
            int i = itemstack.getCount();
            if(i > 1) {
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

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public int getTalkInterval() {
        return 100;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if(this.getRNG().nextInt(3) == 0 && this.isStealth()) {
            return MnSoundEvents.SKULK_SNIFFING;
        } else {
            return MnSoundEvents.SKULK_AMBIENT;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MnSoundEvents.SKULK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MnSoundEvents.SKULK_DEATH;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos.down()).getBlock() == MnBlocks.NIGHT_GRASS ? 10.0F : 1.0F;
    }

    //when you stands near skulk, you can see skulk
    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInvisibleToPlayer(PlayerEntity player) {
        return this.getDistanceSq(player) < 8d || this.isOwner(player);
    }
}
