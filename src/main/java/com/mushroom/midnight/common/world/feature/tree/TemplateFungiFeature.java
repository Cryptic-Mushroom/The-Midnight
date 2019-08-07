package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.util.WorldUtil;
import com.mushroom.midnight.common.world.template.CompiledTemplate;
import com.mushroom.midnight.common.world.template.ExtendRootsProcessor;
import com.mushroom.midnight.common.world.template.RootsAttachProcessor;
import com.mushroom.midnight.common.world.template.RotatedSettingConfigurator;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import com.mushroom.midnight.common.world.template.TemplateMarkers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.common.IPlantable;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public abstract class TemplateFungiFeature extends MidnightTreeFeature {
    private static final int MAX_DEPTH = 4;

    private static final String ANCHOR_MARKER = "origin";
    private static final String TRUNK_TOP_MARKER = "trunk_top";
    private static final String TRUNK_CORNER_MARKER = "trunk_corner";

    protected final ResourceLocation[] templates;
    protected final BlockState stem;
    protected final BlockState hat;
    protected final BlockState[] roots;

    private TemplateCompiler templateCompiler;

    protected TemplateFungiFeature(
            Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize,
            ResourceLocation[] templates,
            BlockState stem, BlockState hat,
            BlockState[] roots
    ) {
        super(deserialize);
        this.templates = templates;
        this.stem = stem;
        this.hat = hat;
        this.roots = roots;

        this.setSapling((IPlantable) MidnightBlocks.NIGHTSHROOM);
    }

    protected TemplateCompiler buildCompiler() {
        return TemplateCompiler.of(this.templates)
                .withAnchor(ANCHOR_MARKER)
                .withSettingConfigurator(RotatedSettingConfigurator.INSTANCE)
                .withProcessor(this::processState)
                .withPostProcessor(new ExtendRootsProcessor(this.stem))
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf, ShelfAttachProcessor.FOREST_SHELF_BLOCKS))
                .withPostProcessor(new RootsAttachProcessor(6, this.roots));
    }

    @Override
    protected boolean place(IWorld world, Random random, BlockPos origin) {
        if (!isSoil(world, origin.down(), this.getSapling())) {
            return false;
        }

        if (this.templateCompiler == null) {
            this.templateCompiler = this.buildCompiler();
        }

        CompiledTemplate template = this.templateCompiler.compile(world, random, origin);
        TemplateMarkers markers = template.markers;

        BlockPos anchor = markers.lookupAny(ANCHOR_MARKER);
        BlockPos trunkTop = markers.lookupAny(TRUNK_TOP_MARKER);
        Collection<BlockPos> trunkCorners = markers.lookup(TRUNK_CORNER_MARKER);
        if (trunkTop == null || trunkCorners.isEmpty()) {
            Midnight.LOGGER.warn("Template '{}' did not have required '{}' and '{}' data blocks", template, TRUNK_TOP_MARKER, TRUNK_CORNER_MARKER);
            return false;
        }

        BlockPos minCorner = WorldUtil.min(trunkCorners);
        BlockPos maxCorner = WorldUtil.max(trunkCorners);

        minCorner = new BlockPos(minCorner.getX() + 1, anchor.getY(), minCorner.getZ() + 1);
        maxCorner = new BlockPos(maxCorner.getX() - 1, anchor.getY(), maxCorner.getZ() - 1);

        if (!this.canGrow(world, minCorner, maxCorner) || !this.canFit(world, trunkTop, minCorner, maxCorner)) {
            return false;
        }

        this.setDirtAt(world, origin.down(), origin);

        template.addTo(world, random, 2 | 16);

        return true;
    }

    protected boolean canGrow(IWorld world, BlockPos minCorner, BlockPos maxCorner) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxCorner)) {
            mutablePos.setPos(pos);
            mutablePos.move(Direction.DOWN);

            int depth = 0;
            while (isAirOrLeaves(world, mutablePos)) {
                mutablePos.move(Direction.DOWN);
                if (depth++ >= MAX_DEPTH) {
                    return false;
                }
            }

            if (!isSoil(world, mutablePos, this.getSapling())) {
                return false;
            }
        }

        return true;
    }

    protected boolean canFit(IWorld world, BlockPos trunkTop, BlockPos minCorner, BlockPos maxCorner) {
        BlockPos maxFit = new BlockPos(maxCorner.getX(), trunkTop.getY(), maxCorner.getZ());

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxFit)) {
            if (!canGrowInto(world, pos)) {
                return false;
            }
        }

        return true;
    }

    protected Template.BlockInfo processState(IWorldReader world, BlockPos origin, Template.BlockInfo srcInfo, Template.BlockInfo info, PlacementSettings settings) {
        Block block = info.state.getBlock();
        return block == Blocks.STRUCTURE_BLOCK || block == Blocks.AIR ? null : info;
    }

    private boolean canPlaceShelf(IWorld world, BlockPos pos) {
        if (World.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == MidnightBlocks.FUNGI_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.isIn(BlockTags.LEAVES) || state.getMaterial() == Material.PLANTS;
    }
}
