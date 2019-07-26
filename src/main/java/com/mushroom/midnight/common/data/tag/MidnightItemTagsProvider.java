package com.mushroom.midnight.common.data.tag;

import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

public class MidnightItemTagsProvider extends ItemTagsProvider {
    public MidnightItemTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void registerTags() {
        this.copy(MidnightTags.Blocks.BOGSHROOM, MidnightTags.Items.BOGSHROOM);
        this.copy(MidnightTags.Blocks.DEWSHROOM, MidnightTags.Items.DEWSHROOM);
        this.copy(MidnightTags.Blocks.NIGHTSHROOM, MidnightTags.Items.NIGHTSHROOM);
        this.copy(MidnightTags.Blocks.VIRIDSHROOM, MidnightTags.Items.VIRIDSHROOM);
        this.copy(MidnightTags.Blocks.FUNGI_STEMS, MidnightTags.Items.FUNGI_STEMS);
        this.copy(MidnightTags.Blocks.LOGS, MidnightTags.Items.LOGS);

        this.getBuilder(MidnightTags.Items.SPORE_BOMBS).add(
                MidnightItems.NIGHTSHROOM_SPORE_BOMB,
                MidnightItems.VIRIDSHROOM_SPORE_BOMB,
                MidnightItems.DEWSHROOM_SPORE_BOMB,
                MidnightItems.BOGSHROOM_SPORE_BOMB
        );

        this.getBuilder(MidnightTags.Items.STICKS).add(MidnightItems.DARK_STICK);

        this.getBuilder(MidnightTags.Items.UNSTABLE_FRUITS).add(
                MidnightItems.BLUE_UNSTABLE_FRUIT,
                MidnightItems.GREEN_UNSTABLE_FRUIT,
                MidnightItems.LIME_UNSTABLE_FRUIT
        );
    }

    @Override
    public String getName() {
        return "Midnight Item Tags";
    }
}
