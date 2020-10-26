/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

public class ColorDeserializeException extends Exception {
    public ColorDeserializeException() {
    }

    public ColorDeserializeException(String message) {
        super(message);
    }

    public ColorDeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColorDeserializeException(Throwable cause) {
        super(cause);
    }
}
