package com.mushroom.midnight.common.config;

import com.mushroom.midnight.Midnight;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Midnight.MODID, name = Midnight.MODID)
@Config.LangKey("midnight.config.title")
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightConfig {
    @Config.Name("midnight_dimension_id")
    @Config.LangKey("config.midnight.midnight_dimension_id")
    @Config.Comment("The ID to be used for the Midnight Dimension")
    @Config.RequiresMcRestart
    public static int midnightDimensionId = -23;

    @Config.Name("rift_shaders")
    @Config.LangKey("config.midnight.rift_shaders")
    @Config.Comment("If true, rifts will be rendered with custom shaders for standard effects. If false, a simpler texture will be rendered.")
    public static boolean riftShaders = true;

    @Config.Name("bladeshroom_damage_chance")
    @Config.LangKey("config.midnight.bladeshroom_damage_chance")
    @Config.Comment("The chance to take damage when picking a bladeshroom cap.")
    @Config.RangeInt(min=0, max=100)
    public static int bladeshroomDamageChance = 5;

    @Config.Name("can_respawn_in_midnight")
    @Config.LangKey("config.midnight.can_respawn_in_midnight")
    @Config.Comment("If true, players will be allowed to respawn in Midnight.")
    public static boolean canRespawnInMidnight = false;

    @SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Midnight.MODID)) {
            ConfigManager.sync(Midnight.MODID, Config.Type.INSTANCE);
        }
    }
}
