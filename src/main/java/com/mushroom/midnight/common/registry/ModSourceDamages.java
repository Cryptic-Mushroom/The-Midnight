package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModSourceDamages {
    public final static DamageSource BLADESHROOM_CAP = new DamageSource("bladeshroom_cap") {
        @Override
        public ITextComponent getDeathMessage(EntityLivingBase entity) {
            return new TextComponentTranslation("death."+ Midnight.MODID + "." + damageType, entity.getDisplayName());
        }
    }.setDamageBypassesArmor().setDamageIsAbsolute();
}
