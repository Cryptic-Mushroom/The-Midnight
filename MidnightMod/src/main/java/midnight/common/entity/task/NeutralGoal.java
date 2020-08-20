package midnight.common.entity.task;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.Difficulty;

import java.util.EnumSet;

public class NeutralGoal extends Goal {
    private final CreatureEntity creature;
    private final Goal task;
    private final boolean isTaskForPeaceful;

    public NeutralGoal(CreatureEntity creature, Goal task, boolean isTaskForPeaceful) {
        this.creature = creature;
        this.task = task;
        this.isTaskForPeaceful = isTaskForPeaceful;
    }

    @Override
    public void startExecuting() {
        task.startExecuting();
    }

    @Override
    public boolean shouldExecute() {
        return canProceed() && task.shouldExecute();
    }

    @Override
    public void tick() {
        task.tick();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return canProceed() && task.shouldContinueExecuting();
    }

    @Override
    public boolean isPreemptible() {
        return task.isPreemptible();
    }

    @Override
    public void resetTask() {
        task.resetTask();
    }

    @Override
    public void setMutexFlags(EnumSet<Flag> flags) {
        task.setMutexFlags(flags);
    }

    @Override
    public EnumSet<Flag> getMutexFlags() {
        return task.getMutexFlags();
    }

    private boolean canProceed() {
        return creature != null && (creature.world.getWorldInfo().getDifficulty() == Difficulty.PEACEFUL) == isTaskForPeaceful;
    }
}
