package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.common.entity.projectile.EntityProjectile;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBladeshroomCap extends EntityProjectile {
    private static final float DAMAGE = 1.0F;
    private static final double GRAVITY = 0.015;

    public float spin;
    public float prevSpin;

    public EntityBladeshroomCap(World world) {
        super(world);
        this.setDamage(DAMAGE);
    }

    public EntityBladeshroomCap(World world, EntityLivingBase thrower) {
        super(world, thrower);
        this.setDamage(DAMAGE);
    }

    public EntityBladeshroomCap(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setDamage(DAMAGE);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.prevSpin = this.spin;

        if (!this.inGround) {
            this.spin += 25.0F;

            if (!this.hasNoGravity()) {
                this.motionY += 0.05 - GRAVITY;
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.BLADESHROOM_CAP);
    }

    @Override
    public SoundEvent getSoundOnHit() { return ModSounds.BLADESHROOM_CAP_HIT; }
}
