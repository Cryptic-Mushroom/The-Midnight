package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class MidnightGemBlock extends Block {
    private final int harvestLevel;

    public MidnightGemBlock(int harvestLevel) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3f, 5f).sound(SoundType.STONE));
        this.harvestLevel = harvestLevel;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silkTouch) {
        if (silkTouch != 0) return 0;
        return MathHelper.nextInt(this.RANDOM, 3, 7);
    }
}
