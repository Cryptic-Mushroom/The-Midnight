package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class UnstableBushBlock extends MidnightPlantBlock implements IGrowable {
    public static final int MAX_STAGE = 4;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);
    protected static final VoxelShape[] BOUNDS = new VoxelShape[] {
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 13.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
    };

    public UnstableBushBlock(Properties properties) {
        super(properties, false);
        setDefaultState(getStateContainer().getBaseState().with(STAGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(MidnightItems.UNSTABLE_SEEDS);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (state.get(STAGE) < MAX_STAGE) {
            world.setBlockState(pos, state.with(STAGE, Math.min(state.get(STAGE) + 1, MAX_STAGE)), 2);
        } else {
            Block block = rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED : (rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED : MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED);
            world.setBlockState(pos, block.getDefaultState(), 2);
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        if (!isValidPosition(state, world, pos)) {
            MidnightUtil.spawnItemStack(world, pos, state.getBlock());
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        } else {
            if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(10) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS[state.get(STAGE)];
    }
}
