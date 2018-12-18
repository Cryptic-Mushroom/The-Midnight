package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.MessageItemActivation;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemRawDeceitfulSnapper extends ItemFoodBasic {
    private static final DamageSource DECEITFUL_SNAPPER_DAMAGE = new MidnightDamageSource("deceitful_snapper");

    public ItemRawDeceitfulSnapper() {
        super(6, 0.6f, false);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, @Nullable EntityLivingBase entity) {
        if (!world.isRemote && entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            if (player.world.rand.nextInt(3) == 0) {
                PotionEffect eff = player.getActivePotionEffect(MobEffects.SLOWNESS);
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 5, false, true));
                player.attackEntityFrom(DECEITFUL_SNAPPER_DAMAGE, player.world.rand.nextInt(3) + 1);
                player.sendStatusMessage(new TextComponentTranslation("status.midnight.snapped"), true);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_FANGS_ATTACK, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                ModCriterion.SNAPPED_BY_SNAPPER.trigger(player);
                Midnight.NETWORK.sendTo(new MessageItemActivation(stack), player);
            } else {
                player.getFoodStats().addStats(this, stack);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                onFoodEaten(stack, world, player);
                stack.shrink(1);
                player.addStat(StatList.getObjectUseStats(this));
                CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            }
        }
        return stack;
    }
}
