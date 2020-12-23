/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.security;

/**
 * This class holds info about the mod fingerprint.
 *
 * @author Jonathing
 * @since 0.6.0
 */
// TODO: Add more documentation.
public class FingerprintInfo {
    private boolean fingerprintVerified;
    private String fingerprint;
    private String expectedFingerprint;
    private String trustData;

    public FingerprintInfo(String fingerprint, String expectedFingerprint, String trustData) {
        this.fingerprint = fingerprint;
        this.trustData = trustData;

        if (expectedFingerprint.isEmpty()) throw new NullPointerException("Expected fingerprint cannot be empty!");
        this.expectedFingerprint = expectedFingerprint;

        this.fingerprintVerified = fingerprint.equals(expectedFingerprint.toLowerCase()) && !fingerprint.isEmpty();
    }

    public boolean hasFingerprint() {
        return !this.fingerprint.isEmpty();
    }

    public boolean matchesExpectedFingerprint() {
        return this.fingerprintVerified;
    }

    public String getFingerprint() {
        return !this.fingerprint.isEmpty() ? this.fingerprint : "UNSIGNED";
    }

    public String getExpectedFingerprint() {
        return this.expectedFingerprint;
    }

    public String getTrustData() {
        return !this.trustData.isEmpty() ? this.trustData : "NONE";
    }
}
