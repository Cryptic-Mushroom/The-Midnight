package midnight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

/**
 * Config for storing two state providers
 * Use config.stateProvider1/2.getState when checking states in a feature
 * Can also instantiate with subclasses of SimpleBlockStateProvider such as WeightedBlockStateProvider if needed
 *
 * @author FlashFyre
 * @since 0.6.0
 */
public class TwoBlockStateProvidingFeatureConfig implements IFeatureConfig {
	
	public static final Codec<TwoBlockStateProvidingFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(BlockStateProvider.CODEC.fieldOf("state_provider_1").forGetter((config) -> {
			return config.stateProvider1;
		}), BlockStateProvider.CODEC.fieldOf("state_provider_2").forGetter((config) -> {
			return config.stateProvider2;	
		})).apply(instance, TwoBlockStateProvidingFeatureConfig::new);
	});
	public final BlockStateProvider stateProvider1;
	public final BlockStateProvider stateProvider2;

	public TwoBlockStateProvidingFeatureConfig(BlockStateProvider stateProvider1, BlockStateProvider stateProvider2) {		
		this.stateProvider1 = stateProvider1;
		this.stateProvider2 = stateProvider2;
	}
}
