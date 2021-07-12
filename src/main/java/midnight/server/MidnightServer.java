/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.server;

import midnight.common.Midnight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

/**
 * The dedicated-server-only main class of the Midnight, just to indicate the dedicated server. Everything just
 * delegates to {@link Midnight} currently.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightServer extends Midnight {
    @Override
    public Dist getRuntimeDist() {
        return Dist.DEDICATED_SERVER;
    }

    /**
     * @return The direct instance of {@link MidnightServer}, or throws a {@link ClassCastException} when not running on
     *     the dedicated server (that would already have caused a class loading failure in most cases).
     */
    public static MidnightServer get() {
        return (MidnightServer) Midnight.get();
    }

    public static void addEventListeners(IEventBus mod, IEventBus forge) {
    }

    @Override
    public DynamicRegistries getDynamicRegistries() {
        return ((MinecraftServer) LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER)).registryAccess();
    }
}
