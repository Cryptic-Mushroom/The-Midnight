package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import com.mushroom.midnight.common.registry.MidnightTileEntities;
import com.mushroom.midnight.common.tile.MidnightFurnaceTileEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class MidnightFurnaceBlock extends AbstractFurnaceBlock {
    public MidnightFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof MidnightFurnaceTileEntity) {
            player.openContainer((INamedContainerProvider) tileentity);
            player.addStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return MidnightTileEntities.MIDNIGHT_FURNACE.create();
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double x = (double) pos.getX() + 0.5d;
            double y = (double) pos.getY() + random.nextDouble() * 6d / 16d;
            double z = (double) pos.getZ() + 0.5d;
            if (random.nextDouble() < 0.1d) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1f, 1f, false);
            }

            Direction facing = state.get(FACING);
            Direction.Axis facingAxis = facing.getAxis();
            double outwardOffset = 0.52d;
            double sidewardOffset = random.nextDouble() * 0.6d - 0.3d;
            double addX = facingAxis == Direction.Axis.X ? (double) facing.getXOffset() * outwardOffset : sidewardOffset;
            double addY = random.nextDouble() * 6d / 16d;
            double addZ = facingAxis == Direction.Axis.Z ? (double) facing.getZOffset() * outwardOffset : sidewardOffset;
            world.addParticle(ParticleTypes.SMOKE, x + addX, y + addY, z + addZ, 0d, 0d, 0d);
            world.addParticle(MidnightParticleTypes.FURNACE_FLAME, x + addX, y + addY, z + addZ, 0d, 0d, 0d);
        }
    }
}

