/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.data.models;

import midnight.common.block.MnBlocks;
import midnight.data.models.stategen.IBlockStateGen;
import midnight.data.models.stategen.ModelInfo;
import midnight.data.models.stategen.VariantBlockStateGen;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static midnight.data.models.modelgen.ExtrudedSquareModelGen.*;

@Deprecated
public final class BlockStateTable {
    private static BiConsumer<Block, IBlockStateGen> consumer;

    public static void registerBlockStates(BiConsumer<Block, IBlockStateGen> c) {
        consumer = c;

        // custom models that are still using Shadew's custom model generators
        register(MnBlocks.SUAVIS, block -> suavis(name(block, "block/%s")));
    }

    private static IBlockStateGen suavis(String name) {
        return VariantBlockStateGen.create("stage=0", ModelInfo.create(name + "_stage_0", extruded(name + "_side_small", name + "_top").size(16, 4)))
                                   .variant("stage=1", ModelInfo.create(name + "_stage_1", extruded(name + "_side_small", name + "_top").size(16, 7)))
                                   .variant("stage=2", ModelInfo.create(name + "_stage_2", extruded(name + "_side_medium", name + "_top").size(16, 13)))
                                   .variant("stage=3", ModelInfo.create(name + "_stage_3", extruded(name + "_side", name + "_top").size(16, 16)));
    }

    private static void register(Block block, Function<Block, IBlockStateGen> genFactory) {
        consumer.accept(block, genFactory.apply(block));
    }

    private static String name(Block block, String nameFormat) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Block block, String nameFormat, String removeSuffix) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;

        String pth = id.getPath();
        if (pth.endsWith(removeSuffix))
            pth = pth.substring(0, pth.length() - removeSuffix.length());

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, pth));
    }

    private static String name(Block block) {
        ResourceLocation id = block.getRegistryName();
        assert id != null;
        return id.toString();
    }


    private BlockStateTable() {
    }
}
