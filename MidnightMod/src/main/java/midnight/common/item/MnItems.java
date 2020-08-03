package midnight.common.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import midnight.common.block.MnBlocks;
import midnight.common.fluid.MnFluids;
import midnight.common.registry.ItemBuilder;
import midnight.common.registry.MidnightItemGroups;
import midnight.common.registry.RegistryManager;

@ObjectHolder("midnight")
public class MnItems {
    public static final Item BOGSHROOM_POWDER = register("bogshroom_powder", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item NIGHTSHROOM_POWDER = register("nightshroom_powder", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item DEWSHROOM_POWDER = register("dewshroom_powder", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item VIRIDSHROOM_POWDER = register("viridshroom_powder", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());

    public static final Item GEODE = register("geode", ItemBuilder.builder(GeodeItem::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item DARK_PEARL = register("dark_pearl", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item DARK_STICK = register("dark_stick", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item ROCKSHROOM_CLUMP = register("rockshroom_clump", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item TENEBRUM_INGOT = register("tenebrum_ingot", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item TENEBRUM_NUGGET = register("tenebrum_nugget", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item NAGRILITE_INGOT = register("nagrilite_ingot", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item NAGRILITE_NUGGET = register("nagrilite_nugget", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item EBONITE = register("ebonite", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item ARCHAIC_SHARD = register("archaic_shard", ItemBuilder.builder(Item::new).group(MidnightItemGroups.ITEMS).makeItem());

    public static final Item BLADESHROOM_CAP = register("bladeshroom_cap", ItemBuilder.builder(BladeshroomCapItem::new).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item BLADESHROOM_SPORES = register("bladeshroom_spores", ItemBuilder.builder(properties -> new BlockNamedItem(MnBlocks.BLADESHROOM, properties)).group(MidnightItemGroups.ITEMS).makeItem());

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

    public static final Item DARK_WATER_BUCKET = register("dark_water_bucket", ItemBuilder.builder(props -> new BucketItem(MnFluids.DARK_WATER, props)).group(MidnightItemGroups.ITEMS).makeItem());
    public static final Item MIASMA_BUCKET = register("miasma_bucket", ItemBuilder.builder(props -> new BucketItem(MnFluids.MIASMA, props)).group(MidnightItemGroups.ITEMS).makeItem());

    public static final Item ROCKSHROOM_SHEARS = Items.AIR;
    public static final Item ROCKSHROOM_BUCKET = Items.AIR;
    public static final Item WATER_ROCKSHROOM_BUCKET = Items.AIR;
    public static final Item DARKWATER_ROCKSHROOM_BUCKET = Items.AIR;

    public static final Item ADVANCEMENT_SNAPPER = Items.AIR;
    public static final Item ADVANCEMENT_HIGHNESS = Items.AIR;

    private static <I extends Item> I register(String id, I block) {
        RegistryManager.ITEMS.register(id, block);
        return block;
    }
}
