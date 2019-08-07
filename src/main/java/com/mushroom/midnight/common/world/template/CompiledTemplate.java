package com.mushroom.midnight.common.world.template;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CompiledTemplate {
    public final ResourceLocation templateId;
    private final Template template;
    public final PlacementSettings settings;
    public final BlockPos origin;

    private final TemplateMarkerProcessor markerProcessor;
    private final Collection<TemplatePostProcessor> postProcessors;

    public final TemplateMarkers markers;

    CompiledTemplate(
            ResourceLocation templateId,
            Template template, PlacementSettings settings, BlockPos origin,
            TemplateMarkerProcessor markerProcessor,
            Collection<TemplatePostProcessor> postProcessors
    ) {
        this.templateId = templateId;
        this.template = template;
        this.settings = settings;
        this.origin = origin;

        this.markerProcessor = markerProcessor;
        this.postProcessors = postProcessors;

        this.markers = TemplateCompiler.compileMarkers(origin, settings, template);
    }

    public void addTo(IWorld world, Random random, int flags) {
        this.template.addBlocksToWorld(world, this.origin, this.settings, flags);
        this.markers.forEachReplacement((pos, state) -> world.setBlockState(pos, state, flags));

        if (this.markerProcessor != null) {
            this.markers.forEach((marker, pos) -> this.markerProcessor.process(world, pos, marker));
        }

        if (!this.postProcessors.isEmpty()) {
            List<Template.BlockInfo> blocks = Template.func_215387_a(world, this.origin, this.settings, this.settings.func_204764_a(this.template.blocks, this.origin));
            for (TemplatePostProcessor processor : this.postProcessors) {
                for (Template.BlockInfo info : blocks) {
                    processor.process(this, world, random, info.pos, info.state);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "CompiledTemplate{" +
                "template=" + this.templateId +
                ", origin=" + this.origin +
                '}';
    }
}
