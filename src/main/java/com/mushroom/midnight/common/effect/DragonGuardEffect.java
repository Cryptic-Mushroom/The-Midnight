package com.mushroom.midnight.common.effect;

import net.minecraft.entity.EntityLivingBase;

public class DragonGuardEffect extends GenericEffect {
    public DragonGuardEffect() {
        super(false, 0);
        setBeneficial();
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.extinguish();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
