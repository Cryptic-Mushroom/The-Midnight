package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class EntityBladeshroomCap extends EntityArrow {
    private static final ThreadLocal<Boolean> HIT_ACTIVE = ThreadLocal.withInitial(() -> false);

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
    protected void onHit(RayTraceResult result) {
        try {
            HIT_ACTIVE.set(true);
            super.onHit(result);
        } finally {
            HIT_ACTIVE.set(false);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.BLADESHROOM_CAP);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (!HIT_ACTIVE.get()) {
            return;
        }

        if (event.getSound() == SoundEvents.ENTITY_ARROW_HIT) {
            event.setSound(ModSounds.BLADESHROOM_CAP_HIT);
        }
    }
}
