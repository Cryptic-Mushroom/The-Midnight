package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModEffects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTendrilweed extends BlockGlowingPlant {
    // TODO particle  tendril spore
    public BlockTendrilweed() {
        setTickRandomly(true);
    }

    @Override
    public int tickRate(World world) {
        return 10;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(world, pos, state, random);
        if (!world.isRemote && random.nextFloat() < 0.01f) {
            BlockPos placePos = pos.add(random.nextInt(5) - 2, random.nextInt(3) - 1, random.nextInt(5) - 2);
            if (ModBlocks.TENDRILWEED.canPlaceBlockAt(world, placePos)) {
                int flowers = 0;
                for (BlockPos currentPos : BlockPos.getAllInBox(pos.add(-2, -1, -2), pos.add(2, 1, 2))) {
                    if (world.getBlockState(currentPos).getBlock() == ModBlocks.TENDRILWEED) {
                        flowers++;
                    }
                }
                if (flowers < 6) {
                    world.setBlockState(placePos, ModBlocks.TENDRILWEED.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase && !entity.world.isRemote && entity.ticksExisted % 20 == 0) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModEffects.POLLINATED, 200, 1, false, true));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustainBush(world.getBlockState(pos.down())) && super.canPlaceBlockAt(world, pos);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == ModBlocks.NIGHTSTONE;
    }
}
