/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.item.Item.Properties;

public class ThrowableItem extends Item {
    private final Supplier<SoundEvent> throwSound;
    private final BiFunction<? super PlayerEntity, ? super World, ? extends ProjectileItemEntity> throwableFactory;

    public ThrowableItem(Properties props, Supplier<SoundEvent> throwSound, BiFunction<? super PlayerEntity, ? super World, ? extends ProjectileItemEntity> throwableFactory) {
        super(props);
        this.throwSound = throwSound;
        this.throwableFactory = throwableFactory;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        world.playSound(
            null,
            player.getX(), player.getY(), player.getZ(),
            throwSound.get(),
            SoundCategory.PLAYERS,
            0.5f, 0.4f / (world.random.nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClientSide) {
            ProjectileItemEntity entity = throwableFactory.apply(player, world);
            entity.setItem(stack);
            entity.shootFromRotation(player, player.xRot, player.yRot, 0, 1.5f, 1);
            world.addFreshEntity(entity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.abilities.instabuild) {
            stack.shrink(1);
        }

        return ActionResult.sidedSuccess(stack, world.isClientSide());
    }
}
