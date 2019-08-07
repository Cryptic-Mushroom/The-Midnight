package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.util.WorldUtil;
import com.mushroom.midnight.common.world.template.CompiledTemplate;
import com.mushroom.midnight.common.world.template.RotatedSettingConfigurator;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import com.mushroom.midnight.common.world.template.TemplateMarkers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public abstract class TemplateTreeFeature extends MidnightTreeFeature {
    private static final String ANCHOR_MARKER = "origin";
    private static final String TRUNK_TOP_MARKER = "trunk_top";
    private static final String TRUNK_CORNER_MARKER = "trunk_corner";

    protected final ResourceLocation[] templates;
    protected final BlockState log;
    protected final BlockState leaf;

    private TemplateCompiler templateCompiler;

    protected TemplateTreeFeature(
            Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize,
            ResourceLocation[] templates,
            BlockState log, BlockState leaf
    ) {
        super(deserialize);
        this.templates = templates;
        this.log = log;
        this.leaf = leaf;
    }

    protected TemplateCompiler buildCompiler() {
        return TemplateCompiler.of(this.templates)
                .withAnchor(ANCHOR_MARKER)
                .withSettingConfigurator(RotatedSettingConfigurator.INSTANCE)
                .withProcessor(this::processState);
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
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxCorner)) {
            if (!isSoil(world, pos.down(), this.getSapling())) {
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
}
