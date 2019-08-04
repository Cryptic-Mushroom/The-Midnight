package com.mushroom.midnight.common.world.template;

import net.minecraft.block.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class TemplateCompiler {
    private final List<ResourceLocation> templates = new ArrayList<>();
    private String anchorKey;

    private final Collection<BiConsumer<PlacementSettings, Random>> settingConfigurators = new ArrayList<>();
    private final Collection<StructureProcessor> processors = new ArrayList<>();
    private TemplateMarkerProcessor markerProcessor;
    private final Collection<TemplatePostProcessor> postProcessors = new ArrayList<>();

    public static TemplateCompiler of(ResourceLocation... templates) {
        return new TemplateCompiler().withTemplates(templates);
    }

    public TemplateCompiler withTemplates(ResourceLocation... templates) {
        Collections.addAll(this.templates, templates);
        return this;
    }

    public TemplateCompiler withSettingConfigurator(BiConsumer<PlacementSettings, Random> configurator) {
        this.settingConfigurators.add(configurator);
        return this;
    }

    public TemplateCompiler withProcessor(RuntimeStructureProcessor.Function processor) {
        this.processors.add(new RuntimeStructureProcessor(processor));
        return this;
    }

    public TemplateCompiler withMarkerProcessor(TemplateMarkerProcessor processor) {
        this.markerProcessor = processor;
        return this;
    }

    public TemplateCompiler withPostProcessor(TemplatePostProcessor processor) {
        this.postProcessors.add(processor);
        return this;
    }

    public TemplateCompiler withAnchor(String key) {
        if (this.anchorKey != null) {
            throw new IllegalStateException("Template is already anchored to '" + this.anchorKey + "'");
        }
        this.anchorKey = key;
        return this;
    }

    public CompiledTemplate compile(IWorld world, Random random, BlockPos origin) {
        World rootWorld = world.getWorld();
        if (!(rootWorld instanceof ServerWorld)) {
            throw new IllegalArgumentException("Cannot load template on " + rootWorld);
        }

        ResourceLocation templateId = this.templates.get(random.nextInt(this.templates.size()));

        TemplateManager templateManager = ((ServerWorld) rootWorld).getStructureTemplateManager();

        PlacementSettings settings = this.buildPlacementSettings(random);
        Template template = templateManager.getTemplate(templateId);

        TemplateMarkers markers = compileMarkers(BlockPos.ZERO, settings, template);

        BlockPos anchor = this.computeAnchor(markers);
        BlockPos anchoredOrigin = anchor != null ? origin.subtract(anchor) : origin;

        return new CompiledTemplate(templateId, template, settings, anchoredOrigin, this.markerProcessor, this.postProcessors);
    }

    @Nullable
    private BlockPos computeAnchor(TemplateMarkers markers) {
        if (this.anchorKey == null) {
            return null;
        }
        return markers.lookupAny(this.anchorKey);
    }

    private PlacementSettings buildPlacementSettings(Random random) {
        PlacementSettings settings = new PlacementSettings();
        for (BiConsumer<PlacementSettings, Random> configurator : this.settingConfigurators) {
            configurator.accept(settings, random);
        }
        this.processors.forEach(settings::addProcessor);
        return settings;
    }

    public static TemplateMarkers compileMarkers(BlockPos origin, PlacementSettings settings, Template template) {
        TemplateMarkers.Builder builder = TemplateMarkers.builder();

        List<Template.BlockInfo> structureBlocks = template.func_215381_a(origin, settings, Blocks.STRUCTURE_BLOCK);
        for (Template.BlockInfo info : structureBlocks) {
            if (info.nbt != null) {
                StructureMode mode = StructureMode.valueOf(info.nbt.getString("mode"));
                if (mode == StructureMode.DATA) {
                    String metadata = info.nbt.getString("metadata");
                    builder.add(info.pos, metadata);
                }
            }
        }

        return builder.build();
    }
}
