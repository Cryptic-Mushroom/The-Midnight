package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.client.render.item.MidnightChestItemRenderer;
import com.mushroom.midnight.common.block.BasicBlock;
import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.block.BloomCrystalBlock;
import com.mushroom.midnight.common.block.BogweedBlock;
import com.mushroom.midnight.common.block.CacheBlock;
import com.mushroom.midnight.common.block.CrystalBlock;
import com.mushroom.midnight.common.block.CrystalotusBlock;
import com.mushroom.midnight.common.block.DeceitfulAlgaeBlock;
import com.mushroom.midnight.common.block.DeceitfulMossBlock;
import com.mushroom.midnight.common.block.DeceitfulMudBlock;
import com.mushroom.midnight.common.block.DoubleFungiBlock;
import com.mushroom.midnight.common.block.DragonNestBlock;
import com.mushroom.midnight.common.block.FingeredGrassBlock;
import com.mushroom.midnight.common.block.FungiBlock;
import com.mushroom.midnight.common.block.FungiInsideBlock;
import com.mushroom.midnight.common.block.GlobFungusBlock;
import com.mushroom.midnight.common.block.GlobFungusHatBlock;
import com.mushroom.midnight.common.block.HangablePlantBlock;
import com.mushroom.midnight.common.block.MiasmaSurfaceBlock;
import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.block.MidnightDoublePlantBlock;
import com.mushroom.midnight.common.block.MidnightFluidBlock;
import com.mushroom.midnight.common.block.MidnightFungiHatBlock;
import com.mushroom.midnight.common.block.MidnightFungiShelfBlock;
import com.mushroom.midnight.common.block.MidnightFurnaceBlock;
import com.mushroom.midnight.common.block.MidnightGemBlock;
import com.mushroom.midnight.common.block.MidnightGlassBlock;
import com.mushroom.midnight.common.block.MidnightGlassPaneBlock;
import com.mushroom.midnight.common.block.MidnightMyceliumBlock;
import com.mushroom.midnight.common.block.MidnightOreBlock;
import com.mushroom.midnight.common.block.MidnightPlantBlock;
import com.mushroom.midnight.common.block.MidnightSaplingBlock;
import com.mushroom.midnight.common.block.MidnightStairsBlock;
import com.mushroom.midnight.common.block.MidnightWoodPlankBlock;
import com.mushroom.midnight.common.block.RockshroomBlock;
import com.mushroom.midnight.common.block.SoilBlock;
import com.mushroom.midnight.common.block.SporchBlock;
import com.mushroom.midnight.common.block.SpreadableSoilBlock;
import com.mushroom.midnight.common.block.StingerEggBlock;
import com.mushroom.midnight.common.block.SuavisBlock;
import com.mushroom.midnight.common.block.TendrilweedBlock;
import com.mushroom.midnight.common.block.UnstableBushBlock;
import com.mushroom.midnight.common.block.UnstableBushBloomedBlock;
import com.mushroom.midnight.common.block.VioleafBlock;
import com.mushroom.midnight.common.block.WallSporchBlock;
import com.mushroom.midnight.common.item.DeceitfulAlgaeItem;
import com.mushroom.midnight.common.world.tree.BogshroomTree;
import com.mushroom.midnight.common.world.tree.DarkWillowTree;
import com.mushroom.midnight.common.world.tree.DewshroomTree;
import com.mushroom.midnight.common.world.tree.GlobFungusTree;
import com.mushroom.midnight.common.world.tree.NightshroomTree;
import com.mushroom.midnight.common.world.tree.ShadowrootTree;
import com.mushroom.midnight.common.world.tree.ViridshroomTree;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.concurrent.Callable;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("WeakerAccess")
public class MidnightBlocks {

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_STRIPPED_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;
    public static final Block SHADOWROOT_PLANKS = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_STRIPPED_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANKS = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_STRIPPED_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_LEAVES = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block CHISELED_NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block TRENCHSTONE = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICKS = Blocks.AIR;

    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;
    public static final Block TENEBRUM_ORE = Blocks.AIR;
    public static final Block TENEBRUM_BLOCK = Blocks.AIR;
    public static final Block NAGRILITE_ORE = Blocks.AIR;
    public static final Block NAGRILITE_BLOCK = Blocks.AIR;
    public static final Block EBONITE_ORE = Blocks.AIR;
    public static final Block EBONITE_BLOCK = Blocks.AIR;
    public static final Block ARCHAIC_ORE = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block DARK_WILLOW_CRAFTING_TABLE = Blocks.AIR;
    public static final Block DEAD_WOOD_CRAFTING_TABLE = Blocks.AIR;
    public static final Block NIGHTSHROOM_CRAFTING_TABLE = Blocks.AIR;
    public static final Block DEWSHROOM_CRAFTING_TABLE = Blocks.AIR;
    public static final Block VIRIDSHROOM_CRAFTING_TABLE = Blocks.AIR;

    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block DARK_WILLOW_CHEST = Blocks.AIR;
    public static final Block DEAD_WOOD_CHEST = Blocks.AIR;
    public static final Block NIGHTSHROOM_CHEST = Blocks.AIR;
    public static final Block DEWSHROOM_CHEST = Blocks.AIR;
    public static final Block VIRIDSHROOM_CHEST = Blocks.AIR;

    public static final Block NIGHTSTONE_FURNACE = Blocks.AIR;
    public static final Block VIRIDSHROOM_STEM_CACHE = Blocks.AIR;

    public static final Block COARSE_DIRT = Blocks.AIR;
    public static final Block DIRT = Blocks.AIR;
    public static final Block GRASS_BLOCK = Blocks.AIR;
    public static final Block MYCELIUM = Blocks.AIR;

    public static final Block GRASS = Blocks.AIR;
    public static final Block TALL_GRASS = Blocks.AIR;

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
    public static final Block VIRIDSHROOM_ROOTS = Blocks.AIR;
    public static final Block VIRIDSHROOM_FLOWERING_ROOTS = Blocks.AIR;

    public static final Block NIGHTSHROOM_STEM = Blocks.AIR;
    public static final Block NIGHTSHROOM_HAT = Blocks.AIR;
    public static final Block NIGHTSHROOM_ROOTS = Blocks.AIR;
    public static final Block NIGHTSHROOM_FLOWERING_ROOTS = Blocks.AIR;

    public static final Block NIGHTSHROOM_PLANKS = Blocks.AIR;

    public static final Block DEWSHROOM_STEM = Blocks.AIR;
    public static final Block DEWSHROOM_HAT = Blocks.AIR;
    public static final Block DEWSHROOM_ROOTS = Blocks.AIR;
    public static final Block DEWSHROOM_FLOWERING_ROOTS = Blocks.AIR;

    public static final Block BOGSHROOM = Blocks.AIR;
    public static final Block DOUBLE_BOGSHROOM = Blocks.AIR;
    public static final Block BOGSHROOM_SHELF = Blocks.AIR;
    public static final Block BOGSHROOM_STEM = Blocks.AIR;
    public static final Block BOGSHROOM_HAT = Blocks.AIR;

    public static final Block GLOB_FUNGUS = Blocks.AIR;
    public static final Block GLOB_FUNGUS_STEM = Blocks.AIR;
    public static final Block GLOB_FUNGUS_HAT = Blocks.AIR;

    public static final Block ROCKSHROOM = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS = Blocks.AIR;

    public static final Block LUMEN_BUD = Blocks.AIR;
    public static final Block DOUBLE_LUMEN_BUD = Blocks.AIR;

    public static final Block BLADESHROOM = Blocks.AIR;
    public static final Block BOGWEED = Blocks.AIR;
    public static final Block GHOST_PLANT = Blocks.AIR;
    public static final Block FINGERED_GRASS = Blocks.AIR;
    public static final Block TENDRILWEED = Blocks.AIR;
    public static final Block RUNEBUSH = Blocks.AIR;
    public static final Block DRAGON_NEST = Blocks.AIR;
    public static final Block VIOLEAF = Blocks.AIR;

    public static final Block CRYSTAL_FLOWER = Blocks.AIR;

    public static final Block SHADOWROOT_SAPLING = Blocks.AIR;
    public static final Block DARK_WILLOW_SAPLING = Blocks.AIR;

    public static final Block SHADOWROOT_DOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_DOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_DOOR = Blocks.AIR;

    public static final Block TENEBRUM_DOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_DOOR = Blocks.AIR;
    public static final Block DEWSHROOM_DOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOOR = Blocks.AIR;

    public static final Block SHADOWROOT_TRAPDOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_TRAPDOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_TRAPDOOR = Blocks.AIR;

    public static final Block TENEBRUM_TRAPDOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block DEWSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_TRAPDOOR = Blocks.AIR;

    public static final Block BLOOMCRYSTAL = Blocks.AIR;
    public static final Block BLOOMCRYSTAL_ROCK = Blocks.AIR;

    public static final Block ROUXE = Blocks.AIR;
    public static final Block ROUXE_ROCK = Blocks.AIR;

    public static final Block ARCHAIC_GLASS = Blocks.AIR;
    public static final Block ARCHAIC_GLASS_PANE = Blocks.AIR;

    public static final Block MIASMA_SURFACE = Blocks.AIR;
    public static final Block MIASMA = Blocks.AIR;
    public static final Block DARK_WATER = Blocks.AIR;

    public static final Block FUNGI_INSIDE = Blocks.AIR;

    public static final Block DECEITFUL_PEAT = Blocks.AIR;
    public static final Block DECEITFUL_MUD = Blocks.AIR;
    public static final Block DECEITFUL_ALGAE = Blocks.AIR;
    public static final Block DECEITFUL_MOSS = Blocks.AIR;

    public static final Block SHADOWROOT_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_SLAB = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_SLAB = Blocks.AIR;

    public static final Block SHADOWROOT_STAIRS = Blocks.AIR;
    public static final Block DEAD_WOOD_STAIRS = Blocks.AIR;
    public static final Block DARK_WILLOW_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block DEWSHROOM_STAIRS = Blocks.AIR;
    public static final Block VIRIDSHROOM_STAIRS = Blocks.AIR;
    public static final Block NIGHTSHROOM_STAIRS = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_STAIRS = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE = Blocks.AIR;
    public static final Block NIGHTSTONE_WALL = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_WALL = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE_GATE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE_GATE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE_GATE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE_GATE = Blocks.AIR;

    public static final Block SUAVIS = Blocks.AIR;

    public static final Block SHADOWROOT_LADDER = Blocks.AIR;
    public static final Block DEAD_WOOD_LADDER = Blocks.AIR;
    public static final Block DARK_WILLOW_LADDER = Blocks.AIR;
    public static final Block DEWSHROOM_LADDER = Blocks.AIR;
    public static final Block VIRIDSHROOM_LADDER = Blocks.AIR;
    public static final Block NIGHTSHROOM_LADDER = Blocks.AIR;

    public static final Block STINGER_EGG = Blocks.AIR;
    public static final Block UNSTABLE_BUSH = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_BLUE_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_GREEN_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_LIME_BLOOMED = Blocks.AIR;

    public static final Block BOGSHROOM_SPORCH = Blocks.AIR;
    public static final Block NIGHTSHROOM_SPORCH = Blocks.AIR;
    public static final Block DEWSHROOM_SPORCH = Blocks.AIR;
    public static final Block VIRIDSHROOM_SPORCH = Blocks.AIR;

    public static final Block BOGSHROOM_WALL_SPORCH = Blocks.AIR;
    public static final Block NIGHTSHROOM_WALL_SPORCH = Blocks.AIR;
    public static final Block DEWSHROOM_WALL_SPORCH = Blocks.AIR;
    public static final Block VIRIDSHROOM_WALL_SPORCH = Blocks.AIR;

    public static final Block CRYSTALOTUS = Blocks.AIR;

    public static final Block SHADOWROOT_BUTTON = Blocks.AIR;
    public static final Block DEAD_WOOD_BUTTON = Blocks.AIR;
    public static final Block DARK_WILLOW_BUTTON = Blocks.AIR;
    public static final Block DEWSHROOM_BUTTON = Blocks.AIR;
    public static final Block VIRIDSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSTONE_BUTTON = Blocks.AIR;
    public static final Block TRENCHSTONE_BUTTON = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_BUTTON = Blocks.AIR;

    public static final Block SHADOWROOT_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEAD_WOOD_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DARK_WILLOW_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEWSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TRENCHSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NAGRILITE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TENEBRUM_PRESSURE_PLATE = Blocks.AIR;

    public static final Block MIDNIGHT_LEVER = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        RegUtil.blocks(event.getRegistry())
                .add("grass_block", new SpreadableSoilBlock(
                        Block.Properties.create(Material.ORGANIC, MaterialColor.MAGENTA_TERRACOTTA)
                                .hardnessAndResistance(0.6f)
                                .sound(SoundType.PLANT)
                , () -> DIRT))
                .add("dirt", new SoilBlock(
                        Block.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(0.5f)
                                .sound(SoundType.GROUND)
                , true))
                .add("coarse_dirt", new SoilBlock(
                        Block.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(0.5f)
                                .sound(SoundType.GROUND)
                , false))
                .add("mycelium", new MidnightMyceliumBlock(
                        Block.Properties.create(Material.ROCK, MaterialColor.PINK)
                                .hardnessAndResistance(1.5f, 10f)
                                .sound(SoundType.STONE))
                )
                .add("deceitful_mud", new DeceitfulMudBlock())
                .add("deceitful_peat", new SoilBlock(
                        Block.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(0.5F)
                                .sound(SoundType.GROUND)
                        , true));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.ROCK, MaterialColor.BLUE_TERRACOTTA)
                        .hardnessAndResistance(1.5f, 10f)
                        .sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                )
                .add("nightstone", props -> new SoilBlock(props, true))
                .add("nightstone_bricks", BasicBlock::new)
                .add("chiseled_nightstone_bricks", BasicBlock::new)
                .add("rockshroom_bricks", BasicBlock::new)

                .add("nightstone_slab", SlabBlock::new)
                .add("nightstone_brick_slab", SlabBlock::new)
                .add("rockshroom_brick_slab", SlabBlock::new)

                .add("nightstone_stairs", props -> new MidnightStairsBlock(() -> NIGHTSTONE.getDefaultState(), props) {})
                .add("nightstone_brick_stairs", props -> new MidnightStairsBlock(() -> NIGHTSTONE_BRICKS.getDefaultState(), props) {})
                .add("rockshroom_brick_stairs", props -> new MidnightStairsBlock(() -> ROCKSHROOM_BRICKS.getDefaultState(), props) {})

                .add("nightstone_wall", WallBlock::new)
                .add("nightstone_brick_wall", WallBlock::new)
                .add("rockshroom_brick_wall", WallBlock::new);

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.ROCK).hardnessAndResistance(5f, 200f))
                .add("trenchstone", props -> new BasicBlock(props.harvestTool(ToolType.PICKAXE).harvestLevel(2)))
                .add("trenchstone_slab", SlabBlock::new)
                .add("trenchstone_stairs", props -> new MidnightStairsBlock(() -> TRENCHSTONE.getDefaultState(), props) {})
                .add("trenchstone_wall", WallBlock::new);

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 200f))
                .add("trenchstone_bricks", props -> new BasicBlock(props.harvestTool(ToolType.PICKAXE).harvestLevel(2)))
                .add("trenchstone_brick_slab", SlabBlock::new)
                .add("trenchstone_brick_stairs", props -> new MidnightStairsBlock(() -> TRENCHSTONE_BRICKS.getDefaultState(), props) {})
                .add("trenchstone_brick_wall", WallBlock::new);

        RegUtil.blocks(event.getRegistry())
                .withProperties(() ->
                        Block.Properties.create(Material.WOOD, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(2f)
                                .sound(SoundType.WOOD)
                )
                .add("shadowroot_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dead_wood_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dark_willow_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("shadowroot_stripped_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dead_wood_stripped_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dark_willow_stripped_log", props -> new LogBlock(MaterialColor.BROWN, props));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() ->
                        Block.Properties.create(Material.ROCK)
                                .hardnessAndResistance(3.5f)
                                .lightValue(13)
                                .tickRandomly()
                )
                .add("nightstone_furnace", props -> new MidnightFurnaceBlock(props){});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.TALL_PLANTS)
                        .sound(SoundType.PLANT)
                        .hardnessAndResistance(0f)
                        .doesNotBlockMovement()
                )
                .add("grass", props -> new MidnightPlantBlock(props, false, () -> TALL_GRASS).setReplacable())
                .add("tall_grass", props -> new MidnightDoublePlantBlock(props, false))
                .add("nightshroom", props -> new FungiBlock(props, true, () -> DOUBLE_NIGHTSHROOM))
                .add("dewshroom", props -> new FungiBlock(props, true, () -> DOUBLE_DEWSHROOM))
                .add("viridshroom", props -> new FungiBlock(props, true, () -> DOUBLE_VIRIDSHROOM))
                .add("bogshroom", props -> new FungiBlock(props, true, () -> DOUBLE_BOGSHROOM))

                .add("double_nightshroom", props -> new DoubleFungiBlock(props, new NightshroomTree()))
                .add("double_dewshroom", props -> new DoubleFungiBlock(props, new DewshroomTree()))
                .add("double_viridshroom", props -> new DoubleFungiBlock(props, new ViridshroomTree()))
                .add("double_bogshroom", props -> new DoubleFungiBlock(props, new BogshroomTree()))
                .add("lumen_bud", props -> new MidnightPlantBlock(props, true, () -> DOUBLE_LUMEN_BUD))
                .add("double_lumen_bud", props -> new MidnightDoublePlantBlock(props, true))
                .add("glob_fungus", props -> new GlobFungusBlock(props, new GlobFungusTree()))
                .add("bogweed", BogweedBlock::new)
                .add("ghost_plant", props -> new MidnightPlantBlock(props, true))
                .add("fingered_grass", FingeredGrassBlock::new)
                .add("tendrilweed", TendrilweedBlock::new)
                .add("violeaf", VioleafBlock::new)
                .add("dragon_nest", DragonNestBlock::new)
                .add("bladeshroom", BladeshroomBlock::new)
                .add("crystal_flower", props -> new MidnightPlantBlock(props, true))
                .add("runebush", props -> new MidnightPlantBlock(props, false))
                .add("deceitful_algae", new DeceitfulAlgaeBlock())
                .add("deceitful_moss", new DeceitfulMossBlock())
                .add("crystalotus", new CrystalotusBlock())
                .add("unstable_bush", UnstableBushBlock::new)
                .add("unstable_bush_blue_bloomed", props -> new UnstableBushBloomedBlock(props, () -> MidnightItems.BLUE_UNSTABLE_FRUIT))
                .add("unstable_bush_green_bloomed", props -> new UnstableBushBloomedBlock(props, () -> MidnightItems.GREEN_UNSTABLE_FRUIT))
                .add("unstable_bush_lime_bloomed", props -> new UnstableBushBloomedBlock(props, () -> MidnightItems.LIME_UNSTABLE_FRUIT))

                .add("nightshroom_roots", HangablePlantBlock::new)
                .add("dewshroom_roots", HangablePlantBlock::new)
                .add("viridshroom_roots", HangablePlantBlock::new)

                .add("nightshroom_flowering_roots", props -> new HangablePlantBlock(props, true))
                .add("dewshroom_flowering_roots", props -> new HangablePlantBlock(props, true))
                .add("viridshroom_flowering_roots", props -> new HangablePlantBlock(props, true));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.WOOD)
                        .sound(SoundType.WOOD)
                        .hardnessAndResistance(2f)
                )
                .add("nightshroom_stem", Block::new)
                .add("dewshroom_stem", Block::new)
                .add("viridshroom_stem", Block::new)
                .add("bogshroom_stem", Block::new)
                // TODO glob fungus stem drops 4 GLOB_FUNGUS_HAND, can silk touch & sheared
                .add("glob_fungus_stem", props -> new LogBlock(MaterialColor.BROWN, props.hardnessAndResistance(0.5f)))
                .add("fungi_inside", new FungiInsideBlock(Block.Properties.create(Material.AIR).doesNotBlockMovement()));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.LEAVES)
                        .hardnessAndResistance(0.2F)
                        .tickRandomly()
                        .sound(SoundType.PLANT)
                )
                .add("shadowroot_leaves", LeavesBlock::new) // SHADOWROOT_SAPLING TODO checks if saplings are in json loottables
                .add("dark_willow_leaves", LeavesBlock::new); // DARK_WILLOW_SAPLING

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.PLANTS)
                        .hardnessAndResistance(0f)
                        .doesNotBlockMovement()
                        .tickRandomly()
                        .sound(SoundType.PLANT)
                )
                .add("shadowroot_sapling", props -> new MidnightSaplingBlock(new ShadowrootTree(), props))
                .add("dark_willow_sapling", props -> new MidnightSaplingBlock(new DarkWillowTree(), props));

        RegUtil.blocks(event.getRegistry())
                .add("nightshroom_hat", new MidnightFungiHatBlock(() -> NIGHTSHROOM, () -> MidnightItems.NIGHTSHROOM_POWDER, MaterialColor.CYAN))
                .add("dewshroom_hat", new MidnightFungiHatBlock(() -> DEWSHROOM, () -> MidnightItems.DEWSHROOM_POWDER, MaterialColor.PURPLE))
                .add("viridshroom_hat", new MidnightFungiHatBlock(() -> VIRIDSHROOM, () -> MidnightItems.VIRIDSHROOM_POWDER, MaterialColor.EMERALD))
                .add("bogshroom_hat", new MidnightFungiHatBlock(() -> BOGSHROOM, () -> MidnightItems.BOGSHROOM_POWDER, MaterialColor.ADOBE))
                .add("glob_fungus_hat", new GlobFungusHatBlock());

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.PLANTS, MaterialColor.PURPLE_TERRACOTTA).hardnessAndResistance(0f).doesNotBlockMovement().sound(SoundType.PLANT))
                .add("bogshroom_shelf", MidnightFungiShelfBlock::new)
                .add("nightshroom_shelf", MidnightFungiShelfBlock::new)
                .add("dewshroom_shelf", MidnightFungiShelfBlock::new)
                .add("viridshroom_shelf", MidnightFungiShelfBlock::new);

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.IRON).hardnessAndResistance(3f, 0f).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0))
                .add("dark_pearl_block", BasicBlock::new)
                .add("tenebrum_block", BasicBlock::new)
                .add("nagrilite_block", BasicBlock::new)
                .add("ebonite_block", BasicBlock::new);

        RegUtil.blocks(event.getRegistry())
                .add("rockshroom", new RockshroomBlock())
                .add("stinger_egg", new StingerEggBlock())
                .add("bloomcrystal", new BloomCrystalBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).hardnessAndResistance(2.0F).sound(SoundType.GLASS).lightValue(15).tickRandomly()))
                .add("bloomcrystal_rock", new BasicBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).sound(SoundType.GLASS).lightValue(14).hardnessAndResistance(4f).harvestTool(ToolType.PICKAXE).harvestLevel(1)))
                .add("rouxe", new CrystalBlock(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2.0F).sound(SoundType.GLASS).lightValue(3)))
                .add("rouxe_rock", new BasicBlock(Block.Properties.create(Material.ROCK, MaterialColor.RED).sound(SoundType.GLASS).lightValue(2).hardnessAndResistance(4f).harvestTool(ToolType.PICKAXE).harvestLevel(1)).withGlow())
                .add("miasma_surface", new MiasmaSurfaceBlock())
                .add("dark_pearl_ore", new MidnightGemBlock(0))
                .add("tenebrum_ore", new MidnightOreBlock(2))
                .add("nagrilite_ore", new MidnightOreBlock(2))
                .add("ebonite_ore", new MidnightGemBlock(1))
                .add("archaic_ore", new MidnightGemBlock(0))
                .add("archaic_glass", new MidnightGlassBlock())
                .add("archaic_glass_pane", new MidnightGlassPaneBlock())
                .add("shadowroot_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("dark_willow_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("dead_wood_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("nightshroom_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("dewshroom_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("viridshroom_chest", new MidnightChestBlock(Block.Properties.from(Blocks.CHEST)){})
                .add("suavis", new SuavisBlock());

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.from(Blocks.TORCH))
                .add("bogshroom_sporch", props -> new SporchBlock(SporchBlock.SporchType.BOGSHROOM, props))
                .add("nightshroom_sporch", props -> new SporchBlock(SporchBlock.SporchType.NIGHTSHROOM, props))
                .add("dewshroom_sporch", props -> new SporchBlock(SporchBlock.SporchType.DEWSHROOM, props))
                .add("viridshroom_sporch", props -> new SporchBlock(SporchBlock.SporchType.VIRIDSHROOM, props))
                .add("bogshroom_wall_sporch", props -> new WallSporchBlock(SporchBlock.SporchType.BOGSHROOM, props.lootFrom(BOGSHROOM_SPORCH)))
                .add("nightshroom_wall_sporch", props -> new WallSporchBlock(SporchBlock.SporchType.NIGHTSHROOM, props.lootFrom(NIGHTSHROOM_SPORCH)))
                .add("dewshroom_wall_sporch", props -> new WallSporchBlock(SporchBlock.SporchType.DEWSHROOM, props.lootFrom(DEWSHROOM_SPORCH)))
                .add("viridshroom_wall_sporch", props -> new WallSporchBlock(SporchBlock.SporchType.VIRIDSHROOM, props.lootFrom(VIRIDSHROOM_SPORCH)));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2f, 5f).sound(SoundType.WOOD))
                .add("shadowroot_planks", MidnightWoodPlankBlock::new)
                .add("dead_wood_planks", MidnightWoodPlankBlock::new)
                .add("dark_willow_planks", MidnightWoodPlankBlock::new)
                .add("nightshroom_planks", MidnightWoodPlankBlock::new)
                .add("dewshroom_planks", MidnightWoodPlankBlock::new)
                .add("viridshroom_planks", MidnightWoodPlankBlock::new)

                .add("shadowroot_slab", SlabBlock::new)
                .add("dead_wood_slab", SlabBlock::new)
                .add("dark_willow_slab", SlabBlock::new)
                .add("dewshroom_slab", SlabBlock::new)
                .add("viridshroom_slab", SlabBlock::new)
                .add("nightshroom_slab", SlabBlock::new)

                .add("shadowroot_stairs", props -> new MidnightStairsBlock(() -> SHADOWROOT_PLANKS.getDefaultState(), props) {})
                .add("dead_wood_stairs", props -> new MidnightStairsBlock(() -> DEAD_WOOD_PLANKS.getDefaultState(), props) {})
                .add("dark_willow_stairs", props -> new MidnightStairsBlock(() -> DARK_WILLOW_PLANKS.getDefaultState(), props) {})
                .add("dewshroom_stairs", props -> new MidnightStairsBlock(() -> DEWSHROOM_PLANKS.getDefaultState(), props) {})
                .add("viridshroom_stairs", props -> new MidnightStairsBlock(() -> VIRIDSHROOM_PLANKS.getDefaultState(), props) {})
                .add("nightshroom_stairs", props -> new MidnightStairsBlock(() -> NIGHTSHROOM_PLANKS.getDefaultState(), props) {})

                .add("viridshroom_stem_cache", props -> new CacheBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2f)){});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.from(Blocks.CRAFTING_TABLE))
                .add("shadowroot_crafting_table", props -> new CraftingTableBlock(props){})
                .add("dark_willow_crafting_table", props -> new CraftingTableBlock(props){})
                .add("dead_wood_crafting_table", props -> new CraftingTableBlock(props){})
                .add("nightshroom_crafting_table", props -> new CraftingTableBlock(props){})
                .add("dewshroom_crafting_table", props -> new CraftingTableBlock(props){})
                .add("viridshroom_crafting_table", props -> new CraftingTableBlock(props){});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.IRON, MaterialColor.ADOBE).hardnessAndResistance(3f).sound(SoundType.METAL))
                .add("tenebrum_door", props -> new DoorBlock(props) {});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(3f).sound(SoundType.WOOD))
                .add("shadowroot_door", props -> new DoorBlock(props) {})
                .add("dark_willow_door", props -> new DoorBlock(props) {})
                .add("dead_wood_door", props -> new DoorBlock(props) {})
                .add("nightshroom_door", props -> new DoorBlock(props) {})
                .add("dewshroom_door", props -> new DoorBlock(props) {})
                .add("viridshroom_door", props -> new DoorBlock(props) {})

                .add("shadowroot_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("dark_willow_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("dead_wood_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("tenebrum_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("nightshroom_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("dewshroom_trapdoor", props -> new TrapDoorBlock(props) {})
                .add("viridshroom_trapdoor", props -> new TrapDoorBlock(props) {});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))
                .add("shadowroot_fence", FenceBlock::new)
                .add("dead_wood_fence", FenceBlock::new)
                .add("dark_willow_fence", FenceBlock::new)
                .add("dewshroom_fence", FenceBlock::new)
                .add("viridshroom_fence", FenceBlock::new)
                .add("nightshroom_fence", FenceBlock::new)

                .add("shadowroot_fence_gate", FenceGateBlock::new)
                .add("dead_wood_fence_gate", FenceGateBlock::new)
                .add("dark_willow_fence_gate", FenceGateBlock::new)
                .add("dewshroom_fence_gate", FenceGateBlock::new)
                .add("viridshroom_fence_gate", FenceGateBlock::new)
                .add("nightshroom_fence_gate", FenceGateBlock::new);

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.4F).sound(SoundType.LADDER))
                .add("shadowroot_ladder", props -> new LadderBlock(props) {})
                .add("dead_wood_ladder", props -> new LadderBlock(props) {})
                .add("dark_willow_ladder", props -> new LadderBlock(props) {})
                .add("dewshroom_ladder", props -> new LadderBlock(props) {})
                .add("viridshroom_ladder", props -> new LadderBlock(props) {})
                .add("nightshroom_ladder", props -> new LadderBlock(props) {});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD))
                .add("shadowroot_button", props -> new WoodButtonBlock(props) {})
                .add("dead_wood_button", props -> new WoodButtonBlock(props) {})
                .add("dark_willow_button", props -> new WoodButtonBlock(props) {})
                .add("dewshroom_button", props -> new WoodButtonBlock(props) {})
                .add("viridshroom_button", props -> new WoodButtonBlock(props) {})
                .add("nightshroom_button", props -> new WoodButtonBlock(props) {})

                .add("midnight_lever", props -> new LeverBlock(props){});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F))
                .add("nightstone_button", props -> new StoneButtonBlock(props) {}) //1.0F
                .add("rockshroom_brick_button", props -> new StoneButtonBlock(props) {}) //1.0F
                .add("trenchstone_button", props -> new StoneButtonBlock(props) {}); // TODO 3.75F button

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.IRON, MaterialColor.GOLD).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD))
                .add("shadowroot_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){})
                .add("dead_wood_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){})
                .add("dark_willow_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){})
                .add("dewshroom_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){})
                .add("viridshroom_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){})
                .add("nightshroom_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props){});

        RegUtil.blocks(event.getRegistry())
                .withProperties(() -> Block.Properties.create(Material.IRON).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD))
                .add("nightstone_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, props){})
                .add("rockshroom_brick_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, props){})
                .add("trenchstone_pressure_plate", props -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, props){}) // TODO 3.75F pressure_plate
                .add("nagrilite_pressure_plate", props -> new WeightedPressurePlateBlock(150, props){})
                .add("tenebrum_pressure_plate", props -> new WeightedPressurePlateBlock(150, props){});

        RegUtil.blocks(event.getRegistry())
                .add("dark_water", new MidnightFluidBlock(MidnightFluids.DARK_WATER, false, Block.Properties.create(Material.WATER)
                        .doesNotBlockMovement()
                        .hardnessAndResistance(100.0F)
                        .noDrops()
                ))
                .add("miasma", new MidnightFluidBlock(MidnightFluids.MIASMA, true, Block.Properties.create(Material.LAVA)
                        .doesNotBlockMovement()
                        .hardnessAndResistance(100.0F)
                        .lightValue(15)
                        .noDrops()
                ));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.BUILDING))
                .addAll(BlockItem::new,
                        SHADOWROOT_LOG, SHADOWROOT_STRIPPED_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS, DARK_WILLOW_LOG, DARK_WILLOW_STRIPPED_LOG, DARK_WILLOW_LEAVES, DARK_WILLOW_PLANKS, DEAD_WOOD_LOG, DEAD_WOOD_STRIPPED_LOG,
                        DEAD_WOOD_PLANKS, DEWSHROOM_PLANKS, VIRIDSHROOM_PLANKS, NIGHTSHROOM_PLANKS,
                        NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS, TRENCHSTONE, TRENCHSTONE_BRICKS,
                        DARK_PEARL_ORE, DARK_PEARL_BLOCK, TENEBRUM_ORE, TENEBRUM_BLOCK, NAGRILITE_ORE, NAGRILITE_BLOCK, EBONITE_ORE, EBONITE_BLOCK, ARCHAIC_ORE,
                        COARSE_DIRT, DIRT, GRASS_BLOCK, MYCELIUM,
                        BOGSHROOM_HAT, BOGSHROOM_STEM, NIGHTSHROOM_STEM, NIGHTSHROOM_HAT, DEWSHROOM_STEM, DEWSHROOM_HAT, VIRIDSHROOM_STEM, VIRIDSHROOM_HAT, GLOB_FUNGUS_HAT, GLOB_FUNGUS_STEM,
                        ROCKSHROOM, ROCKSHROOM_BRICKS, BLOOMCRYSTAL_ROCK, ROUXE_ROCK, ARCHAIC_GLASS, ARCHAIC_GLASS_PANE, MIASMA_SURFACE, DECEITFUL_PEAT, DECEITFUL_MUD, // MIASMA, DARK_WATER
                        SHADOWROOT_STAIRS, DEAD_WOOD_STAIRS, DARK_WILLOW_STAIRS, NIGHTSTONE_STAIRS, NIGHTSTONE_BRICK_STAIRS, TRENCHSTONE_STAIRS, TRENCHSTONE_BRICK_STAIRS,
                        DEWSHROOM_STAIRS, VIRIDSHROOM_STAIRS, NIGHTSHROOM_STAIRS, ROCKSHROOM_BRICK_STAIRS,
                        SHADOWROOT_SLAB, DEAD_WOOD_SLAB, DARK_WILLOW_SLAB, NIGHTSTONE_SLAB, NIGHTSTONE_BRICK_SLAB, TRENCHSTONE_SLAB, TRENCHSTONE_BRICK_SLAB, DEWSHROOM_SLAB, VIRIDSHROOM_SLAB, NIGHTSHROOM_SLAB, ROCKSHROOM_BRICK_SLAB
                );
        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.DECORATION))
                .addAll(BlockItem::new, SHADOWROOT_CRAFTING_TABLE, DARK_WILLOW_CRAFTING_TABLE, DEAD_WOOD_CRAFTING_TABLE, NIGHTSHROOM_CRAFTING_TABLE, DEWSHROOM_CRAFTING_TABLE, VIRIDSHROOM_CRAFTING_TABLE,
                        NIGHTSTONE_FURNACE, SHADOWROOT_SAPLING, DARK_WILLOW_SAPLING,
                        GRASS, TALL_GRASS, ROUXE, BLOOMCRYSTAL, GLOB_FUNGUS, DECEITFUL_MOSS,
                        NIGHTSHROOM, DOUBLE_NIGHTSHROOM, NIGHTSHROOM_SHELF, DEWSHROOM, DOUBLE_DEWSHROOM, DEWSHROOM_SHELF, VIRIDSHROOM, DOUBLE_VIRIDSHROOM, VIRIDSHROOM_SHELF, BOGSHROOM, DOUBLE_BOGSHROOM, BOGSHROOM_SHELF,
                        LUMEN_BUD, DOUBLE_LUMEN_BUD, BOGWEED, GHOST_PLANT, FINGERED_GRASS, TENDRILWEED, RUNEBUSH, DRAGON_NEST, VIOLEAF, CRYSTAL_FLOWER,
                        NIGHTSHROOM_ROOTS, NIGHTSHROOM_FLOWERING_ROOTS, DEWSHROOM_ROOTS, DEWSHROOM_FLOWERING_ROOTS, VIRIDSHROOM_ROOTS, VIRIDSHROOM_FLOWERING_ROOTS,
                        SHADOWROOT_DOOR, DEAD_WOOD_DOOR, DARK_WILLOW_DOOR, TENEBRUM_DOOR, NIGHTSHROOM_DOOR, DEWSHROOM_DOOR, VIRIDSHROOM_DOOR,
                        SHADOWROOT_TRAPDOOR, DARK_WILLOW_TRAPDOOR, DEAD_WOOD_TRAPDOOR, TENEBRUM_TRAPDOOR, NIGHTSHROOM_TRAPDOOR, DEWSHROOM_TRAPDOOR, VIRIDSHROOM_TRAPDOOR,
                        NIGHTSTONE_WALL, NIGHTSTONE_BRICK_WALL, TRENCHSTONE_WALL, TRENCHSTONE_BRICK_WALL, ROCKSHROOM_BRICK_WALL,
                        SHADOWROOT_FENCE, DEAD_WOOD_FENCE, DARK_WILLOW_FENCE, DEWSHROOM_FENCE, VIRIDSHROOM_FENCE, NIGHTSHROOM_FENCE,
                        SHADOWROOT_FENCE_GATE, DEAD_WOOD_FENCE_GATE, DARK_WILLOW_FENCE_GATE, DEWSHROOM_FENCE_GATE, VIRIDSHROOM_FENCE_GATE, NIGHTSHROOM_FENCE_GATE,
                        SHADOWROOT_LADDER, DEAD_WOOD_LADDER, DARK_WILLOW_LADDER, DEWSHROOM_LADDER, VIRIDSHROOM_LADDER, NIGHTSHROOM_LADDER,
                        SUAVIS, STINGER_EGG, CRYSTALOTUS, MIDNIGHT_LEVER,
                        SHADOWROOT_BUTTON, DEAD_WOOD_BUTTON, DARK_WILLOW_BUTTON, DEWSHROOM_BUTTON, VIRIDSHROOM_BUTTON, NIGHTSHROOM_BUTTON, NIGHTSTONE_BUTTON, TRENCHSTONE_BUTTON, ROCKSHROOM_BRICK_BUTTON,
                        SHADOWROOT_PRESSURE_PLATE, DEAD_WOOD_PRESSURE_PLATE, DARK_WILLOW_PRESSURE_PLATE, DEWSHROOM_PRESSURE_PLATE, VIRIDSHROOM_PRESSURE_PLATE, NIGHTSHROOM_PRESSURE_PLATE, NIGHTSTONE_PRESSURE_PLATE,
                        TRENCHSTONE_PRESSURE_PLATE, ROCKSHROOM_BRICK_PRESSURE_PLATE, NAGRILITE_PRESSURE_PLATE, TENEBRUM_PRESSURE_PLATE
                ) //, FUNGI_INSIDE, UNSTABLE_BUSH, UNSTABLE_BUSH_BLUE_BLOOMED, UNSTABLE_BUSH_GREEN_BLOOMED, UNSTABLE_BUSH_LIME_BLOOMED, BLADESHROOM,
                .add(DECEITFUL_ALGAE, DeceitfulAlgaeItem::new)
                .add(NIGHTSHROOM_SPORCH, (block, props) -> new WallOrFloorItem(NIGHTSHROOM_SPORCH, NIGHTSHROOM_WALL_SPORCH, props))
                .add(DEWSHROOM_SPORCH, (block, props) -> new WallOrFloorItem(DEWSHROOM_SPORCH, DEWSHROOM_WALL_SPORCH, props))
                .add(BOGSHROOM_SPORCH, (block, props) -> new WallOrFloorItem(BOGSHROOM_SPORCH, BOGSHROOM_WALL_SPORCH, props))
                .add(VIRIDSHROOM_SPORCH, (block, props) -> new WallOrFloorItem(VIRIDSHROOM_SPORCH, VIRIDSHROOM_WALL_SPORCH, props));

        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.DECORATION))
                .add(SHADOWROOT_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(DARK_WILLOW_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(DEAD_WOOD_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(NIGHTSHROOM_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(DEWSHROOM_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(VIRIDSHROOM_CHEST, (block, props) -> new BlockItem(block, props.setTEISR(() -> getChestItemRenderer(block))))
                .add(VIRIDSHROOM_STEM_CACHE, BlockItem::new);
    }

    @OnlyIn(Dist.CLIENT)
    private static Callable<ItemStackTileEntityRenderer> getChestItemRenderer(Block block) {
        return () -> new MidnightChestItemRenderer(block);
    }
}
