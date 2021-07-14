package midnight.common.world.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalClusterFeatureConfig implements IFeatureConfig {
	
	public static final Codec<CrystalClusterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(BlockStateProvider.CODEC.fieldOf("rock_state_provider").forGetter((config) -> {
			return config.rockStateProvider;
		}), BlockStateProvider.CODEC.fieldOf("crystal_state_provider").forGetter((config) -> {
			return config.crystalStateProvider;	
		})).apply(instance, CrystalClusterFeatureConfig::new);
	});
	public final BlockStateProvider rockStateProvider;
	public final BlockStateProvider crystalStateProvider;

	public CrystalClusterFeatureConfig(BlockStateProvider rockStateProvider, BlockStateProvider crystalStateProvider) {
		this.crystalStateProvider = crystalStateProvider;
		this.rockStateProvider = rockStateProvider;
	}
}
