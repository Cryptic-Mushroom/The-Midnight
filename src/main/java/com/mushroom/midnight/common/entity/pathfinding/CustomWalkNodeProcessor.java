package com.mushroom.midnight.common.entity.pathfinding;

import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class CustomWalkNodeProcessor extends NodeProcessor {
	private float avoidsWater;

	/** the method allows mobs to move in diagonals correctly (and not when it's not a good pathNode) */
	private boolean isValidForDiagonal(PathPoint pointStart, @Nullable PathPoint pointEnd) {
		/* this replaces the vanilla condition :
		   pointEnd == null || pointEnd.nodeType == PathNodeType.OPEN || pointEnd.costMalus != 0.0F
		   the check about y is because the tryToMoveXYZ is not able to jump in the case of 2 neighbours fullblock */
		return pointEnd != null && pointEnd.nodeType == PathNodeType.WALKABLE && pointEnd.costMalus == 0f && pointEnd.y <= pointStart.y;
	}

	@Override
	public void init(IBlockAccess sourceIn, EntityLiving mob) {
		super.init(sourceIn, mob);
		avoidsWater = mob.getPathPriority(PathNodeType.WATER);
	}

	@Override
	public void postProcess() {
		entity.setPathPriority(PathNodeType.WATER, avoidsWater);
		super.postProcess();
	}

	@Override
	public PathPoint getStart() {
		int i;
		if (getCanSwim() && entity.isInWater()) {
			i = (int)entity.getEntityBoundingBox().minY;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(entity.posX), i, MathHelper.floor(entity.posZ));
			for (Block block = blockaccess.getBlockState(blockpos$mutableblockpos).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = blockaccess.getBlockState(blockpos$mutableblockpos).getBlock()) {
				++i;
				blockpos$mutableblockpos.setPos(MathHelper.floor(entity.posX), i, MathHelper.floor(entity.posZ));
			}
		} else if (entity.onGround) {
			i = MathHelper.floor(entity.getEntityBoundingBox().minY + 0.5D);
		} else {
			BlockPos blockpos;
			for (blockpos = new BlockPos(entity); (blockaccess.getBlockState(blockpos).getMaterial() == Material.AIR || blockaccess.getBlockState(blockpos).getBlock().isPassable(blockaccess, blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down()) {
				;
			}
			i = blockpos.up().getY();
		}
		BlockPos blockpos2 = new BlockPos(entity);
		PathNodeType pathnodetype1 = getPathNodeType(entity, blockpos2.getX(), i, blockpos2.getZ());
		if (entity.getPathPriority(pathnodetype1) < 0.0F) {
			Set<BlockPos> set = Sets.newHashSet();
			set.add(new BlockPos(entity.getEntityBoundingBox().minX, (double)i, entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(entity.getEntityBoundingBox().minX, (double)i, entity.getEntityBoundingBox().maxZ));
			set.add(new BlockPos(entity.getEntityBoundingBox().maxX, (double)i, entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(entity.getEntityBoundingBox().maxX, (double)i, entity.getEntityBoundingBox().maxZ));
			for (BlockPos blockpos1 : set) {
				PathNodeType pathnodetype = getPathNodeType(entity, blockpos1);
				if (entity.getPathPriority(pathnodetype) >= 0.0F) {
					return openPoint(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
				}
			}
		}
		return openPoint(blockpos2.getX(), i, blockpos2.getZ());
	}

	@Override
	public PathPoint getPathPointToCoords(double x, double y, double z) {
		return openPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	@Override
	public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
		int optionIndex = 0;
		int stepHeight = 0;
		PathNodeType pathnodetype = getPathNodeType(entity, currentPoint.x, currentPoint.y + 1, currentPoint.z);
		if (entity.getPathPriority(pathnodetype) >= 0f) {
			stepHeight = MathHelper.floor(Math.max(1f, entity.stepHeight));
		}
		BlockPos blockpos = (new BlockPos(currentPoint.x, currentPoint.y, currentPoint.z)).down();
		double d0 = (double)currentPoint.y - (1d - blockaccess.getBlockState(blockpos).getBoundingBox(blockaccess, blockpos).maxY);
		PathPoint pointS = getSafePoint(currentPoint.x, currentPoint.y, currentPoint.z + 1, stepHeight, d0, EnumFacing.SOUTH);
		PathPoint pointW = getSafePoint(currentPoint.x - 1, currentPoint.y, currentPoint.z, stepHeight, d0, EnumFacing.WEST);
		PathPoint pointE = getSafePoint(currentPoint.x + 1, currentPoint.y, currentPoint.z, stepHeight, d0, EnumFacing.EAST);
		PathPoint pointN = getSafePoint(currentPoint.x, currentPoint.y, currentPoint.z - 1, stepHeight, d0, EnumFacing.NORTH);
		if (pointS != null && !pointS.visited && pointS.distanceTo(targetPoint) < maxDistance) {
			pathOptions[optionIndex++] = pointS;
		}
		if (pointW != null && !pointW.visited && pointW.distanceTo(targetPoint) < maxDistance) {
			pathOptions[optionIndex++] = pointW;
		}
		if (pointE != null && !pointE.visited && pointE.distanceTo(targetPoint) < maxDistance) {
			pathOptions[optionIndex++] = pointE;
		}
		if (pointN != null && !pointN.visited && pointN.distanceTo(targetPoint) < maxDistance) {
			pathOptions[optionIndex++] = pointN;
		}
		boolean allowDiagonalN = isValidForDiagonal(currentPoint, pointN);
		boolean allowDiagonalS = isValidForDiagonal(currentPoint, pointS);
		boolean allowDiagonalW = isValidForDiagonal(currentPoint, pointW);
		boolean allowDiagonalE = isValidForDiagonal(currentPoint, pointE);
		if (allowDiagonalN && allowDiagonalW) {
			PathPoint pointNW = getSafePoint(currentPoint.x - 1, currentPoint.y, currentPoint.z - 1, stepHeight, d0, EnumFacing.NORTH);
			if (pointNW != null && !pointNW.visited && pointNW.distanceTo(targetPoint) < maxDistance) {
				pathOptions[optionIndex++] = pointNW;
			}
		}
		if (allowDiagonalN && allowDiagonalE) {
			PathPoint pointNE = getSafePoint(currentPoint.x + 1, currentPoint.y, currentPoint.z - 1, stepHeight, d0, EnumFacing.NORTH);
			if (pointNE != null && !pointNE.visited && pointNE.distanceTo(targetPoint) < maxDistance) {
				pathOptions[optionIndex++] = pointNE;
			}
		}
		if (allowDiagonalS && allowDiagonalW) {
			PathPoint pointSW = getSafePoint(currentPoint.x - 1, currentPoint.y, currentPoint.z + 1, stepHeight, d0, EnumFacing.SOUTH);
			if (pointSW != null && !pointSW.visited && pointSW.distanceTo(targetPoint) < maxDistance) {
				pathOptions[optionIndex++] = pointSW;
			}
		}
		if (allowDiagonalS && allowDiagonalE) {
			PathPoint pointSE = getSafePoint(currentPoint.x + 1, currentPoint.y, currentPoint.z + 1, stepHeight, d0, EnumFacing.SOUTH);
			if (pointSE != null && !pointSE.visited && pointSE.distanceTo(targetPoint) < maxDistance) {
				pathOptions[optionIndex++] = pointSE;
			}
		}
		return optionIndex;
	}

	@Nullable
	private PathPoint getSafePoint(int x, int y, int z, int stepHeight, double currentGroundY, EnumFacing facing) {
		PathPoint point = null;
		BlockPos pos = new BlockPos(x, y, z);
		BlockPos ground = pos.down();
		double groundY = (double)y - (1.0D - blockaccess.getBlockState(ground).getBoundingBox(blockaccess, ground).maxY);
		if (groundY - currentGroundY > 1.125d) {
			return null;
		} else {
			PathNodeType placetype = getPathNodeType(entity, x, y, z);
			float priority = entity.getPathPriority(placetype);
			double halfWidth = (double)entity.width / 2d;
			if (priority >= 0f) {
				point = openPoint(x, y, z);
				point.nodeType = placetype;
				point.costMalus = Math.max(point.costMalus, priority);
			}
			if (placetype == PathNodeType.WALKABLE) {
				return point;
			} else {
				if (point == null && stepHeight > 0 && placetype != PathNodeType.FENCE && placetype != PathNodeType.TRAPDOOR) {
					point = getSafePoint(x, y + 1, z, stepHeight - 1, currentGroundY, facing);
					if (point != null && (point.nodeType == PathNodeType.OPEN || point.nodeType == PathNodeType.WALKABLE) && entity.width < 1f) {
						double d2 = (double)(x - facing.getXOffset()) + 0.5d;
						double d3 = (double)(z - facing.getZOffset()) + 0.5d;
						AxisAlignedBB axisalignedbb = new AxisAlignedBB(d2 - halfWidth, (double)y + 0.001d, d3 - halfWidth, d2 + halfWidth, (double)((float)y + entity.height), d3 + halfWidth);
						AxisAlignedBB axisalignedbb1 = blockaccess.getBlockState(pos).getBoundingBox(blockaccess, pos);
						AxisAlignedBB axisalignedbb2 = axisalignedbb.expand(0d, axisalignedbb1.maxY - 0.002d, 0d);
						if (entity.world.collidesWithAnyBlock(axisalignedbb2)) {
							point = null;
						}
					}
				}
				if (placetype == PathNodeType.OPEN) {
					AxisAlignedBB axisalignedbb3 = new AxisAlignedBB((double)x - halfWidth + 0.5d, (double)y + 0.001d, (double)z - halfWidth + 0.5d, (double)x + halfWidth + 0.5d, (double)((float)y + entity.height), (double)z + halfWidth + 0.5d);
					if (entity.world.collidesWithAnyBlock(axisalignedbb3)) {
						return null;
					}
					if (entity.width >= 1f) {
						PathNodeType pathnodetype1 = getPathNodeType(entity, x, y - 1, z);
						if (pathnodetype1 == PathNodeType.BLOCKED) {
							point = openPoint(x, y, z);
							point.nodeType = PathNodeType.WALKABLE;
							point.costMalus = Math.max(point.costMalus, priority);
							return point;
						}
					}
					int i = 0;
					while (y > 0 && placetype == PathNodeType.OPEN) {
						--y;
						if (i++ >= entity.getMaxFallHeight()) {
							return null;
						}
						placetype = getPathNodeType(entity, x, y, z);
						priority = entity.getPathPriority(placetype);
						if (placetype != PathNodeType.OPEN && priority >= 0f) {
							point = openPoint(x, y, z);
							point.nodeType = placetype;
							point.costMalus = Math.max(point.costMalus, priority);
							break;
						}
						if (priority < 0f) {
							return null;
						}
					}
				}
				return point;
			}
		}
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
		EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
		PathNodeType pathnodetype = PathNodeType.BLOCKED;
		double d0 = (double)entitylivingIn.width / 2d;
		BlockPos blockpos = new BlockPos(entitylivingIn);
		pathnodetype = getPathNodeType(blockaccessIn, x, y, z, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn, enumset, pathnodetype, blockpos);
		if (enumset.contains(PathNodeType.FENCE)) {
			return PathNodeType.FENCE;
		} else {
			PathNodeType pathnodetype1 = PathNodeType.BLOCKED;
			for (PathNodeType pathnodetype2 : enumset) {
				if (entitylivingIn.getPathPriority(pathnodetype2) < 0f) {
					return pathnodetype2;
				}
				if (entitylivingIn.getPathPriority(pathnodetype2) >= entitylivingIn.getPathPriority(pathnodetype1)) {
					pathnodetype1 = pathnodetype2;
				}
			}
			if (pathnodetype == PathNodeType.OPEN && entitylivingIn.getPathPriority(pathnodetype1) == 0f) {
				return PathNodeType.OPEN;
			} else {
				return pathnodetype1;
			}
		}
	}

	private PathNodeType getPathNodeType(IBlockAccess p_193577_1_, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoorsIn, boolean canEnterDoorsIn, EnumSet<PathNodeType> p_193577_10_, PathNodeType p_193577_11_, BlockPos p_193577_12_) {
		for (int i = 0; i < xSize; ++i) {
			for (int j = 0; j < ySize; ++j) {
				for (int k = 0; k < zSize; ++k) {
					int l = i + x;
					int i1 = j + y;
					int j1 = k + z;
					PathNodeType pathnodetype = getPathNodeType(p_193577_1_, l, i1, j1);
					if (pathnodetype == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoorsIn && canEnterDoorsIn) {
						pathnodetype = PathNodeType.WALKABLE;
					}
					if (pathnodetype == PathNodeType.DOOR_OPEN && !canEnterDoorsIn) {
						pathnodetype = PathNodeType.BLOCKED;
					}
					if (pathnodetype == PathNodeType.RAIL && !(p_193577_1_.getBlockState(p_193577_12_).getBlock() instanceof BlockRailBase) && !(p_193577_1_.getBlockState(p_193577_12_.down()).getBlock() instanceof BlockRailBase)) {
						pathnodetype = PathNodeType.FENCE;
					}
					if (i == 0 && j == 0 && k == 0) {
						p_193577_11_ = pathnodetype;
					}
					p_193577_10_.add(pathnodetype);
				}
			}
		}
		return p_193577_11_;
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, BlockPos pos) {
		return getPathNodeType(entitylivingIn, pos.getX(), pos.getY(), pos.getZ());
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, int x, int y, int z) {
		return getPathNodeType(blockaccess, x, y, z, entitylivingIn, entitySizeX, entitySizeY, entitySizeZ, getCanOpenDoors(), getCanEnterDoors());
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
		PathNodeType pathnodetype = getPathNodeTypeRaw(blockaccessIn, x, y, z);
		if (pathnodetype == PathNodeType.OPEN && y >= 1) {
			Block block = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
			PathNodeType pathnodetype1 = getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
			pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER && pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
			if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.MAGMA) {
				pathnodetype = PathNodeType.DAMAGE_FIRE;
			}
			if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
				pathnodetype = PathNodeType.DAMAGE_CACTUS;
			}
		}
		pathnodetype = checkNeighborBlocks(blockaccessIn, x, y, z, pathnodetype);
		return pathnodetype;
	}

	private PathNodeType checkNeighborBlocks(IBlockAccess p_193578_1_, int p_193578_2_, int p_193578_3_, int p_193578_4_, PathNodeType p_193578_5_) {
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
		if (p_193578_5_ == PathNodeType.WALKABLE) {
			for (int i = -1; i <= 1; ++i) {
				for (int j = -1; j <= 1; ++j) {
					if (i != 0 || j != 0) {
						Block block = p_193578_1_.getBlockState(blockpos$pooledmutableblockpos.setPos(i + p_193578_2_, p_193578_3_, j + p_193578_4_)).getBlock();
						if (block == Blocks.CACTUS) {
							p_193578_5_ = PathNodeType.DANGER_CACTUS;
						} else if (block == Blocks.FIRE) {
							p_193578_5_ = PathNodeType.DANGER_FIRE;
						} else if(block.isBurning(p_193578_1_,blockpos$pooledmutableblockpos)) {
							p_193578_5_ = PathNodeType.DAMAGE_FIRE;
						}
					}
				}
			}
		}
		blockpos$pooledmutableblockpos.release();
		return p_193578_5_;
	}

	private PathNodeType getPathNodeTypeRaw(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
		BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
		IBlockState iblockstate = p_189553_1_.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		Material material = iblockstate.getMaterial();
		PathNodeType type = block.getAiPathNodeType(iblockstate, p_189553_1_, blockpos);
		if (type != null) return type;
		if (material == Material.AIR) {
			return PathNodeType.OPEN;
		} else if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY) {
			if (block == Blocks.FIRE) {
				return PathNodeType.DAMAGE_FIRE;
			} else if (block == Blocks.CACTUS) {
				return PathNodeType.DAMAGE_CACTUS;
			} else if (block instanceof BlockDoor && material == Material.WOOD && !iblockstate.getValue(BlockDoor.OPEN)) {
				return PathNodeType.DOOR_WOOD_CLOSED;
			} else if (block instanceof BlockDoor && material == Material.IRON && !iblockstate.getValue(BlockDoor.OPEN)) {
				return PathNodeType.DOOR_IRON_CLOSED;
			} else if (block instanceof BlockDoor && iblockstate.getValue(BlockDoor.OPEN)) {
				return PathNodeType.DOOR_OPEN;
			} else if (block instanceof BlockRailBase) {
				return PathNodeType.RAIL;
			} else if (!(block instanceof BlockFence) && !(block instanceof BlockWall) && (!(block instanceof BlockFenceGate) || iblockstate.getValue(BlockFenceGate.OPEN))) {
				if (material == Material.WATER) {
					return PathNodeType.WATER;
				} else if (material == Material.LAVA) {
					return PathNodeType.LAVA;
				} else {
					return block.isPassable(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
				}
			} else {
				return PathNodeType.FENCE;
			}
		} else {
			return PathNodeType.TRAPDOOR;
		}
	}
}
