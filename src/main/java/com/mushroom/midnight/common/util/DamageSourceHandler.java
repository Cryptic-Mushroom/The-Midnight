package com.mushroom.midnight.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class DamageSourceHandler {
    public static DamageSource causeShockedDamage(Entity source, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("midnight.shocked", source, indirectEntityIn)).setDamageIsAbsolute();
    }
}
