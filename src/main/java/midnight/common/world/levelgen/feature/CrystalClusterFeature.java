package midnight.common.world.levelgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class CrystalClusterFeature extends Feature<CrystalClusterFeatureConfig> {
	
	private final int radius;
	private final int maxHeight;

	public CrystalClusterFeature(Codec<CrystalClusterFeatureConfig> codec, int radius, int maxHeight) {		
		super(codec);
		this.radius = radius;
		this.maxHeight = maxHeight;
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos,
			CrystalClusterFeatureConfig config) {
		int size = (this.radius * 2) + 1;

        int[] heights = new int[size * size];
        BlockPos basePos = this.populateHeights(world, rand, pos, heights, size);
        
        if (basePos == null || !this.canGenerate(world, pos, heights, size)) {
            return false;
        }

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for (int localZ = -this.radius; localZ <= this.radius; localZ++) {
            for (int localX = -this.radius; localX <= this.radius; localX++) {
                int height = heights[(localX + this.radius) + (localZ + this.radius) * size];
                if (height > 0) {
                    mutablePos.set(basePos.getX() + localX, basePos.getY(), basePos.getZ() + localZ);
                    this.generatePillar(world, rand, mutablePos, height, config);
                }
            }
        }

        return true;
	}
	
	private boolean canGenerate(ISeedReader world, BlockPos origin, int[] heights, int size) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable().set(origin);
        int centerHeight = heights[this.radius + this.radius * size] + 1;
        for (int localY = 0; localY < centerHeight; localY++) {
            mutablePos.setY(origin.getY() + localY);
            if (!(world.getBlockState(mutablePos) == Blocks.AIR.defaultBlockState())) {
                return false;
            }
        }
        return true;
    }

    private void generatePillar(ISeedReader world, Random rand, BlockPos.Mutable mutablePos, int height, CrystalClusterFeatureConfig config) {
        int originY = mutablePos.getY();
        for (int offsetY = 0; offsetY < height; offsetY++) {
            mutablePos.setY(originY + offsetY);
            world.setBlock(mutablePos, config.rockStateProvider.getState(rand, mutablePos), 2);
        }
        if (rand.nextInt(2) == 0) {
            mutablePos.setY(originY + height);
            world.setBlock(mutablePos, config.crystalStateProvider.getState(rand, mutablePos), 2);
        }
    }
    
    private BlockPos populateHeights(ISeedReader world, Random rand, BlockPos origin, int[] heights, int size) {
        BlockPos.Mutable basePos = new BlockPos.Mutable().set(origin);

        for (int localZ = -this.radius; localZ <= this.radius; localZ++) {
            for (int localX = -this.radius; localX <= this.radius; localX++) {
                int index = (localX + this.radius) + (localZ + this.radius) * size;

                double deltaX = localX + rand.nextDouble() * 2.0 - 1.0;
                double deltaZ = localZ + rand.nextDouble() * 2.0 - 1.0;
                double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                double alpha = (this.radius - distance) / this.radius;

                int height = MathHelper.floor(alpha * this.maxHeight);
                if (height > 0) {
                    BlockPos surfacePos = this.findSurfaceBelow(world, origin.offset(localX, 0, localZ), 16);
                    if (surfacePos == null) {
                        return null;
                    }

                    if (surfacePos.getY() < basePos.getY()) {
                        basePos.setY(surfacePos.getY());
                    }

                    heights[index] = height;
                }
            }
        }

        return basePos.immutable();
    }
    
    private BlockPos findSurfaceBelow(ISeedReader world, BlockPos origin, int maxSteps) {
        BlockState currentState = world.getBlockState(origin);
        BlockPos.Mutable currentPos = new BlockPos.Mutable().set(origin);
        for (int i = 0; i < maxSteps; i++) {
            currentPos.move(Direction.DOWN);
            BlockState nextState = world.getBlockState(currentPos);
            if (currentState.getBlock() == Blocks.AIR && nextState.isCollisionShapeFullBlock(world, origin)) {
                currentPos.move(Direction.UP);
                return currentPos.immutable();
            }
            currentState = nextState;
        }
        return null;
    }
}
