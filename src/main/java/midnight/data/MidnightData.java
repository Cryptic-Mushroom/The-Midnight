/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.data;

import midnight.data.loottables.MnLootTableProvider;
import midnight.data.models.MnStateModelProvider;
import midnight.data.recipes.MnRecipeProvider;
import midnight.data.recipes.MnStonecuttingRecipeProvider;
import midnight.data.tags.MnBlockTagsProvider;
import midnight.data.tags.MnEntityTypeTagsProvider;
import midnight.data.tags.MnFluidTagsProvider;
import midnight.data.tags.MnItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * Data generator that circumvents Forges very slow modloading. Using this data can be generated rapidly right before
 * the processResources Gradle task runs.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = "midnight", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class MidnightData {
    private MidnightData() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {
            gen.addProvider(new MnStateModelProvider(gen));
        }

        if (event.includeServer()) {
            MnBlockTagsProvider blockTags = new MnBlockTagsProvider(gen);
            gen.addProvider(blockTags);
            gen.addProvider(new MnItemTagsProvider(gen, blockTags));
            gen.addProvider(new MnFluidTagsProvider(gen));
            gen.addProvider(new MnEntityTypeTagsProvider(gen));

            gen.addProvider(new MnRecipeProvider(gen));
            gen.addProvider(new MnStonecuttingRecipeProvider(gen));
            gen.addProvider(new MnLootTableProvider(gen));
        }
    }
}
