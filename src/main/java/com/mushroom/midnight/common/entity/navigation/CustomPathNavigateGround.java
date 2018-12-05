package com.mushroom.midnight.common.entity.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CustomPathNavigateGround extends PathNavigateGround {
    public CustomPathNavigateGround(EntityLiving owner, World world) {
        super(owner, world);
    }

    @Override
    protected PathFinder getPathFinder() {
        nodeProcessor = new WalkNodeProcessor() {
            /**
             * this replaces the vanilla condition : pointEnd == null || pointEnd.nodeType == PathNodeType.OPEN || pointEnd.costMalus != 0.0F
             * this fix allows creature to move in diagonals correctly
             * the check about y is because the tryToMoveXYZ is not able to jump in the case of 2 neighbours fullblock
             */
            private boolean isValidForDiagonal(PathPoint pointStart, @Nullable PathPoint pointEnd) {
                return pointEnd != null && pointEnd.nodeType == PathNodeType.WALKABLE && pointEnd.costMalus == 0f && pointEnd.y <= pointStart.y;
            }

            @Override
            public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
                int optionIndex = 0;
                int stepHeight = 0;
                PathNodeType pathnodetype = getPathNodeType(blockaccess, currentPoint.x, currentPoint.y + 1, currentPoint.z, entity, entitySizeX, entitySizeY, entitySizeZ, getCanOpenDoors(), getCanEnterDoors());
                if (entity.getPathPriority(pathnodetype) >= 0f) {
                    stepHeight = MathHelper.floor(Math.max(1f, entity.stepHeight));
                }
                BlockPos blockpos = (new BlockPos(currentPoint.x, currentPoint.y, currentPoint.z)).down();
                double d0 = currentPoint.y - (1.0 - blockaccess.getBlockState(blockpos).getBoundingBox(blockaccess, blockpos).maxY);
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
        };
        nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(nodeProcessor);
    }
}
