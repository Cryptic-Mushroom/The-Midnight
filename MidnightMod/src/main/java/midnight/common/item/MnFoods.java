/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.item;

import net.minecraft.item.Food;

public final class MnFoods {
    public static final Food GLOB_FUNGUS_HAND = simple(1, 0.3);

    private MnFoods() {
    }

    private static Food simple(int hunger, double saturation) {
        return new Food.Builder().hunger(hunger).saturation((float) saturation).build();
    }

    private static Food meat(int hunger, double saturation) {
        return new Food.Builder().hunger(hunger).saturation((float) saturation).meat().build();
    }
}
