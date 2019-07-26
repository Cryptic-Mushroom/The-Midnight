package com.mushroom.midnight.common.data.tag;

import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;

public class MidnightFluidTagsProvider extends FluidTagsProvider {
    public MidnightFluidTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void registerTags() {
        this.getBuilder(MidnightTags.Fluids.DARK_WATER).add(MidnightFluids.DARK_WATER, MidnightFluids.FLOWING_DARK_WATER);
        this.getBuilder(MidnightTags.Fluids.MIASMA).add(MidnightFluids.MIASMA, MidnightFluids.FLOWING_MIASMA);
    }

    @Override
    public String getName() {
        return "Midnight Fluid Tags";
    }
}
