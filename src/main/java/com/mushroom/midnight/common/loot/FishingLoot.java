package com.mushroom.midnight.common.loot;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.List;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class FishingLoot {
    private static final Field poolConditions;
    static {
        poolConditions = ObfuscationReflectionHelper.findField(LootPool.class, "field_186454_b");
        poolConditions.setAccessible(true);
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName() == LootTableList.GAMEPLAY_FISHING) {
            addConditionToPool(event.getTable(), "main", (rand, context) -> !Helper.isMidnightDimension(context.getWorld()));
            event.getTable().addPool(event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(MODID,"fishing")).getPool("midnight_fishing"));
            addConditionToPool(event.getTable(), "midnight_fishing", (rand, context) -> Helper.isMidnightDimension(context.getWorld()));
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToPool(LootTable table, String poolName, LootCondition condition) {
        LootPool pool = table.getPool(poolName);
        try {
            List<LootCondition> conditions = (List<LootCondition>) poolConditions.get(pool);
            conditions.add(condition);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
