package midnight.common.entity.task;

import midnight.common.entity.SkulkEntity;
import midnight.common.sound.MnSoundEvents;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class StealFoodGoal extends Goal {
    private final SkulkEntity mobEntity;
    private final double speed;
    private final float chance;
    private PlayerEntity targetEntity;
    private int cooldown;

    private final Predicate<PlayerEntity> canSteal = (player) -> {
        return !player.isCreative() && !player.isSpectator() && player.isAlive();
    };

    public StealFoodGoal(SkulkEntity mobEntity, double speed, float chance) {
        this.mobEntity = mobEntity;
        this.speed = speed;
        this.chance = chance;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if(mobEntity.getAttackTarget() == null && mobEntity.getRevengeTarget() == null) {
            if(mobEntity.getRNG().nextFloat() >= chance) {
                return false;
            } else if(!mobEntity.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
                return false;
            } else if(mobEntity.isTamed()) {
                return false;
            } else {
                List<PlayerEntity> list = mobEntity.world.getEntitiesWithinAABB(PlayerEntity.class, mobEntity.getBoundingBox().grow(12.0D, 8.0D, 12.0D), canSteal);

                if(!list.isEmpty()) {
                    targetEntity = list.get(0);
                }

                return targetEntity != null && mobEntity.canEntityBeSeen(this.targetEntity) && SkulkEntity.canEatFood.test(this.targetEntity.getHeldItem(Hand.MAIN_HAND));
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return targetEntity != null && targetEntity.isAlive() && this.mobEntity.isAlive() && this.mobEntity.getDistanceSq(this.targetEntity) < 36 && SkulkEntity.canEatFood.test(this.targetEntity.getHeldItem(Hand.MAIN_HAND));
    }

    @Override
    public void tick() {
        super.tick();
        if(targetEntity != null && targetEntity.isAlive()) {
            mobEntity.getNavigator().tryMoveToEntityLiving(targetEntity, (double) speed);

            if(this.mobEntity.getDistanceSq(this.targetEntity) < 3) {
                if(--cooldown <= 0) {
                    this.stealFood();
                }
            }
        }
    }

    private void stealFood() {
        ItemStack stack = this.targetEntity.getHeldItem(Hand.MAIN_HAND).copy();
        this.cooldown = 40;
        this.mobEntity.playSound(MnSoundEvents.SKULK_GRAB, 0.8F, 1.0F);
        this.targetEntity.dropItem(stack, false, false);
        this.targetEntity.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
    }
}
