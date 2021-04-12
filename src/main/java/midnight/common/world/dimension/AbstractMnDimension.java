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

/**
 * All of the Midnight's dimensions will be based on this class. It essentially provides the groundwork for allowing
 * them to be added into a world's registry.
 *
 * @author Jonathing
 * @since 0.6.0
 */
public abstract class AbstractMnDimension implements IChunkGenFactory {
    private final MnDimension reference;

    /**
     * This constructor instantializes this abstract dimension with a given reference, containing the information needed
     * for the methods in this class to function for its subclasses.
     *
     * @param reference The enum reference to use for this dimension.
     * @implNote It is recommended for the subclass to use a constructor with empty parameters and feed its own
     *     static reference to this constructor.
     */
    public AbstractMnDimension(MnDimension reference) {
        this.reference = reference;
    }

    /**
     * This method registers the dimension into the world's dimension registry along with its chunk generator given by
     * {@link IChunkGenFactory#createChunkGenerator(Registry, Registry, long)}.
     *
     * @param dimensionReg The dimension registry to add the world to.
     * @param biomeReg     The biome registry for the chunk generator to use.
     * @param settingsReg  The dimension settings registry for the chunk generator to use.
     * @param seed         The seed for the chunk generator to use.
     */
    public void createDefaultDimensionOptions(SimpleRegistry<Dimension> dimensionReg, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        dimensionReg.register(reference.getDimKey(), new Dimension(
            reference::getType,
            this.createChunkGenerator(biomeReg, settingsReg, seed)
        ), Lifecycle.stable());
    }
}
