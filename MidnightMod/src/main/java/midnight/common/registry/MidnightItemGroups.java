package midnight.common.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;

public class MidnightItemGroups {
    public static final ItemGroup BUILDING = new ItemGroup("midnight_building") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MnBlocks.GRASS_BLOCK);
        }
    };

    public static final ItemGroup DECORATION = new ItemGroup("midnight_decoration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MnBlocks.ROUXE);
        }
    };

    public static final ItemGroup ITEMS = new ItemGroup("midnight_items") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MnItems.DARK_STICK);
        }
    };

    /*public static final ItemGroup TOOLS = new ItemGroup("midnight_tools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MnItems.SHADOWROOT_PICKAXE);
        }
    };

    public static final ItemGroup COMBAT = new ItemGroup("midnight_combat") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MnItems.SHADOWROOT_SWORD);
        }
    };*/
}
