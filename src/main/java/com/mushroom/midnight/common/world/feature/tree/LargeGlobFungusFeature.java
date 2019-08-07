package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.function.Function;

public class LargeGlobFungusFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/glob_fungus_1"),
            new ResourceLocation(Midnight.MODID, "mushroom/glob_fungus_2")
    };

    private static final BlockState LOG = MidnightBlocks.GLOB_FUNGUS_STEM.getDefaultState();
    private static final BlockState LEAVES = MidnightBlocks.GLOB_FUNGUS_HAT.getDefaultState();

    public LargeGlobFungusFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, LOG, LEAVES);
        this.setSapling((IPlantable) MidnightBlocks.GLOB_FUNGUS);
    }
}
