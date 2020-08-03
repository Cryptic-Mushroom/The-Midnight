package midnight.common.item.tool;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import midnight.common.Midnight;
import midnight.common.block.MnBlocks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class MidnightAxeItem extends AxeItem {
    static {
        try {
            Field field = ObfuscationReflectionHelper.findField(AxeItem.class, "field_203176_a");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            Map<Block, Block> strippableBlocks = (Map<Block, Block>) field.get(null);

            ImmutableMap.Builder<Block, Block> builder = new ImmutableMap.Builder<>();
            builder.putAll(strippableBlocks);

            builder.put(MnBlocks.SHADOWROOT_LOG, MnBlocks.SHADOWROOT_STRIPPED_LOG);
            builder.put(MnBlocks.DARK_WILLOW_LOG, MnBlocks.DARK_WILLOW_STRIPPED_LOG);
            builder.put(MnBlocks.DEAD_WOOD_LOG, MnBlocks.DEAD_WOOD_STRIPPED_LOG);

            builder.put(MnBlocks.SHADOWROOT_WOOD, MnBlocks.SHADOWROOT_STRIPPED_WOOD);
            builder.put(MnBlocks.DARK_WILLOW_WOOD, MnBlocks.DARK_WILLOW_STRIPPED_WOOD);
            builder.put(MnBlocks.DEAD_WOOD, MnBlocks.DEAD_WOOD_STRIPPED);

            field.set(null, builder.build());
        } catch (ReflectiveOperationException e) {
            Midnight.LOGGER.warn("Failed to reflect strippable logs field", e);
        }
    }

    public MidnightAxeItem(IItemTier tier, Properties properties) {
        super(tier, 6f, -3.2f, properties);
    }
}
