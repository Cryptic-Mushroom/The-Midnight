/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.handler;

import midnight.common.block.MnBlocks;
import midnight.common.block.fluid.MnFluids;
import midnight.common.entity.MnEntityTypes;
import midnight.common.item.MnItems;
import midnight.common.misc.MnParticleTypes;
import midnight.common.misc.MnSoundEvents;
import midnight.common.world.biome.MnBiomes;
import midnight.common.world.levelgen.feature.MnFeatures;
import midnight.common.world.levelgen.surface.MnSurfaceBuilders;
import midnight.core.util.IRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handler responsible for handling all registry events and passing them to their respective object holders.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = "midnight", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegistryHandler {
    private RegistryHandler() {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        MnBlocks.registerBlocks(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        MnBlocks.registerItems(IRegistry.forge(event.getRegistry()));
        MnItems.registerItems(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        MnFluids.registerFluids(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        MnEntityTypes.registerEntityTypes(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        MnSoundEvents.registerSoundEvents(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        MnBiomes.registerBiomes(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        MnSurfaceBuilders.registerSurfaceBuilders(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
        MnParticleTypes.registerParticleTypes(IRegistry.forge(event.getRegistry()));
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        MnFeatures.registerFeatures(event.getRegistry());
    }
}
