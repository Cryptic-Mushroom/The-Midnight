/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.client;

import midnight.client.audio.MnAmbientTicker;
import midnight.client.audio.MnMusicTicker;
import midnight.client.environment.TheMidnightRenderInfo;
import midnight.client.util.BiomeColorCache;
import midnight.client.util.BiomeColorCacheManager;
import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.block.fluid.MnFluids;
import midnight.common.entity.MnEntityTypes;
import midnight.common.misc.MnParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

/**
 * The client-only main class of the Midnight, to handle certain client-only initialization and processing.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightClient extends Midnight {
    private final BiomeColorCacheManager colorCacheManager = new BiomeColorCacheManager();
    private final BiomeColorCache darkWaterColorCache = colorCacheManager.newCache();
    private final BiomeColorCache nightGrassColorCache = colorCacheManager.newCache();
    private final BiomeColorCache shadowrootColorCache = colorCacheManager.newCache();

    private final MnMusicTicker mnMusicTicker = new MnMusicTicker(Minecraft.getInstance());
    private final MnAmbientTicker mnAmbientTicker = new MnAmbientTicker(Minecraft.getInstance());


    @Override
    public void init() {
        super.init();
        MnBlocks.setupRenderers();
        MnFluids.setupRenderers();
        MnEntityTypes.setupRenderers();

        TheMidnightRenderInfo.init();
    }

    @Override
    public Dist getRuntimeDist() {
        return Dist.CLIENT;
    }

    @SubscribeEvent
    public void onRegisterParticles(ParticleFactoryRegisterEvent event) {
        MnParticleTypes.setupClient();
    }

    public BiomeColorCacheManager getColorCacheManager() {
        return colorCacheManager;
    }

    public BiomeColorCache getDarkWaterColorCache() {
        return darkWaterColorCache;
    }

    public BiomeColorCache getNightGrassColorCache() {
        return nightGrassColorCache;
    }

    public BiomeColorCache getShadowrootColorCache() {
        return shadowrootColorCache;
    }

    @Override
    public DynamicRegistries getDynamicRegistries() {
        return ((MinecraftServer) LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER)).registryAccess();
    }

    public MnMusicTicker getMusicTicker() {
        return mnMusicTicker;
    }

    public MnAmbientTicker getAmbientTicker() {
        return mnAmbientTicker;
    }

    /**
     * Returns the direct instance of {@link MidnightClient}, or throws a {@link ClassCastException} when not on the
     * client (that would already have caused a class loading failure in most cases).
     */
    public static MidnightClient get() {
        return (MidnightClient) Midnight.get();
    }
}
