package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.function.Function;

public class LargeNightshroomFeature extends TemplateFungiFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/nightshroom_large")
    };

    private static final BlockState STEM = MidnightBlocks.NIGHTSHROOM_STEM.getDefaultState();
    private static final BlockState HAT = MidnightBlocks.NIGHTSHROOM_HAT.getDefaultState();
    private static final BlockState[] ROOTS = new BlockState[] {
            MidnightBlocks.NIGHTSHROOM_ROOTS.getDefaultState(),
            MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS.getDefaultState()
    };

    public LargeNightshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, STEM, HAT, ROOTS);
    }
}
