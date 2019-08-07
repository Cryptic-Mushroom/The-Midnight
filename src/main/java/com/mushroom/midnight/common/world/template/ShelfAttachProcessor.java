package com.mushroom.midnight.common.world.template;

import com.mushroom.midnight.common.block.MidnightFungiShelfBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.util.BlockStatePredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class ShelfAttachProcessor implements TemplatePostProcessor {
    protected static final Direction[] ATTACH_SIDES = new Direction[] { Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST, Direction.UP };

    public static final Block[] FOREST_SHELF_BLOCKS = new Block[] { MidnightBlocks.NIGHTSHROOM_SHELF, MidnightBlocks.DEWSHROOM_SHELF, MidnightBlocks.VIRIDSHROOM_SHELF };
    public static final Block[] SHELF_BLOCKS = new Block[] {
            MidnightBlocks.NIGHTSHROOM_SHELF,
            MidnightBlocks.VIRIDSHROOM_SHELF,
            MidnightBlocks.DEWSHROOM_SHELF,
            MidnightBlocks.BOGSHROOM_SHELF
    };

    private final int attachChance;
    private final BlockStatePredicate replaceable;
    private final Block[] shelfBlocks;

    public ShelfAttachProcessor(int attachChance, BlockStatePredicate replaceable, Block[] shelfBlocks) {
        this.attachChance = attachChance;
        this.replaceable = replaceable;
        this.shelfBlocks = shelfBlocks;
    }

    public ShelfAttachProcessor(BlockStatePredicate replaceable, Block[] shelfBlocks) {
        this(6, replaceable, shelfBlocks);
    }

    @Override
    public void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.isSolid() && random.nextInt(this.attachChance) == 0) {
            this.attachShelf(world, random, pos);
        }
    }

    protected void attachShelf(IWorld world, Random random, BlockPos pos) {
        Block shelfBlock = this.shelfBlocks[random.nextInt(this.shelfBlocks.length)];
        Direction attachSide = ATTACH_SIDES[random.nextInt(ATTACH_SIDES.length)];

        BlockPos offsetPos = pos.offset(attachSide);
        if (this.replaceable.test(world, offsetPos)) {
            world.setBlockState(offsetPos, shelfBlock.getDefaultState()
                    .with(MidnightFungiShelfBlock.FACING, attachSide), 2 | 16
            );
        }
    }
}
