package com.mushroom.midnight.common.world.feature.tree;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class ShadowrootTreeFeature extends MidnightTreeFeature {
    private static final Direction[] HORIZONTALS = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

    private static final BlockState LOG = MidnightBlocks.SHADOWROOT_LOG.getDefaultState();
    private static final BlockState LEAVES = MidnightBlocks.SHADOWROOT_LEAVES.getDefaultState();

    private static final int BRANCH_SPACING = 3;

    public ShadowrootTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
        this.setSapling((IPlantable) MidnightBlocks.SHADOWROOT_SAPLING);
    }

    @Override
    protected boolean place(IWorld world, Random random, BlockPos origin) {
        int height = random.nextInt(8) + 10;

        if (!this.canFit(world, origin, 1, height)) {
            return false;
        }

        if (isSoil(world, origin.down(), this.getSapling())) {
            this.setDirtAt(world, origin.down(), origin);

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(origin);
            for (int localY = 0; localY < height; localY++) {
                mutablePos.setY(origin.getY() + localY);
                this.setBlockState(world, mutablePos, LOG);
            }

            this.generateRoots(world, random, origin);

            Set<Branch> branches = this.collectBranches(world, random, origin, height);
            for (Branch branch : branches) {
                this.setBlockState(world, branch.pos, LOG.with(LogBlock.AXIS, branch.getAxis()));
            }

            Set<BlockPos> leafPositions = this.produceBlob(origin.up(height - 2), 1.5, 2.5);
            leafPositions = this.droopLeaves(leafPositions, random, 4);

            for (Branch branch : branches) {
                leafPositions.addAll(this.collectBranchLeaves(branch, random));
            }

            for (BlockPos leafPos : leafPositions) {
                this.setBlockState(world, leafPos, LEAVES);
            }

            return true;
        }

        return false;
    }

    private boolean canFit(IWorld world, BlockPos origin, int width, int height) {
        BlockPos min = origin.add(-width, 0, -width);
        BlockPos max = origin.add(width, height, width);

        for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
            if (!canGrowInto(world, pos)) {
                return false;
            }
        }

        return true;
    }

    private void generateRoots(IWorldGenerationReader world, Random random, BlockPos origin) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        List<Direction> availableSides = Lists.newArrayList(HORIZONTALS);

        int count = random.nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            Direction side = availableSides.remove(random.nextInt(availableSides.size()));
            BlockPos rootOrigin = origin.offset(side);

            int height = random.nextInt(3) + 1;
            for (int localY = 0; localY < height; localY++) {
                mutablePos.setPos(rootOrigin.getX(), rootOrigin.getY() + localY, rootOrigin.getZ());
                this.setBlockState(world, mutablePos, LOG);
            }
        }
    }

    private Set<BlockPos> droopLeaves(Set<BlockPos> leafPositions, Random random, int amount) {
        Set<BlockPos> droopedLeaves = new HashSet<>(leafPositions);
        for (BlockPos pos : leafPositions) {
            int droopAmount = random.nextInt(amount);
            for (int i = 0; i < droopAmount; i++) {
                droopedLeaves.add(pos.down(i));
            }
        }

        return droopedLeaves;
    }

    private Set<Branch> collectBranches(IWorldGenerationReader world, Random random, BlockPos origin, int height) {
        int minBranchHeight = 3;
        int maxBranchHeight = height - 4;
        int heightRange = maxBranchHeight - minBranchHeight;

        Set<Branch> branches = new HashSet<>();

        int branchCount = heightRange / BRANCH_SPACING;
        double normalizedSpacing = (double) heightRange / branchCount;

        Direction lastDirection = null;

        for (int branch = 0; branch < branchCount; branch++) {
            int y = MathHelper.ceil(minBranchHeight + 1 + branch * normalizedSpacing);

            Direction direction = null;
            while (direction == null || direction == lastDirection) {
                direction = HORIZONTALS[random.nextInt(HORIZONTALS.length)];
            }
            lastDirection = direction;

            BlockPos branchPos = origin.up(y).offset(direction);
            if (!isAirOrLeaves(world, branchPos)) {
                continue;
            }

            float outerAngle = direction.getHorizontalAngle();
            float angle = (random.nextFloat() * 90.0F) + outerAngle - 45.0F;

            branches.add(new Branch(branchPos, direction, angle));
        }

        return branches;
    }

    private Set<BlockPos> collectBranchLeaves(Branch branch, Random random) {
        Set<BlockPos> branchLeaves = new HashSet<>();

        float theta = (float) Math.toRadians(branch.angle);
        float deltaX = -MathHelper.sin(theta);
        float deltaZ = MathHelper.cos(theta);

        for (int step = 0; step < 3; step++) {
            int droop = -step / 2 + 1;
            BlockPos origin = branch.pos.add(deltaX * step, droop, deltaZ * step);

            Set<BlockPos> stepLeaves = this.produceBlob(origin, 1.0);
            branchLeaves.addAll(this.droopLeaves(stepLeaves, random, step + 1));
        }

        return branchLeaves;
    }

    private static class Branch {
        final BlockPos pos;
        final Direction direction;
        final float angle;

        private Branch(BlockPos pos, Direction direction, float angle) {
            this.pos = pos;
            this.direction = direction;
            this.angle = angle;
        }

        public Direction.Axis getAxis() {
            return this.direction.getAxis();
        }

        @Override
        public int hashCode() {
            return this.pos.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Branch && ((Branch) obj).pos.equals(this.pos);
        }
    }
}
