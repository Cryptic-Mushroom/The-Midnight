/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.handler;

import midnight.MnConstants;
import midnight.common.Midnight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * Event handler responsible for sending the warning message about the incompleteness of 0.6.0.
 *
 * @author Jonathing
 * @author Shadew
 * @since 0.6.0
 */
public final class WarningMessageHandler {
    private static final Marker MARKER = MarkerManager.getMarker("WarningMessageHandler");

    // Only meant for server at the current moment, since the event fires twice.
    private static boolean warningShown = false;

    private WarningMessageHandler() {
    }

    public static void addEventListeners(IEventBus mod, IEventBus forge) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> forge.addListener(WarningMessageHandler::warningForClient));
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> forge.addListener(WarningMessageHandler::warningForServer));
    }

    /**
     * This method is fired whenever {@link ClientPlayerNetworkEvent.LoggedInEvent} is fired on a {@link Dist#CLIENT}.
     * It is currently used to display the warning message to the player when logging into a world. The message is also
     * sent to the console at {@link Level#WARN} level.
     */
    private static void warningForClient(ClientPlayerNetworkEvent.LoggedInEvent event) {
        ClientPlayerEntity player = event.getPlayer();

        // Null-check the player
        if (player != null) {
            Midnight.LOGGER.warn(MARKER, MnConstants.DEV_WARNING);
            Minecraft.getInstance().gui.getChat().addMessage(
                new TranslationTextComponent(MnConstants.DEV_WARNING).withStyle(TextFormatting.RED)
            );
        }
    }

    /**
     * This method is fired whenever an {@link FMLServerStartingEvent} is fired on a {@link Dist#DEDICATED_SERVER}. It
     * is currently used to display the warning message to the server owner when the world has loaded. The message is
     * sent at {@link Level#WARN} level.
     */
    private static void warningForServer(FMLServerStartingEvent event) {
        if (!warningShown) {
            Midnight.LOGGER.warn(MARKER, MnConstants.DEV_WARNING);
            warningShown = true;
        }
    }
}
