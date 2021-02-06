/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block.type;

import midnight.core.util.ToolLevel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class BlockTypes {
    public static FabricBlockSettings stone(double hardness, double resistance, MaterialColor color, BlockSoundGroup sounds, int toolLevel) {
        return FabricBlockSettings.of(Material.STONE, color)
                                  .sounds(sounds)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool()
                                  .breakByHand(false)
                                  .breakByTool(FabricToolTags.PICKAXES, toolLevel);
    }

    public static FabricBlockSettings luminantStone(double hardness, double resistance, MaterialColor color, BlockSoundGroup sounds, int toolLevel, int luminance) {
        return FabricBlockSettings.of(Material.STONE, color)
                                  .sounds(sounds)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool()
                                  .breakByHand(false)
                                  .breakByTool(FabricToolTags.PICKAXES, toolLevel)
                                  .emissiveLighting((state, world, pos) -> true)
                                  .luminance(luminance);
    }

    public static FabricBlockSettings soil(double strength, MaterialColor color, BlockSoundGroup sounds) {
        return FabricBlockSettings.of(Material.SOIL, color)
                                  .sounds(sounds)
                                  .strength((float) strength)
                                  .breakByHand(true)
                                  .breakByTool(FabricToolTags.SHOVELS, ToolLevel.WOOD);
    }

    public static FabricBlockSettings organicSoil(double strength, MaterialColor color, BlockSoundGroup sounds) {
        return FabricBlockSettings.of(Material.SOLID_ORGANIC, color)
                                  .sounds(sounds)
                                  .strength((float) strength)
                                  .breakByHand(true)
                                  .breakByTool(FabricToolTags.SHOVELS, ToolLevel.WOOD);
    }

    public static FabricBlockSettings aggregateSoil(double strength, MaterialColor color, BlockSoundGroup sounds) {
        return FabricBlockSettings.of(Material.AGGREGATE, color)
                                  .sounds(sounds)
                                  .strength((float) strength)
                                  .breakByHand(true)
                                  .breakByTool(FabricToolTags.SHOVELS, ToolLevel.WOOD);
    }
}
