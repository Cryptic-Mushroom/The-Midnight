package com.mushroom.midnight.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mushroom.midnight.common.entity.projectile.CrystalBulbSpearEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CrystalBulbSpearItem extends TridentItem {
    public CrystalBulbSpearItem(Item.Properties builder) {
        super(builder);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 10) {
                int j = EnchantmentHelper.getRiptideModifier(stack);
                if (j <= 0 || playerentity.isWet()) {
                    if (!worldIn.isRemote) {
                        stack.damageItem(1, playerentity, (p_220047_1_) -> {
                            p_220047_1_.sendBreakAnimation(entityLiving.getActiveHand());
                        });
                        if (j == 0) {
                            CrystalBulbSpearEntity tridententity = new CrystalBulbSpearEntity(worldIn, playerentity, stack);
                            tridententity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 2.5F + (float) j * 0.5F, 1.0F);
                            if (playerentity.abilities.isCreativeMode) {
                                tridententity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            }

                            worldIn.addEntity(tridententity);
                            worldIn.playMovingSound((PlayerEntity) null, tridententity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerentity.abilities.isCreativeMode) {
                                playerentity.inventory.deleteStack(stack);
                            }
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                    if (j > 0) {
                        float f7 = playerentity.rotationYaw;
                        float f = playerentity.rotationPitch;
                        float f1 = -MathHelper.sin(f7 * ((float) Math.PI / 180F)) * MathHelper.cos(f * ((float) Math.PI / 180F));
                        float f2 = -MathHelper.sin(f * ((float) Math.PI / 180F));
                        float f3 = MathHelper.cos(f7 * ((float) Math.PI / 180F)) * MathHelper.cos(f * ((float) Math.PI / 180F));
                        float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                        float f5 = 3.0F * ((1.0F + (float) j) / 4.0F);
                        f1 = f1 * (f5 / f4);
                        f2 = f2 * (f5 / f4);
                        f3 = f3 * (f5 / f4);
                        playerentity.addVelocity((double) f1, (double) f2, (double) f3);
                        playerentity.startSpinAttack(20);
                        if (playerentity.onGround) {
                            float f6 = 1.1999999F;
                            playerentity.move(MoverType.SELF, new Vec3d(0.0D, (double) 1.1999999F, 0.0D));
                        }

                        SoundEvent soundevent;
                        if (j >= 3) {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (j == 2) {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        worldIn.playMovingSound((PlayerEntity) null, playerentity, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 4.0D, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) -2.3F, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public int getItemEnchantability() {
        return 1;
    }
}