package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.entity.EntityBladeshroomCap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBladeshroomCap extends Item implements IModelProvider {
    public ItemBladeshroomCap() {
        this.maxStackSize = 16;
        this.setCreativeTab(Midnight.MIDNIGHT_ITEMS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        CooldownTracker cd = player.getCooldownTracker();
        if (cd.hasCooldown(this)) { return super.onItemRightClick(world, player, hand); }
        cd.setCooldown(this, 10);
        ItemStack heldItem = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            EntityBladeshroomCap entity = new EntityBladeshroomCap(world, player);
            entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(entity);
        }

        player.addStat(StatList.getObjectUseStats(this));

        return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
    }
}
