package com.mushroom.midnight.common.world.template;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public interface TemplatePostProcessor {
    void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state);
}
