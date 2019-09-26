package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.item.HighnessItemRenderer;
import com.mushroom.midnight.client.render.item.ShieldItemRenderer;
import com.mushroom.midnight.common.item.BladeshroomCapItem;
import com.mushroom.midnight.common.item.DeceitfulSnapperItem;
import com.mushroom.midnight.common.item.DrinkableItem;
import com.mushroom.midnight.common.item.GeodeItem;
import com.mushroom.midnight.common.item.RawSuavisItem;
import com.mushroom.midnight.common.item.SporeBombItem;
import com.mushroom.midnight.common.item.UnstableFruitItem;
import com.mushroom.midnight.common.item.tool.MidnightAxeItem;
import com.mushroom.midnight.common.item.tool.MidnightHoeItem;
import com.mushroom.midnight.common.item.tool.MidnightPickaxeItem;
import com.mushroom.midnight.common.item.tool.MidnightShieldItem;
import com.mushroom.midnight.common.item.tool.MidnightShovelItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightItems {
    public static final Item BOGSHROOM_POWDER = Items.AIR;
    public static final Item NIGHTSHROOM_POWDER = Items.AIR;
    public static final Item DEWSHROOM_POWDER = Items.AIR;
    public static final Item VIRIDSHROOM_POWDER = Items.AIR;

    public static final Item GEODE = Items.AIR;
    public static final Item DARK_PEARL = Items.AIR;
    public static final Item DARK_STICK = Items.AIR;
    public static final Item ROCKSHROOM_CLUMP = Items.AIR;
    public static final Item TENEBRUM_INGOT = Items.AIR;
    public static final Item TENEBRUM_NUGGET = Items.AIR;
    public static final Item NAGRILITE_INGOT = Items.AIR;
    public static final Item NAGRILITE_NUGGET = Items.AIR;
    public static final Item EBONITE = Items.AIR;
    public static final Item ARCHAIC_SHARD = Items.AIR;

    public static final Item BLADESHROOM_CAP = Items.AIR;
    public static final Item BLADESHROOM_SPORES = Items.AIR;

    public static final Item RAW_SUAVIS = Items.AIR;
    public static final Item COOKED_SUAVIS = Items.AIR;
    public static final Item DECEITFUL_SNAPPER = Items.AIR;
    public static final Item RAW_STAG_FLANK = Items.AIR;
    public static final Item COOKED_STAG_FLANK = Items.AIR;
    public static final Item COOKED_STINGER_EGG = Items.AIR;
    public static final Item HUNTER_WING = Items.AIR;
    public static final Item COOKED_HUNTER_WING = Items.AIR;
    public static final Item GLOB_FUNGUS_HAND = Items.AIR;
    public static final Item UNSTABLE_SEEDS = Items.AIR;
    public static final Item BLUE_UNSTABLE_FRUIT = Items.AIR;
    public static final Item LIME_UNSTABLE_FRUIT = Items.AIR;
    public static final Item GREEN_UNSTABLE_FRUIT = Items.AIR;

    public static final Item NIGHTSHROOM_SPORE_BOMB = Items.AIR;
    public static final Item DEWSHROOM_SPORE_BOMB = Items.AIR;
    public static final Item VIRIDSHROOM_SPORE_BOMB = Items.AIR;
    public static final Item BOGSHROOM_SPORE_BOMB = Items.AIR;

    public static final Item SHADOWROOT_PICKAXE = Items.AIR;
    public static final Item NIGHTSTONE_PICKAXE = Items.AIR;
    public static final Item EBONITE_PICKAXE = Items.AIR;
    public static final Item NAGRILITE_PICKAXE = Items.AIR;
    public static final Item TENEBRUM_PICKAXE = Items.AIR;
    public static final Item SHADOWROOT_SHOVEL = Items.AIR;
    public static final Item NIGHTSTONE_SHOVEL = Items.AIR;
    public static final Item EBONITE_SHOVEL = Items.AIR;
    public static final Item NAGRILITE_SHOVEL = Items.AIR;
    public static final Item TENEBRUM_SHOVEL = Items.AIR;
    public static final Item SHADOWROOT_AXE = Items.AIR;
    public static final Item NIGHTSTONE_AXE = Items.AIR;
    public static final Item EBONITE_AXE = Items.AIR;
    public static final Item NAGRILITE_AXE = Items.AIR;
    public static final Item TENEBRUM_AXE = Items.AIR;
    public static final Item SHADOWROOT_HOE = Items.AIR;
    public static final Item NIGHTSTONE_HOE = Items.AIR;
    public static final Item EBONITE_HOE = Items.AIR;
    public static final Item NAGRILITE_HOE = Items.AIR;
    public static final Item TENEBRUM_HOE = Items.AIR;

    public static final Item SHADOWROOT_SWORD = Items.AIR;
    public static final Item NIGHTSTONE_SWORD = Items.AIR;
    public static final Item EBONITE_SWORD = Items.AIR;
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
    public static final Item ROCKSHROOM_SHIELD = Items.AIR;

    public static final Item DECEITFUL_SNAPPER_BUCKET = Items.AIR;

    public static final Item DARK_WATER_BUCKET = Items.AIR;
    public static final Item MIASMA_BUCKET = Items.AIR;

    public static final Item ADVANCEMENT_SNAPPER = Items.AIR;
    public static final Item ADVANCEMENT_HIGHNESS = Items.AIR;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.ITEMS))

                .add("geode", GeodeItem::new)

                .add("bogshroom_powder", Item::new)
                .add("nightshroom_powder", Item::new)
                .add("dewshroom_powder", Item::new)
                .add("viridshroom_powder", Item::new)
                .add("dark_pearl", Item::new)
                .add("dark_stick", Item::new)
                .add("rockshroom_clump", Item::new)
                .add("tenebrum_ingot", Item::new)
                .add("tenebrum_nugget", Item::new)
                .add("nagrilite_ingot", Item::new)
                .add("nagrilite_nugget", Item::new)
                .add("ebonite", Item::new)
                .add("archaic_shard", Item::new)

                .add("raw_suavis", props -> new RawSuavisItem(props.food(MidnightFood.RAW_SUAVIS)))
                .add("cooked_suavis", props -> new Item(props.food(MidnightFood.COOKED_SUAVIS)))
                .add("deceitful_snapper", props -> new DeceitfulSnapperItem(props.food(MidnightFood.DECEITFUL_SNAPPER)))
                .add("raw_stag_flank", props -> new Item(props.food(MidnightFood.RAW_STAG_FLANK)))
                .add("cooked_stag_flank", props -> new Item(props.food(MidnightFood.COOKED_STAG_FLANK)))
                .add("cooked_stinger_egg", props -> new DrinkableItem(props.food(MidnightFood.COOKED_STINGER_EGG)))
                .add("hunter_wing", props -> new Item(props.food(MidnightFood.HUNTER_WING)))
                .add("cooked_hunter_wing", props -> new Item(props.food(MidnightFood.COOKED_HUNTER_WING)))
                .add("glob_fungus_hand", props -> new Item(props.food(MidnightFood.GLOB_FUNGUS_HAND)))

                .add("blue_unstable_fruit", props -> new UnstableFruitItem(UnstableFruitItem.Color.BLUE, props.food(MidnightFood.UNSTABLE_FRUIT)))
                .add("lime_unstable_fruit", props -> new UnstableFruitItem(UnstableFruitItem.Color.LIME, props.food(MidnightFood.UNSTABLE_FRUIT)))
                .add("green_unstable_fruit", props -> new UnstableFruitItem(UnstableFruitItem.Color.GREEN, props.food(MidnightFood.UNSTABLE_FRUIT)))

                .add("deceitful_snapper_bucket", props -> new FishBucketItem(MidnightEntities.DECEITFUL_SNAPPER, MidnightFluids.DARK_WATER, props.maxStackSize(1)));

        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.TOOLS))

                .add("shadowroot_pickaxe", props -> new MidnightPickaxeItem(MidnightItemTiers.SHADOWROOT, props))
                .add("shadowroot_axe", props -> new MidnightAxeItem(MidnightItemTiers.SHADOWROOT, props))
                .add("shadowroot_shovel", props -> new MidnightShovelItem(MidnightItemTiers.SHADOWROOT, props))
                .add("shadowroot_hoe", props -> new MidnightHoeItem(MidnightItemTiers.SHADOWROOT, props))
                .add("nightstone_pickaxe", props -> new MidnightPickaxeItem(MidnightItemTiers.NIGHTSTONE, props))
                .add("nightstone_axe", props -> new MidnightAxeItem(MidnightItemTiers.NIGHTSTONE, props))
                .add("nightstone_shovel", props -> new MidnightShovelItem(MidnightItemTiers.NIGHTSTONE, props))
                .add("nightstone_hoe", props -> new MidnightHoeItem(MidnightItemTiers.NIGHTSTONE, props))
                .add("ebonite_pickaxe", props -> new MidnightPickaxeItem(MidnightItemTiers.EBONITE, props))
                .add("ebonite_axe", props -> new MidnightAxeItem(MidnightItemTiers.EBONITE, props))
                .add("ebonite_shovel", props -> new MidnightShovelItem(MidnightItemTiers.EBONITE, props))
                .add("ebonite_hoe", props -> new MidnightHoeItem(MidnightItemTiers.EBONITE, props))
                .add("nagrilite_pickaxe", props -> new MidnightPickaxeItem(MidnightItemTiers.NAGRILITE, props))
                .add("nagrilite_axe", props -> new MidnightAxeItem(MidnightItemTiers.NAGRILITE, props))
                .add("nagrilite_shovel", props -> new MidnightShovelItem(MidnightItemTiers.NAGRILITE, props))
                .add("nagrilite_hoe", props -> new MidnightHoeItem(MidnightItemTiers.NAGRILITE, props))
                .add("tenebrum_pickaxe", props -> new MidnightPickaxeItem(MidnightItemTiers.TENEBRUM, props))
                .add("tenebrum_axe", props -> new MidnightAxeItem(MidnightItemTiers.TENEBRUM, props))
                .add("tenebrum_shovel", props -> new MidnightShovelItem(MidnightItemTiers.TENEBRUM, props))
                .add("tenebrum_hoe", props -> new MidnightHoeItem(MidnightItemTiers.TENEBRUM, props));

        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.COMBAT))

                .add("shadowroot_sword", props -> new SwordItem(MidnightItemTiers.SHADOWROOT, 3, -2.4F, props))
                .add("nightstone_sword", props -> new SwordItem(MidnightItemTiers.NIGHTSTONE, 3, -2.4F, props))
                .add("ebonite_sword", props -> new SwordItem(MidnightItemTiers.EBONITE, 3, -2.4F, props))
                .add("nagrilite_sword", props -> new SwordItem(MidnightItemTiers.NAGRILITE, 3, -2.4F, props))
                .add("tenebrum_sword", props -> new SwordItem(MidnightItemTiers.TENEBRUM, 3, -2.4F, props))
                .add("rockshroom_helmet", props -> new ArmorItem(MidnightArmorMaterials.ROCKSHROOM, EquipmentSlotType.HEAD, props))
                .add("rockshroom_chestplate", props -> new ArmorItem(MidnightArmorMaterials.ROCKSHROOM, EquipmentSlotType.CHEST, props))
                .add("rockshroom_leggings", props -> new ArmorItem(MidnightArmorMaterials.ROCKSHROOM, EquipmentSlotType.LEGS, props))
                .add("rockshroom_boots", props -> new ArmorItem(MidnightArmorMaterials.ROCKSHROOM, EquipmentSlotType.FEET, props))
                .add("tenebrum_helmet", props -> new ArmorItem(MidnightArmorMaterials.TENEBRUM, EquipmentSlotType.HEAD, props))
                .add("tenebrum_chestplate", props -> new ArmorItem(MidnightArmorMaterials.TENEBRUM, EquipmentSlotType.CHEST, props))
                .add("tenebrum_leggings", props -> new ArmorItem(MidnightArmorMaterials.TENEBRUM, EquipmentSlotType.LEGS, props))
                .add("tenebrum_boots", props -> new ArmorItem(MidnightArmorMaterials.TENEBRUM, EquipmentSlotType.FEET, props))
                .add("rockshroom_shield", props -> new MidnightShieldItem(MidnightArmorMaterials.ROCKSHROOM, props.maxDamage(336).setTEISR(() -> ShieldItemRenderer::new)))

                .add("bladeshroom_cap", BladeshroomCapItem::new);

        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.COMBAT).maxStackSize(1))
                .add("nightshroom_spore_bomb", props -> new SporeBombItem(SporeBombItem.Type.NIGHTSHROOM, props))
                .add("dewshroom_spore_bomb", props -> new SporeBombItem(SporeBombItem.Type.DEWSHROOM, props))
                .add("viridshroom_spore_bomb", props -> new SporeBombItem(SporeBombItem.Type.VIRIDSHROOM, props))
                .add("bogshroom_spore_bomb", props -> new SporeBombItem(SporeBombItem.Type.BOGSHROOM, props));

        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(MidnightItemGroups.ITEMS))
                .add("dark_water_bucket", props -> new BucketItem(MidnightFluids.DARK_WATER, props))
                .add("miasma_bucket", props -> new BucketItem(MidnightFluids.MIASMA, props));

        RegUtil.items(event.getRegistry())
                .withProperties(Item.Properties::new)

                .add("advancement_snapper", Item::new)
                .add("advancement_highness", props -> new Item(props.setTEISR(() -> HighnessItemRenderer::new)));

        RegUtil.items(event.getRegistry()).withProperties(() -> new Item.Properties().group(MidnightItemGroups.ITEMS))
                .add("bladeshroom_spores", MidnightBlocks.BLADESHROOM, BlockNamedItem::new)
                .add("unstable_seeds", MidnightBlocks.UNSTABLE_BUSH, BlockNamedItem::new);

        RegUtil.items(event.getRegistry()).withProperties(() -> new Item.Properties().group(ItemGroup.MISC))
                .add("rifter_spawn_egg", props -> new SpawnEggItem(MidnightEntities.RIFTER, 0x384740, 0x5E8C6C, props))
                .add("hunter_spawn_egg", props -> new SpawnEggItem(MidnightEntities.HUNTER, 0x2C3964, 0x19203A, props))
                .add("nova_spawn_egg", props -> new SpawnEggItem(MidnightEntities.NOVA, 0x932C3B, 0x47415E, props))
                .add("crystal_bug_spawn_egg", props -> new SpawnEggItem(MidnightEntities.CRYSTAL_BUG, 0x4B2277, 0xAF27E0, props))
                //.add("penumbrian_spawn_egg", props -> new SpawnEggItem(MidnightEntities.PENUMBRIAN, 0x8CDCC9, 0x456B6F, props))
                //.add("tree_hopper_spawn_egg", props -> new SpawnEggItem(MidnightEntities.TREE_HOPPER, 0x6E5A84, 0x303847, props))
                .add("stinger_spawn_egg", props -> new SpawnEggItem(MidnightEntities.STINGER, 0x291E23, 0x4D6A43, props))
                .add("nightstag_spawn_egg", props -> new SpawnEggItem(MidnightEntities.NIGHTSTAG, 0x946CC2, 0x221F1D, props))
                .add("deceitful_snapper_spawn_egg", props -> new SpawnEggItem(MidnightEntities.DECEITFUL_SNAPPER, 0x45404E, 0x906F99, props))
                .add("bulb_angler_spawn_egg", props -> new SpawnEggItem(MidnightEntities.BULB_ANGLER, 0x1f042e, 0xebe579, props))
                .add("skulk_spawn_egg", props -> new SpawnEggItem(MidnightEntities.SKULK, 0x4B5065, 0x4E268A, props));
//                .add("shade_squirrel_spawn_egg", props -> new SpawnEggItem(MidnightEntities.SHADE_SQUIRREL, 0x4f435a, 0xdccf70, props))

        // TODO: Reimplement
//        Items.COMPASS.addPropertyOverride(new ResourceLocation("angle"), new CompassRotationGetter());
    }
}
