package midnight.common.entity.util;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.Random;

public class FlyingTargetGenerator implements IRandomTargetGenerator {
    private final CreatureEntity owner;

    private final int minRange;
    private final int maxRange;

    private final int minHeight;
    private final int maxHeight;

    public FlyingTargetGenerator(CreatureEntity owner, int minRange, int maxRange, int minHeight, int maxHeight) {
        this.owner = owner;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Nullable
    @Override
    public BlockPos generate(@Nullable BlockPos anchor) {
        // Adapted from RandomPositionGenerator

        Random random = this.owner.getRNG();

        BlockPos result = null;
        float maxWeight = -Float.MIN_VALUE;

        for (int i = 0; i < 10; i++) {
            int displacementX = this.generateCoordinate(random);
            int displacementZ = this.generateCoordinate(random);

            if (anchor != null) {
                if (this.owner.getPosX() > anchor.getX()) {
                    displacementX -= random.nextInt(this.maxRange / 2);
                } else {
                    displacementX += random.nextInt(this.maxRange / 2);
                }

                if (this.owner.getPosZ() > anchor.getZ()) {
                    displacementZ -= random.nextInt(this.maxRange / 2);
                } else {
                    displacementZ += random.nextInt(this.maxRange / 2);
                }
            }

            BlockPos displacedPos = new BlockPos(displacementX + this.owner.getPosX(), 0, displacementZ + this.owner.getPosZ());
            BlockPos target = this.generateTarget(random, displacedPos);

            float weight = this.owner.getBlockPathWeight(target);
            if (weight > maxWeight) {
                maxWeight = weight;
                result = target;
            }
        }

        return result;
    }

    private BlockPos generateTarget(Random random, BlockPos displacedPos) {
        int flightHeight = random.nextInt(this.maxHeight - this.minHeight + 1) + this.minHeight;
        BlockPos surface = this.owner.world.getHeight(Heightmap.Type.MOTION_BLOCKING, displacedPos);
        return surface.up(flightHeight);
    }

    private int generateCoordinate(Random random) {
        return (random.nextBoolean() ? 1 : -1) * (random.nextInt(this.maxRange - this.minRange + 1) + this.minRange);
    }
}
