package com.mushroom.midnight.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Note from ZombieEnderman5: Didn't call super AABB code in hopes of resolving the no AABB problem. Didn't work... */
public class BlockViridshroomStairs extends BlockStairs implements IModelProvider {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<BlockStairs.EnumHalf> HALF = PropertyEnum.<BlockStairs.EnumHalf>create("half", BlockStairs.EnumHalf.class);
    public static final PropertyEnum<BlockStairs.EnumShape> SHAPE = PropertyEnum.<BlockStairs.EnumShape>create("shape", BlockStairs.EnumShape.class);
    protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
	
	public BlockViridshroomStairs() {
		
		super(ModBlocks.VIRIDSHROOM_PLANKS.getDefaultState());
		this.setCreativeTab(Midnight.MIDNIGHT_TAB);
		this.setSoundType(SoundType.WOOD);
		
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		
		if (!isActualState) {
            state = this.getActualState(state, worldIn, pos);
        }

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
		
	}
	
	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
        boolean flag = bstate.getValue(HALF) == BlockStairs.EnumHalf.TOP;
        list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
        BlockStairs.EnumShape blockstairs$enumshape = (BlockStairs.EnumShape)bstate.getValue(SHAPE);

        if (blockstairs$enumshape == BlockStairs.EnumShape.STRAIGHT || blockstairs$enumshape == BlockStairs.EnumShape.INNER_LEFT || blockstairs$enumshape == BlockStairs.EnumShape.INNER_RIGHT) {
            list.add(getCollQuarterBlock(bstate));
        }

        if (blockstairs$enumshape != BlockStairs.EnumShape.STRAIGHT) {
            list.add(getCollEighthBlock(bstate));
        }

        return list;
    }
	
    private static AxisAlignedBB getCollQuarterBlock(IBlockState bstate) {
        boolean flag = bstate.getValue(HALF) == BlockStairs.EnumHalf.TOP;

        switch ((EnumFacing)bstate.getValue(FACING)) {
            case NORTH:
            default:
                return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
            case SOUTH:
                return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
            case WEST:
                return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
            case EAST:
                return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
        }
    }
    
    private static AxisAlignedBB getCollEighthBlock(IBlockState bstate) {
        EnumFacing enumfacing = (EnumFacing)bstate.getValue(FACING);
        EnumFacing enumfacing1;

        switch ((BlockStairs.EnumShape)bstate.getValue(SHAPE)) {
            case OUTER_LEFT:
            default:
                enumfacing1 = enumfacing;
                break;
            case OUTER_RIGHT:
                enumfacing1 = enumfacing.rotateY();
                break;
            case INNER_RIGHT:
                enumfacing1 = enumfacing.getOpposite();
                break;
            case INNER_LEFT:
                enumfacing1 = enumfacing.rotateYCCW();
        }

        boolean flag = bstate.getValue(HALF) == BlockStairs.EnumHalf.TOP;

        switch (enumfacing1) {
            case NORTH:
            default:
                return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
            case SOUTH:
                return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
            case WEST:
                return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
            case EAST:
                return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
        }
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		
		return super.getBlockFaceShape(worldIn, state, pos, face);
		
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return super.isOpaqueCube(state);
		
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		
		return super.isFullCube(state);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		
		super.onBlockClicked(worldIn, pos, playerIn);
		
	}
	
	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
		
		super.onPlayerDestroy(worldIn, pos, state);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return super.getPackedLightmapCoords(state, source, pos);
		
	}
	
	@Override
	public float getExplosionResistance(Entity exploder) {
		
		return super.getExplosionResistance(exploder);
		
	}
	
	@Override
	public int tickRate(World worldIn) {
		
		return super.tickRate(worldIn);
		
	}
	
	@Override
	public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
		
		return super.modifyAcceleration(worldIn, pos, entityIn, motion);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
		
		return super.getRenderLayer();
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		
		return super.getSelectedBoundingBox(state, worldIn, pos);
		
	}
	
	@Override
	public boolean isCollidable() {
		
		return super.isCollidable();
		
	}
	
	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		
		return super.canCollideCheck(state, hitIfLiquid);
		
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		
		return super.canPlaceBlockAt(worldIn, pos);
		
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		
		super.onBlockAdded(worldIn, pos, state);
		
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		super.breakBlock(worldIn, pos, state);
		
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		
		super.onEntityWalk(worldIn, pos, entityIn);
		
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		
		super.updateTick(worldIn, pos, state, rand);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		
	}
	
	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		
		super.onExplosionDestroy(worldIn, pos, explosionIn);
		
	}
	
	@Override
	public boolean isTopSolid(IBlockState state) {
		
		return super.isTopSolid(state);
		
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		return super.getMapColor(state, worldIn, pos);
		
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		
	}
	
	@Override
	@Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		
		return super.collisionRayTrace(blockState, worldIn, pos, start, end);
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return super.getStateFromMeta(meta);
		
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return super.getMetaFromState(state);
		
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		return super.getActualState(state, worldIn, pos);
		
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		
		return super.withRotation(state, rot);
		
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		
		return super.withMirror(state, mirrorIn);
		
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return super.createBlockState();
		
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		
		return super.doesSideBlockRendering(state, world, pos, face);
		
	}
	
}
