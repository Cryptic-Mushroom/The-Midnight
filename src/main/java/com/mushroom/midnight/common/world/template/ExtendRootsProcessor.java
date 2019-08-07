package com.mushroom.midnight.common.world.template;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class ExtendRootsProcessor implements TemplatePostProcessor {
    private final BlockState root;

    public ExtendRootsProcessor(BlockState root) {
        this.root = root;
    }

    @Override
    public void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state) {
        if (state == this.root && pos.getY() == template.origin.getY()) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
            mutablePos.move(Direction.DOWN);

            while (world.hasBlockState(mutablePos, s -> s.canBeReplacedByLeaves(world, mutablePos))) {
                world.setBlockState(mutablePos, this.root, Constants.BlockFlags.NOTIFY_LISTENERS);
                mutablePos.move(Direction.DOWN);
            }
        }
    }
}
