/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.core.mixin;

import midnight.MidnightMod;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Jonathing
 */
@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    /**
     * Funny witty comment replacement for the Midnight because we need funny references. Will probably delete/disable
     * this on release.
     *
     * @param info Mixin's way of returning a result.
     */
    @Inject(method = "getWittyComment()Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    private static void getWittyComment(CallbackInfoReturnable<String> info) {
        String[] comments = {
            ":whythefuckisthisabug:",
            ":missingno:",
            ":nobrain:",
            "Error, brain not found.",
            "God damnit, Jonathing.",
            "We shouldn't have removed Dark Water.",
            "Planet Bound died for this.",
            "This happened because Cydonia mentioned Rifter milk again.",
            "Ah fuck, not again.",
            "Voodoo magic ritual failed.",
            "Somebody asked for a 1.12.2 update again. Oh no.",
            "Pro-tip: If you throw a geode at a rock, you'll get a NullPointerException!"
        };

        try {
            info.setReturnValue(comments[(int) (Util.nanoTime() % (long) comments.length)]);
        } catch (Throwable throwable) {
            LOGGER.error("Couldn't get a funny comment for the Midnight crashing. Continuing with a normal witty comment.", throwable);
        }
    }
}
