/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import midnight.core.dimension.DimensionUtil;
import midnight.core.dimension.IChunkGenFactory;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
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

// TODO Not sure exactly what this does. Do you mind making a Javadoc comment for this, Shadew?
@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {
    @Inject(method = "addRegistryDefaults", at = @At("RETURN"))
    private static void onAddRegistryDefaults(DynamicRegistries.Impl dynareg, CallbackInfoReturnable<DynamicRegistries.Impl> info) {
        MutableRegistry<DimensionType> reg = dynareg.get(Registry.DIMENSION_TYPE_KEY);
        for(Map.Entry<RegistryKey<DimensionType>, DimensionType> entry : DimensionUtil.DIMENSION_TYPES.entrySet()) {
            RegistryKey<DimensionType> key = entry.getKey();
            DimensionType value = entry.getValue();
            System.out.println("Midnight registering");
            reg.register(key, value, Lifecycle.stable());
        }
    }

    @Inject(method = "createDefaultDimensionOptions", at = @At("RETURN"))
    private static void onCreateDefaultDimensionOptions(Registry<DimensionType> typereg, Registry<Biome> biomereg, Registry<DimensionSettings> settingsreg, long seed, CallbackInfoReturnable<SimpleRegistry<Dimension>> info) {
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
