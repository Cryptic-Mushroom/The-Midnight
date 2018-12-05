package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.function.Supplier;

public class ItemMidnightSeed extends Item implements IPlantable, IModelProvider {
    private final Supplier<IBlockState> plantSupplier;

    public ItemMidnightSeed(Supplier<IBlockState> plantSupplier) {
        this.plantSupplier = plantSupplier;
        this.setCreativeTab(Midnight.MIDNIGHT_ITEMS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        IBlockState groundState = world.getBlockState(pos);
        BlockPos placePos = pos.offset(facing);

        if (!(player.canPlayerEdit(placePos, facing, stack) && groundState.getBlock().canSustainPlant(groundState, world, pos, facing, this) && world.isAirBlock(placePos))) {
            return EnumActionResult.FAIL;
        }

        world.setBlockState(placePos, this.plantSupplier.get());
        if (player instanceof EntityPlayerMP) {
            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), stack);
        }

        stack.shrink(1);

        return EnumActionResult.SUCCESS;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.plantSupplier.get();
    }
}
