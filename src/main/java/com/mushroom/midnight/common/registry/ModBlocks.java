package com.mushroom.midnight.common.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockBasic;
import com.mushroom.midnight.common.block.BlockCrystal;
import com.mushroom.midnight.common.block.BlockDarkWillowSlab;
import com.mushroom.midnight.common.block.BlockDarkWillowStairs;
import com.mushroom.midnight.common.block.BlockDeadWoodSlab;
import com.mushroom.midnight.common.block.BlockDeadWoodStairs;
import com.mushroom.midnight.common.block.BlockDewshroomSlab;
import com.mushroom.midnight.common.block.BlockDewshroomStairs;
import com.mushroom.midnight.common.block.BlockDoubleMidnightPlant;
import com.mushroom.midnight.common.block.BlockGlowingDoublePlant;
import com.mushroom.midnight.common.block.BlockGlowingPlant;
import com.mushroom.midnight.common.block.BlockMiasmaFluid;
import com.mushroom.midnight.common.block.BlockMiasmaSurface;
import com.mushroom.midnight.common.block.BlockMidnightButton;
import com.mushroom.midnight.common.block.BlockMidnightDirt;
import com.mushroom.midnight.common.block.BlockMidnightDoor;
import com.mushroom.midnight.common.block.BlockMidnightFence;
import com.mushroom.midnight.common.block.BlockMidnightFenceGate;
import com.mushroom.midnight.common.block.BlockMidnightFungi;
import com.mushroom.midnight.common.block.BlockMidnightFungiHat;
import com.mushroom.midnight.common.block.BlockMidnightFungiShelf;
import com.mushroom.midnight.common.block.BlockMidnightFungiStem;
import com.mushroom.midnight.common.block.BlockMidnightFurnace;
import com.mushroom.midnight.common.block.BlockMidnightGem;
import com.mushroom.midnight.common.block.BlockMidnightGrass;
import com.mushroom.midnight.common.block.BlockMidnightLeaves;
import com.mushroom.midnight.common.block.BlockMidnightLog;
import com.mushroom.midnight.common.block.BlockMidnightPlant;
import com.mushroom.midnight.common.block.BlockMidnightPressurePlate;
import com.mushroom.midnight.common.block.BlockMidnightSapling;
import com.mushroom.midnight.common.block.BlockMidnightTrapDoor;
import com.mushroom.midnight.common.block.BlockMushroomInside;
import com.mushroom.midnight.common.block.BlockNightshroomSlab;
import com.mushroom.midnight.common.block.BlockNightshroomStairs;
import com.mushroom.midnight.common.block.BlockNightstone;
import com.mushroom.midnight.common.block.BlockNightstoneSlab;
import com.mushroom.midnight.common.block.BlockNightstoneStairs;
import com.mushroom.midnight.common.block.BlockNightstoneWall;
import com.mushroom.midnight.common.block.BlockShadowrootChest;
import com.mushroom.midnight.common.block.BlockShadowrootCraftingTable;
import com.mushroom.midnight.common.block.BlockShadowrootSlab;
import com.mushroom.midnight.common.block.BlockShadowrootStairs;
import com.mushroom.midnight.common.block.BlockTrenchstoneSlab;
import com.mushroom.midnight.common.block.BlockTrenchstoneStairs;
import com.mushroom.midnight.common.block.BlockTrenchstoneWall;
import com.mushroom.midnight.common.block.BlockViridshroomSlab;
import com.mushroom.midnight.common.block.BlockViridshroomStairs;
import com.mushroom.midnight.common.block.PlantBehaviorType;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import com.mushroom.midnight.common.tile.base.TileEntityShadowrootChest;
import com.mushroom.midnight.common.world.feature.DefaultTreeFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModBlocks {

    static List<Block> blocks;

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;
    public static final Block SHADOWROOT_PLANKS = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANKS = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_LEAVES = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block CHISELED_NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block TRENCHSTONE = Blocks.AIR;

    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE_LIT = Blocks.AIR;

    public static final Block MIDNIGHT_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block TALL_MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block DOUBLE_MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block NIGHTSHROOM = Blocks.AIR;
    public static final Block DOUBLE_NIGHTSHROOM = Blocks.AIR;
    public static final Block NIGHTSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM = Blocks.AIR;
    public static final Block DOUBLE_DEWSHROOM = Blocks.AIR;
    public static final Block DEWSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM = Blocks.AIR;
    public static final Block DOUBLE_VIRIDSHROOM = Blocks.AIR;
    public static final Block VIRIDSHROOM_SHELF = Blocks.AIR;

    public static final Block VIRIDSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM_STEM = Blocks.AIR;
    public static final Block VIRIDSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_STEM = Blocks.AIR;
    public static final Block NIGHTSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_PLANKS = Blocks.AIR;

    public static final Block DEWSHROOM_STEM = Blocks.AIR;
    public static final Block DEWSHROOM_HAT = Blocks.AIR;

    public static final Block LUMEN_BUD = Blocks.AIR;
    public static final Block DOUBLE_LUMEN_BUD = Blocks.AIR;

    public static final Block CRYSTAL_FLOWER = Blocks.AIR;

    public static final Block SHADOWROOT_SAPLING = Blocks.AIR;
    public static final Block DARK_WILLOW_SAPLING = Blocks.AIR;

    public static final Block SHADOWROOT_DOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_DOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_DOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_DOOR = Blocks.AIR;
    public static final Block DEWSHROOM_DOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOOR = Blocks.AIR;

    public static final Block SHADOWROOT_TRAPDOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_TRAPDOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_TRAPDOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block DEWSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_TRAPDOOR = Blocks.AIR;

    public static final Block BLOOMCRYSTAL = Blocks.AIR;
    public static final Block BLOOMCRYSTAL_ROCK = Blocks.AIR;

    public static final Block ROUXE = Blocks.AIR;
    public static final Block ROUXE_ROCK = Blocks.AIR;

    public static final Block MIASMA_SURFACE = Blocks.AIR;
    public static final Block MIASMA = Blocks.AIR;

    public static final Block MUSHROOM_INSIDE = Blocks.AIR;
    
    public static final Block SHADOWROOT_SLAB = Blocks.AIR;
    public static final Block DOUBLE_SHADOWROOT_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_SLAB = Blocks.AIR;
    public static final Block DOUBLE_DARK_WILLOW_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_SLAB = Blocks.AIR;
    public static final Block DOUBLE_DEAD_WOOD_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_SLAB = Blocks.AIR;
    public static final Block DOUBLE_NIGHTSHROOM_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_SLAB = Blocks.AIR;
    public static final Block DOUBLE_VIRIDSHROOM_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_SLAB = Blocks.AIR;
    public static final Block DOUBLE_DEWSHROOM_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_SLAB = Blocks.AIR;
    public static final Block DOUBLE_TRENCHSTONE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_SLAB = Blocks.AIR;
    public static final Block DOUBLE_NIGHTSTONE_SLAB = Blocks.AIR;
    
    public static final Block SHADOWROOT_STAIRS = Blocks.AIR;
    public static final Block DARK_WILLOW_STAIRS = Blocks.AIR;
    public static final Block DEAD_WOOD_STAIRS = Blocks.AIR;
    public static final Block NIGHTSHROOM_STAIRS = Blocks.AIR;
    public static final Block VIRIDSHROOM_STAIRS = Blocks.AIR;
    public static final Block DEWSHROOM_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_STAIRS = Blocks.AIR;
    
    public static final Block TRENCHSTONE_WALL = Blocks.AIR;
    public static final Block NIGHTSTONE_WALL = Blocks.AIR;
    
    public static final Block SHADOWROOT_FENCE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE = Blocks.AIR;
    
    public static final Block SHADOWROOT_FENCE_GATE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE_GATE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE_GATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE_GATE = Blocks.AIR;
    
    public static final Block SHADOWROOT_BUTTON = Blocks.AIR;
    public static final Block DARK_WILLOW_BUTTON = Blocks.AIR;
    public static final Block DEAD_WOOD_BUTTON = Blocks.AIR;
    public static final Block NIGHTSHROOM_BUTTON = Blocks.AIR;
    public static final Block VIRIDSHROOM_BUTTON = Blocks.AIR;
    public static final Block DEWSHROOM_BUTTON = Blocks.AIR;
    public static final Block TRENCHSTONE_BUTTON = Blocks.AIR;
    public static final Block NIGHTSTONE_BUTTON = Blocks.AIR;
    
    public static final Block SHADOWROOT_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DARK_WILLOW_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEAD_WOOD_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEWSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TRENCHSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSTONE_PRESSURE_PLATE = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks = Lists.newArrayList(
                RegUtil.withName(new BlockMidnightGrass(), "midnight_grass"),
                RegUtil.withName(new BlockMidnightDirt(), "midnight_dirt"),
                RegUtil.withName(new BlockNightstone(), "nightstone"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "chiseled_nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "trenchstone")
                        .withHarvestLevel("pickaxe", 2)
                        .setHardness(5.0F)
                        .setResistance(200.0F),
                RegUtil.withName(new BlockMidnightLog(), "shadowroot_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> SHADOWROOT_SAPLING), "shadowroot_leaves"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "shadowroot_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dead_wood_log"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dead_wood_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dark_willow_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> DARK_WILLOW_SAPLING), "dark_willow_leaves"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dark_willow_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFungiStem(), "nightshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> NIGHTSHROOM), "nightshroom_hat"),
                RegUtil.withName(new BlockMidnightFungiStem(), "dewshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> DEWSHROOM), "dewshroom_hat"),
                RegUtil.withName(new BlockMidnightFungiStem(), "viridshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> VIRIDSHROOM), "viridshroom_hat"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "nightshroom_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dewshroom_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockBasic(Material.WOOD), "viridshroom_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockCrystal().setLightLevel(1.0F), "bloomcrystal"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "bloomcrystal_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .setLightLevel(1.0F)
                        .setHardness(4.0F),
                RegUtil.withName(new BlockCrystal(), "rouxe"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "rouxe_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .setHardness(4.0F),
                RegUtil.withName(new BlockMiasmaSurface(), "miasma_surface"),
                RegUtil.withName(new BlockMiasmaFluid(), "miasma"),
                RegUtil.withName(new BlockMidnightGem(() -> ModItems.DARK_PEARL), "dark_pearl_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "dark_pearl_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockMidnightPlant(PlantBehaviorType.BUSH), "tall_midnight_grass"),
                RegUtil.withName(new BlockDoubleMidnightPlant(PlantBehaviorType.BUSH), "double_midnight_grass"),
                RegUtil.withName(new BlockMidnightFungi(() -> new LargeFungiFeature(
                        ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                        ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
                )), "nightshroom"),
                RegUtil.withName(new BlockGlowingDoublePlant(), "double_nightshroom"),
                RegUtil.withName(new BlockMidnightFungi(() -> new LargeFungiFeature(
                        ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                        ModBlocks.DEWSHROOM_HAT.getDefaultState()
                )), "dewshroom"),
                RegUtil.withName(new BlockGlowingDoublePlant(), "double_dewshroom"),
                RegUtil.withName(new BlockMidnightFungi(() -> new LargeFungiFeature(
                        ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                        ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
                )), "viridshroom"),
                RegUtil.withName(new BlockGlowingDoublePlant(), "double_viridshroom"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "nightshroom_shelf"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "dewshroom_shelf"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "viridshroom_shelf"),
                RegUtil.withName(new BlockGlowingPlant(), "lumen_bud"),
                RegUtil.withName(new BlockGlowingDoublePlant(), "double_lumen_bud"),
                RegUtil.withName(new BlockGlowingPlant(), "crystal_flower"),
                RegUtil.withName(new BlockMidnightSapling(ShadowrootTreeFeature::new), "shadowroot_sapling"),
                RegUtil.withName(new BlockMidnightSapling(() -> new DefaultTreeFeature(DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, 6)), "dark_willow_sapling"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.NIGHTSHROOM_DOOR), "nightshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DEWSHROOM_DOOR), "dewshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.VIRIDSHROOM_DOOR), "viridshroom_door"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "shadowroot_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dark_willow_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dead_wood_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "nightshroom_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dewshroom_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "viridshroom_trapdoor"),
                RegUtil.withName(new BlockShadowrootCraftingTable(), "shadowroot_crafting_table"),
                RegUtil.withName(new BlockShadowrootChest(), "shadowroot_chest"),
                RegUtil.withName(new BlockMidnightFurnace(false), "midnight_furnace"),
                RegUtil.withName(new BlockMidnightFurnace(true), "midnight_furnace_lit"),
                RegUtil.withName(new BlockMushroomInside(), "mushroom_inside"),
                RegUtil.withName(new BlockShadowrootSlab(false), "shadowroot_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockShadowrootSlab(true), "double_shadowroot_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDarkWillowSlab(false), "dark_willow_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDarkWillowSlab(true), "double_dark_willow_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDeadWoodSlab(false), "dead_wood_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDeadWoodSlab(true), "double_dead_wood_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockNightshroomSlab(false), "nightshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockNightshroomSlab(true), "double_nightshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockViridshroomSlab(false), "viridshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockViridshroomSlab(true), "double_viridshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDewshroomSlab(false), "dewshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockDewshroomSlab(true), "double_dewshroom_slab")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockTrenchstoneSlab(false), "trenchstone_slab")
                    .setHardness(5.0F)
                    .setResistance(200.0F),
                RegUtil.withName(new BlockTrenchstoneSlab(true), "double_trenchstone_slab")
                    .setHardness(5.0F)
                    .setResistance(200.0F),
                RegUtil.withName(new BlockNightstoneSlab(false), "nightstone_slab")
                    .setHardness(1.5F)
                    .setResistance(10.0F),
                RegUtil.withName(new BlockNightstoneSlab(true), "double_nightstone_slab")
                    .setHardness(1.5F)
                    .setResistance(10.0F),
                RegUtil.withName(new BlockShadowrootStairs(), "shadowroot_stairs"),
                RegUtil.withName(new BlockDarkWillowStairs(), "dark_willow_stairs"),
                RegUtil.withName(new BlockDeadWoodStairs(), "dead_wood_stairs"),
                RegUtil.withName(new BlockNightshroomStairs(), "nightshroom_stairs"),
                RegUtil.withName(new BlockViridshroomStairs(), "viridshroom_stairs"),
                RegUtil.withName(new BlockDewshroomStairs(), "dewshroom_stairs"),
                RegUtil.withName(new BlockTrenchstoneStairs(), "trenchstone_stairs"),
                RegUtil.withName(new BlockNightstoneStairs(), "nightstone_stairs"),
                RegUtil.withName(new BlockTrenchstoneWall(), "trenchstone_wall"),
                RegUtil.withName(new BlockNightstoneWall(), "nightstone_wall"),
                RegUtil.withName(new BlockMidnightFence(MapColor.CYAN_STAINED_HARDENED_CLAY), "shadowroot_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFence(MapColor.CYAN_STAINED_HARDENED_CLAY), "dark_willow_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFence(MapColor.GRAY), "dead_wood_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFence(MapColor.LIGHT_BLUE), "nightshroom_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFence(MapColor.GREEN_STAINED_HARDENED_CLAY), "viridshroom_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFence(MapColor.PURPLE), "dewshroom_fence")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.CYAN_STAINED_HARDENED_CLAY), "shadowroot_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.CYAN_STAINED_HARDENED_CLAY), "dark_willow_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.GRAY), "dead_wood_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.LIGHT_BLUE), "nightshroom_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.GREEN_STAINED_HARDENED_CLAY), "viridshroom_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightFenceGate(MapColor.PURPLE), "dewshroom_fence_gate")
                    .setHardness(2.0F)
                    .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightButton(true), "shadowroot_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(true), "dark_willow_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(true), "dead_wood_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(true), "nightshroom_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(true), "viridshroom_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(true), "dewshroom_button")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightButton(false), "trenchstone_button")
                    .setHardness(4.0F),
                RegUtil.withName(new BlockMidnightButton(false), "nightstone_button")
                    .setHardness(0.5F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "shadowroot_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "dark_willow_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "dead_wood_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "nightshroom_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "viridshroom_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(true), "dewshroom_pressure_plate")
                    .setHardness(1.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(false), "trenchstone_pressure_plate")
                    .setHardness(4.0F),
                RegUtil.withName(new BlockMidnightPressurePlate(false), "nightstone_pressure_plate")
                    .setHardness(0.5F)
        );

        blocks.forEach(event.getRegistry()::register);

        registerTile(TileEntityShadowrootChest.class, "tile_shadowroot_chest");
        registerTile(TileEntityMidnightFurnace.class, "tile_midnight_furnace");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(itemBlocks(
                SHADOWROOT_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS,
                DEAD_WOOD_LOG, DEAD_WOOD_PLANKS,
                DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, DARK_WILLOW_PLANKS,
                NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS,
                TRENCHSTONE,
                DARK_PEARL_ORE, DARK_PEARL_BLOCK,
                SHADOWROOT_CRAFTING_TABLE,
                SHADOWROOT_CHEST,
                MIDNIGHT_FURNACE,
                MIDNIGHT_DIRT, MIDNIGHT_GRASS,
                TALL_MIDNIGHT_GRASS, DOUBLE_MIDNIGHT_GRASS,
                NIGHTSHROOM, DOUBLE_NIGHTSHROOM, NIGHTSHROOM_SHELF,
                DEWSHROOM, DOUBLE_DEWSHROOM, DEWSHROOM_SHELF,
                VIRIDSHROOM, DOUBLE_VIRIDSHROOM, VIRIDSHROOM_SHELF,
                NIGHTSHROOM_STEM, NIGHTSHROOM_HAT,
                DEWSHROOM_STEM, DEWSHROOM_HAT,
                VIRIDSHROOM_STEM, VIRIDSHROOM_HAT,
                NIGHTSHROOM_PLANKS, DEWSHROOM_PLANKS, VIRIDSHROOM_PLANKS,
                LUMEN_BUD, DOUBLE_LUMEN_BUD,
                CRYSTAL_FLOWER,
                SHADOWROOT_SAPLING, DARK_WILLOW_SAPLING,
                SHADOWROOT_TRAPDOOR, DARK_WILLOW_TRAPDOOR, DEAD_WOOD_TRAPDOOR,
                NIGHTSHROOM_TRAPDOOR, DEWSHROOM_TRAPDOOR, VIRIDSHROOM_TRAPDOOR,
                BLOOMCRYSTAL, BLOOMCRYSTAL_ROCK,
                ROUXE, ROUXE_ROCK,
                MIASMA_SURFACE, MIASMA,
                SHADOWROOT_SLAB, DARK_WILLOW_SLAB, DEAD_WOOD_SLAB, NIGHTSHROOM_SLAB, VIRIDSHROOM_SLAB, DEWSHROOM_SLAB, TRENCHSTONE_SLAB, NIGHTSTONE_SLAB,
                SHADOWROOT_STAIRS, DARK_WILLOW_STAIRS, DEAD_WOOD_STAIRS, NIGHTSHROOM_STAIRS, VIRIDSHROOM_STAIRS, DEWSHROOM_STAIRS, TRENCHSTONE_STAIRS, NIGHTSTONE_STAIRS,
                TRENCHSTONE_WALL, NIGHTSTONE_WALL,
                SHADOWROOT_FENCE, DARK_WILLOW_FENCE, DEAD_WOOD_FENCE, NIGHTSHROOM_FENCE, VIRIDSHROOM_FENCE, DEWSHROOM_FENCE,
                SHADOWROOT_FENCE_GATE, DARK_WILLOW_FENCE_GATE, DEAD_WOOD_FENCE_GATE, NIGHTSHROOM_FENCE_GATE, VIRIDSHROOM_FENCE_GATE, DEWSHROOM_FENCE_GATE,
                SHADOWROOT_BUTTON, DARK_WILLOW_BUTTON, DEAD_WOOD_BUTTON, NIGHTSHROOM_BUTTON, VIRIDSHROOM_BUTTON, DEWSHROOM_BUTTON, TRENCHSTONE_BUTTON, NIGHTSTONE_BUTTON,
                SHADOWROOT_PRESSURE_PLATE, DARK_WILLOW_PRESSURE_PLATE, DEAD_WOOD_PRESSURE_PLATE, NIGHTSHROOM_PRESSURE_PLATE, VIRIDSHROOM_PRESSURE_PLATE, DEWSHROOM_PRESSURE_PLATE, TRENCHSTONE_PRESSURE_PLATE, NIGHTSTONE_PRESSURE_PLATE
        ));
    }

    public static void onInit() {
        OreDictionary.registerOre("logWood", SHADOWROOT_LOG);
        OreDictionary.registerOre("logWood", DARK_WILLOW_LOG);
        OreDictionary.registerOre("logWood", DEAD_WOOD_LOG);

        // TODO: Temporary solution. Trapdoor recipes get overridden by vanilla recipes if these are registered
        /*OreDictionary.registerOre("plankWood", SHADOWROOT_PLANKS);
        OreDictionary.registerOre("plankWood", DARK_WILLOW_PLANKS);
        OreDictionary.registerOre("plankWood", DEAD_WOOD_PLANKS);*/

        OreDictionary.registerOre("treeLeaves", SHADOWROOT_LEAVES);
        OreDictionary.registerOre("treeLeaves", DARK_WILLOW_LEAVES);

        /*OreDictionary.registerOre("oreEbonys", EBONYS_ORE);
        OreDictionary.registerOre("blockEbonys", EBONYS_BLOCK);

        OreDictionary.registerOre("oreNagrilite", NAGRILITE_ORE);
        OreDictionary.registerOre("blockNagrilite", NAGRILITE_BLOCK);

        OreDictionary.registerOre("oreTenebrum", TENEBRUM_ORE);
        OreDictionary.registerOre("blockTenebrum", TENEBRUM_BLOCK);*/

        OreDictionary.registerOre("chest", SHADOWROOT_CHEST);
        OreDictionary.registerOre("chestWood", SHADOWROOT_CHEST);
    }

    private static Item[] itemBlocks(Block... blocks) {
        Item[] items = new Item[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            items[i] = itemBlock(blocks[i]);
        }
        return items;
    }

    private static Item itemBlock(Block block) {
        ItemBlock item = new ItemBlock(block);
        if (block.getRegistryName() == null) {
            throw new IllegalArgumentException("Cannot create ItemBlock for Block without registry name");
        }
        item.setRegistryName(block.getRegistryName());
        return item;
    }

    private static void registerTile(Class<? extends TileEntity> entityClass, String registryName) {
        GameRegistry.registerTileEntity(entityClass, new ResourceLocation(Midnight.MODID, registryName));
    }

    public static Collection<Block> getBlocks() {
        return Collections.unmodifiableCollection(blocks);
    }
}
