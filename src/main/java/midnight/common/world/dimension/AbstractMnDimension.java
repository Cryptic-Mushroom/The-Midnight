/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 4 - 12
 */

package midnight.common.world.dimension;

import com.mojang.serialization.Lifecycle;
import midnight.common.Midnight;
import midnight.core.dimension.IChunkGenFactory;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.common.util.NonNullLazy;

/**
 * All of the Midnight's dimensions will be based on this class. It essentially provides the groundwork for allowing
 * them to be added into a world's registry.
 *
 * @author Jonathing
 * @since 0.6.0
 */
public abstract class AbstractMnDimension implements IChunkGenFactory {
    private final ResourceLocation id;
    private final NonNullLazy<RegistryKey<World>> key;
    private final NonNullLazy<RegistryKey<Dimension>> dimKey;
    private final NonNullLazy<DimensionType> type;

    /**
     * This constructor instantializes this abstract dimension with a given reference, containing the information needed
     * for the methods in this class to function for its subclasses.
     * <p>
     * It is recommended for the subclass to use a constructor with empty parameters and feed its own static reference
     * to this constructor.
     *
     * @param name The name for the dimension to use.
     * @param type The dimension type for the dimension to use.
     */
    public AbstractMnDimension(String name, NonNullLazy<DimensionType> type) {
        this.id = Midnight.id(name);
        this.key = () -> RegistryKey.create(Registry.DIMENSION_REGISTRY, this.id);
        this.dimKey = () -> RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, this.id);
        this.type = type;
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
        dimensionReg.register(this.getDimKey(), new Dimension(
            this::getType,
            this.createChunkGenerator(biomeReg, settingsReg, seed)
        ), Lifecycle.stable());
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RegistryKey<World> getKey() {
        return this.key.get();
    }

    public RegistryKey<Dimension> getDimKey() {
        return this.dimKey.get();
    }

    public DimensionType getType() {
        return this.type.get();
    }
}
