/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

public class ErrorColorProvider extends Checkerboard2DColorProvider {
    public static final ErrorColorProvider INSTANCE = new ErrorColorProvider();

    private ErrorColorProvider() {
        super(new SolidColorProvider(0xff00ff), new SolidColorProvider(0x000000), 1, 1);
    }
}
