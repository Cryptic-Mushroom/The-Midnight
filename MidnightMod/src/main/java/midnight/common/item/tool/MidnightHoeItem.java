package midnight.common.item.tool;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class MidnightHoeItem extends HoeItem {
    public MidnightHoeItem(IItemTier tier, Properties properties) {
        super(tier, (int) -tier.getAttackDamage(), -3.0F, properties);
    }
}
