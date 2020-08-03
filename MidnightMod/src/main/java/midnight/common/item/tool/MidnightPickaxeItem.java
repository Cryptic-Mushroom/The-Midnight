package midnight.common.item.tool;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class MidnightPickaxeItem extends PickaxeItem {
    public MidnightPickaxeItem(IItemTier tier, Item.Properties properties) {
        // why the attackDamage is an integer here?
        super(tier, 1, -2.8F, properties);
    }
}
