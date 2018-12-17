package com.mushroom.midnight.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFingeredGrass extends BlockMidnightPlant {
    public BlockFingeredGrass() {
        super();
        setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(10) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (double)(pos.getX() + rand.nextFloat()), pos.getY() + 1.1D, (double)(pos.getZ() + rand.nextFloat()), 0D, 0D, 0D);
        }
    }
}
