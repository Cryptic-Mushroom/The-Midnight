package midnight.common.food;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class MnFoods {
    public static final Food RAW_SUAVIS = new Food.Builder()
                                              .hunger(3)
                                              .saturation(0.6F)
                                              .effect(new EffectInstance(Effects.NAUSEA, (20 * 15), 0, false, true), 1.0F)
                                              .build();

    public static final Food COOKED_SUAVIS = new Food.Builder()
                                                 .hunger(5)
                                                 .saturation(0.6F)
                                                 .build();

    public static final Food DECEITFUL_SNAPPER = new Food.Builder()
                                                     .hunger(6)
                                                     .saturation(0.6F)
                                                     .build();

    public static final Food RAW_STAG_FLANK = new Food.Builder()
                                                  .hunger(3)
                                                  .saturation(0.3F)
                                                  .meat()
                                                  .build();

    public static final Food COOKED_STAG_FLANK = new Food.Builder()
                                                     .hunger(8)
                                                     .saturation(0.8F)
                                                     .meat()
                                                     .build();

    public static final Food COOKED_STINGER_EGG = new Food.Builder()
                                                      .hunger(6)
                                                      .saturation(0.6F)
                                                      .build();

    public static final Food HUNTER_WING = new Food.Builder()
                                               .hunger(3)
                                               .saturation(0.6F)
                                               .build();

    public static final Food COOKED_HUNTER_WING = new Food.Builder()
                                                      .hunger(8)
                                                      .saturation(1.4F)
                                                      .build();

    public static final Food GLOB_FUNGUS_HAND = new Food.Builder()
                                                    .hunger(1)
                                                    .saturation(0.3F)
                                                    .build();

    public static final Food UNSTABLE_FRUIT = new Food.Builder()
                                                  .hunger(1)
                                                  .saturation(0.3F)
                                                  .setAlwaysEdible()
                                                  .build();
}
