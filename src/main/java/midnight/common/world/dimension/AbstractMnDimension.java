/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 4 - 12
 */

package midnight.common.world.dimension;

import com.mojang.serialization.Lifecycle;
import midnight.core.dimension.IChunkGenFactory;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;

public abstract class AbstractMnDimension implements IChunkGenFactory {
    private final MnDimension reference;

    public AbstractMnDimension(MnDimension reference) {
        this.reference = reference;
    }

    public void createDefaultDimensionOptions(SimpleRegistry<Dimension> dimensionReg, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        dimensionReg.register(reference.getDimKey(), new Dimension(
            reference::getType,
            this.createChunkGenerator(biomeReg, settingsReg, seed)
        ), Lifecycle.stable());
    }
}
