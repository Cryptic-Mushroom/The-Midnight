package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.entity.CloudEntity;
import com.mushroom.midnight.common.entity.projectile.SporeBombEntity;
import com.mushroom.midnight.common.particle.ParticleDataOneInt;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

public class SporeBombItem extends Item {
    public int maxFuseTime = 200;

    public enum Type {
        NIGHTSHROOM(0x6F3FD6),
        DEWSHROOM(0x69D9E5),
        VIRIDSHROOM(0x3FD647),
        BOGSHROOM(0xE6742D);

        private final int color;

        Type(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static Type getDefault() {
            return NIGHTSHROOM;
        }
    }

    private final Type bombType;

    public SporeBombItem(Type bombType, Item.Properties properties) {
        super(properties);
        this.bombType = bombType;
        DispenserBlock.registerDispenseBehavior(this, new DispenserBehavior());

        this.addPropertyOverride(new ResourceLocation("blinking"), (stack, world, entity) -> {
            float fuseTime = world != null ? this.getFuseTime(world, stack) / (float) this.maxFuseTime : 1.0F;
            float ratio = fuseTime * 10f % 1;
            return ratio > 0.5F ? 1.0F : 0.0F;
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() == this) {
            player.getCooldownTracker().setCooldown(this, 10);
            if (!world.isRemote) {
                SporeBombEntity bomb = this.createEntityBomb(world, heldItem, player);
                bomb.shoot(player, player.rotationPitch, player.rotationYaw, 0f, 1.5f, 1f);
                world.addEntity(bomb);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            }
            return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean isSelected) {
        this.updateBomb(world, stack, entity.posX, entity.posY, entity.posZ);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        boolean valid = this.updateBomb(entity.world, entity.getItem(), entity.posX, entity.posY, entity.posZ);
        if (valid) {
            entity.remove();
        }
        return valid;
    }

    private boolean updateBomb(World world, ItemStack stack, double x, double y, double z) {
        if (!world.isRemote) {
            long fuseTime = getFuseTime(world, stack);
            if (fuseTime <= 0) {
                this.explode((ServerWorld)world, x, y, z);
                stack.shrink(1);
                return true;
            } else if (fuseTime < maxFuseTime) {
                world.playSound(null, x, y, z, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.HOSTILE, world.rand.nextFloat(), world.rand.nextFloat());
            }
        }
        return false;
    }

    public void setFuseTime(World world, ItemStack stack, int time) {
        if (stack.getItem() == this) {
            CompoundNBT compound = stack.getOrCreateTag();
            compound.putLong("fuse_time", world.getGameTime() + time);
        }
    }

    public long getFuseTime(World world, ItemStack stack) {
        if (!stack.getItem().isIn(MidnightTags.Items.SPORE_BOMBS) || !stack.hasTag()) return maxFuseTime;

        CompoundNBT tag = stack.getOrCreateTag();
        if (tag.contains("fuse_time", Constants.NBT.TAG_LONG)) {
            return Math.max(0, tag.getLong("fuse_time") - world.getGameTime());
        }

        return maxFuseTime;
    }

    public boolean checkExplode(World world, ItemStack stack) {
        return getFuseTime(world, stack) <= 0;
    }

    public void explode(ServerWorld world, double x, double y, double z) {
        world.spawnParticle(new ParticleDataOneInt(MidnightParticleTypes.BOMB_EXPLOSION, this.bombType.getColor()), x, y, z, 1, 0d, 0d, 0d, 1d);
        world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1f, 1f);
        switch (this.bombType) {
            case NIGHTSHROOM:
                world.addEntity(createLingeringCloud(this.bombType, world, x, y, z)
                        .setParticleData(new ParticleDataOneInt(MidnightParticleTypes.FADING_SPORE, this.bombType.getColor()))
                        .addEffect(new EffectInstance(MidnightEffects.DARKNESS, 100, 0, false, true)));
                break;
            case DEWSHROOM:
                world.addEntity(createLingeringCloud(this.bombType, world, x, y, z)
                        .setParticleData(new ParticleDataOneInt(MidnightParticleTypes.FADING_SPORE, this.bombType.getColor()))
                        .setRadiusPerTick(0.0025f)
                        .addEffect(new EffectInstance(MidnightEffects.STUNNED, 100, 0, false, true))
                        .addEffect(new EffectInstance(Effects.SLOWNESS, 100, 5, false, true)));
                break;
            case VIRIDSHROOM:
                world.addEntity(createLingeringCloud(this.bombType, world, x, y, z)
                        .setAllowTeleport()
                        .setParticleData(new ParticleDataOneInt(MidnightParticleTypes.FADING_SPORE, this.bombType.getColor()))
                        .addEffect(new EffectInstance(MidnightEffects.TORMENTED, 100, 0, false, true)));
                break;
            case BOGSHROOM:
                world.addEntity(createLingeringCloud(this.bombType, world, x, y, z)
                        .setParticleData(new ParticleDataOneInt(MidnightParticleTypes.FADING_SPORE, this.bombType.getColor()))
                        .addEffect(new EffectInstance(MidnightEffects.CONFUSION, 200, 0, false, true)));
                break;
        }
    }

    private static CloudEntity createLingeringCloud(Type bombType, World world, double x, double y, double z) {
        float radius = 2.5f;
        int duration = 300;
        return new CloudEntity(world, x, y, z)
                .setRadius(radius)
                //.setRadiusOnUse(-0.5f)
                .setWaitTime(10)
                .setDuration(duration)
                .setRadiusPerTick(-radius / (float) duration)
                .setPotion(Potions.EMPTY)
                .setColor(bombType.getColor());
    }

    private class DispenserBehavior extends ProjectileDispenseBehavior {
        @Override
        protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack stack) {
            return SporeBombItem.this.createEntityBomb(world, stack, pos);
        }
    }

    private SporeBombEntity createEntityBomb(World world, ItemStack stack, PlayerEntity player) {
        SporeBombEntity bomb = new SporeBombEntity(world, player);
        return this.addStackToBomb(bomb, stack);
    }

    private SporeBombEntity createEntityBomb(World world, ItemStack stack, IPosition pos) {
        SporeBombEntity bomb = new SporeBombEntity(world, pos.getX(), pos.getY(), pos.getZ());
        return this.addStackToBomb(bomb, stack);
    }

    private SporeBombEntity addStackToBomb(SporeBombEntity bomb, ItemStack stack) {
        CompoundNBT compound = stack.getTag();
        ItemStack newStack = stack.copy();
        if (compound == null || !compound.contains("fuse_time", Constants.NBT.TAG_LONG)) {
            this.setFuseTime(bomb.world, newStack, maxFuseTime);
        }
        bomb.setBombStack(newStack);
        return bomb;
    }
}
