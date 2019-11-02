package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.common.item.SporeBombItem;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class SporeBombEntity extends ThrowableEntity implements IRendersAsItem {
    private static final DataParameter<ItemStack> BOMB_STACK = EntityDataManager.createKey(SporeBombEntity.class, DataSerializers.ITEMSTACK);

    public SporeBombEntity(World world) {
        super(MidnightEntities.SPORE_BOMB, world);
    }

    public SporeBombEntity(EntityType<? extends ThrowableEntity> entityType, World world) {
        super(entityType, world);
    }

    public SporeBombEntity(World world, double x, double y, double z) {
        super(MidnightEntities.SPORE_BOMB, x, y, z, world);
    }

    public SporeBombEntity(World world, LivingEntity thrower) {
        super(MidnightEntities.SPORE_BOMB, thrower, world);
    }

    public SporeBombEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MidnightEntities.SPORE_BOMB, world);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(BOMB_STACK, new ItemStack(MidnightItems.NIGHTSHROOM_SPORE_BOMB));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("bomb_stack", getBombStack().write(new CompoundNBT()));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("bomb_stack", Constants.NBT.TAG_COMPOUND)) {
            setBombStack(ItemStack.read(compound.getCompound("bomb_stack")));
        }
    }

    public ItemStack getBombStack() {
        return this.dataManager.get(BOMB_STACK);
    }

    public void setBombStack(ItemStack bombStack) {
        this.dataManager.set(BOMB_STACK, bombStack);
    }

    @Override
    public ITextComponent getName() {
        return hasCustomName() ? super.getName() : getBombStack().getDisplayName();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (isInWater()) {
                entityDropItem(new ItemStack(MidnightItems.DARK_PEARL), 0.1f);
                remove();
            } else {
                SporeBombItem bomb = (SporeBombItem) getBombStack().getItem();
                if (bomb.checkExplode(this.world, getBombStack())) {
                    bomb.explode((ServerWorld) this.world, this.posX, this.posY, this.posZ);
                    remove();
                }
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            ((EntityRayTraceResult)result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1f);
        }
        if (!world.isRemote) {
            if (canBreakOn(result)) {
                ((SporeBombItem) getBombStack().getItem()).explode((ServerWorld) this.world, this.posX, this.posY, this.posZ);
            } else {
                entityDropItem(getBombStack().copy(), 0.01f);
            }
            remove();
        }
    }

    private boolean canBreakOn(RayTraceResult result) {
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return false;
        }
        BlockState impactedState = this.world.getBlockState(((BlockRayTraceResult)result).getPos());
        return impactedState.getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON;
    }

    @Override
    public ItemStack getItem() {
        return getBombStack();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
