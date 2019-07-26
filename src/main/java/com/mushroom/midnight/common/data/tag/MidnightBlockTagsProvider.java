package com.mushroom.midnight.common.data.tag;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;

public class MidnightBlockTagsProvider extends BlockTagsProvider {
    public MidnightBlockTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void registerTags() {
        this.getBuilder(MidnightTags.Blocks.BOGSHROOM).add(
                MidnightBlocks.BOGSHROOM,
                MidnightBlocks.DOUBLE_BOGSHROOM,
                MidnightBlocks.BOGSHROOM_SHELF,
                MidnightBlocks.BOGSHROOM_HAT,
                MidnightBlocks.BOGSHROOM_STEM
        );
        this.getBuilder(MidnightTags.Blocks.DEWSHROOM).add(
                MidnightBlocks.DEWSHROOM,
                MidnightBlocks.DOUBLE_DEWSHROOM,
                MidnightBlocks.DEWSHROOM_SHELF,
                MidnightBlocks.DEWSHROOM_HAT,
                MidnightBlocks.DEWSHROOM_STEM,
                MidnightBlocks.DEWSHROOM_ROOTS,
                MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS
        );
        this.getBuilder(MidnightTags.Blocks.NIGHTSHROOM).add(
                MidnightBlocks.NIGHTSHROOM,
                MidnightBlocks.DOUBLE_NIGHTSHROOM,
                MidnightBlocks.NIGHTSHROOM_SHELF,
                MidnightBlocks.NIGHTSHROOM_HAT,
                MidnightBlocks.NIGHTSHROOM_STEM,
                MidnightBlocks.NIGHTSHROOM_ROOTS,
                MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS
        );
        this.getBuilder(MidnightTags.Blocks.VIRIDSHROOM).add(
                MidnightBlocks.VIRIDSHROOM,
                MidnightBlocks.DOUBLE_VIRIDSHROOM,
                MidnightBlocks.VIRIDSHROOM_SHELF,
                MidnightBlocks.VIRIDSHROOM_HAT,
                MidnightBlocks.VIRIDSHROOM_STEM,
                MidnightBlocks.VIRIDSHROOM_ROOTS,
                MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS
        );

        this.getBuilder(MidnightTags.Blocks.BONEMEAL_GROUNDS).add(
                MidnightBlocks.NIGHTSTONE,
                MidnightBlocks.GRASS_BLOCK,
                MidnightBlocks.MYCELIUM,
                MidnightBlocks.GRASS
        );

        this.getBuilder(MidnightTags.Blocks.CAN_HOLD_ORES).add(MidnightBlocks.NIGHTSTONE);

        this.getBuilder(MidnightTags.Blocks.FUNGI_HATS).add(
                MidnightBlocks.BOGSHROOM_HAT,
                MidnightBlocks.DEWSHROOM_HAT,
                MidnightBlocks.NIGHTSHROOM_HAT,
                MidnightBlocks.VIRIDSHROOM_HAT
        );

        this.getBuilder(MidnightTags.Blocks.FUNGI_STEMS).add(
                MidnightBlocks.BOGSHROOM_STEM,
                MidnightBlocks.DEWSHROOM_STEM,
                MidnightBlocks.NIGHTSHROOM_STEM,
                MidnightBlocks.VIRIDSHROOM_STEM
        );

        this.getBuilder(MidnightTags.Blocks.LOGS).add(
                MidnightBlocks.DARK_WILLOW_LOG,
                MidnightBlocks.SHADOWROOT_LOG,
                MidnightBlocks.DEAD_WOOD_LOG
        );

        this.getBuilder(MidnightTags.Blocks.PLANTABLE_GROUNDS).add(
                MidnightBlocks.GRASS_BLOCK,
                MidnightBlocks.MYCELIUM,
                MidnightBlocks.DIRT,
                MidnightBlocks.DECEITFUL_MUD,
                MidnightBlocks.DECEITFUL_PEAT,
                Blocks.GRASS,
                Blocks.FARMLAND,
                Blocks.DIRT
        );
    }

    @Override
    public String getName() {
        return "Midnight Block Tags";
    }
}
