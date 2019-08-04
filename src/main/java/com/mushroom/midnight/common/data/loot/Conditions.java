package com.mushroom.midnight.common.data.loot;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;

public final class Conditions {
    public static final ILootCondition.IBuilder SURVIVES_EXPLOSION = SurvivesExplosion.builder();

    public static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.builder(
            ItemPredicate.Builder.create()
                    .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
    );

    public static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.builder(
            ItemPredicate.Builder.create().item(Items.SHEARS)
    );

    public static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.alternative(HAS_SILK_TOUCH);

    public static final ILootCondition.IBuilder NOT_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.inverted();
}
