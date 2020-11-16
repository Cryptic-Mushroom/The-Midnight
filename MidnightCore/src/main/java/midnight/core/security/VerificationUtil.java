/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 11 - 16
 */

package midnight.core.security;

import net.minecraftforge.fml.*;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * This is a utility class to help with the verification of the mod.
 *
 * @author Jonathing
 * @since 0.6.0
 */
public final class VerificationUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    private VerificationUtil() {
    }

    @Nullable
    private static FingerprintInfo getFingerprintInfo(ModFileInfo mod, String expectedFingerprint) {
        if (!FMLEnvironment.secureJarsEnabled) {
            return null;
        }

        return new FingerprintInfo(mod.getCodeSigningFingerprint().orElse(""), expectedFingerprint, mod.getTrustData().orElse(""));
    }

    /**
     * This method gathers the SHA256 fingerprint of the specific mod (by mod id) and checks it with a given expected
     * SHA256 fingerprint. Below are three possible outcomes of this method, ordered by least to most severe.
     *
     * <ol>
     *     <li>If the SHA256 fingerprint of the mod and the expected SHA256 fingerprint match, and the game continues gracefully.</li>
     *     <li>If the mod does not have any SHA256, the user is informed via a {@link ModLoadingWarning} at startup.</li>
     *     <li>If the SHA256 fingerprint of the mod and the expected SHA256 fingerprint do not match, throw a {@link ModLoadingException} to stop all mod loading and inform the user.</li>
     * </ol>
     *
     * @param modId          The mod id of the mod.
     * @param expectedSHA256 The expected SHA256 fingerprint for the mod to have.
     * @throws ModLoadingException Thrown if the mod has a fingerprint and it does not match the expected fingerprint.
     * @see #validateMod(String, String, Function)
     */
    public static void validateMod(String modId, String expectedSHA256) {
        validateMod(modId, expectedSHA256, shouldContinue -> {
            LOGGER.fatal(String.format("VerificationUtil failed to validate the certificate of mod %s since you gave an empty expected fingerprint.", modId));
            return false;
        });
    }

    /**
     * This method gathers the SHA256 fingerprint of the specific mod (by mod id) and checks it with a given expected
     * SHA256 fingerprint. Below are three possible outcomes of this method, ordered by least to most severe.
     *
     * <ol>
     *     <li>If the SHA256 fingerprint of the mod and the expected SHA256 fingerprint match, and the game continues gracefully.</li>
     *     <li>If the mod does not have any SHA256, the user is informed via a {@link ModLoadingWarning} at startup.</li>
     *     <li>If the SHA256 fingerprint of the mod and the expected SHA256 fingerprint do not match, throw a {@link ModLoadingException} to stop all mod loading and inform the user.</li>
     * </ol>
     *
     * @param modId          The mod id of the mod.
     * @param expectedSHA256 The expected SHA256 fingerprint for the mod to have.
     * @param shouldContinue A function that takes a {@link String} (the mod id) and returns a boolean. This function is
     *                       run if the expected SHA256 fingerprint is empty and if the method returns false, it will
     *                       crash the game with a {@link NullPointerException}.
     * @throws ModLoadingException Thrown if the mod has a fingerprint and it does not match the expected fingerprint.
     */
    public static void validateMod(String modId, String expectedSHA256, Function<String, Boolean> shouldContinue) {
        if (!ModList.get().getModContainerById(modId).isPresent()) {
            LOGGER.error(String.format("Could not validate mod %s since it is not present in the mod list!", modId));
            return;
        }

        ModContainer modContainer = ModList.get().getModContainerById(modId).get();

        if (expectedSHA256.isEmpty()) {
            if (!shouldContinue.apply(modId)) {
                throw new NullPointerException(String.format("VerificationUtil failed to validate the certificate of mod %s. Please view the console for more information.", modId));
            } else {
                return;
            }
        }

        FingerprintInfo modFingerprint = getFingerprintInfo((ModFileInfo) modContainer.getModInfo().getOwningFile(), expectedSHA256);

        if (modFingerprint == null) {
            LOGGER.fatal("FML has decided that Java is unable to check any of your mods for valid signature data.");
            LOGGER.fatal("This is very bad news. Please consider updating your Java version or scan your computer for viruses.");
            new FingerprintMismatchException(modId).printStackTrace();
        } else {
            if (!modFingerprint.hasFingerprint()) {
                handleNoFingerprint(modId, modContainer.getModInfo());
            } else if (!modFingerprint.matchesExpectedFingerprint()) {
                handleFailedVerification(modId, modContainer.getModInfo(), modFingerprint);
            } else {
                handleSuccessfulVerification(modId, modFingerprint);
            }
        }
    }

    /**
     * This method will show the warning that the mod is unsigned without needing to provide an expected SHA256
     * fingerprint. This way, the game will not crash with a {@link NullPointerException}.
     *
     * @param modId The mod id of the mod.
     */
    public static boolean assumeUnsigned(String modId) {
        if (!ModList.get().getModContainerById(modId).isPresent()) {
            LOGGER.error(String.format("Could not validate mod %s since it is not present in the mod list!", modId));
            return true;
        }

        handleNoFingerprint(modId, ModList.get().getModContainerById(modId).get().getModInfo());
        return true;
    }

    private static void handleNoFingerprint(String modId, IModInfo modInfo) {
        LOGGER.fatal(String.format("You are running an unsigned build of mod %s. If you do not know what you are doing, please close the game now and download a legitimate build from the mod author's distribution site of choice. Otherwise, please proceed with caution.", modId));
        ModLoader.get().addWarning(new ModLoadingWarning(
            modInfo,
            ModLoadingStage.VALIDATE,
            String.format("You are running an unsigned build of mod %s. If you don't know what you are doing, close the game now and read the message in console.", modId),
            new FingerprintMismatchException(modId)
        ));
    }

    private static void handleSuccessfulVerification(String modId, FingerprintInfo modFingerprint) {
        LOGGER.info(String.format("Verification of mod %s successful. Here is the signature data just in case:", modId));
        LOGGER.info(String.format(" - Expected Fingerprint: %s", modFingerprint.getExpectedFingerprint().toUpperCase()));
        LOGGER.info(String.format(" - Actual Fingerprint:   %s", modFingerprint.getFingerprint().toUpperCase()));
        LOGGER.info(String.format(" - Trust Data:           %s", modFingerprint.getTrustData()));
    }

    private static void handleFailedVerification(String modId, IModInfo modInfo, FingerprintInfo modFingerprint) {
        LOGGER.fatal(String.format("YOU ARE PLAYING A BUILD OF MOD %s THAT HAS BEEN SIGNED WITH AN UNAUTHORIZED FINGERPRINT!", modId.toUpperCase()));
        LOGGER.fatal("THIS IS VERY BAD NEWS. PLEASE RUN A COMPUTER VIRUS SCAN AND REPORT THIS TO THE MOD DEVELOPER AS SOON AS POSSIBLE.");
        LOGGER.fatal(String.format(" - Expected Fingerprint: %s", modFingerprint.getExpectedFingerprint().toUpperCase()));
        LOGGER.fatal(String.format(" - Actual Fingerprint:   %s", modFingerprint.getFingerprint().toUpperCase()));
        LOGGER.fatal(String.format(" - Trust Data:           %s", modFingerprint.getTrustData()));
        throw new ModLoadingException(
            modInfo,
            ModLoadingStage.VALIDATE,
            String.format("VerificationUtil failed to validate the certificate of mod %s. Please view the console for more information.", modId),
            new FingerprintMismatchException(modId)
        );
    }
}
