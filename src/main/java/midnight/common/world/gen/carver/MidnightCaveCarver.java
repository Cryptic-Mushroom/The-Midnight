package midnight.common.world.gen.carver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.mojang.serialization.Codec;

import midnight.common.block.MnBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

/**
 * Pretty much the same as the vanilla cave carver, just tweaked to carve more than just vanilla blocks.
 * 
 * @author FlashFyre
 * @since 0.6.0
 */
public class MidnightCaveCarver extends CaveWorldCarver {
	
	public MidnightCaveCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
		super(codec, maxHeight);
	}
	
	@Override
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel,
			int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, ProbabilityConfig config) {
		return super.carve(chunk, biomePos, rand, seaLevel, chunkXOffset, chunkZOffset, chunkX,
				chunkZ, carvingMask, config);
	}
	
	@Override
	protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random rand, 
				       BlockPos.Mutable posHere, BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, 
				       int seaLevel, int xChunk, int zChunk, int globalX, int globalZ, int x, int y, int z, 
				       MutableBoolean foundSurface)	{
		
		int index = x | z << 4 | y << 8;
		if (carvingMask.get(index))
		{
			return false;
		}
		carvingMask.set(index);
		
		
		posHere.set(globalX, y, globalZ);
		BlockState blockState = chunk.getBlockState(posHere);
		BlockState blockStateAbove = chunk.getBlockState(posAbove.set(posHere).move(Direction.UP));
		if (!this.canReplaceBlock(blockState, blockStateAbove))  // Makes sure we aren't carving a non terrain or liquid space
		{
			return false;
		}
		
		if (blockState == CAVE_AIR) {
			return false;
		}

		chunk.setBlockState(posHere, CAVE_AIR, false);
		return true;
	}
	
	@Override
	protected boolean canReplaceBlock(BlockState blockState, BlockState aboveBlockState)
	{
		if (blockState.getBlock() == MnBlocks.NIGHT_BEDROCK)
			return false;

		Material material = blockState.getMaterial();
		Material aboveMaterial = aboveBlockState.getMaterial();
		return (material == Material.STONE || material == Material.DIRT || material == Material.GRASS) && 
		        material != Material.WATER && 
			material != Material.LAVA && 
			aboveMaterial != Material.WATER && 
			aboveMaterial != Material.LAVA;
	}
}
