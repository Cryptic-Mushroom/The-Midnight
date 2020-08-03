package midnight.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import midnight.MidnightInfo;

public class MidnightDamageSource extends DamageSource {
    public MidnightDamageSource(String damageType) {
        super(damageType);
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity entity) {
        return new TranslationTextComponent("death." + MidnightInfo.MODID + "." + damageType, entity.getDisplayName());
    }
}
