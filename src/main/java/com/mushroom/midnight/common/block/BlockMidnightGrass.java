package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMidnightGrass extends Block implements IModelProvider {
    public BlockMidnightGrass() {
        super(Material.GRASS);
        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setCreativeTab(Midnight.BUILDING_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote || !world.isAreaLoaded(pos, 3)) {
            return;
        }

        BlockPos abovePos = pos.up();
        if (!Helper.isMidnightDimension(world) || world.getBlockState(abovePos).getLightOpacity(world, abovePos) > 2) {
            world.setBlockState(pos, ModBlocks.MIDNIGHT_DIRT.getDefaultState());
            return;
        }

        for (int i = 0; i < 4; ++i) {
            BlockPos spreadPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            if (spreadPos.getY() >= 0 && spreadPos.getY() < 256 && !world.isBlockLoaded(spreadPos)) {
                return;
            }

            IBlockState surfaceState = world.getBlockState(spreadPos);
            if (surfaceState.getBlock() == ModBlocks.MIDNIGHT_DIRT) {
                IBlockState coverState = world.getBlockState(spreadPos.up());
                if (coverState.getLightOpacity(world, spreadPos.up()) <= 2) {
                    world.setBlockState(spreadPos, this.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
        world.setBlockState(pos, ModBlocks.MIDNIGHT_DIRT.getDefaultState(), 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.MIDNIGHT_DIRT);
    }
}
