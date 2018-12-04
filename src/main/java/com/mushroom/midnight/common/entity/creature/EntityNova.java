package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.pathfinding.CustomWalkNodeProcessor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/** TODO only the base is set */
public class EntityNova extends EntityMob { // ? implements IRangedAttackMob
    public EntityNova(World world) {
        super(world);
        setSize(1f, 1f);
        // ? experienceValue
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new PathNavigateGround(this, world) {
            @Override
            protected PathFinder getPathFinder() {
                nodeProcessor = new CustomWalkNodeProcessor();
                //nodeProcessor.setCanEnterDoors(true);
                //nodeProcessor.setCanOpenDoors(true); // is breakDoor
                return new PathFinder(nodeProcessor);
            }
        };
    }

    @Override
    public boolean getCanSpawnHere() {
        //&& world.provider.getDimensionType() == ModDimensions.MIDNIGHT
        return world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        //dataManager.register(SPECIAL_DATA, Boolean.FALSE);
    }

    @Override
    protected void initEntityAI() {
        /* mutex flags
		100 EntityAIMoveTowardsRestriction
		100 EntityAIWander
		110 EntityAILookIdle
		110 EntityAIAttackMelee
		010 EntityAIWatchClosest
		001 EntityAISwimming
		 */
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1d, false));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6d));
        tasks.addTask(7, new EntityAIWander(this, 0.6d, 120));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.02f));
        tasks.addTask(8, new EntityAILookIdle(this));
        applyEntityAI();
    }

    private void applyEntityAI() {
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, p -> true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        // TODO to define
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24d);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60d);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3d);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0);
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
        data = super.onInitialSpawn(difficulty, data);
        float f = difficulty.getClampedAdditionalDifficulty();
        /*setCanPickUpLoot(rand.nextFloat() < 0.55f * f);
        setEquipmentBasedOnDifficulty(difficulty);
        setEnchantmentBasedOnDifficulty(difficulty);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.05000000074505806d, 0));
        double d0 = rand.nextDouble() * 1.5d * (double) f;
        if (d0 > 1d) {
            getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }*/
        return data;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    /*@Override
	protected SoundEvent getAmbientSound() { return null; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return null; }

    @Override
    protected SoundEvent getDeathSound() { return null; }

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) { playSound(SoundEvents.BLOCK_WOOD_STEP, 0.15f, 1f); }

	@Override
    @Nullable
    protected ResourceLocation getLootTable() { return null; }*/

    @Override
    @Nullable
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
}
