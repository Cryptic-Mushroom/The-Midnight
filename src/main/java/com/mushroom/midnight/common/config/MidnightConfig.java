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
    @Config.Name("general")
    @Config.LangKey("config_cat.midnight.general")
    @Config.Comment("All the options that can only be set on the server.")
    public static CatGeneral general = new CatGeneral();

    @Config.Name("client")
    @Config.LangKey("config_cat.midnight.client")
    @Config.Comment("All the options that can be modified by players on server.")
    public static CatClient client = new CatClient();

    public static class CatGeneral {
        @Config.Name("midnight_dimension_id")
        @Config.LangKey("config.midnight.midnight_dimension_id")
        @Config.Comment("The ID to be used for the Midnight Dimension")
        @Config.RequiresMcRestart
        public int midnightDimensionId = -23;

        @Config.Name("rift_shaders")
        @Config.LangKey("config.midnight.rift_shaders")
        @Config.Comment("If true, rifts will be rendered with custom shaders for standard effects. If false, a simpler texture will be rendered.")
        public boolean riftShaders = true;

        @Config.Name("bladeshroom_damage_chance")
        @Config.LangKey("config.midnight.bladeshroom_damage_chance")
        @Config.Comment("The chance to take damage when picking a bladeshroom cap.")
        @Config.RangeInt(min=0, max=100)
        public int bladeshroomDamageChance = 5;

        @Config.Name("can_respawn_in_midnight")
        @Config.LangKey("config.midnight.can_respawn_in_midnight")
        @Config.Comment("If true, players will be allowed to respawn in Midnight.")
        public boolean canRespawnInMidnight = false;

        @Config.Name("allow_lightning_damage")
        @Config.LangKey("config.midnight.allow_lightning_damage")
        @Config.Comment("Allows the lightnings in Midnight to burn the lands and do damage to entities.")
        public boolean allowLightningDamage = false;

        @Config.Name("rift_spawn_rarity")
        @Config.LangKey("config.midnight.rift_spawn_rarity")
        @Config.Comment("The rarity that a rift appears. Larger numbers increase rarity. If 0, rifts don't spawn.")
        @Config.RangeInt(min=0, max=10000)
        public int riftSpawnRarity = 1000;

        @Config.Name("rifter_spawn_rarity")
        @Config.LangKey("config.midnight.rifter_spawn_rarity")
        @Config.Comment("The rarity that rifts spawn rifters. Larger numbers increase rarity. If 0, rifters don't spawn.")
        @Config.RangeInt(min=0, max=10000)
        public int rifterSpawnRarity = 1000;

        @Config.Name("max_rifter_by_rift")
        @Config.LangKey("config.midnight.max_rifter_by_rift")
        @Config.Comment("The maximum of rifters spawning from a rift.")
        @Config.RangeInt(min=1, max=5)
        public int maxRifterByRift = 1;
    }

    public static class CatClient {
        @Config.Name("hide_vignette_effect")
        @Config.LangKey("config.midnight.hide_vignette_effect")
        @Config.Comment("Hides the vignette effect in the dark areas of Midnight.")
        public boolean hideVignetteEffect = false;
    }

    @SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Midnight.MODID)) {
            ConfigManager.sync(Midnight.MODID, Config.Type.INSTANCE);
        }
    }
}
