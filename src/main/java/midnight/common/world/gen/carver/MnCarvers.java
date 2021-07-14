package midnight.common.world.gen.carver;

import java.util.ArrayList;
import java.util.List;

import midnight.common.Midnight;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author FlashFyre
 * @since 0.6.0
 */
public class MnCarvers {
	
	private static final List<WorldCarver<?>> CARVERS = new ArrayList<>();
	
	public static final MidnightCaveCarver MIDNIGHT_CAVE = register("midnight_cave", new MidnightCaveCarver(ProbabilityConfig.CODEC, 256));

    public static void registerCarvers(IForgeRegistry<WorldCarver<?>> registry) {
    	CARVERS.forEach(registry::register);
    }

    private static <FC extends ICarverConfig, F extends WorldCarver<FC>> F register(String id, F carver) {
    	carver.setRegistryName(Midnight.id(id));
        CARVERS.add(carver);
        return carver;
    }
    
    public static final class ConfiguredCarvers {
    	
    	private static <WC extends ICarverConfig> ConfiguredCarver<WC> register(String id, ConfiguredCarver<WC> carver) {
    		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, Midnight.id(id), carver); 
        }
    	
    	public static final ConfiguredCarver<?> MIDNIGHT_CAVE = register("midnight_cave", MnCarvers.MIDNIGHT_CAVE.configured(new ProbabilityConfig(0.2F)));
    	
    }

}
