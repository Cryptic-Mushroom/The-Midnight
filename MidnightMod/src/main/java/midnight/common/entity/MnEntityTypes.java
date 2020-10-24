/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

@ObjectHolder("midnight")
public final class MnEntityTypes {
    public static final EntityType<ThrownGeodeEntity> THROWN_GEODE = inj();

    public static void registerEntityTypes(IRegistry<EntityType<?>> registry) {
        registry.registerAll(
            type("thrown_geode", EntityType.Builder.create(ThrownGeodeEntity::new, EntityClassification.MISC), ThrownGeodeEntity.class)
        );
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(THROWN_GEODE, manager -> new SpriteRenderer<>(manager, itemRenderer, 1, true));
    }

    private MnEntityTypes() {
    }

    private static <T extends Entity> EntityType<T> type(String id, EntityType.Builder<T> builder, Class<T> cls) {
        EntityType<T> type = builder.build(Midnight.resStr(id));
        type.setRegistryName(Midnight.resLoc(id));
        return type;
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T extends Entity> EntityType<T> inj() {
        return null;
    }
}
