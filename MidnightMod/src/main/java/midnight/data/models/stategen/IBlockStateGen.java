/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.data.models.stategen;

import com.google.gson.JsonElement;
import midnight.data.models.modelgen.IModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public interface IBlockStateGen {
    JsonElement makeJson(ResourceLocation id, Block block);
    void getModels(BiConsumer<String, IModelGen> consumer);
}
