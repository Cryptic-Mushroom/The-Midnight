/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;

import midnight.common.Midnight;
import midnight.core.util.WrappingRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.impl.tool.attribute.ToolManagerImpl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public abstract class MnBlocks {
    private static final Map<Identifier, Block> BLOCKS = new HashMap<>();

    // Basic stones
    public static final Block NIGHTSTONE = stone("nightstone", 1.5, 6, MaterialColor.BLACK);
    public static final Block NIGHT_BEDROCK = stone("night_bedrock", 1.5, 6, MaterialColor.LIGHT_GRAY_TERRACOTTA);
    public static final Block TRENCHSTONE = stone("trenchstone", 1.5, 6, MaterialColor.BLACK);



    //////////////////////////
    ///// BLOCK REGISTRY /////
    //////////////////////////

    public static void registerBlocks(WrappingRegistry<Block> registry) {
        registry.registerAll(BLOCKS);
    }




    private MnBlocks() {
    }

    /////////////////////////////////
    ///// BLOCK FACTORY METHODS /////
    /////////////////////////////////

    private static <B extends Block> B block(String id, B block) {
        Registry.register(Registry.BLOCK, Midnight.id(id), block);
        return block;
    }

    private static <B extends Block> B block(String id, B block, Tag<Item> tool, int level, boolean hand) {
        Registry.register(Registry.BLOCK, Midnight.id(id), block);
        ToolManagerImpl.entry(block).putBreakByTool(tool, level);
        ToolManagerImpl.entry(block).setBreakByHand(hand);
        return block;
    }

    private static Block stone(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Settings.of(Material.STONE, color)
                                  .sounds(BlockSoundGroup.STONE)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool()
        ), FabricToolTags.PICKAXES, 0, false);
    }

    private static Block bricks(String id, double hardness, double resistance, MaterialColor color) {
        return block(id, new Block(
            AbstractBlock.Settings.of(Material.STONE, color)
                                  .sounds(BlockSoundGroup.NETHER_BRICKS)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool()
        ), FabricToolTags.PICKAXES, 0, false);
    }

    private static Block ore(String id, double hardness, double resistance, MaterialColor color, int toolLevel) {
        return block(id, new Block(
            AbstractBlock.Settings.of(Material.STONE, color)
                                  .sounds(BlockSoundGroup.STONE)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool()
        ), FabricToolTags.PICKAXES, toolLevel, false);
    }

    private static Block xpOre(String id, double hardness, double resistance, MaterialColor color, int toolLevel, int minxp, int maxxp) {
        return block(id, new XPDroppingBlock(
            AbstractBlock.Settings.of(Material.STONE, color)
                                  .sounds(BlockSoundGroup.STONE)
                                  .strength((float) hardness, (float) resistance)
                                  .requiresTool(),
            minxp, maxxp
        ), FabricToolTags.PICKAXES, toolLevel, false);
    }
}
