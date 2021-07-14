package midnight.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class BoulderFeature extends Feature<TwoBlockStateProvidingFeatureConfig> {

	public BoulderFeature(Codec<TwoBlockStateProvidingFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos origin,
			TwoBlockStateProvidingFeatureConfig config) {
		float baseRadius = 2.0F;
		
		System.out.println(origin.getY());

        origin = origin.above(MathHelper.floor(baseRadius / 2.0F));

        this.generateBlob(world, rand, origin, baseRadius, config);
        for (int i = 0; i < 2; i++) {
            int offsetX = rand.nextInt(5) - 2;
            int offsetY = -rand.nextInt(2);
            int offsetZ = rand.nextInt(5) - 2;
            BlockPos center = origin.offset(offsetX, offsetY, offsetZ);

            float radius = baseRadius + rand.nextFloat() * 0.5F;
            this.generateBlob(world, rand, center, radius, config);
        }

        return true;
	}
	
	private void generateBlob(ISeedReader world, Random rand, BlockPos origin, float radius, TwoBlockStateProvidingFeatureConfig config) {
        float radiusSquare = radius * radius;
        int radiusCeil = MathHelper.ceil(radius);

        BlockPos minPos = origin.offset(-radiusCeil, -radiusCeil, -radiusCeil);
        BlockPos maxPos = origin.offset(radiusCeil, radiusCeil, radiusCeil);
        BlockPos.betweenClosedStream(minPos, maxPos).forEach(pos -> {
            double distSquare = pos.distSqr(origin);
            if (distSquare <= radiusSquare) {
                world.setBlock(pos, getStateForPlacement(world, rand, pos, distSquare, radiusSquare, config), 3);
            }
        });
    }

    private BlockState getStateForPlacement(ISeedReader world, Random rand, BlockPos pos, double distSquare, float radiusSquare, TwoBlockStateProvidingFeatureConfig config) {
    	return distSquare <= 1 ? config.stateProvider2.getState(rand, pos) : config.stateProvider1.getState(rand, pos);
    }

}
