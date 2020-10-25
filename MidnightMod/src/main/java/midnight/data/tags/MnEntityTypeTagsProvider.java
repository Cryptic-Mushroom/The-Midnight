/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.data.tags;

import midnight.common.misc.tags.MnEntityTypeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class MnEntityTypeTagsProvider extends TagsProvider<EntityType<?>> {
    @SuppressWarnings("deprecation") // We need Registry.ENTITY_TYPE. Sorry Forge...
    public MnEntityTypeTagsProvider(DataGenerator gen) {
        super(gen, Registry.ENTITY_TYPE);
    }

    @Override
    protected void registerTags() {
        getOrCreateTagBuilder(MnEntityTypeTags.IGNORE_MUD);
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/entity_types/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight - Entity type tags";
    }
}
