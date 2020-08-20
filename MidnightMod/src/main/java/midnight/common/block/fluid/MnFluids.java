package midnight.common.block.fluid;

import midnight.common.Midnight;
import midnight.common.registry.RegistryManager;
import midnight.core.util.BlockLayer;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;

public final class MnFluids {
    public static final FlowingFluid DARK_WATER = register("dark_water", new DarkWaterFluid.Source());
    public static final FlowingFluid FLOWING_DARK_WATER = register("flowing_dark_water", new DarkWaterFluid.Flowing());
    public static final FlowingFluid MIASMA = register("miasma", new MiasmaFluid.Source());
    public static final FlowingFluid FLOWING_MIASMA = register("flowing_miasma", new MiasmaFluid.Flowing());



    private MnFluids() {
    }

    private static <F extends Fluid> F register(String id, F fluid) {
        RegistryManager.FLUIDS.register(id, fluid);
        return fluid;
    }

    static {
        Midnight.get().getBlockItemProxy().registerRenderLayer(DARK_WATER, BlockLayer.TRANSLUCENT);
        Midnight.get().getBlockItemProxy().registerRenderLayer(FLOWING_DARK_WATER, BlockLayer.TRANSLUCENT);
    }
}
