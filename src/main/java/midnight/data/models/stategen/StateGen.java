/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.stategen;

import com.google.gson.JsonElement;
import midnight.data.models.modelgen.ModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

/**
 * Basic generator for a block state definition. This generator is also responsible for providing models to be generated
 * alongside the block state definition.
 */
public interface StateGen {
    /**
     * Makes the block state JSON to be exported.
     *
     * @param id    The block ID
     * @param block The block to generate for
     */
    JsonElement makeJson(Identifier id, Block block);

    /**
     * Collects all {@link ModelGen}s that should be generated alongside this block state and feeds them to the given
     * consumer.
     */
    void getModels(BiConsumer<String, ModelGen> consumer);
}
