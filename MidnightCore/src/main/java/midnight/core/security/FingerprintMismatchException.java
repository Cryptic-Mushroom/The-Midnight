/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 11 - 16
 */

package midnight.core.security;

/**
 * This exception is thrown when the expected fingerprint for a mod does not match the actual fingerprint.
 *
 * @author Jonathing
 * @since 0.6.0
 */
public class FingerprintMismatchException extends RuntimeException {
    public FingerprintMismatchException(String modId) {
        super(String.format("Fingerprint found in mod %s does not match the expected fingerprint!", modId));
    }
}
