/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.data;

import midnight.MnInfo;
import midnight.data.loottables.MnLootTableProvider;
import midnight.data.models.MnBlockStateProvider;
import midnight.data.models.MnItemModelProvider;
import midnight.data.recipes.MnRecipeProvider;
import midnight.data.recipes.MnStonecuttingRecipeProvider;
import midnight.data.tags.MnBlockTagsProvider;
import midnight.data.tags.MnEntityTypeTagsProvider;
import midnight.data.tags.MnFluidTagsProvider;
import midnight.data.tags.MnItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * Data generator that circumvents Forges very slow modloading. Using this data can be generated rapidly right before
 * the processResources Gradle task runs.
 *
 * @author Shadew
 * @since 0.6.0
 */
public final class MidnightData {
    private MidnightData() {
    }

    public static void addEventListeners(IEventBus mod, IEventBus forge) {
        mod.addListener(MidnightData::gatherData);
    }

    private static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            gen.addProvider(new MnBlockStateProvider(gen, MnInfo.MOD_ID, existingFileHelper));
            gen.addProvider(new MnItemModelProvider(gen, MnInfo.MOD_ID, existingFileHelper));
        }

        if (event.includeServer()) {
            MnBlockTagsProvider blockTags = new MnBlockTagsProvider(gen, existingFileHelper);
            gen.addProvider(blockTags);
            gen.addProvider(new MnItemTagsProvider(gen, blockTags, existingFileHelper));
            gen.addProvider(new MnFluidTagsProvider(gen, existingFileHelper));
            gen.addProvider(new MnEntityTypeTagsProvider(gen, existingFileHelper));

            gen.addProvider(new MnRecipeProvider(gen));
            gen.addProvider(new MnStonecuttingRecipeProvider(gen));
            gen.addProvider(new MnLootTableProvider(gen));
        }
    }
}
