/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 19
 */

package midnight.core.mixin;

import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Jonathing
 */
@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Shadow
    @Final
    private String title;
    private static boolean funnyMidnightCrashReport = false;

    /**
     * This basically just dictates that we should only fuck with the crash report if the stacktrace contains
     * "midnight." or if it's a manually triggered debug crash.
     *
     * @see CrashReport#getFriendlyReport()
     */
    @Inject(method = "getFriendlyReport()Ljava/lang/String;", at = @At("HEAD"))
    private void getFriendlyReport(CallbackInfoReturnable<String> info) {
        if (((CrashReport) (Object) this).getExceptionMessage().contains("midnight.") || this.title.contains("Manually triggered debug crash")) {
            funnyMidnightCrashReport = true;
        }
    }

    /**
     * Funny witty comment replacement for the Midnight because we need funny references. Will probably delete/disable
     * this on release.
     *
     * @param wittyComments Our witty comments to be used in the crash report
     * @see CrashReport#getErrorComment()
     */
    @ModifyVariable(method = "getErrorComment()Ljava/lang/String;", at = @At("STORE"), index = 0)
    private static String[] getErrorComment(String[] wittyComments) {
        if (!funnyMidnightCrashReport) return wittyComments;

        return new String[] {
            ":whythefuckisthisabug:",
            ":missingno:",
            ":nobrain:",
            ":mlep:",
            "Error, brain not found.",
            "God damnit, Jonathing.",
            "We shouldn't have removed Dark Water.",
            "Planet Bound died for this.",
            "This happened because Cydonia mentioned Rifter milk again.",
            "Ah fuck, not again.",
            "Voodoo magic ritual failed.",
            "Somebody asked for a 1.12.2 update again. Oh no.",
            "Manually triggered debug crash",
            "If this is the CI then I'm not sorry",
            "Pro-tip: If you throw a geode at a rock, you'll get a NullPointerException!",
            "Brought to you (in part) by our favorite mod, Crashes O' Plenty!",
            "You should check out our sister mod, Midday!",
            "Hi. I'm the Midnight, and I'm a crashaholic.",
            "Lord Majesty Crash Report",
            "Shaders are your enemy",
            "Missing bedrock edition",
            "The red lightning has struck again!",
            "Shadew pls fix",
            "When rifters fly!",
            "Also try the shitnight",
            "404 rift not found",
            "Cresh raport",
            "Who invoked /time set noon in the Midnight?",
            "There are no lights!",
            "Missing refmap confirmed again"
        };
    }

    @ModifyVariable(method = "getFriendlyReport()Ljava/lang/String;", at = @At(value = "LOAD", ordinal = 1), index = 1)
    private StringBuilder modifyCrashHeader(StringBuilder stringbuilder) {
        if (!funnyMidnightCrashReport) return stringbuilder;

        stringbuilder = new StringBuilder();
        stringbuilder.append("---- Midnight Crash Report ----\n");
        return stringbuilder;
    }
}
