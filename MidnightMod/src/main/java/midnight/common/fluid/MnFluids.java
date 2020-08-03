package midnight.common.fluid;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import midnight.common.registry.RegistryManager;

@ObjectHolder("midnight")
public class MnFluids {
    public static final FlowingFluid DARK_WATER = register("dark_water", new DarkWaterFluid.Source());
    public static final FlowingFluid MIASMA = register("flowing_dark_water", new MiasmaFluid.Source());
    public static final FlowingFluid FLOWING_DARK_WATER = register("miasma", new DarkWaterFluid.Flowing());
    public static final FlowingFluid FLOWING_MIASMA = register("flowing_miasma", new MiasmaFluid.Flowing());

    private static <F extends Fluid> F register(String id, F fluid) {
        RegistryManager.FLUIDS.register(id, fluid);
        return fluid;
    }
}
