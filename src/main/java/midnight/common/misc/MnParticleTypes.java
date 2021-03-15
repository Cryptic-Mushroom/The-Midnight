/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.misc;

import midnight.client.particle.RisingSporeParticle;
import midnight.client.particle.SporeParticle;
import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public final class MnParticleTypes {
    private static final Map<ResourceLocation, ParticleType<?>> REGISTRY = new HashMap<>();

    public static final BasicParticleType SHROOM_SPORE = type("shroom_spore", new BasicParticleType(false));
    public static final BasicParticleType SPORE = type("spore", new BasicParticleType(false));
    public static final BasicParticleType TENDRIL_POLLEN = type("tendril_pollen", new BasicParticleType(false));

    private MnParticleTypes() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupClient() {
        ParticleManager particles = Minecraft.getInstance().particleEngine;
        particles.register(SHROOM_SPORE, RisingSporeParticle.ShroomFactory::new);
        particles.register(TENDRIL_POLLEN, RisingSporeParticle.TendrilweedFactory::new);
        particles.register(SPORE, SporeParticle.Factory::new);
    }

    public static void registerParticleTypes(IRegistry<ParticleType<?>> registry) {
        registry.registerAll(REGISTRY);
    }

    private static <T extends ParticleType<?>> T type(String id, T type) {
        type.setRegistryName(Midnight.id(id));
        REGISTRY.put(Midnight.id(id), type);
        return type;
    }
}
