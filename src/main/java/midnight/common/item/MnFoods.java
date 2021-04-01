/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public abstract class MnFoods {
    public static final Food GLOB_FUNGUS_HAND = simple(1, 0.3);

    public static final Food RAW_SUAVIS =
        new Food.Builder()
            .nutrition(3).saturationMod(0.6f)
            .effect(() -> new EffectInstance(Effects.CONFUSION, 300, 0, false, true), 0.96f)
            .build();

    public static final Food COOKED_SUAVIS = simple(5, 0.6);

    private MnFoods() {
    }

    private static Food simple(int hunger, double saturation) {
        return new Food.Builder().nutrition(hunger).saturationMod((float) saturation).build();
    }

    private static Food meat(int hunger, double saturation) {
        return new Food.Builder().nutrition(hunger).saturationMod((float) saturation).meat().build();
    }
}
