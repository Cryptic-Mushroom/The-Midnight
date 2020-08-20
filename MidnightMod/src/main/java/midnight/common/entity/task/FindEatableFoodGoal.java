package midnight.common.entity.task;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class FindEatableFoodGoal extends Goal {
    private final MobEntity mobEntity;
    private final Predicate<ItemStack> itemStack;
    private final Predicate<ItemEntity> canPickUp = (item) -> {
        return !item.cannotPickup() && getCanEatItem(item.getItem()) && item.isAlive();
    };
    private final double speed;

    public FindEatableFoodGoal(MobEntity entity, Predicate<ItemStack> stack, double speed) {
        this.mobEntity = entity;
        this.itemStack = stack;
        this.speed = speed;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if(mobEntity.getAttackTarget() == null && mobEntity.getRevengeTarget() == null) {
            if(mobEntity.getRNG().nextInt(10) != 0) {
                return false;
            } else if(mobEntity instanceof TameableEntity && ((TameableEntity) mobEntity).isTamed()) {
                return false;
            } else {
                List<ItemEntity> list = mobEntity.world.getEntitiesWithinAABB(ItemEntity.class, mobEntity.getBoundingBox().grow(8.0D, 8.0D, 8.0D), canPickUp);
                return !list.isEmpty();
            }
        } else {
            return false;
        }
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        List<ItemEntity> list = mobEntity.world.getEntitiesWithinAABB(ItemEntity.class, mobEntity.getBoundingBox().grow(8.0D, 8.0D, 8.0D), canPickUp);
        if(!list.isEmpty()) {
            mobEntity.getNavigator().tryMoveToEntityLiving(list.get(0), (double) speed);
        }

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        List<ItemEntity> list = mobEntity.world.getEntitiesWithinAABB(ItemEntity.class, mobEntity.getBoundingBox().grow(8.0D, 8.0D, 8.0D), canPickUp);
        if(!list.isEmpty()) {
            mobEntity.getNavigator().tryMoveToEntityLiving(list.get(0), (double) speed);
        }

    }

    private boolean getCanEatItem(ItemStack item) {
        return itemStack.test(item);
    }
}