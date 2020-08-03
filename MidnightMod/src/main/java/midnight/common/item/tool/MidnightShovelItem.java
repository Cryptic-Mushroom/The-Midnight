package midnight.common.item.tool;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;

public class MidnightShovelItem extends ShovelItem {
    public MidnightShovelItem(IItemTier tier, Properties properties) {
        super(tier, 1.5F, -3.0F, properties);
    }
}
