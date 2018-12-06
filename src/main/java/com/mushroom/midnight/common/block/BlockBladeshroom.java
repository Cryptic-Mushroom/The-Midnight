package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;

public class BlockBladeshroom extends BlockMidnightPlant implements IGrowable {
    public static final PropertyEnum<Stage> STAGE = PropertyEnum.create("stage", Stage.class);
    private static final int REGROW_CHANCE = 10;

    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.5625, 0.9375);
    private static final AxisAlignedBB STEM_BOUNDS = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

    public BlockBladeshroom() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Stage.SPORE));
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }

    @Override
    @Nullable
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
        return state.getValue(STAGE) == Stage.CAPPED ? PathNodeType.DAMAGE_CACTUS : super.getAiPathNodeType(state, world, pos, entity);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == ModBlocks.NIGHTSTONE;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(STAGE) == Stage.CAPPED) {
            player.addItemStackToInventory(new ItemStack(ModItems.BLADESHROOM_CAP));
            world.setBlockState(pos, state.withProperty(STAGE, Stage.STEM));
            player.attackEntityFrom(DamageSource.CACTUS, 1.0F);

            return true;
        }

        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(STAGE) == Stage.CAPPED) {
            drops.add(new ItemStack(ModItems.BLADESHROOM_CAP));
        }

        Random random = world instanceof World ? ((World) world).rand : RANDOM;
        drops.add(new ItemStack(ModItems.BLADESHROOM_SPORES, this.quantityDropped(state, fortune, random)));
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int quantity = 0;
        if (state.getValue(STAGE) == Stage.CAPPED) {
            quantity += 1;
        }
        if (random.nextInt(3) == 0) {
            quantity += 1;
        }
        return quantity;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(STAGE) == Stage.CAPPED ? BOUNDS : STEM_BOUNDS;
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.NONE;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (state.getValue(STAGE) == Stage.CAPPED) {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (random.nextInt(REGROW_CHANCE) == 0) {
            this.incrementStage(world, pos, state);
        }
    }

    private void incrementStage(World world, BlockPos pos, IBlockState state) {
        Stage nextStage = state.getValue(STAGE).next();
        if (nextStage == null) {
            return;
        }
        world.setBlockState(pos, state.withProperty(STAGE, nextStage));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, Stage.deserialize(meta));
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(STAGE) != Stage.CAPPED;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.incrementStage(world, pos, state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModItems.BLADESHROOM_SPORES);
    }

    public enum Stage implements IStringSerializable {
        SPORE,
        STEM,
        CAPPED;

        @Nullable
        public Stage next() {
            Stage[] stages = values();
            int ordinal = this.ordinal();
            if (ordinal < stages.length - 1) {
                return stages[ordinal + 1];
            }
            return null;
        }

        public static Stage deserialize(int meta) {
            Stage[] stages = values();
            return stages[meta % stages.length];
        }

        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
