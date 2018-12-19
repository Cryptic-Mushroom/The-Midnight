package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.item.ItemBasic;
import com.mushroom.midnight.common.item.ItemBladeshroomCap;
import com.mushroom.midnight.common.item.ItemCookSuavis;
import com.mushroom.midnight.common.item.ItemMidnightDoor;
import com.mushroom.midnight.common.item.ItemMidnightSeed;
import com.mushroom.midnight.common.item.ItemRawDeceitfulSnapper;
import com.mushroom.midnight.common.item.ItemRawSuavis;
import com.mushroom.midnight.common.item.armors.ItemMidnightBoots;
import com.mushroom.midnight.common.item.armors.ItemMidnightChestplate;
import com.mushroom.midnight.common.item.armors.ItemMidnightHelmet;
import com.mushroom.midnight.common.item.armors.ItemMidnightLeggings;
import com.mushroom.midnight.common.item.tools.ItemMidnightAxe;
import com.mushroom.midnight.common.item.tools.ItemMidnightHoe;
import com.mushroom.midnight.common.item.tools.ItemMidnightPickaxe;
import com.mushroom.midnight.common.item.tools.ItemMidnightShovel;
import com.mushroom.midnight.common.item.tools.ItemMidnightSword;
import com.mushroom.midnight.common.util.CompassRotationGetter;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
    public static final Item TENEBRUM_DOOR = Items.AIR;

    public static final Item GEODE = Items.AIR;
    public static final Item DARK_PEARL = Items.AIR;
    public static final Item DARK_STICK = Items.AIR;
    public static final Item ROCKSHROOM_CLUMP = Items.AIR;
    public static final Item TENEBRUM_INGOT = Items.AIR;
    public static final Item TENEBRUM_NUGGET = Items.AIR;
    public static final Item NAGRILITE_INGOT = Items.AIR;
    public static final Item NAGRILITE_NUGGET = Items.AIR;
    public static final Item EBONYS = Items.AIR;
    public static final Item ARCHAIC_SHARD = Items.AIR;

    public static final Item BLADESHROOM_CAP = Items.AIR;
    public static final Item BLADESHROOM_SPORES = Items.AIR;

    public static final Item RAW_SUAVIS = Items.AIR;
    public static final Item COOK_SUAVIS = Items.AIR;
    public static final Item RAW_DECEITFUL_SNAPPER = Items.AIR;

    public static final Item SHADOWROOT_PICKAXE = Items.AIR;
    public static final Item NIGHTSTONE_PICKAXE = Items.AIR;
    public static final Item EBONYS_PICKAXE = Items.AIR;
    public static final Item NAGRILITE_PICKAXE = Items.AIR;
    public static final Item TENEBRUM_PICKAXE = Items.AIR;
    public static final Item SHADOWROOT_SHOVEL = Items.AIR;
    public static final Item NIGHTSTONE_SHOVEL = Items.AIR;
    public static final Item EBONYS_SHOVEL = Items.AIR;
    public static final Item NAGRILITE_SHOVEL = Items.AIR;
    public static final Item TENEBRUM_SHOVEL = Items.AIR;
    public static final Item SHADOWROOT_AXE = Items.AIR;
    public static final Item NIGHTSTONE_AXE = Items.AIR;
    public static final Item EBONYS_AXE = Items.AIR;
    public static final Item NAGRILITE_AXE = Items.AIR;
    public static final Item TENEBRUM_AXE = Items.AIR;
    public static final Item SHADOWROOT_HOE = Items.AIR;
    public static final Item NIGHTSTONE_HOE = Items.AIR;
    public static final Item EBONYS_HOE = Items.AIR;
    public static final Item NAGRILITE_HOE = Items.AIR;
    public static final Item TENEBRUM_HOE = Items.AIR;

    public static final Item SHADOWROOT_SWORD = Items.AIR;
    public static final Item NIGHTSTONE_SWORD = Items.AIR;
    public static final Item EBONYS_SWORD = Items.AIR;
    public static final Item NAGRILITE_SWORD = Items.AIR;
    public static final Item TENEBRUM_SWORD = Items.AIR;

    public static final Item ROCKSHROOM_HELMET = Items.AIR;
    public static final Item ROCKSHROOM_CHESTPLATE = Items.AIR;
    public static final Item ROCKSHROOM_LEGGINGS = Items.AIR;
    public static final Item ROCKSHROOM_BOOTS = Items.AIR;
    public static final Item TENEBRUM_HELMET = Items.AIR;
    public static final Item TENEBRUM_CHESTPLATE = Items.AIR;
    public static final Item TENEBRUM_LEGGINGS = Items.AIR;
    public static final Item TENEBRUM_BOOTS = Items.AIR;

    public static final Item ADVANCEMENT_RIFT = Items.AIR;
    public static final Item ADVANCEMENT_SNAPPER = Items.AIR;

    static List<Item> items;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        items = Lists.newArrayList(
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.NIGHTSHROOM_DOOR), "nightshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEWSHROOM_DOOR), "dewshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.VIRIDSHROOM_DOOR), "viridshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.TENEBRUM_DOOR), "tenebrum_door"),
                RegUtil.withName(new ItemBasic(), "geode"),
                RegUtil.withName(new ItemBasic(), "dark_pearl"),
                RegUtil.withName(new ItemBasic(), "dark_stick"),
                RegUtil.withName(new ItemBasic(), "rockshroom_clump"),
                RegUtil.withName(new ItemBasic(), "tenebrum_ingot"),
                RegUtil.withName(new ItemBasic(), "tenebrum_nugget"),
                RegUtil.withName(new ItemBasic(), "nagrilite_ingot"),
                RegUtil.withName(new ItemBasic(), "nagrilite_nugget"),
                RegUtil.withName(new ItemBasic(), "ebonys"),
                RegUtil.withName(new ItemBasic(), "archaic_shard"),
                RegUtil.withName(new ItemBladeshroomCap(), "bladeshroom_cap"),
                RegUtil.withName(new ItemMidnightSeed(() -> ModBlocks.BLADESHROOM.getDefaultState()), "bladeshroom_spores"),
                RegUtil.withName(new ItemRawSuavis(), "raw_suavis"),
                RegUtil.withName(new ItemCookSuavis(), "cook_suavis"),
                RegUtil.withName(new ItemRawDeceitfulSnapper(), "raw_deceitful_snapper"),
                RegUtil.withName(new ItemMidnightPickaxe(ModToolMaterials.SHADOWROOT), "shadowroot_pickaxe"),
                RegUtil.withName(new ItemMidnightPickaxe(ModToolMaterials.NIGHTSTONE), "nightstone_pickaxe"),
                RegUtil.withName(new ItemMidnightPickaxe(ModToolMaterials.EBONYS), "ebonys_pickaxe"),
                RegUtil.withName(new ItemMidnightPickaxe(ModToolMaterials.NAGRILITE), "nagrilite_pickaxe"),
                RegUtil.withName(new ItemMidnightPickaxe(ModToolMaterials.TENEBRUM), "tenebrum_pickaxe"),
                RegUtil.withName(new ItemMidnightShovel(ModToolMaterials.SHADOWROOT), "shadowroot_shovel"),
                RegUtil.withName(new ItemMidnightShovel(ModToolMaterials.NIGHTSTONE), "nightstone_shovel"),
                RegUtil.withName(new ItemMidnightShovel(ModToolMaterials.EBONYS), "ebonys_shovel"),
                RegUtil.withName(new ItemMidnightShovel(ModToolMaterials.NAGRILITE), "nagrilite_shovel"),
                RegUtil.withName(new ItemMidnightShovel(ModToolMaterials.TENEBRUM), "tenebrum_shovel"),
                RegUtil.withName(new ItemMidnightAxe(ModToolMaterials.SHADOWROOT, 6.0F, -3.2F), "shadowroot_axe"),
                RegUtil.withName(new ItemMidnightAxe(ModToolMaterials.NIGHTSTONE, 7.0F, -3.1F), "nightstone_axe"),
                RegUtil.withName(new ItemMidnightAxe(ModToolMaterials.EBONYS, 8.0F, -3.0F), "ebonys_axe"),
                RegUtil.withName(new ItemMidnightAxe(ModToolMaterials.NAGRILITE, 9.0F, -2.9F), "nagrilite_axe"),
                RegUtil.withName(new ItemMidnightAxe(ModToolMaterials.TENEBRUM, 10.0F, -2.8F), "tenebrum_axe"),
                RegUtil.withName(new ItemMidnightHoe(ModToolMaterials.SHADOWROOT), "shadowroot_hoe"),
                RegUtil.withName(new ItemMidnightHoe(ModToolMaterials.NIGHTSTONE), "nightstone_hoe"),
                RegUtil.withName(new ItemMidnightHoe(ModToolMaterials.EBONYS), "ebonys_hoe"),
                RegUtil.withName(new ItemMidnightHoe(ModToolMaterials.NAGRILITE), "nagrilite_hoe"),
                RegUtil.withName(new ItemMidnightHoe(ModToolMaterials.TENEBRUM), "tenebrum_hoe"),
                RegUtil.withName(new ItemMidnightSword(ModToolMaterials.SHADOWROOT), "shadowroot_sword"),
                RegUtil.withName(new ItemMidnightSword(ModToolMaterials.NIGHTSTONE), "nightstone_sword"),
                RegUtil.withName(new ItemMidnightSword(ModToolMaterials.EBONYS), "ebonys_sword"),
                RegUtil.withName(new ItemMidnightSword(ModToolMaterials.NAGRILITE), "nagrilite_sword"),
                RegUtil.withName(new ItemMidnightSword(ModToolMaterials.TENEBRUM), "tenebrum_sword"),
                RegUtil.withName(new ItemMidnightHelmet(ModArmorMaterials.ROCKSHROOM).setCreativeTab(null), "rockshroom_helmet"),
                RegUtil.withName(new ItemMidnightChestplate(ModArmorMaterials.ROCKSHROOM).setCreativeTab(null), "rockshroom_chestplate"),
                RegUtil.withName(new ItemMidnightLeggings(ModArmorMaterials.ROCKSHROOM).setCreativeTab(null), "rockshroom_leggings"),
                RegUtil.withName(new ItemMidnightBoots(ModArmorMaterials.ROCKSHROOM).setCreativeTab(null), "rockshroom_boots"),
                RegUtil.withName(new ItemMidnightHelmet(ModArmorMaterials.TENEBRUM), "tenebrum_helmet"),
                RegUtil.withName(new ItemMidnightChestplate(ModArmorMaterials.TENEBRUM), "tenebrum_chestplate"),
                RegUtil.withName(new ItemMidnightLeggings(ModArmorMaterials.TENEBRUM), "tenebrum_leggings"),
                RegUtil.withName(new ItemMidnightBoots(ModArmorMaterials.TENEBRUM), "tenebrum_boots"),
                RegUtil.withName(new ItemBasic().setCreativeTab(null), "advancement_rift"),
                RegUtil.withName(new ItemBasic().setCreativeTab(null), "advancement_snapper")
        );

        items.forEach(event.getRegistry()::register);

        Items.COMPASS.addPropertyOverride(new ResourceLocation("angle"), new CompassRotationGetter());
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
