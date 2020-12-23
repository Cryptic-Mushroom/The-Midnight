/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.handler;

import midnight.MnConstants;
import midnight.MnInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Event handler responsible for sending the warning message about the incompleteness of 0.6.0.
 *
 * @author Jonathing
 * @author Shadew
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = MnInfo.MODID)
public final class WarningMessageHandler {
    private static final Logger LOGGER = LogManager.getLogger("Midnight/WarningMessageHandler");

    // Only meant for server at the current moment, since the event fires twice.
    private static boolean warningShown = false;

    private WarningMessageHandler() {
    }

    /**
     * This method is fired whenever LoggedInEvent is called. It is currently used to display the warning message to the
     * player when logging into a world. The message is also sent to the console at WARN level.
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void loggedInEvent(ClientPlayerNetworkEvent.LoggedInEvent event) {
        ClientPlayerEntity player = event.getPlayer();

        // Null-check the player
        if (player != null) {
            LOGGER.warn(MnConstants.DEV_WARNING);
            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(
                new TranslationTextComponent(MnConstants.DEV_WARNING).formatted(TextFormatting.RED)
            );
        }
    }

    /**
     * This method is fired whenever FMLServerStartedEvent is called. It is currently used to display the warning
     * message to the server owner when the world has loaded. The message is sent at WARN level.
     */
    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void serverStarting(FMLServerStartingEvent event) {
        if (!warningShown) {
            LOGGER.warn(MnConstants.DEV_WARNING);
            warningShown = true;
        }
    }
}
