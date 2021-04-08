/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.data.loottables;

import midnight.common.block.MnBlockStateProperties;
import midnight.common.block.MnBlocks;
import midnight.common.block.SuavisBlock;
import midnight.common.item.MnItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class MnBlockLootTables extends BlockLootTables {
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.invert();
    private static final ILootCondition.IBuilder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();
    private static final float[] DEFAULT_SAPLING_DROP_RATES = {1 / 20F, 1 / 16F, 1 / 12F, 1 / 10F};
    private static final float[] DEFAULT_GLOB_DROP_RATES = {1 / 5F, 1 / 3F, 2 / 3F, 1};

    @Override
    protected void addTables() {
        dropSelf(MnBlocks.NIGHTSTONE);
        dropSelf(MnBlocks.TRENCHSTONE);
        add(MnBlocks.NIGHT_BEDROCK, block -> droppingNothing());

        dropSelf(MnBlocks.NIGHT_DIRT);
        add(MnBlocks.NIGHT_GRASS_BLOCK, block -> createSingleItemTableWithSilkTouch(block, MnBlocks.NIGHT_DIRT));
        dropSelf(MnBlocks.DECEITFUL_PEAT);
        dropSelf(MnBlocks.DECEITFUL_MUD);
        dropSelf(MnBlocks.STRANGE_SAND);
        dropSelf(MnBlocks.COARSE_NIGHT_DIRT);
        add(MnBlocks.NIGHT_MYCELIUM, block -> createSingleItemTableWithSilkTouch(block, MnBlocks.NIGHTSTONE));

        dropSelf(MnBlocks.NIGHTSTONE_BRICKS);
        dropSelf(MnBlocks.TRENCHSTONE_BRICKS);
        dropSelf(MnBlocks.SHROOMBRICKS);

        dropSelf(MnBlocks.GHOST_PLANT_LEAF);
        dropSelf(MnBlocks.GHOST_PLANT_STEM);
        dropSelf(MnBlocks.GHOST_PLANT);

        add(MnBlocks.NIGHT_GRASS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.TALL_NIGHT_GRASS, BlockLootTables::createShearsOnlyDrop);

        dropSelf(MnBlocks.DEAD_WOOD);
        dropSelf(MnBlocks.DEAD_WOOD_LOG);
        dropSelf(MnBlocks.DEAD_WOOD_PLANKS);
        dropSelf(MnBlocks.STRIPPED_DEAD_WOOD);
        dropSelf(MnBlocks.STRIPPED_DEAD_WOOD_LOG);
        dropSelf(MnBlocks.DEAD_SAPLING);

        dropSelf(MnBlocks.SHADOWROOT_WOOD);
        dropSelf(MnBlocks.SHADOWROOT_LOG);
        dropSelf(MnBlocks.SHADOWROOT_PLANKS);
        dropSelf(MnBlocks.SHADOWROOT_SAPLING);
        add(MnBlocks.SHADOWROOT_LEAVES, block -> droppingWithChancesAndDarkSticks(block, MnBlocks.SHADOWROOT_SAPLING, DEFAULT_SAPLING_DROP_RATES));
        dropSelf(MnBlocks.STRIPPED_SHADOWROOT_WOOD);
        dropSelf(MnBlocks.STRIPPED_SHADOWROOT_LOG);
        dropSelf(MnBlocks.SHADOWROOT_BOOKSHELF);

        dropSelf(MnBlocks.DARK_WILLOW_WOOD);
        dropSelf(MnBlocks.DARK_WILLOW_LOG);
        dropSelf(MnBlocks.DARK_WILLOW_PLANKS);
        dropSelf(MnBlocks.DARK_WILLOW_SAPLING);
        add(MnBlocks.DARK_WILLOW_LEAVES, block -> droppingWithChancesAndDarkSticks(block, MnBlocks.DARK_WILLOW_SAPLING, DEFAULT_SAPLING_DROP_RATES));
        add(MnBlocks.HANGING_DARK_WILLOW_LEAVES, createShearsOnlyDrop(MnBlocks.HANGING_DARK_WILLOW_LEAVES));
        dropSelf(MnBlocks.STRIPPED_DARK_WILLOW_WOOD);
        dropSelf(MnBlocks.STRIPPED_DARK_WILLOW_LOG);
        dropSelf(MnBlocks.DARK_WILLOW_BOOKSHELF);

        dropSelf(MnBlocks.NIGHTSHROOM_STEM);
        dropSelf(MnBlocks.NIGHTSHROOM_PLANKS);
        dropSelf(MnBlocks.NIGHTSHROOM);
        dropSelf(MnBlocks.NIGHTSHROOM_SHELF);
        add(MnBlocks.NIGHTSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        add(MnBlocks.NIGHTSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.TALL_NIGHTSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(MnBlocks.NIGHTSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.NIGHTSHROOM_POWDER, MnBlocks.NIGHTSHROOM, MnBlocks.TALL_NIGHTSHROOM));

        dropSelf(MnBlocks.DEWSHROOM_STEM);
        dropSelf(MnBlocks.DEWSHROOM_PLANKS);
        dropSelf(MnBlocks.DEWSHROOM);
        dropSelf(MnBlocks.DEWSHROOM_SHELF);
        add(MnBlocks.DEWSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        add(MnBlocks.DEWSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.TALL_DEWSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(MnBlocks.DEWSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.DEWSHROOM_POWDER, MnBlocks.DEWSHROOM, MnBlocks.TALL_DEWSHROOM));

        dropSelf(MnBlocks.VIRIDSHROOM_STEM);
        dropSelf(MnBlocks.VIRIDSHROOM_PLANKS);
        dropSelf(MnBlocks.VIRIDSHROOM);
        dropSelf(MnBlocks.VIRIDSHROOM_SHELF);
        add(MnBlocks.VIRIDSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        add(MnBlocks.VIRIDSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.TALL_VIRIDSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(MnBlocks.VIRIDSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.VIRIDSHROOM_POWDER, MnBlocks.VIRIDSHROOM, MnBlocks.TALL_VIRIDSHROOM));

        dropSelf(MnBlocks.MOONSHROOM_STEM);
        dropSelf(MnBlocks.MOONSHROOM_PLANKS);
        dropSelf(MnBlocks.MOONSHROOM);
        dropSelf(MnBlocks.MOONSHROOM_SHELF);
        add(MnBlocks.MOONSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        add(MnBlocks.MOONSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.TALL_MOONSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(MnBlocks.MOONSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.MOONSHROOM_POWDER, MnBlocks.MOONSHROOM, MnBlocks.TALL_MOONSHROOM));

        dropSelf(MnBlocks.BOGSHROOM_STEM);
        dropSelf(MnBlocks.BOGSHROOM_PLANKS);
        dropSelf(MnBlocks.BOGSHROOM);
        dropSelf(MnBlocks.BOGSHROOM_SHELF);
        add(MnBlocks.BOGSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        add(MnBlocks.TALL_BOGSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(MnBlocks.BOGSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.BOGSHROOM_POWDER, MnBlocks.BOGSHROOM, MnBlocks.TALL_BOGSHROOM));

        dropSelf(MnBlocks.GLOB_FUNGUS);
        add(MnBlocks.GLOB_FUNGUS_CAP, block -> droppingWithChances(block, MnBlocks.GLOB_FUNGUS, DEFAULT_GLOB_DROP_RATES));
        add(MnBlocks.GLOB_FUNGUS_STEM, block -> droppingSilkTouchOrRanged(block, MnItems.GLOB_FUNGUS_HAND, 2, 4));
        add(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, block -> droppingNothing());
        add(MnBlocks.GLOB_FUNGUS_HYPHAE, block -> droppingSilkTouchOrRanged(block, MnItems.GLOB_FUNGUS_HAND, 2, 4));
        dropSelf(MnBlocks.GLOB_FUNGUS_THATCH);

        dropSelf(MnBlocks.REED_THATCH);
        dropSelf(MnBlocks.CUT_REED_THATCH);

        dropSelf(MnBlocks.MISTSHROOM);
        add(MnBlocks.TALL_MISTSHROOM, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf(MnBlocks.BRISTLY_GRASS);
        dropSelf(MnBlocks.LUMEN_BUD);
        add(MnBlocks.TALL_LUMEN_BUD, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf(MnBlocks.RUNEBUSH);
        dropSelf(MnBlocks.BOGWEED);
        dropSelf(MnBlocks.CRYSTALOTUS);
        add(MnBlocks.SUAVIS, MnBlockLootTables::droppingSuavis);
        add(MnBlocks.VIOLEAF, BlockLootTables::createShearsOnlyDrop);
        dropSelf(MnBlocks.TENDRILWEED);
        dropSelf(MnBlocks.NIGHT_REED);
        add(MnBlocks.DECEITFUL_MOSS, BlockLootTables::createShearsOnlyDrop);
        add(MnBlocks.DECEITFUL_ALGAE, BlockLootTables::createShearsOnlyDrop);

        add(MnBlocks.ROCKSHROOM, block -> droppingSilkTouchOrRanged(block, MnItems.ROCKSHROOM_CLUMP, 2, 3));

        dropSelf(MnBlocks.ROUXE_ROCK);
        dropSelf(MnBlocks.BLOOMCRYSTAL_ROCK);
        dropSelf(MnBlocks.ROUXE);
        dropSelf(MnBlocks.BLOOMCRYSTAL);
        dropSelf(MnBlocks.CRYSTAL_FLOWER);

        add(MnBlocks.DARK_PEARL_ORE, block -> createOreDrop(block, MnItems.GEODE));
        dropSelf(MnBlocks.DARK_PEARL_BLOCK);

        dropWhenSilkTouch(MnBlocks.STRANGE_GLASS);
        dropWhenSilkTouch(MnBlocks.STRANGE_GLASS_PANE);

        add(MnBlocks.ARCHAIC_ORE, block -> createOreDrop(block, MnItems.ARCHAIC_SHARD));
        dropWhenSilkTouch(MnBlocks.ARCHAIC_GLASS);
        dropWhenSilkTouch(MnBlocks.ARCHAIC_GLASS_PANE);

        dropSelf(MnBlocks.TENEBRUM_ORE);
        dropSelf(MnBlocks.TENEBRUM_BLOCK);

        dropSelf(MnBlocks.NAGRILITE_ORE);
        dropSelf(MnBlocks.NAGRILITE_BLOCK);

        add(MnBlocks.EBONITE_ORE, block -> createOreDrop(block, MnItems.EBONITE));
        dropSelf(MnBlocks.EBONITE_BLOCK);

        add(MnBlocks.VIRILUX_ORE, block -> createOreDrop(block, MnItems.VIRILUX));
        dropSelf(MnBlocks.VIRILUX_BLOCK);
    }

    protected static LootTable.Builder droppingNothing() {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(0)));
    }

    protected static LootTable.Builder droppingSuavis(Block block) {
        StatePropertiesPredicate.Builder suavisStage1 = StatePropertiesPredicate.Builder
                                                            .properties()
                                                            .hasProperty(SuavisBlock.STAGE, 1);
        StatePropertiesPredicate.Builder suavisStage2 = StatePropertiesPredicate.Builder
                                                            .properties()
                                                            .hasProperty(SuavisBlock.STAGE, 2);
        StatePropertiesPredicate.Builder suavisStage3 = StatePropertiesPredicate.Builder
                                                            .properties()
                                                            .hasProperty(SuavisBlock.STAGE, 3);
        return createSilkTouchOrShearsDispatchTable(
            block,
            applyExplosionDecay(block, ItemLootEntry.lootTableItem(MnItems.RAW_SUAVIS))
                .apply(
                    SetCount.setCount(ConstantRange.exactly(2))
                            .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(suavisStage1))
                )
                .apply(
                    SetCount.setCount(ConstantRange.exactly(3))
                            .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(suavisStage2))
                )
                .apply(
                    SetCount.setCount(ConstantRange.exactly(4))
                            .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(suavisStage3))
                )
                .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.3f, 3))
        );
    }

    protected static LootTable.Builder droppingWithChances(Block block, IItemProvider drop, float... saplingBonus) {
        return createSilkTouchOrShearsDispatchTable(
            block,
            applyExplosionCondition(block, ItemLootEntry.lootTableItem(drop))
                .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, saplingBonus))
        );
    }

    protected static LootTable.Builder droppingSilkTouchOrRanged(Block block, IItemProvider item, int min, int max) {
        return createSilkTouchDispatchTable(
            block,
            applyExplosionCondition(block, ItemLootEntry.lootTableItem(item))
                .apply(SetCount.setCount(RandomValueRange.between(min, max)))
                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
        );
    }

    protected static LootTable.Builder droppingWithChancesAndDarkSticks(Block leaves, Block sapling, float... saplingBonus) {
        return createSilkTouchOrShearsDispatchTable(
            leaves,
            applyExplosionCondition(leaves, ItemLootEntry.lootTableItem(sapling))
                .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, saplingBonus))
        ).withPool(
            LootPool.lootPool()
                    .setRolls(ConstantRange.exactly(1))
                    .when(NOT_SILK_TOUCH_OR_SHEARS)
                    .add(
                        applyExplosionDecay(
                            leaves,
                            ItemLootEntry.lootTableItem(MnItems.DARK_STICK)
                                         .apply(SetCount.setCount(RandomValueRange.between(1, 2)))
                        ).when(
                            TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 1 / 50F, 1 / 45F, 1 / 40F, 1 / 30F, 1 / 10F)
                        )
                    )
        );
    }

    protected static LootTable.Builder droppingShroomOrPowder(Block block, IItemProvider item, IItemProvider shroom, IItemProvider tall_shroom) {
        return createSilkTouchDispatchTable(
            block,
            applyExplosionCondition(
                block,
                AlternativesLootEntry.alternatives(ItemLootEntry.lootTableItem(item).when(RandomChance.randomChance(1 / 2F)))
                                     .otherwise(ItemLootEntry.lootTableItem(shroom).when(RandomChance.randomChance(2 / 3F)))
                                     .otherwise(ItemLootEntry.lootTableItem(tall_shroom).when(RandomChance.randomChance(1 / 2F)))
            )
        );
    }

    protected static LootTable.Builder droppingFibre(Block block) {
        return LootTable.lootTable().withPool(
            LootPool.lootPool().add(
                ItemLootEntry.lootTableItem(block)
                             .apply(
                                 SetCount.setCount(ConstantRange.exactly(2))
                                         .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(
                                             StatePropertiesPredicate.Builder.properties().hasProperty(
                                                 MnBlockStateProperties.DENSE,
                                                 true
                                             )
                                         ))
                             )
                             .when(
                                 SILK_TOUCH_OR_SHEARS
                             )
            )
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    protected Iterable<Block> getKnownBlocks() {
        return Registry.BLOCK
                   .stream()
                   .filter(block -> {
                       ResourceLocation loc = block.getRegistryName();
                       assert loc != null;
                       return loc.getNamespace().equals("midnight");
                   })
                   .distinct()
                   ::iterator;
    }
}
