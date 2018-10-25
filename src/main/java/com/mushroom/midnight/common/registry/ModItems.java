package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.item.ItemBasic;
import com.mushroom.midnight.common.item.ItemMidnightDoor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModItems {
    public static final Item SHADOWROOT_DOOR = Items.AIR;
    public static final Item DARK_WILLOW_DOOR = Items.AIR;
    public static final Item DEAD_WOOD_DOOR = Items.AIR;

    public static final Item NIGHTSHROOM_DOOR = Items.AIR;
    public static final Item DEWSHROOM_DOOR = Items.AIR;
    public static final Item VIRIDSHROOM_DOOR = Items.AIR;

    public static final Item DARK_PEARL = Items.AIR;

    static List<Item> items;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        items = Lists.newArrayList(
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.SHADOWROOT_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "shadowroot_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DARK_WILLOW_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "dark_willow_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEAD_WOOD_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "dead_wood_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.NIGHTSHROOM_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "nightshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEWSHROOM_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "dewshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.VIRIDSHROOM_DOOR).setCreativeTab(Midnight.MIDNIGHT_TAB), "viridshroom_door"),
                RegUtil.withName(new ItemBasic(), "dark_pearl")
        );

        items.forEach(event.getRegistry()::register);
    }

    public static void onInit() {
        OreDictionary.registerOre("doorWood", SHADOWROOT_DOOR);
        OreDictionary.registerOre("doorWood", DARK_WILLOW_DOOR);
        OreDictionary.registerOre("doorWood", DEAD_WOOD_DOOR);
    }

    public static Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }
}
