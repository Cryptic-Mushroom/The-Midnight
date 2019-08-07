package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.function.Function;

public class MediumDewshroomFeature extends TemplateFungiFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/dewshroom_medium")
    };

    private static final BlockState STEM = MidnightBlocks.DEWSHROOM_STEM.getDefaultState();
    private static final BlockState HAT = MidnightBlocks.DEWSHROOM_HAT.getDefaultState();
    private static final BlockState[] ROOTS = new BlockState[] {
            MidnightBlocks.DEWSHROOM_ROOTS.getDefaultState(),
            MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS.getDefaultState()
    };

    public MediumDewshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, STEM, HAT, ROOTS);
    }
}
