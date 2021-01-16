/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block.fluid;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * This class registers and stores the list of Midnight fluids and their respective blocks.
 *
 * @author Shadew
 * @since 0.6.0
 */
@ObjectHolder("midnight")
public final class MnFluids {
    private static final Map<ResourceLocation, Fluid> FLUIDS = new HashMap<>();


    public static void registerFluids(IRegistry<Fluid> registry) {
        registry.registerAll(FLUIDS);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
    }

    private MnFluids() {
    }

    private static <F extends Fluid> F fluid(String id, F fluid) {
        fluid.setRegistryName(Midnight.id(id));
        FLUIDS.put(Midnight.id(id), fluid);
        return fluid;
    }
}
