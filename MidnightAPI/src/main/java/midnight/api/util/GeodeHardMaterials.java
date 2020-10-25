/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.api.util;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

import java.util.Set;

/**
 * Registry for {@link Material}s and {@link Block}s that are hard enough for a geode to crack open and release a dark
 * pearl. One can register materials and blocks at any time, and does not necessarily need to happen from a plugin even
 * though that is highly recommended.
 * <p>
 * One can register hard blocks via {@link #addBlock(Block)}, and materials via {@link #addMaterial(Material)}.
 * Additionally, one can exclude certain blocks (even though they have a hard material) using {@link
 * #excludeBlock(Block)}. To test if a block is hard enough for breaking open a geode, {@link #isHard(BlockState)} can
 * be used.
 * </p>
 *
 * @author Shadew
 * @since 0.6.0
 */
public final class GeodeHardMaterials {
    // Using a concurrent hash set since modloading is multithreaded and it does not allow null values by default
    private static final Set<Material> HARD_MATERIALS = Sets.newConcurrentHashSet();
    private static final Set<Block> HARD_BLOCKS = Sets.newConcurrentHashSet();
    private static final Set<Block> NON_HARD_BLOCKS = Sets.newConcurrentHashSet();

    private GeodeHardMaterials() {
    }

    /**
     * Registers a {@link Material} to be hard enough for a geode to break open. All blocks with this material will
     * automatically be blocks that break open a geode.
     *
     * @param material The material to register
     */
    public static void addMaterial(Material material) {
        HARD_MATERIALS.add(material);
    }

    /**
     * Registers a single {@link Block} to be hard enough for a geode to break open.
     *
     * @param block The block to be registered
     */
    public static void addBlock(Block block) {
        HARD_BLOCKS.add(block);
        NON_HARD_BLOCKS.remove(block);
    }

    /**
     * Registers a single {@link Block} to be hard enough for a geode to break open.
     *
     * @param block The block to be registered
     */
    public static void excludeBlock(Block block) {
        HARD_BLOCKS.remove(block);
        NON_HARD_BLOCKS.add(block);
    }

    /**
     * Tests whether a {@link BlockState} is hard enough to break open a geode. This first checks the {@link Material}
     * and then the {@link Block}.
     *
     * @param state The block state to be tested
     * @return True if this block is hard enough to open a geode, false otherwise
     */
    public static boolean isHard(BlockState state) {
        return (HARD_MATERIALS.contains(state.getMaterial())
                    || HARD_BLOCKS.contains(state.getBlock()))
                   && !NON_HARD_BLOCKS.contains(state.getBlock());
    }
}
