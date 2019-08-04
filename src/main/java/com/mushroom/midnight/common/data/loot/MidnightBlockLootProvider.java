package com.mushroom.midnight.common.data.loot;

import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.block.SuavisBlock;
import com.mushroom.midnight.common.block.UnstableBushBloomedBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ILootConditionConsumer;
import net.minecraft.world.storage.loot.ILootFunctionConsumer;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.conditions.TableBonus;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class MidnightBlockLootProvider extends MidnightLootTableProvider {
    private static final IRandomRange ONE = ConstantRange.of(1);

    private final Map<Block, LootTable.Builder> lootTables = new HashMap<>();

    public MidnightBlockLootProvider(DataGenerator generator) {
        super(generator, LootParameterSets.BLOCK);
    }

    @Override
    protected void addTables(LootConsumer consumer) {
        this.add(MidnightBlocks.SHADOWROOT_LOG);
        this.add(MidnightBlocks.SHADOWROOT_STRIPPED_LOG);
        this.add(MidnightBlocks.SHADOWROOT_PLANKS);
        this.add(MidnightBlocks.DEAD_WOOD_LOG);
        this.add(MidnightBlocks.DEAD_WOOD_STRIPPED_LOG);
        this.add(MidnightBlocks.DEAD_WOOD_PLANKS);
        this.add(MidnightBlocks.DARK_WILLOW_LOG);
        this.add(MidnightBlocks.DARK_WILLOW_STRIPPED_LOG);
        this.add(MidnightBlocks.DARK_WILLOW_PLANKS);
        this.add(MidnightBlocks.NIGHTSTONE);
        this.add(MidnightBlocks.NIGHTSTONE_BRICKS);
        this.add(MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS);
        this.add(MidnightBlocks.TRENCHSTONE);
        this.add(MidnightBlocks.TRENCHSTONE_BRICKS);
        this.add(MidnightBlocks.DARK_PEARL_BLOCK);
        this.add(MidnightBlocks.TENEBRUM_BLOCK);
        this.add(MidnightBlocks.NAGRILITE_ORE);
        this.add(MidnightBlocks.NAGRILITE_BLOCK);
        this.add(MidnightBlocks.EBONITE_BLOCK);
        this.add(MidnightBlocks.SHADOWROOT_CRAFTING_TABLE);
        this.add(MidnightBlocks.DARK_WILLOW_CRAFTING_TABLE);
        this.add(MidnightBlocks.DEAD_WOOD_CRAFTING_TABLE);
        this.add(MidnightBlocks.NIGHTSHROOM_CRAFTING_TABLE);
        this.add(MidnightBlocks.DEWSHROOM_CRAFTING_TABLE);
        this.add(MidnightBlocks.VIRIDSHROOM_CRAFTING_TABLE);
        this.add(MidnightBlocks.COARSE_DIRT);
        this.add(MidnightBlocks.DIRT);
        this.add(MidnightBlocks.NIGHTSHROOM);
        this.add(MidnightBlocks.NIGHTSHROOM_SHELF);
        this.add(MidnightBlocks.DEWSHROOM);
        this.add(MidnightBlocks.DEWSHROOM_SHELF);
        this.add(MidnightBlocks.DEWSHROOM_PLANKS);
        this.add(MidnightBlocks.VIRIDSHROOM);
        this.add(MidnightBlocks.VIRIDSHROOM_SHELF);
        this.add(MidnightBlocks.VIRIDSHROOM_PLANKS);
        this.add(MidnightBlocks.VIRIDSHROOM_STEM);
        this.add(MidnightBlocks.VIRIDSHROOM_ROOTS);
        this.add(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS);
        this.add(MidnightBlocks.NIGHTSHROOM_STEM);
        this.add(MidnightBlocks.NIGHTSHROOM_ROOTS);
        this.add(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS);
        this.add(MidnightBlocks.NIGHTSHROOM_PLANKS);
        this.add(MidnightBlocks.DEWSHROOM_STEM);
        this.add(MidnightBlocks.DEWSHROOM_ROOTS);
        this.add(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS);
        this.add(MidnightBlocks.BOGSHROOM);
        this.add(MidnightBlocks.BOGSHROOM_SHELF);
        this.add(MidnightBlocks.BOGSHROOM_STEM);
        this.add(MidnightBlocks.GLOB_FUNGUS);
        this.add(MidnightBlocks.GLOB_FUNGUS_STEM);
        this.add(MidnightBlocks.ROCKSHROOM);
        this.add(MidnightBlocks.ROCKSHROOM_BRICKS);
        this.add(MidnightBlocks.LUMEN_BUD);
        this.add(MidnightBlocks.TENDRILWEED);
        this.add(MidnightBlocks.RUNEBUSH);
        this.add(MidnightBlocks.DRAGON_NEST);
        this.add(MidnightBlocks.VIOLEAF);
        this.add(MidnightBlocks.CRYSTAL_FLOWER);
        this.add(MidnightBlocks.SHADOWROOT_SAPLING);
        this.add(MidnightBlocks.DARK_WILLOW_SAPLING);
        this.add(MidnightBlocks.SHADOWROOT_TRAPDOOR);
        this.add(MidnightBlocks.DEAD_WOOD_TRAPDOOR);
        this.add(MidnightBlocks.DARK_WILLOW_TRAPDOOR);
        this.add(MidnightBlocks.TENEBRUM_TRAPDOOR);
        this.add(MidnightBlocks.NIGHTSHROOM_TRAPDOOR);
        this.add(MidnightBlocks.DEWSHROOM_TRAPDOOR);
        this.add(MidnightBlocks.VIRIDSHROOM_TRAPDOOR);
        this.add(MidnightBlocks.BLOOMCRYSTAL);
        this.add(MidnightBlocks.BLOOMCRYSTAL_ROCK);
        this.add(MidnightBlocks.ROUXE);
        this.add(MidnightBlocks.ROUXE_ROCK);
        this.add(MidnightBlocks.MIASMA_SURFACE);
        this.add(MidnightBlocks.DECEITFUL_PEAT);
        this.add(MidnightBlocks.DECEITFUL_MUD);
        this.add(MidnightBlocks.DECEITFUL_ALGAE);
        this.add(MidnightBlocks.DECEITFUL_MOSS);
        this.add(MidnightBlocks.SHADOWROOT_STAIRS);
        this.add(MidnightBlocks.DEAD_WOOD_STAIRS);
        this.add(MidnightBlocks.DARK_WILLOW_STAIRS);
        this.add(MidnightBlocks.NIGHTSTONE_STAIRS);
        this.add(MidnightBlocks.NIGHTSTONE_BRICK_STAIRS);
        this.add(MidnightBlocks.TRENCHSTONE_STAIRS);
        this.add(MidnightBlocks.TRENCHSTONE_BRICK_STAIRS);
        this.add(MidnightBlocks.DEWSHROOM_STAIRS);
        this.add(MidnightBlocks.VIRIDSHROOM_STAIRS);
        this.add(MidnightBlocks.NIGHTSHROOM_STAIRS);
        this.add(MidnightBlocks.ROCKSHROOM_BRICK_STAIRS);
        this.add(MidnightBlocks.SHADOWROOT_FENCE);
        this.add(MidnightBlocks.DEAD_WOOD_FENCE);
        this.add(MidnightBlocks.DARK_WILLOW_FENCE);
        this.add(MidnightBlocks.NIGHTSTONE_WALL);
        this.add(MidnightBlocks.NIGHTSTONE_BRICK_WALL);
        this.add(MidnightBlocks.TRENCHSTONE_WALL);
        this.add(MidnightBlocks.TRENCHSTONE_BRICK_WALL);
        this.add(MidnightBlocks.ROCKSHROOM_BRICK_WALL);
        this.add(MidnightBlocks.DEWSHROOM_FENCE);
        this.add(MidnightBlocks.VIRIDSHROOM_FENCE);
        this.add(MidnightBlocks.NIGHTSHROOM_FENCE);
        this.add(MidnightBlocks.SHADOWROOT_FENCE_GATE);
        this.add(MidnightBlocks.DEAD_WOOD_FENCE_GATE);
        this.add(MidnightBlocks.DARK_WILLOW_FENCE_GATE);
        this.add(MidnightBlocks.DEWSHROOM_FENCE_GATE);
        this.add(MidnightBlocks.VIRIDSHROOM_FENCE_GATE);
        this.add(MidnightBlocks.NIGHTSHROOM_FENCE_GATE);
        this.add(MidnightBlocks.SHADOWROOT_LADDER);
        this.add(MidnightBlocks.DEAD_WOOD_LADDER);
        this.add(MidnightBlocks.DARK_WILLOW_LADDER);
        this.add(MidnightBlocks.DEWSHROOM_LADDER);
        this.add(MidnightBlocks.VIRIDSHROOM_LADDER);
        this.add(MidnightBlocks.NIGHTSHROOM_LADDER);
        this.add(MidnightBlocks.CRYSTALOTUS);
        this.add(MidnightBlocks.SHADOWROOT_BUTTON);
        this.add(MidnightBlocks.DEAD_WOOD_BUTTON);
        this.add(MidnightBlocks.DARK_WILLOW_BUTTON);
        this.add(MidnightBlocks.DEWSHROOM_BUTTON);
        this.add(MidnightBlocks.VIRIDSHROOM_BUTTON);
        this.add(MidnightBlocks.NIGHTSHROOM_BUTTON);
        this.add(MidnightBlocks.NIGHTSTONE_BUTTON);
        this.add(MidnightBlocks.TRENCHSTONE_BUTTON);
        this.add(MidnightBlocks.ROCKSHROOM_BRICK_BUTTON);
        this.add(MidnightBlocks.SHADOWROOT_PRESSURE_PLATE);
        this.add(MidnightBlocks.DEAD_WOOD_PRESSURE_PLATE);
        this.add(MidnightBlocks.DARK_WILLOW_PRESSURE_PLATE);
        this.add(MidnightBlocks.DEWSHROOM_PRESSURE_PLATE);
        this.add(MidnightBlocks.VIRIDSHROOM_PRESSURE_PLATE);
        this.add(MidnightBlocks.NIGHTSHROOM_PRESSURE_PLATE);
        this.add(MidnightBlocks.NIGHTSTONE_PRESSURE_PLATE);
        this.add(MidnightBlocks.TRENCHSTONE_PRESSURE_PLATE);
        this.add(MidnightBlocks.ROCKSHROOM_BRICK_PRESSURE_PLATE);
        this.add(MidnightBlocks.NAGRILITE_PRESSURE_PLATE);
        this.add(MidnightBlocks.TENEBRUM_PRESSURE_PLATE);
        this.add(MidnightBlocks.MIDNIGHT_LEVER);
        this.add(MidnightBlocks.BOGSHROOM_SPORCH);
        this.add(MidnightBlocks.NIGHTSHROOM_SPORCH);
        this.add(MidnightBlocks.DEWSHROOM_SPORCH);
        this.add(MidnightBlocks.VIRIDSHROOM_SPORCH);
        this.add(MidnightBlocks.TENEBRUM_ORE);
        this.add(MidnightBlocks.EBONITE_ORE);
        this.add(MidnightBlocks.GLOB_FUNGUS_HAT);

        this.addSheared(MidnightBlocks.BOGWEED);
        this.addSheared(MidnightBlocks.GHOST_PLANT);
        this.addSheared(MidnightBlocks.FINGERED_GRASS);
        this.addSheared(MidnightBlocks.GRASS);

        this.addDoubleBlock(MidnightBlocks.DOUBLE_NIGHTSHROOM);
        this.addDoubleBlock(MidnightBlocks.DOUBLE_DEWSHROOM);
        this.addDoubleBlock(MidnightBlocks.DOUBLE_VIRIDSHROOM);
        this.addDoubleBlock(MidnightBlocks.DOUBLE_BOGSHROOM);
        this.addDoubleBlock(MidnightBlocks.DOUBLE_LUMEN_BUD);
        this.addShearedDoubleBlock(MidnightBlocks.TALL_GRASS);

        this.addDoubleBlock(MidnightBlocks.SHADOWROOT_DOOR);
        this.addDoubleBlock(MidnightBlocks.DEAD_WOOD_DOOR);
        this.addDoubleBlock(MidnightBlocks.DARK_WILLOW_DOOR);
        this.addDoubleBlock(MidnightBlocks.TENEBRUM_DOOR);
        this.addDoubleBlock(MidnightBlocks.NIGHTSHROOM_DOOR);
        this.addDoubleBlock(MidnightBlocks.DEWSHROOM_DOOR);
        this.addDoubleBlock(MidnightBlocks.VIRIDSHROOM_DOOR);

        this.addSilkTouched(MidnightBlocks.ARCHAIC_GLASS);
        this.addSilkTouched(MidnightBlocks.ARCHAIC_GLASS_PANE);

        this.addSlab(MidnightBlocks.SHADOWROOT_SLAB);
        this.addSlab(MidnightBlocks.DEAD_WOOD_SLAB);
        this.addSlab(MidnightBlocks.DARK_WILLOW_SLAB);
        this.addSlab(MidnightBlocks.NIGHTSTONE_SLAB);
        this.addSlab(MidnightBlocks.NIGHTSTONE_BRICK_SLAB);
        this.addSlab(MidnightBlocks.TRENCHSTONE_SLAB);
        this.addSlab(MidnightBlocks.TRENCHSTONE_BRICK_SLAB);
        this.addSlab(MidnightBlocks.DEWSHROOM_SLAB);
        this.addSlab(MidnightBlocks.VIRIDSHROOM_SLAB);
        this.addSlab(MidnightBlocks.NIGHTSHROOM_SLAB);
        this.addSlab(MidnightBlocks.ROCKSHROOM_BRICK_SLAB);

        this.addFungiHat(MidnightBlocks.VIRIDSHROOM_HAT, MidnightItems.VIRIDSHROOM_POWDER);
        this.addFungiHat(MidnightBlocks.NIGHTSHROOM_HAT, MidnightItems.NIGHTSHROOM_POWDER);
        this.addFungiHat(MidnightBlocks.DEWSHROOM_HAT, MidnightItems.DEWSHROOM_POWDER);
        this.addFungiHat(MidnightBlocks.BOGSHROOM_HAT, MidnightItems.BOGSHROOM_POWDER);

        this.addSilkTouchedAlternative(MidnightBlocks.GRASS_BLOCK, MidnightBlocks.DIRT);
        this.addSilkTouchedAlternative(MidnightBlocks.MYCELIUM, MidnightBlocks.NIGHTSTONE);

        this.addGem(MidnightBlocks.DARK_PEARL_ORE, MidnightItems.GEODE);
        this.addGem(MidnightBlocks.ARCHAIC_ORE, MidnightItems.ARCHAIC_SHARD);

        this.add(MidnightBlocks.UNSTABLE_BUSH, MidnightItems.UNSTABLE_SEEDS);

        this.addUnstableBush(MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED, MidnightItems.BLUE_UNSTABLE_FRUIT);
        this.addUnstableBush(MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED, MidnightItems.GREEN_UNSTABLE_FRUIT);
        this.addUnstableBush(MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED, MidnightItems.LIME_UNSTABLE_FRUIT);

        this.addLeaves(MidnightBlocks.SHADOWROOT_LEAVES, MidnightBlocks.SHADOWROOT_SAPLING);
        this.addLeaves(MidnightBlocks.DARK_WILLOW_LEAVES, MidnightBlocks.DARK_WILLOW_SAPLING);

        this.add(MidnightBlocks.SHADOWROOT_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.DARK_WILLOW_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.DEAD_WOOD_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.NIGHTSHROOM_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.DEWSHROOM_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.VIRIDSHROOM_CHEST, MidnightBlockLootProvider::copyName);
        this.add(MidnightBlocks.NIGHTSTONE_FURNACE, MidnightBlockLootProvider::copyName);

        this.add(MidnightBlocks.BLADESHROOM, block -> LootTable.builder().addLootPool(
                LootPool.builder().rolls(ONE)
                        .addEntry(ItemLootEntry.builder(MidnightItems.BLADESHROOM_CAP)
                                .acceptCondition(BlockStateProperty.builder(block).with(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED))
                        )
                        .addEntry(AlternativesLootEntry.func_216149_a(
                                ItemLootEntry.builder(MidnightItems.BLADESHROOM_SPORES)
                                        .acceptCondition(BlockStateProperty.builder(block).with(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED)),
                                ItemLootEntry.builder(MidnightItems.BLADESHROOM_SPORES)
                                        .acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))
                        ))
        ));

        this.add(MidnightBlocks.SUAVIS, block -> LootTable.builder().addLootPool(
                LootPool.builder().rolls(ONE).addEntry(AlternativesLootEntry.func_216149_a(
                        ItemLootEntry.builder(block)
                                .acceptCondition(Conditions.HAS_SILK_TOUCH)
                                .acceptCondition(BlockStateProperty.builder(block).with(SuavisBlock.STAGE, 3)),
                        ItemLootEntry.builder(MidnightItems.RAW_SUAVIS)
                                .acceptCondition(BlockStateProperty.builder(block).with(SuavisBlock.STAGE, 3))
                                .acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 3.0F))),
                        ItemLootEntry.builder(MidnightItems.RAW_SUAVIS)
                                .acceptCondition(BlockStateProperty.builder(block).with(SuavisBlock.STAGE, 2))
                                .acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))),
                        ItemLootEntry.builder(MidnightItems.RAW_SUAVIS)
                                .acceptCondition(BlockStateProperty.builder(block).with(SuavisBlock.STAGE, 1))
                                .acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 1.0F)))
                ))
        ));

        this.lootTables.forEach(consumer::accept);
    }

    private static <T> T explosionDecay(ILootFunctionConsumer<T> consumer) {
        return consumer.acceptFunction(ExplosionDecay.func_215863_b());
    }

    private static <T> T checkExplosion(ILootConditionConsumer<T> consumer) {
        return consumer.acceptCondition(SurvivesExplosion.builder());
    }

    private static LootTable.Builder drop(IItemProvider item) {
        return LootTable.builder()
                .addLootPool(checkExplosion(LootPool.builder()
                        .rolls(ONE)
                        .addEntry(ItemLootEntry.builder(item))
                ));
    }

    private static LootTable.Builder dropIf(IItemProvider item, ILootCondition.IBuilder condition) {
        return LootTable.builder()
                .addLootPool(checkExplosion(LootPool.builder()
                        .rolls(ONE)
                        .addEntry(ItemLootEntry.builder(item))
                        .acceptCondition(condition)
                ));
    }

    private static LootTable.Builder selfOrAlternative(Block block, ILootCondition.IBuilder condition, LootEntry.Builder<?> alternative) {
        return LootTable.builder().addLootPool(LootPool.builder()
                .addEntry(ItemLootEntry.builder(block).acceptCondition(condition).func_216080_a(alternative))
                .rolls(ONE)
        );
    }

    private static LootTable.Builder silkTouched(Block block, LootEntry.Builder<?> alternative) {
        return selfOrAlternative(block, Conditions.HAS_SILK_TOUCH, alternative);
    }

    private static LootTable.Builder silkOrSheared(Block block, LootEntry.Builder<?> alternative) {
        return selfOrAlternative(block, Conditions.HAS_SHEARS_OR_SILK_TOUCH, alternative);
    }

    private static LootTable.Builder copyName(Block block) {
        return LootTable.builder().addLootPool(checkExplosion(LootPool.builder()
                .rolls(ONE)
                .addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)))
        ));
    }

    private void add(Block block, Function<Block, LootTable.Builder> function) {
        this.add(block, function.apply(block));
    }

    private void add(Block block, LootTable.Builder table) {
        this.lootTables.put(block, table);
    }

    private void add(Block block, IItemProvider item) {
        this.add(block, drop(item));
    }

    private void add(Block block) {
        this.add(block, block);
    }

    private void addSilkTouchedAlternative(Block block, IItemProvider drop) {
        this.add(block, silkTouched(block, ItemLootEntry.builder(drop)));
    }

    private void addSilkTouched(Block block) {
        this.add(block, dropIf(block, Conditions.HAS_SILK_TOUCH));
    }

    private void addSheared(Block block, IItemProvider drop) {
        this.add(block, dropIf(drop, Conditions.HAS_SHEARS));
    }

    private void addSheared(Block block) {
        this.addSheared(block, block);
    }

    private void addSlab(Block block) {
        ILootCondition.IBuilder isDouble = BlockStateProperty.builder(block).with(SlabBlock.TYPE, SlabType.DOUBLE);
        LootFunction.Builder<?> doubleFunction = SetCount.func_215932_a(ConstantRange.of(2)).acceptCondition(isDouble);

        LootTable.Builder table = LootTable.builder().addLootPool(LootPool.builder()
                .addEntry(explosionDecay(ItemLootEntry.builder(block).acceptFunction(doubleFunction)))
                .rolls(ONE)
        );

        this.add(block, table);
    }

    private void addGem(Block block, IItemProvider gem) {
        LootTable.Builder table = silkTouched(block, explosionDecay(
                ItemLootEntry.builder(gem).acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))
        ));

        this.add(block, table);
    }

    private void addFungiHat(Block block, IItemProvider powder) {
        LootPool.Builder pool = LootPool.builder()
                .addEntry(AlternativesLootEntry.func_216149_a(
                        ItemLootEntry.builder(block).acceptCondition(RandomChance.builder(0.5F)),
                        ItemLootEntry.builder(powder)
                ))
                .rolls(ONE);

        this.add(block, LootTable.builder().addLootPool(explosionDecay(pool)));
    }

    private void addUnstableBush(Block block, IItemProvider fruit) {
        ILootCondition.IBuilder condition = BlockStateProperty.builder(block).with(UnstableBushBloomedBlock.HAS_FRUIT, true);
        RandomValueRange count = RandomValueRange.func_215837_a(3.0F, 6.0F);
        this.add(block, LootTable.builder().addLootPool(LootPool.builder()
                .acceptCondition(condition)
                .addEntry(explosionDecay(ItemLootEntry.builder(fruit).acceptFunction(SetCount.func_215932_a(count))))
                .rolls(ONE)
        ));
    }

    private void addLeaves(Block block, IItemProvider sapling) {
        LootTable.Builder table = silkOrSheared(block, checkExplosion(ItemLootEntry.builder(sapling))
                .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.08F, 0.1F)))
                .addLootPool(LootPool.builder()
                        .acceptCondition(Conditions.NOT_SHEARS_OR_SILK_TOUCH)
                        .addEntry(explosionDecay(ItemLootEntry.builder(MidnightItems.DARK_STICK)
                                .acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))))
                                .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.02F, 0.025F, 0.03F, 0.1F))
                        )
                        .rolls(ONE)
                );

        this.add(block, table);
    }

    private void addDoubleBlock(Block block) {
        LootTable.Builder table = LootTable.builder()
                .addLootPool(checkExplosion(LootPool.builder()
                        .rolls(ONE)
                        .addEntry(ItemLootEntry.builder(block))
                        .acceptCondition(BlockStateProperty.builder(block).with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))
                ));

        this.add(block, table);
    }

    private void addShearedDoubleBlock(Block block) {
        LootTable.Builder table = LootTable.builder()
                .addLootPool(checkExplosion(LootPool.builder()
                        .rolls(ONE)
                        .addEntry(ItemLootEntry.builder(block))
                        .acceptCondition(BlockStateProperty.builder(block).with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))
                        .acceptCondition(Conditions.HAS_SHEARS)
                ));

        this.add(block, table);
    }
}
