/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.dimension;

import midnight.common.Midnight;
import midnight.common.world.biome.MnBiomeProvider;
import midnight.common.world.levelgen.midnight.MnChunkGenerator;
import midnight.core.dimension.DimensionUtil;
import midnight.core.util.MnObjects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraftforge.fml.common.Mod;

import java.util.OptionalLong;

/**
 * This class registers and stores the list of Midnight dimensions. Go figure.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = "midnight")
public final class MnDimensions {
    public static final DimensionType MIDNIGHT = new MidnightType(
        OptionalLong.of(18000),
        false,
        false,
        false,
        false,
        1,
        false,
        false,
        false,
        false,
        false,
        256,
        FuzzedBiomeMagnifier.INSTANCE,
        new ResourceLocation("infiniburn_overworld"),
        Midnight.resLoc("midnight"),
        0
    );

    static {
        MnObjects.addDimension("the_midnight", MIDNIGHT);
    }

    private MnDimensions() {
    }

    public static void init() {
        DimensionUtil.addDimension(
            Midnight.resLoc("the_midnight"),
            MIDNIGHT,
            (biomes, configs, seed) -> new MnChunkGenerator(
                seed,
                new MnBiomeProvider(seed, biomes)
            )
        );
    }
//    public static final ResourceLocation MIDNIGHT_ID = new ResourceLocation("midnight:midnight");
//    public static final ModDimension MIDNIGHT_DIMENSION = ModDimension.withFactory(MidnightDimension::new);
//    private static DimensionType midnight;
//
//    public static void registerDimensions(IForgeRegistry<ModDimension> registry) {
//        registry.registerAll(
//            MIDNIGHT_DIMENSION.setRegistryName(MIDNIGHT_ID)
//        );
//    }
//
//    private MnDimensions() {
//    }
//
//    @SubscribeEvent
//    public static void onRegisterDimensions(RegisterDimensionsEvent event) {
//        midnight = DimensionManager.registerOrGetDimension(MIDNIGHT_ID, MIDNIGHT_DIMENSION, null, false);
//    }
//
//    public static DimensionType midnight() {
//        return midnight;
//    }

    public static class MidnightType extends DimensionType {

        protected MidnightType(OptionalLong fixedTime, boolean skylight, boolean ceiling, boolean ultrawarm, boolean natural, double coordsScale, boolean dragon, boolean piglins, boolean bed, boolean anchor, boolean raid, int height, IBiomeMagnifier magnifier, ResourceLocation infiniburn, ResourceLocation sky, float lighting) {
            super(fixedTime, skylight, ceiling, ultrawarm, natural, coordsScale, dragon, piglins, bed, anchor, raid, height, magnifier, infiniburn, sky, lighting);
        }
    }
}
