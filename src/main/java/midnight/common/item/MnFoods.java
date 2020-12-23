/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.item;

import midnight.core.util.MnObjects;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public abstract class MnFoods {
    public static final Food GLOB_FUNGUS_HAND = simple("glob_fungus_hand", 1, 0.3);

    public static final Food RAW_SUAVIS = food(
        "raw_suavis",
        new Food.Builder()
            .hunger(3).saturation(0.6f)
            .effect(() -> new EffectInstance(Effects.NAUSEA, 300, 0, false, true), 0.96f)
            .build()
    );

    public static final Food COOKED_SUAVIS = simple("cooked_suavis", 5, 0.6);

    private MnFoods() {
    }

    private static Food food(String id, Food food) {
        MnObjects.addFood(id, food);
        return food;
    }

    private static Food simple(String id, int hunger, double saturation) {
        return food(id, new Food.Builder().hunger(hunger).saturation((float) saturation).build());
    }

    private static Food meat(String id, int hunger, double saturation) {
        return food(id, new Food.Builder().hunger(hunger).saturation((float) saturation).meat().build());
    }
}
