/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.core.mixin;

import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin skips the check for modification of the default dimension settings. When the target method returns false,
 * MC shows a popup saying that we're using experimental settings. But, this is Modding, it's all experimenting so
 * whoever cares!
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mixin(DimensionGeneratorSettings.class)
public abstract class DimensionGeneratorSettingsMixin {
    /**
     * Injects into the head of the onCheckExperimental to return true and ignore that the default generation
     * settings have been modified.
     */
    @Inject(method = "method_28611", at = @At("HEAD"), cancellable = true)
    private void onCheckExperimental(CallbackInfoReturnable<Boolean> info) {
        // This mixin skips the check for modification of the default dimension settings.
        // When the target method returns false, MC shows up a popup that we're using experimental settings. But,
        // this is Modding, it's all experimenting so whoever cares! Just ensure the output is true and the popup
        // doesn't show up by returning true here.
        info.setReturnValue(true);
    }
}
