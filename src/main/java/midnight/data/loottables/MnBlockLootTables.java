/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 27
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
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
    private static final float[] DEFAULT_SAPLING_DROP_RATES = {1 / 20F, 1 / 16F, 1 / 12F, 1 / 10F};
    private static final float[] DEFAULT_GLOB_DROP_RATES = {1 / 5F, 1 / 3F, 2 / 3F, 1};

    @Override
    protected void addTables() {
        registerDropSelfLootTable(MnBlocks.NIGHTSTONE);
        registerDropSelfLootTable(MnBlocks.TRENCHSTONE);
        registerLootTable(MnBlocks.NIGHT_BEDROCK, block -> droppingNothing());

        registerDropSelfLootTable(MnBlocks.NIGHT_DIRT);
        registerLootTable(MnBlocks.NIGHT_GRASS_BLOCK, block -> droppingWithSilkTouch(block, MnBlocks.NIGHT_DIRT));
        registerDropSelfLootTable(MnBlocks.DECEITFUL_PEAT);
        registerDropSelfLootTable(MnBlocks.DECEITFUL_MUD);
        registerDropSelfLootTable(MnBlocks.STRANGE_SAND);
        registerDropSelfLootTable(MnBlocks.COARSE_NIGHT_DIRT);
        registerLootTable(MnBlocks.NIGHT_MYCELIUM, block -> droppingWithSilkTouch(block, MnBlocks.NIGHTSTONE));

        registerDropSelfLootTable(MnBlocks.NIGHTSTONE_BRICKS);
        registerDropSelfLootTable(MnBlocks.TRENCHSTONE_BRICKS);
        registerDropSelfLootTable(MnBlocks.SHROOMBRICKS);

        registerDropSelfLootTable(MnBlocks.GHOST_PLANT_LEAF);
        registerDropSelfLootTable(MnBlocks.GHOST_PLANT_STEM);
        registerDropSelfLootTable(MnBlocks.GHOST_PLANT);

        registerLootTable(MnBlocks.NIGHT_GRASS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.TALL_NIGHT_GRASS, BlockLootTables::onlyWithShears);

        registerDropSelfLootTable(MnBlocks.DEAD_WOOD);
        registerDropSelfLootTable(MnBlocks.DEAD_WOOD_LOG);
        registerDropSelfLootTable(MnBlocks.DEAD_WOOD_PLANKS);
        registerDropSelfLootTable(MnBlocks.STRIPPED_DEAD_WOOD);
        registerDropSelfLootTable(MnBlocks.STRIPPED_DEAD_WOOD_LOG);
        registerDropSelfLootTable(MnBlocks.DEAD_SAPLING);

        registerDropSelfLootTable(MnBlocks.SHADOWROOT_WOOD);
        registerDropSelfLootTable(MnBlocks.SHADOWROOT_LOG);
        registerDropSelfLootTable(MnBlocks.SHADOWROOT_PLANKS);
        registerDropSelfLootTable(MnBlocks.SHADOWROOT_SAPLING);
        registerLootTable(MnBlocks.SHADOWROOT_LEAVES, block -> droppingWithChancesAndDarkSticks(block, MnBlocks.SHADOWROOT_SAPLING, DEFAULT_SAPLING_DROP_RATES));
        registerDropSelfLootTable(MnBlocks.STRIPPED_SHADOWROOT_WOOD);
        registerDropSelfLootTable(MnBlocks.STRIPPED_SHADOWROOT_LOG);
        registerDropSelfLootTable(MnBlocks.SHADOWROOT_BOOKSHELF);

        registerDropSelfLootTable(MnBlocks.DARK_WILLOW_WOOD);
        registerDropSelfLootTable(MnBlocks.DARK_WILLOW_LOG);
        registerDropSelfLootTable(MnBlocks.DARK_WILLOW_PLANKS);
        registerDropSelfLootTable(MnBlocks.DARK_WILLOW_SAPLING);
        registerLootTable(MnBlocks.DARK_WILLOW_LEAVES, block -> droppingWithChancesAndDarkSticks(block, MnBlocks.DARK_WILLOW_SAPLING, DEFAULT_SAPLING_DROP_RATES));
        registerLootTable(MnBlocks.HANGING_DARK_WILLOW_LEAVES, onlyWithShears(MnBlocks.HANGING_DARK_WILLOW_LEAVES));
        registerDropSelfLootTable(MnBlocks.STRIPPED_DARK_WILLOW_WOOD);
        registerDropSelfLootTable(MnBlocks.STRIPPED_DARK_WILLOW_LOG);
        registerDropSelfLootTable(MnBlocks.DARK_WILLOW_BOOKSHELF);

        registerDropSelfLootTable(MnBlocks.NIGHTSHROOM_STEM);
        registerDropSelfLootTable(MnBlocks.NIGHTSHROOM_PLANKS);
        registerDropSelfLootTable(MnBlocks.NIGHTSHROOM);
        registerDropSelfLootTable(MnBlocks.NIGHTSHROOM_SHELF);
        registerLootTable(MnBlocks.NIGHTSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        registerLootTable(MnBlocks.NIGHTSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.TALL_NIGHTSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(MnBlocks.NIGHTSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.NIGHTSHROOM_POWDER, MnBlocks.NIGHTSHROOM, MnBlocks.TALL_NIGHTSHROOM));

        registerDropSelfLootTable(MnBlocks.DEWSHROOM_STEM);
        registerDropSelfLootTable(MnBlocks.DEWSHROOM_PLANKS);
        registerDropSelfLootTable(MnBlocks.DEWSHROOM);
        registerDropSelfLootTable(MnBlocks.DEWSHROOM_SHELF);
        registerLootTable(MnBlocks.DEWSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        registerLootTable(MnBlocks.DEWSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.FLOWERING_DEWSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.TALL_DEWSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(MnBlocks.DEWSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.DEWSHROOM_POWDER, MnBlocks.DEWSHROOM, MnBlocks.TALL_DEWSHROOM));

        registerDropSelfLootTable(MnBlocks.VIRIDSHROOM_STEM);
        registerDropSelfLootTable(MnBlocks.VIRIDSHROOM_PLANKS);
        registerDropSelfLootTable(MnBlocks.VIRIDSHROOM);
        registerDropSelfLootTable(MnBlocks.VIRIDSHROOM_SHELF);
        registerLootTable(MnBlocks.VIRIDSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        registerLootTable(MnBlocks.VIRIDSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.TALL_VIRIDSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(MnBlocks.VIRIDSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.VIRIDSHROOM_POWDER, MnBlocks.VIRIDSHROOM, MnBlocks.TALL_VIRIDSHROOM));

        registerDropSelfLootTable(MnBlocks.MOONSHROOM_STEM);
        registerDropSelfLootTable(MnBlocks.MOONSHROOM_PLANKS);
        registerDropSelfLootTable(MnBlocks.MOONSHROOM);
        registerDropSelfLootTable(MnBlocks.MOONSHROOM_SHELF);
        registerLootTable(MnBlocks.MOONSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        registerLootTable(MnBlocks.MOONSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.FLOWERING_MOONSHROOM_ROOTS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.TALL_MOONSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(MnBlocks.MOONSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.MOONSHROOM_POWDER, MnBlocks.MOONSHROOM, MnBlocks.TALL_MOONSHROOM));

        registerDropSelfLootTable(MnBlocks.BOGSHROOM_STEM);
        registerDropSelfLootTable(MnBlocks.BOGSHROOM_PLANKS);
        registerDropSelfLootTable(MnBlocks.BOGSHROOM);
        registerDropSelfLootTable(MnBlocks.BOGSHROOM_SHELF);
        registerLootTable(MnBlocks.BOGSHROOM_FIBRE, MnBlockLootTables::droppingFibre);
        registerLootTable(MnBlocks.TALL_BOGSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(MnBlocks.BOGSHROOM_CAP, block -> droppingShroomOrPowder(block, MnItems.BOGSHROOM_POWDER, MnBlocks.BOGSHROOM, MnBlocks.TALL_BOGSHROOM));

        registerDropSelfLootTable(MnBlocks.GLOB_FUNGUS);
        registerLootTable(MnBlocks.GLOB_FUNGUS_CAP, block -> droppingWithChances(block, MnBlocks.GLOB_FUNGUS, DEFAULT_GLOB_DROP_RATES));
        registerLootTable(MnBlocks.GLOB_FUNGUS_STEM, block -> droppingSilkTouchOrRanged(block, MnItems.GLOB_FUNGUS_HAND, 2, 4));
        registerLootTable(MnBlocks.INFESTED_GLOB_FUNGUS_STEM, block -> droppingNothing());
        registerLootTable(MnBlocks.GLOB_FUNGUS_HYPHAE, block -> droppingSilkTouchOrRanged(block, MnItems.GLOB_FUNGUS_HAND, 2, 4));
        registerDropSelfLootTable(MnBlocks.GLOB_FUNGUS_THATCH);

        registerDropSelfLootTable(MnBlocks.REED_THATCH);
        registerDropSelfLootTable(MnBlocks.CUT_REED_THATCH);

        registerDropSelfLootTable(MnBlocks.MISTSHROOM);
        registerLootTable(MnBlocks.TALL_MISTSHROOM, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerDropSelfLootTable(MnBlocks.FINGERED_GRASS);
        registerDropSelfLootTable(MnBlocks.LUMEN_BUD);
        registerLootTable(MnBlocks.TALL_LUMEN_BUD, block -> droppingWhen(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerDropSelfLootTable(MnBlocks.RUNEBUSH);
        registerDropSelfLootTable(MnBlocks.BOGWEED);
        registerDropSelfLootTable(MnBlocks.CRYSTALOTUS);
        registerLootTable(MnBlocks.SUAVIS, MnBlockLootTables::droppingSuavis);
        registerLootTable(MnBlocks.VIOLEAF, BlockLootTables::onlyWithShears);
        registerDropSelfLootTable(MnBlocks.TENDRILWEED);
        registerDropSelfLootTable(MnBlocks.NIGHT_REED);
        registerLootTable(MnBlocks.DECEITFUL_MOSS, BlockLootTables::onlyWithShears);
        registerLootTable(MnBlocks.DECEITFUL_ALGAE, BlockLootTables::onlyWithShears);

        registerLootTable(MnBlocks.ROCKSHROOM, block -> droppingSilkTouchOrRanged(block, MnItems.ROCKSHROOM_CLUMP, 2, 3));

        registerDropSelfLootTable(MnBlocks.ROUXE_ROCK);
        registerDropSelfLootTable(MnBlocks.BLOOMCRYSTAL_ROCK);
        registerDropSelfLootTable(MnBlocks.ROUXE);
        registerDropSelfLootTable(MnBlocks.BLOOMCRYSTAL);
        registerDropSelfLootTable(MnBlocks.CRYSTAL_FLOWER);

        registerLootTable(MnBlocks.DARK_PEARL_ORE, block -> droppingItemWithFortune(block, MnItems.GEODE));
        registerDropSelfLootTable(MnBlocks.DARK_PEARL_BLOCK);

        registerSilkTouch(MnBlocks.STRANGE_GLASS);
        registerSilkTouch(MnBlocks.STRANGE_GLASS_PANE);

        registerLootTable(MnBlocks.ARCHAIC_ORE, block -> droppingItemWithFortune(block, MnItems.ARCHAIC_SHARD));
        registerSilkTouch(MnBlocks.ARCHAIC_GLASS);
        registerSilkTouch(MnBlocks.ARCHAIC_GLASS_PANE);

        registerDropSelfLootTable(MnBlocks.TENEBRUM_ORE);
        registerDropSelfLootTable(MnBlocks.TENEBRUM_BLOCK);

        registerDropSelfLootTable(MnBlocks.NAGRILITE_ORE);
        registerDropSelfLootTable(MnBlocks.NAGRILITE_BLOCK);

        registerLootTable(MnBlocks.EBONITE_ORE, block -> droppingItemWithFortune(block, MnItems.EBONITE));
        registerDropSelfLootTable(MnBlocks.EBONITE_BLOCK);

        registerLootTable(MnBlocks.VIRILUX_ORE, block -> droppingItemWithFortune(block, MnItems.VIRILUX));
        registerDropSelfLootTable(MnBlocks.VIRILUX_BLOCK);

        registerDropSelfLootTable(MnBlocks.CORE);
        registerLootTable(MnBlocks.ACTIVE_CORE, block -> dropping(MnBlocks.DEAD_CORE));
        registerDropSelfLootTable(MnBlocks.DEAD_CORE);
        registerDropSelfLootTable(MnBlocks.ACTIVE_NIGHTSTONE);
        registerLootTable(MnBlocks.DETORIATED_TRENCHSTONE, block -> dropping(MnBlocks.TRENCHSTONE));
    }

    protected static LootTable.Builder droppingNothing() {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(0)));
    }

    protected static LootTable.Builder droppingSuavis(Block block) {
        StatePropertiesPredicate.Builder suavisStage1 = StatePropertiesPredicate.Builder
                                                            .create()
                                                            .exactMatch(SuavisBlock.STAGE, 1);
        StatePropertiesPredicate.Builder suavisStage2 = StatePropertiesPredicate.Builder
                                                            .create()
                                                            .exactMatch(SuavisBlock.STAGE, 2);
        StatePropertiesPredicate.Builder suavisStage3 = StatePropertiesPredicate.Builder
                                                            .create()
                                                            .exactMatch(SuavisBlock.STAGE, 3);
        return droppingWithSilkTouchOrShears(
            block,
            withExplosionDecay(block, ItemLootEntry.builder(MnItems.RAW_SUAVIS))
                .acceptFunction(
                    SetCount.builder(ConstantRange.of(2))
                            .acceptCondition(BlockStateProperty.builder(block).properties(suavisStage1))
                )
                .acceptFunction(
                    SetCount.builder(ConstantRange.of(3))
                            .acceptCondition(BlockStateProperty.builder(block).properties(suavisStage2))
                )
                .acceptFunction(
                    SetCount.builder(ConstantRange.of(4))
                            .acceptCondition(BlockStateProperty.builder(block).properties(suavisStage3))
                )
                .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.3f, 3))
        );
    }

    protected static LootTable.Builder droppingWithChances(Block block, IItemProvider drop, float... saplingBonus) {
        return droppingWithSilkTouchOrShears(
            block,
            withSurvivesExplosion(block, ItemLootEntry.builder(drop))
                .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, saplingBonus))
        );
    }

    protected static LootTable.Builder droppingSilkTouchOrRanged(Block block, IItemProvider item, int min, int max) {
        return droppingWithSilkTouch(
            block,
            withSurvivesExplosion(block, ItemLootEntry.builder(item))
                .acceptFunction(SetCount.builder(RandomValueRange.of(min, max)))
                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, 1))
        );
    }

    protected static LootTable.Builder droppingWithChancesAndDarkSticks(Block leaves, Block sapling, float... saplingBonus) {
        return droppingWithSilkTouchOrShears(
            leaves,
            withSurvivesExplosion(leaves, ItemLootEntry.builder(sapling))
                .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, saplingBonus))
        ).addLootPool(
            LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .acceptCondition(NOT_SILK_TOUCH_OR_SHEARS)
                    .addEntry(
                        withExplosionDecay(
                            leaves,
                            ItemLootEntry.builder(MnItems.DARK_STICK)
                                         .acceptFunction(SetCount.builder(RandomValueRange.of(1, 2)))
                        ).acceptCondition(
                            TableBonus.builder(Enchantments.FORTUNE, 1 / 50F, 1 / 45F, 1 / 40F, 1 / 30F, 1 / 10F)
                        )
                    )
        );
    }

    protected static LootTable.Builder droppingShroomOrPowder(Block block, IItemProvider item, IItemProvider shroom, IItemProvider tall_shroom) {
        return droppingWithSilkTouch(
            block,
            withSurvivesExplosion(
                block,
                AlternativesLootEntry.builder(ItemLootEntry.builder(item).acceptCondition(RandomChance.builder(1 / 2F)))
                                     .alternatively(ItemLootEntry.builder(shroom).acceptCondition(RandomChance.builder(2 / 3F)))
                                     .alternatively(ItemLootEntry.builder(tall_shroom).acceptCondition(RandomChance.builder(1 / 2F)))
            )
        );
    }

    protected static LootTable.Builder droppingFibre(Block block) {
        return LootTable.builder().addLootPool(
            LootPool.builder().addEntry(
                ItemLootEntry.builder(block)
                             .acceptFunction(
                                 SetCount.builder(ConstantRange.of(2))
                                         .acceptCondition(BlockStateProperty.builder(block).properties(
                                             StatePropertiesPredicate.Builder.create().exactMatch(
                                                 MnBlockStateProperties.DENSE,
                                                 true
                                             )
                                         ))
                             )
                             .acceptCondition(
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
