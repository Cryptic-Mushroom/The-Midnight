package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MidnightPlantBlock extends BushBlock implements IGrowable, GeneratablePlant {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2d, 0d, 2d, 14d, 13d, 14d);

    @Nullable
    protected final Supplier<Block> growSupplier;
    private final boolean glowing;
    private boolean replacable;

    public MidnightPlantBlock(Block.Properties properties) {
        this(properties, false, null);
    }

    public MidnightPlantBlock(Block.Properties properties, boolean glowing) {
        this(properties, glowing, null);
    }

    public MidnightPlantBlock(Block.Properties properties, boolean glowing, @Nullable Supplier<Block> growSupplier) {
        super(properties.lightValue(glowing ? 12 : 0));
        this.glowing = glowing;
        this.growSupplier = growSupplier;
        //setCreativeTab(MidnightItemGroups.DECORATION);
    }

    public MidnightPlantBlock setReplacable() {
        this.replacable = true;
        return this;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
            return state.getBlock().isIn(MidnightTags.Blocks.PLANTABLE_GROUNDS);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return this.replacable;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return glowing ? layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT : super.canRenderInLayer(state, layer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader source, BlockPos pos) {
        if (!glowing) {
            return super.getPackedLightmapCoords(state, source, pos);
        }
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            return source.getCombinedLight(pos, 0);
        }
        int skyLight = 14;
        int blockLight = 14;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return this.growSupplier != null;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return this.growSupplier != null;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        if (this.growSupplier != null) {
            MidnightDoublePlantBlock doublePlant = (MidnightDoublePlantBlock) growSupplier.get();
            if (doublePlant.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up())) {
                doublePlant.placeAt(worldIn, pos, 2);
            }
        }
    }
}
