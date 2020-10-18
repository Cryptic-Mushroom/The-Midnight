/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.data;

import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.data.loottables.MnLootTableProvider;
import midnight.data.models.MnStateModelProvider;
import midnight.data.recipes.MnRecipeProvider;
import midnight.data.recipes.MnStonecuttingRecipeProvider;
import midnight.data.tags.MnBlockTagsProvider;
import midnight.data.tags.MnFluidTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * The data-generation-only main class of the Midnight, to handle initialization during data generation and to prevent
 * unnecessary initialization done by the client which is not used during data-generation.
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
public class MidnightData extends MidnightClient {
    @Override
    public Dist getRuntimeDist() {
        return Dist.CLIENT;
    }

    /**
     * Handles the data generation event ({@link GatherDataEvent}).
     */
    @SubscribeEvent
    public void onGenerateData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(new MnStateModelProvider(gen));
        }
        if (event.includeServer()) {
            gen.addProvider(new MnLootTableProvider(gen));
            gen.addProvider(new MnRecipeProvider(gen));
            gen.addProvider(new MnStonecuttingRecipeProvider(gen));
            gen.addProvider(new MnBlockTagsProvider(gen));
            gen.addProvider(new MnFluidTagsProvider(gen));
        }
    }

    /**
     * Returns the direct instance of {@link MidnightData}, or throws a {@link ClassCastException} when not running in
     * the data generator mode (that would already have caused a class loading failure in most cases).
     */
    public static MidnightData get() {
        return (MidnightData) Midnight.get();
    }
}
