package midnight.common.block;

import com.mojang.datafixers.util.Pair;
import midnight.common.Midnight;
import midnight.common.block.color.NightGrassBlockColor;
import midnight.common.block.color.NightGrassColor;
import midnight.common.block.fluid.MnFluids;
import midnight.common.item.MnItemGroups;
import midnight.common.registry.BlockItemBuilder;
import midnight.common.registry.RegistryManager;
import midnight.core.util.BlockLayer;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Function;
import java.util.function.Supplier;

/*
 * We use our own Registry Manager to register blocks for The Midnight.
 * This makes adding and developing new blocks into the mod easier for everyone involved.
 */
@ObjectHolder("midnight")
public final class MnBlocks {
    private static final Factory<Block> STONE = factory(
        () -> BlockItemBuilder.builder(Block::new)
                              .material(Material.ROCK)
                              .sound(SoundType.STONE)
                              .strength(0.5, 6)
                              .harvestTool(ToolType.PICKAXE)
                              .group(MnItemGroups.BLOCKS)
    );

    private static final Factory<NightDirtBlock> DIRT = factory(
        () -> BlockItemBuilder.builder(NightDirtBlock::new)
                              .material(Material.EARTH)
                              .sound(SoundType.GROUND)
                              .strength(0.5)
                              .harvestTool(ToolType.SHOVEL)
                              .group(MnItemGroups.BLOCKS)
    );

    public static final Block NIGHT_STONE = STONE.blockItem("night_stone", config -> config.color(MaterialColor.OBSIDIAN));
    public static final Block NIGHT_BEDROCK = STONE.blockItem("night_bedrock", config -> config.color(MaterialColor.LIGHT_GRAY_TERRACOTTA).unbreakable());
    public static final Block TRENCHSTONE = STONE.blockItem("trenchstone", config -> config.color(MaterialColor.BLACK));

    public static final Block NIGHT_DIRT = DIRT.blockItem("night_dirt", config -> config.color(MaterialColor.BLACK));
    public static final Block COARSE_NIGHT_DIRT = DIRT.blockItem("coarse_night_dirt", config -> config.color(MaterialColor.BLACK));
    public static final Block NIGHT_GRASS_BLOCK = register(
        "night_grass_block",
        BlockItemBuilder.builder(NightGrassBlock::new)
                        .material(Material.EARTH)
                        .sound(SoundType.PLANT)
                        .strength(0.6)
                        .harvestTool(ToolType.SHOVEL)
                        .color(MaterialColor.PURPLE_TERRACOTTA)
                        .renderLayer(BlockLayer.CUTOUT_MIPPED)
                        .multiplier(new NightGrassBlockColor())
                        .ticksRandomly(true)
                        .group(MnItemGroups.BLOCKS)
                        .makeBlockAndItem()
    );
    public static final Block DECEITFUL_PEAT = DIRT.blockItem("deceitful_peat", config -> config.color(MaterialColor.PURPLE_TERRACOTTA));
    public static final Block DECEITFUL_MUD = DIRT.blockItem(
        "deceitful_mud",
        config -> config.factory(DeceitfulMudBlock::new)
                        .sound(MnSoundTypes.MUD)
                        .strength(0.5)
                        .color(MaterialColor.BLUE_TERRACOTTA)
    );
    public static final Block STRANGE_SAND = register(
        "strange_sand",
        BlockItemBuilder.builder(StrangeSandBlock::new)
                        .material(Material.SAND)
                        .sound(SoundType.SAND)
                        .strength(0.5)
                        .harvestTool(ToolType.SHOVEL)
                        .color(MaterialColor.BLUE_TERRACOTTA)
                        .group(MnItemGroups.BLOCKS)
                        .makeBlockAndItem()
    );

    public static final Block DARK_WATER = register(
        "dark_water",
        BlockItemBuilder.builder(props -> new FlowingFluidBlock(() -> MnFluids.DARK_WATER, props))
                        .material(Material.WATER)
                        .strength(100)
                        .makeBlock()
    );
    public static final Block MIASMA = register(
        "miasma",
        BlockItemBuilder.builder(props -> new FlowingFluidBlock(() -> MnFluids.MIASMA, props))
                        .material(Material.LAVA)
                        .strength(100)
                        .makeBlock()
    );

    private static final Factory<MnPlantBlock> PLANTS = factory(
        () -> BlockItemBuilder.builder(MnPlantBlock::new)
                              .solid(false)
                              .group(MnItemGroups.DECOR)
                              .sound(SoundType.PLANT)
                              .renderLayer(BlockLayer.CUTOUT)
                              .material(Material.PLANTS)
    );

    private static final Factory<MnDoublePlantBlock> TALL_PLANTS = factory(
        () -> BlockItemBuilder.builder(MnDoublePlantBlock::new)
                              .solid(false)
                              .group(MnItemGroups.DECOR)
                              .sound(SoundType.PLANT)
                              .renderLayer(BlockLayer.CUTOUT)
                              .material(Material.PLANTS)
    );

    public static final Block NIGHT_GRASS = PLANTS.blockItem(
        "night_grass",
        config -> config.color(MaterialColor.PURPLE_TERRACOTTA)
                        .multiplier(new NightGrassColor())
                        .factory(props -> new SmallGrowablePlantBlock(props, supply("tall_night_grass")))
                        .material(Material.TALL_PLANTS)
                        .processBlock(block -> block.setPlantHitbox(14, 14))
                        .processBlock(block -> block.setOffsetType(Block.OffsetType.XYZ))
    );
    public static final Block TALL_NIGHT_GRASS = TALL_PLANTS.blockItem(
        "tall_night_grass",
        config -> config.color(MaterialColor.PURPLE_TERRACOTTA)
                        .multiplier(new NightGrassColor())
                        .material(Material.TALL_PLANTS)
                        .processBlock(block -> block.setPlantHitbox(14, 30))
                        .processBlock(block -> block.setOffsetType(Block.OffsetType.XZ))
    );

    private static final Factory<Block> GIANT_GHOST_PLANT = factory(
        () -> BlockItemBuilder.<Block>builder(GhostPlantBlock::new)
                  .solid(false)
                  .group(MnItemGroups.DECOR)
                  .hardness(0.3)
                  .sound(SoundType.WOOD)
                  .renderLayer(BlockLayer.TRANSLUCENT)
                  .emission(15)
                  .material(Material.WOOD)
                  .color(MaterialColor.SNOW)
    );

    public static final Block GIANT_GHOST_PLANT_STEM = GIANT_GHOST_PLANT.blockItem(
        "giant_ghost_plant_stem",
        config -> config.factory(GhostPlantStemBlock::new)
    );
    public static final Block GIANT_GHOST_PLANT_LEAF = GIANT_GHOST_PLANT.blockItem("giant_ghost_plant_leaf");
    public static final Block GHOST_PLANT = PLANTS.blockItem(
        "ghost_plant",
        config -> config.color(MaterialColor.SNOW)
                        .emission(9)
                        .processBlock(block -> block.setPlantHitbox(14, 14))
                        .processBlock(block -> block.setOffsetType(Block.OffsetType.XZ))
    );




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

    private static <B extends Block> Factory<B> factory(Supplier<BlockItemBuilder<B>> config) {
        return new Factory<>(config);
    }

    @SuppressWarnings("unchecked")
    public static <B extends Block> Supplier<B> supply(String id) {
        ResourceLocation key = Midnight.resLoc(id);
        return () -> (B) ForgeRegistries.BLOCKS.getValue(key);
    }

    private static class Factory<B extends Block> {
        private final Supplier<BlockItemBuilder<B>> builderSupplier;

        private Factory(Supplier<BlockItemBuilder<B>> builderSupplier) {
            this.builderSupplier = builderSupplier;
        }

        public B block(String id, Function<BlockItemBuilder<B>, BlockItemBuilder<B>> additionalConfig) {
            return register(id, additionalConfig.apply(builderSupplier.get()).makeBlock());
        }

        public B block(String id) {
            return block(id, Function.identity());
        }

        public B blockItem(String id, Function<BlockItemBuilder<B>, BlockItemBuilder<B>> additionalConfig) {
            return register(id, additionalConfig.apply(builderSupplier.get()).makeBlockAndItem());
        }

        public B blockItem(String id) {
            return blockItem(id, Function.identity());
        }
    }
}
