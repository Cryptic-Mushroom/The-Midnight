package com.mushroom.midnight.common.loot;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FishingLoot {
    private static ResourceLocation MIDNIGHT_FISHING = new ResourceLocation(MODID, "fishing");
    private static final Field fieldIsFrozen = ObfuscationReflectionHelper.findField(LootTable.class, "isFrozen");
    private static final Field fieldPools = ObfuscationReflectionHelper.findField(LootTable.class, "field_186466_c");
    private static final Field fieldConditions = ObfuscationReflectionHelper.findField(LootPool.class, "field_186454_b");
    private static final Field fieldCombinedConditions = ObfuscationReflectionHelper.findField(LootPool.class, "field_216101_c");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.GAMEPLAY_FISHING)) {
            MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            server.deferTask(() -> {
                LootTable midnightFishing = event.getLootTableManager().getLootTableFromLocation(MIDNIGHT_FISHING);
                if (midnightFishing == LootTable.EMPTY_LOOT_TABLE) {
                    Midnight.LOGGER.warn("The loottable for midnight fishing is absent");
                } else {
                    try {
                        // because modded loot tables are not fired here
                        fieldIsFrozen.setBoolean(midnightFishing, false);
                        fieldIsFrozen.setBoolean(event.getTable(), false);
                        addConditionToAllMainPools(event.getTable(), context -> !MidnightUtil.isMidnightDimension(context.getWorld()));

                        LootPool midnightPool = midnightFishing.getPool("midnight_fishing");
                        addConditionToPool(context -> MidnightUtil.isMidnightDimension(context.getWorld()), midnightPool);
                        event.getTable().addPool(midnightPool);

                        fieldIsFrozen.setBoolean(event.getTable(), true);
                        fieldIsFrozen.setBoolean(midnightFishing, true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToAllMainPools(LootTable table, ILootCondition condition) throws IllegalAccessException {
        List<LootPool> pools = null;
        pools = (List<LootPool>) fieldPools.get(table);
        if (pools != null) {
            for (LootPool pool : pools) {
                addConditionToPool(condition, pool);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToPool(ILootCondition condition, LootPool pool) throws IllegalAccessException {
        List<ILootCondition> conditions = (List<ILootCondition>) fieldConditions.get(pool);
        conditions.add(condition);
        fieldCombinedConditions.set(pool, LootConditionManager.and(conditions.toArray(new ILootCondition[0])));
    }
}
