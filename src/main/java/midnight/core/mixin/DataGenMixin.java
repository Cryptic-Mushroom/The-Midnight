/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.core.mixin;

import midnight.data.DataMain;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin directly into {@link Main#main} that runs the data generator instead of vanilla Minecraft when a {@code
 * -Dmidnight.datagen=true} is given as a JVM property. When that JVM property is not given this mixin does nothing.
 * <p>
 * We have to start data generation here since we need the Fabric class loader to load all needed APIs. Fabric does not
 * include a data generator distribution itself so we have to use our own way to run it. This mixin is responsible to do
 * that for us.
 * </p>
 */
@Mixin(Main.class)
public class DataGenMixin {
    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static void onDataMain(CallbackInfo info) {
        boolean data = Boolean.parseBoolean(System.getProperty("midnight.datagen"));
        if (data) {
            String[] args = {
                "-all",
                "-output", System.getProperty("midnight.datagen.path", "src/generated/resources/")
            };

            try {
                DataMain.main(args);
            } catch (Throwable e) {
                e.printStackTrace();
                System.exit(-1);
            } finally {
                info.cancel();
            }
        }
    }
}
