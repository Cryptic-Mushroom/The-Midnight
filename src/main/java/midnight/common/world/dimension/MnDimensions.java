/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.fml.common.Mod;

/**
 * This class registers and stores the list of Midnight dimensions. Go figure.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = "midnight")
public final class MnDimensions {
    public static final ResourceLocation THE_MIDNIGHT_ID = TheMidnightDimension.ID;
    public static final RegistryKey<World> THE_MIDNIGHT = TheMidnightDimension.KEY;

    private MnDimensions() {
    }

    public static void addRegistryDefaults(DynamicRegistries.Impl dynaRegs) {
        TheMidnightDimension.addRegistryDefaults(dynaRegs);
    }

    public static void createDefaultDimensionOptions(SimpleRegistry<Dimension> registry, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        TheMidnightDimension.createDefaultDimensionOptions(registry, biomeReg, settingsReg, seed);
    }

    public static boolean isTheMidnight(World world) {
        return world.getRegistryKey().equals(THE_MIDNIGHT);
    }

    public static World getTheMidnight(MinecraftServer server) {
        return server.getWorld(THE_MIDNIGHT);
    }
}
