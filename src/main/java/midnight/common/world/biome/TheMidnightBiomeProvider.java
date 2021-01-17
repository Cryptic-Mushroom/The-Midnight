/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import midnight.common.Midnight;
import midnight.common.world.biome.layer.MidnightBiomeLayer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.stream.Stream;

import net.shadew.ptg.region.FractalGenerator;
import net.shadew.ptg.region.LazyRegionContext;

public class TheMidnightBiomeProvider extends BiomeProvider {
    public static final Codec<TheMidnightBiomeProvider> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.LONG.fieldOf("seed").stable().forGetter(p -> p.seed),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(p -> p.biomeRegistry)
        ).apply(instance, instance.stable(TheMidnightBiomeProvider::new))
    );

    static {
        Registry.register(Registry.BIOME_SOURCE, Midnight.id("midnight"), CODEC);
    }

    private final FractalGenerator<Biome> generator;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public TheMidnightBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        super(Stream.of(
            () -> biomeRegistry.getOrThrow(MnBiomes.NIGHT_PLAINS),
            () -> biomeRegistry.getOrThrow(MnBiomes.VIGILANT_FOREST),
            () -> biomeRegistry.getOrThrow(MnBiomes.DECEITFUL_BOG),
            () -> biomeRegistry.getOrThrow(MnBiomes.CRYSTAL_SPIRES)
        ));
        this.seed = seed;
        this.biomeRegistry = biomeRegistry;

        MidnightBiomeLayer biomeLayer = new MidnightBiomeLayer(
            new int[] {
                biomeRegistry.getId(biomeRegistry.getOrThrow(MnBiomes.NIGHT_PLAINS)),
                biomeRegistry.getId(biomeRegistry.getOrThrow(MnBiomes.VIGILANT_FOREST)),
                biomeRegistry.getId(biomeRegistry.getOrThrow(MnBiomes.DECEITFUL_BOG)),
                biomeRegistry.getId(biomeRegistry.getOrThrow(MnBiomes.CRYSTAL_SPIRES))
            },
            new int[] {
                100, 87, 87, 35
            }
        );

        LazyRegionContext ctx = new LazyRegionContext(25, seed);
        generator = ctx.generate(biomeLayer, 8812L)
                       .zoomFuzzy()
                       .zoom(5)
                       .smooth()
                       .makeGenerator(region -> new FractalGenerator<Biome>(region) {
                           @Override
                           protected Biome toValue(int id) {
                               return biomeRegistry.getByValue(id);
                           }

                           @Override
                           protected Biome[] createArray(int size) {
                               return new Biome[size];
                           }
                       });
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return generator.generate(x, z);
    }

    @Override
    protected Codec<? extends BiomeProvider> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider withSeed(long seed) {
        return new TheMidnightBiomeProvider(seed, biomeRegistry);
    }
}
