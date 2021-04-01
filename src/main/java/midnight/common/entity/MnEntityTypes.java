/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.entity;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@ObjectHolder("midnight")
public final class MnEntityTypes {
    private static final Map<ResourceLocation, EntityType<?>> TYPES = new HashMap<>();

    public static final EntityType<ThrownGeodeEntity> THROWN_GEODE = type(
        "thrown_geode",
        EntityType.Builder.of(ThrownGeodeEntity::new, EntityClassification.MISC),
        ThrownGeodeEntity.class
    );

    public static void registerEntityTypes(IRegistry<EntityType<?>> registry) {
        registry.registerAll(TYPES);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(THROWN_GEODE, manager -> new SpriteRenderer<>(manager, itemRenderer, 1, true));
    }

    private MnEntityTypes() {
    }

    // Suppress unused 'cls' parameter, and a bug in IntelliJ that thinks that the 'unused' suppression is unused
    // Thanks IntelliJ
    @SuppressWarnings({"unused", "RedundantSuppression"})
    private static <T extends Entity> EntityType<T> type(String id, EntityType.Builder<T> builder, Class<T> cls) {
        EntityType<T> type = builder.build(Midnight.idStr(id));
        type.setRegistryName(Midnight.id(id));
        TYPES.put(Midnight.id(id), type);
        return type;
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T extends Entity> EntityType<T> inj() {
        return null;
    }
}
