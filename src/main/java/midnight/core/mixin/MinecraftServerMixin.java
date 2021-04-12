/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 4 - 9
 */

package midnight.core.mixin;

import midnight.common.world.dimension.TheMidnightDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.IServerConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow
    @Final
    protected DynamicRegistries.Impl registryHolder;
    @Shadow
    @Final
    protected IServerConfiguration worldData;

    @Inject(at = @At("HEAD"), method = "loadLevel()V")
    private void initServer(CallbackInfo info) {
        TheMidnightDimension.createDefaultDimensionOptions(
            this.worldData.worldGenSettings().dimensions(),
            this.registryHolder.registryOrThrow(Registry.BIOME_REGISTRY),
            this.registryHolder.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY),
            this.worldData.worldGenSettings().seed()
        );
    }
}
