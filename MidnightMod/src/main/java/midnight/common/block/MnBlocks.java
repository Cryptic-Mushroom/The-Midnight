package midnight.common.block;

import com.mojang.datafixers.util.Pair;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.TallBlockItem;
import midnight.common.fluid.MnFluids;
import midnight.common.registry.BlockItemBuilder;
import midnight.common.registry.MidnightItemGroups;
import midnight.common.registry.RegistryManager;
import midnight.core.util.BlockLayer;

/*
 * We use our own Registry Manager to register blocks for The Midnight.
 * This makes adding and developing new blocks into the mod easier for everyone involved.
 */
@ObjectHolder("midnight")
public final class MnBlocks {
    public static final Block NIGHT_DIRT = register(
            "night_dirt",
            BlockItemBuilder.builder(properties -> new SoilBlock(properties, true))
                            .material(Material.EARTH)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.GROUND)
                            .strength(0.5)
                            .harvestTool(ToolType.SHOVEL)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block GRASS_BLOCK = register(
            "grass_block",
            BlockItemBuilder.builder(properties -> new SpreadableSoilBlock(properties, () -> NIGHT_DIRT))
                            .material(Material.ORGANIC)
                            .color(MaterialColor.PINK)
                            .sound(SoundType.PLANT)
                            .strength(1.0F)
                            .harvestTool(ToolType.SHOVEL)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block COARSE_DIRT = register(
            "coarse_dirt",
            BlockItemBuilder.builder(properties -> new SoilBlock(properties, false))
                            .material(Material.EARTH)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.GROUND)
                            .strength(0.5)
                            .harvestTool(ToolType.SHOVEL)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DECEITFUL_MUD = register(
            "deceitful_mud",
            BlockItemBuilder.builder(MudBlock::new)
                            .material(Material.ORGANIC)
                            .color(MaterialColor.GRAY_TERRACOTTA)
                            .strength(0.5, 0F)
                            .harvestTool(ToolType.SHOVEL)
                            .normalCube(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DECEITFUL_PEAT = register(
            "deceitful_peat",
            BlockItemBuilder.builder(MudBlock::new)
                            .material(Material.EARTH)
                            .color(MaterialColor.BLUE_TERRACOTTA)
                            .sound(SoundType.GROUND)
                            .strength(0.5)
                            .harvestTool(ToolType.SHOVEL)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );

    public static final Block MYCELIUM = register(
            "mycelium",
            BlockItemBuilder.builder(MidnightMyceliumBlock::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.PINK)
                            .sound(SoundType.STONE)
                            .strength(1.5F, 10.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(0)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSTONE = register(
            "nightstone",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.STONE)
                            .strength(1.5F, 6.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(0)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSTONE_BRICKS = register(
            "nightstone_bricks",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.STONE)
                            .strength(1.5F, 6.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(0)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block CHISELED_NIGHTSTONE_BRICKS = register(
            "chiseled_nightstone_bricks",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.STONE)
                            .strength(1.5F, 6.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(0)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block TRENCHSTONE = register(
            "trenchstone",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.STONE)
                            .strength(50.0F, 1200.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(3)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block TRENCHSTONE_BRICKS = register(
            "trenchstone_bricks",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.BLACK)
                            .sound(SoundType.STONE)
                            .strength(50.0F, 1200.0F)
                            .harvestTool(ToolType.PICKAXE)
                            .harvestLevel(3)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_STRIPPED_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;
    public static final Block SHADOWROOT_PLANKS = Blocks.AIR;
    public static final Block SHADOWROOT_WOOD = Blocks.AIR;
    public static final Block SHADOWROOT_STRIPPED_WOOD = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_STRIPPED_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD = Blocks.AIR;
    public static final Block DEAD_WOOD_STRIPPED = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANKS = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_STRIPPED_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_WOOD = Blocks.AIR;
    public static final Block DARK_WILLOW_STRIPPED_WOOD = Blocks.AIR;
    public static final Block DARK_WILLOW_LEAVES = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block HANGING_DARK_WILLOW_LEAVES = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = register(
            "shadowroot_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DARK_WILLOW_CRAFTING_TABLE = register(
            "dark_willow_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.CYAN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DEAD_WOOD_CRAFTING_TABLE = register(
            "dead_wood_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GRAY)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSHROOM_CRAFTING_TABLE = register(
            "nightshroom_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.PURPLE)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DEWSHROOM_CRAFTING_TABLE = register(
            "dewshroom_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block VIRIDSHROOM_CRAFTING_TABLE = register(
            "viridshroom_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block BOGSHROOM_CRAFTING_TABLE = register(
            "bogshroom_crafting_table",
            BlockItemBuilder.builder(MidnightCraftingTableBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );

    public static final Block SHADOWROOT_DOOR = register(
            "shadowroot_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block DARK_WILLOW_DOOR = register(
            "dark_willow_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.CYAN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block DEAD_WOOD_DOOR = register(
            "dead_wood_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GRAY)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSHROOM_DOOR = register(
            "nightshroom_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block DEWSHROOM_DOOR = register(
            "dewshroom_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block VIRIDSHROOM_DOOR = register(
            "viridshroom_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );
    public static final Block BOGSHROOM_DOOR = register(
            "bogshroom_door",
            BlockItemBuilder.builder(DoorBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .renderLayer(BlockLayer.CUTOUT_MIPPED)
                            .makeBlockAndItem()
    );

    public static final Block SHADOWROOT_CHEST = register(
            "shadowroot_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DARK_WILLOW_CHEST = register(
            "dark_willow_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.CYAN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DEAD_WOOD_CHEST = register(
            "dead_wood_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GRAY)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSHROOM_CHEST = register(
            "nightshroom_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.PURPLE)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block DEWSHROOM_CHEST = register(
            "dewshroom_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block VIRIDSHROOM_CHEST = register(
            "viridshroom_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block BOGSHROOM_CHEST = register(
            "bogshroom_chest",
            BlockItemBuilder.builder(MidnightChestBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.5F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block VIRIDSHROOM_STEM_CACHE = register(
            "viridshroom_stem_cache",
            BlockItemBuilder.builder(CacheBlock::new)
                            .material(Material.WOOD)
                            .color(MaterialColor.GREEN)
                            .sound(SoundType.WOOD)
                            .strength(2.0F)
                            .harvestTool(ToolType.AXE)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );
    public static final Block NIGHTSTONE_FURNACE = register(
            "nightstone_furnace",
            BlockItemBuilder.builder(MidnightFurnaceBlock::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.CYAN)
                            .sound(SoundType.STONE)
                            .strength(3.5F)
                            .emission(blockstate -> blockstate.get(MidnightFurnaceBlock.LIT) ? 13 : 0)
                            .harvestTool(ToolType.PICKAXE)
                            .requiresTool(true)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );


    public static final Block GRASS = register(
            "grass",
            BlockItemBuilder.builder(MidnightPlantBlock::new)
                            .material(Material.PLANTS)
                            .color(MaterialColor.PINK)
                            .sound(SoundType.PLANT)
                            .noStrength()
                            .solid(false)
                            .blocksMovement(false)
                            .renderLayer(BlockLayer.CUTOUT)
                            .group(MidnightItemGroups.DECORATION)
                            .makeBlockAndItem()
    );
    public static final Block TALL_GRASS = register(
            "tall_grass",
            BlockItemBuilder.builder(MidnightDoublePlantBlock::new)
                            .itemFactory(TallBlockItem::new)
                            .material(Material.PLANTS)
                            .color(MaterialColor.PINK)
                            .sound(SoundType.PLANT)
                            .noStrength()
                            .solid(false)
                            .blocksMovement(false)
                            .renderLayer(BlockLayer.CUTOUT)
                            .group(MidnightItemGroups.DECORATION)
                            .makeBlockAndItem()
    );

    public static final Block BLOOMCRYSTAL_ROCK = register(
            "bloomcrystal_rock",
            BlockItemBuilder.builder(Block::new)
                            .material(Material.ROCK)
                            .color(MaterialColor.PINK)
                            .sound(SoundType.GLASS)
                            .strength(2.0F)
                            .requiresTool(true)
                            .harvestLevel(1)
                            .emission(14)
                            .group(MidnightItemGroups.BUILDING)
                            .makeBlockAndItem()
    );

    public static final Block BLADESHROOM = register(
            "bladeshroom",
            BlockItemBuilder.builder(BladeshroomBlock::new)
                            .material(Material.TALL_PLANTS)
                            .sound(SoundType.PLANT)
                            .noStrength()
                            .solid(false)
                            .blocksMovement(false)
                            .renderLayer(BlockLayer.CUTOUT)
                            .group(MidnightItemGroups.DECORATION)
                            .makeBlockAndItem()
    );
    public static final Block BOGWEED = Blocks.AIR;
    public static final Block GHOST_PLANT = Blocks.AIR;
    public static final Block FINGERED_GRASS = Blocks.AIR;
    public static final Block TENDRILWEED = Blocks.AIR;
    public static final Block RUNEBUSH = Blocks.AIR;
    public static final Block DRAGON_NEST = Blocks.AIR;
    public static final Block VIOLEAF = Blocks.AIR;

    public static final Block ROUXE = Blocks.AIR;
    public static final Block ROUXE_ROCK = Blocks.AIR;

    public static final Block ARCHAIC_GLASS = Blocks.AIR;
    public static final Block ARCHAIC_GLASS_PANE = Blocks.AIR;

    public static final Block MIASMA_SURFACE = register("miasma_surface", BlockItemBuilder.builder(MiasmaSurfaceBlock::new).material(Material.ROCK).color(MaterialColor.BLUE_TERRACOTTA).strength(0.5f, 0f).makeBlockAndItem());
    public static final Block MIASMA = register("miasma", BlockItemBuilder.builder(properties -> new MidnightFluidBlock(() -> MnFluids.MIASMA, true, properties)).blocksMovement(false).strength(100.0F).drops(false).solid(false).makeBlock());
    public static final Block DARK_WATER = register("dark_water", BlockItemBuilder.builder(properties -> new MidnightFluidBlock(() -> MnFluids.DARK_WATER, false, properties)).blocksMovement(false).strength(100.0F).drops(false).solid(false).makeBlock());

    public static final Block FUNGI_INSIDE = Blocks.AIR;

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
    public static final Block BOGSHROOM_SLAB = Blocks.AIR;
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
    public static final Block BOGSHROOM_STAIRS = Blocks.AIR;
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
    public static final Block BOGSHROOM_FENCE = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE_GATE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE_GATE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE_GATE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block BOGSHROOM_FENCE_GATE = Blocks.AIR;

    public static final Block SUAVIS = Blocks.AIR;

    public static final Block SHADOWROOT_LADDER = Blocks.AIR;
    public static final Block DEAD_WOOD_LADDER = Blocks.AIR;
    public static final Block DARK_WILLOW_LADDER = Blocks.AIR;
    public static final Block DEWSHROOM_LADDER = Blocks.AIR;
    public static final Block VIRIDSHROOM_LADDER = Blocks.AIR;
    public static final Block NIGHTSHROOM_LADDER = Blocks.AIR;
    public static final Block BOGSHROOM_LADDER = Blocks.AIR;

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
    public static final Block BOGSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSTONE_BUTTON = Blocks.AIR;
    public static final Block TRENCHSTONE_BUTTON = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_BUTTON = Blocks.AIR;

    public static final Block SHADOWROOT_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEAD_WOOD_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DARK_WILLOW_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEWSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block BOGSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TRENCHSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICK_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NAGRILITE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TENEBRUM_PRESSURE_PLATE = Blocks.AIR;

    public static final Block MIDNIGHT_LEVER = Blocks.AIR;

    public static final Block MALIGNANT_BLUE_PLANT_BLOCK = Blocks.AIR;
    public static final Block MALIGNANT_RED_PLANT_BLOCK = Blocks.AIR;
    public static final Block MALIGNANT_PURPLE_PLANT_BLOCK = Blocks.AIR;
    public static final Block MALIGNANT_GREEN_PLANT_BLOCK = Blocks.AIR;

    public static final Block GLOWING_MALIGNANT_BLUE_PLANT_BLOCK = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_RED_PLANT_BLOCK = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_PURPLE_PLANT_BLOCK = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_GREEN_PLANT_BLOCK = Blocks.AIR;

    public static final Block MALIGNANT_BLUE_HANGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_RED_HANGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_PURPLE_HANGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_GREEN_HANGING_VINES = Blocks.AIR;

    public static final Block GLOWING_MALIGNANT_BLUE_HANGING_VINES = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_RED_HANGING_VINES = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_PURPLE_HANGING_VINES = Blocks.AIR;
    public static final Block GLOWING_MALIGNANT_GREEN_HANGING_VINES = Blocks.AIR;

    public static final Block MALIGNANT_BLUE_PLANT_SURFACE = Blocks.AIR;
    public static final Block MALIGNANT_RED_PLANT_SURFACE = Blocks.AIR;
    public static final Block MALIGNANT_PURPLE_PLANT_SURFACE = Blocks.AIR;
    public static final Block MALIGNANT_GREEN_PLANT_SURFACE = Blocks.AIR;

    public static final Block MALIGNANT_BLUE_BRIDGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_RED_BRIDGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_PURPLE_BRIDGING_VINES = Blocks.AIR;
    public static final Block MALIGNANT_GREEN_BRIDGING_VINES = Blocks.AIR;

    public static final Block MALIGNANT_FOXGLOVE = Blocks.AIR;
    public static final Block MALIGNANT_HEMLOCK = Blocks.AIR;
    public static final Block MALIGNANT_HYACINTH = Blocks.AIR;
    public static final Block MALIGNANT_IVY = Blocks.AIR;
    public static final Block MALIGNANT_LARKSPUR = Blocks.AIR;
    public static final Block MALIGNANT_LILY = Blocks.AIR;
    public static final Block MALIGNANT_MANDRAKE = Blocks.AIR;
    public static final Block MALIGNANT_MOONSEED = Blocks.AIR;
    public static final Block MALIGNANT_NETTLE = Blocks.AIR;
    public static final Block MALIGNANT_RAGWEED = Blocks.AIR;
    public static final Block MALIGNANT_SNAKEROOT = Blocks.AIR;
    public static final Block MALIGNANT_SPINDLE = Blocks.AIR;
    public static final Block MALIGNANT_TAILFLOWER = Blocks.AIR;
    public static final Block MALIGNANT_THISTLE = Blocks.AIR;
    public static final Block MALIGNANT_WALLFLOWER = Blocks.AIR;
    public static final Block MALIGNANT_WOLFSBANE = Blocks.AIR;

    public static final Block MALIGNANT_BANEBERRY = Blocks.AIR;
    public static final Block MALIGNANT_BLOODROOT = Blocks.AIR;
    public static final Block MALIGNANT_NIGHTSHADE = Blocks.AIR;
    public static final Block MALIGNANT_WISTERIA = Blocks.AIR;

    private MnBlocks() {
    }

    private static <B extends Block> B register(String id, B block) {
        RegistryManager.BLOCKS.register(id, block);
        return block;
    }

    private static <B extends Block> B register(String id, Pair<? extends B, ? extends BlockItem> blockItemPair) {
        RegistryManager.BLOCKS.register(id, blockItemPair.getFirst());
        RegistryManager.ITEMS.register(id, blockItemPair.getSecond());
        return blockItemPair.getFirst();
    }
}
