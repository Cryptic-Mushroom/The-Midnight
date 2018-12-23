package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockMidnightDoublePlant extends BlockBush implements IModelProvider, IShearable {
    protected static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = BlockDoublePlant.HALF;
    private final PlantBehaviorType behaviorType;
    private final boolean glowing;

    public BlockMidnightDoublePlant(PlantBehaviorType behaviorType, boolean glowing) {
        super(Material.VINE);
        this.behaviorType = behaviorType;
        this.glowing = glowing;
        if (this.glowing) {
            setLightLevel(0.8F);
        }
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Midnight.DECORATION_TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER));
    }

    public BlockMidnightDoublePlant(boolean glowing) {
        this(PlantBehaviorType.FLOWER, glowing);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == ModBlocks.MIDNIGHT_DIRT || state.getBlock() == ModBlocks.MIDNIGHT_GRASS;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && world.isAirBlock(pos.up());
    }

    @Override
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(world, pos, state)) {
            BlockPos upperPos = this.getUpperPos(state, pos);
            BlockPos lowerPos = this.getLowerPos(state, pos);

            if (this.isLower(state)) {
                this.dropBlockAsItem(world, pos, state, 0);
            }

            this.breakHalf(world, upperPos, 2);
            this.breakHalf(world, lowerPos, 3);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() != this) {
            return super.canBlockStay(world, pos, state);
        }
        BlockPos otherPos = this.getOtherPos(state, pos);
        IBlockState otherState = world.getBlockState(otherPos);
        if (otherState.getBlock() != this) {
            return false;
        }
        return this.isUpper(state) || super.canBlockStay(world, pos, otherState);
    }

    private BlockPos getUpperPos(IBlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos : pos.up();
    }

    private BlockPos getLowerPos(IBlockState state, BlockPos pos) {
        return this.isLower(state) ? pos : pos.down();
    }

    private BlockPos getOtherPos(IBlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos.down() : pos.up();
    }

    private boolean isUpper(IBlockState state) {
        return state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER;
    }

    private boolean isLower(IBlockState state) {
        return state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.LOWER;
    }

    private void breakHalf(World world, BlockPos pos, int flags) {
        if (world.getBlockState(pos).getBlock() == this) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), flags);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos otherPos = this.getOtherPos(state, pos);
        if (this.isUpper(state)) {
            if (player.capabilities.isCreativeMode) {
                this.breakHalf(world, otherPos, 3);
            } else if (world.isRemote) {
                this.breakHalf(world, otherPos, 3);
            } else {
                world.destroyBlock(otherPos, true);
            }
        } else {
            this.breakHalf(world, otherPos, 2);
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.isUpper(state) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        BlockDoublePlant.EnumBlockHalf half = meta == 1 ? BlockDoublePlant.EnumBlockHalf.UPPER : BlockDoublePlant.EnumBlockHalf.LOWER;
        return this.getDefaultState().withProperty(HALF, half);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return this.behaviorType.isShearable();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        if (this.behaviorType.isShearable()) {
            return NonNullList.withSize(1, new ItemStack(this));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return this.behaviorType.isReplacable();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER || this.behaviorType.isShearable()) {
            return Items.AIR;
        }
        return super.getItemDropped(state, rand, fortune);
    }

    public void placeAt(World world, BlockPos lowerPos, int flags) {
        world.setBlockState(lowerPos, this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER), flags);
        world.setBlockState(lowerPos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), flags);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return glowing ? layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT : super.canRenderInLayer(state, layer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (!glowing) { return super.getPackedLightmapCoords(state, source, pos); }
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            return source.getCombinedLight(pos, 0);
        }
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }
}
