package com.mushroom.midnight.common.world.template;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class RootsAttachProcessor implements TemplatePostProcessor {
    private final int attachChance;
    private final BlockState[] states;

    public RootsAttachProcessor(int attachChance, BlockState[] states) {
        this.attachChance = attachChance;
        this.states = states;
    }

    @Override
    public void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.isIn(MidnightTags.Blocks.FUNGI_STEMS) && random.nextInt(this.attachChance) == 0) {
            BlockState rootBlock = this.states[random.nextInt(this.states.length)];

            BlockPos attachPos = pos.down();
            if (world.isAirBlock(attachPos)) {
                world.setBlockState(attachPos, rootBlock, 2 | 16);
            }
        }
    }
}
