/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.core.dimension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO Since this is in Midnight Core, we might want to document this.
public final class DimensionUtil {
    public static final Set<RegistryKey<Dimension>> DIMENSIONS = new HashSet<>();
    public static final Map<RegistryKey<DimensionType>, DimensionType> DIMENSION_TYPES = new HashMap<>();
    public static final Map<RegistryKey<DimensionType>, Pair<Pair<RegistryKey<Dimension>, DimensionType>, IChunkGenFactory>> CHUNK_GEN_FACTORIES = new HashMap<>();

    private DimensionUtil() {
    }

    public static void addDimension(ResourceLocation location, DimensionType type, IChunkGenFactory factory) {
        RegistryKey<Dimension> dimKey = RegistryKey.of(Registry.DIMENSION_OPTIONS, location);
        RegistryKey<DimensionType> typeKey = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, location);
        DIMENSIONS.add(dimKey);
        DIMENSION_TYPES.put(typeKey, type);
        CHUNK_GEN_FACTORIES.put(typeKey, Pair.of(Pair.of(dimKey, type), factory));
    }
}
