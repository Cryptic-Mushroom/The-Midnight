/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.core.mixin;

import biomesoplenty.common.world.BOPDimensionType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import midnight.core.dimension.DimensionUtil;
import midnight.core.dimension.IChunkGenFactory;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

// TODO Jonathing: JavaDoc the mixin classes?

/**
 * Biomes O' Plenty registers dimensions itself. Therefore, we mix our dimension
 * into Biomes O' Plenty's dimension registry.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mixin(value = BOPDimensionType.class, remap = false)
public class BOPDimensionTypeMixin {
    /**
     * At the return of the onCreateDefaultDimensionOptions method, ask Biomes O' Plenty to include our mod in the
     * registry as well.
     */
    @Inject(method = "bopDimensions", at = @At("RETURN"))
    private static void onCreateDefaultDimensionOptions(Registry<Biome> biomereg, Registry<DimensionSettings> settingsreg, long seed, CallbackInfoReturnable<SimpleRegistry<Dimension>> info) {
        SimpleRegistry<Dimension> reg = info.getReturnValue();
        for(Map.Entry<RegistryKey<DimensionType>, Pair<Pair<RegistryKey<Dimension>, DimensionType>, IChunkGenFactory>> entry : DimensionUtil.CHUNK_GEN_FACTORIES.entrySet()) {
            Pair<Pair<RegistryKey<Dimension>, DimensionType>, IChunkGenFactory> pair = entry.getValue();
            reg.register(pair.getFirst().getFirst(), new Dimension(
                () -> pair.getFirst().getSecond(),
                pair.getSecond().createChunkGenerator(biomereg, settingsreg, seed)
            ), Lifecycle.stable());
        }
    }
}
