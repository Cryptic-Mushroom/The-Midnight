package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class SpreadableSoilBlock extends SoilBlock {
    private final Supplier<Block> groundSupplier;

    public SpreadableSoilBlock(Properties properties, Supplier<Block> groundSupplier) {
        super(properties.tickRandomly(), true);
        this.groundSupplier = groundSupplier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        if (world.isRemote || !world.isAreaLoaded(pos, 3)) {
            return;
        }
        BlockPos abovePos = pos.up();
        BlockState aboveState;
        if (!MidnightUtil.isMidnightDimension(world) || LightEngine.func_215613_a(world, state, pos, (aboveState = world.getBlockState(abovePos)), abovePos, Direction.UP, aboveState.getOpacity(world, abovePos)) > 2) {
            world.setBlockState(pos, this.groundSupplier.get().getDefaultState());
            return;
        }
        for (int i = 0; i < 4; ++i) {
            BlockPos spreadPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            if (spreadPos.getY() >= 0 && spreadPos.getY() < 256 && !world.isBlockLoaded(spreadPos)) {
                return;
            }
            BlockState surfaceState = world.getBlockState(spreadPos);
            if (surfaceState.getBlock() == this.groundSupplier.get()) {
                BlockState coverState = world.getBlockState(spreadPos.up());
                if (coverState.getOpacity(world, spreadPos.up()) <= 2) {
                    world.setBlockState(spreadPos, getDefaultState());
                }
            }
        }
    }

    @Override
    public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
        world.setBlockState(pos, this.groundSupplier.get().getDefaultState(), 2);
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }
}
