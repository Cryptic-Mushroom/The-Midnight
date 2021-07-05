/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight;

import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.data.MidnightData;
import midnight.server.MidnightServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.*;

/**
 * Bootstrap class of the Midnight. This is the first class of the Midnight that is being loaded by Forge. From here we
 * start interfacing with Minecraft.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
@Mod(MnInfo.MOD_ID)
public class MidnightMod {
    public static final Logger LOGGER = LogManager.getLogger("Midnight Mod");
    private static final Marker MARKER = MarkerManager.getMarker("Init");

    /**
     * The general {@link Midnight} instance. Don't use - use {@link Midnight#get()} instead.
     */
    public static final Midnight MIDNIGHT = DistExecutor.safeRunForDist(() -> MidnightClient::new, () -> MidnightServer::new);

    public MidnightMod() {
        LOGGER.info(MARKER, String.format("Initializing %s.%s", MnInfo.NAME, !MnInfo.IDE ? " See the debug log for build information." : ""));

        // Print info to debug or IDE console
        printInfo();

        // Register event listeners
        LOGGER.debug(MARKER, "Registering event listeners");
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;
        MidnightMod.addEventListeners(mod, forge);
        Midnight.addEventListeners(mod, forge);
        MidnightData.addEventListeners(mod, forge);

        // Run pre-init for the Midnight
        MIDNIGHT.preInit();
        LOGGER.debug("Midnight pre-initialized");
    }

    private static void addEventListeners(IEventBus mod, IEventBus forge) {
        mod.addListener(MidnightMod::setup);
        mod.addListener(MidnightMod::loadComplete);
    }

    private void printInfo() {
        Level level = MnInfo.IDE ? Level.INFO : Level.DEBUG;

        LOGGER.log(level, MARKER, String.format("%s Build Information", MnInfo.NAME));
        LOGGER.log(level, MARKER, String.format(" - Version:     %s - %s", MnInfo.VERSION, MnInfo.VERSION_NAME));
        LOGGER.log(level, MARKER, String.format(" - Build Date:  %s", MnInfo.BUILD_DATE));
        LOGGER.log(level, MARKER, String.format(" - Dist:        %s", MnInfo.DATAGEN ? "DATAGEN" : FMLEnvironment.dist.toString()));
        LOGGER.log(level, MARKER, String.format(" - Environment: %s", MnInfo.IDE ? MnInfo.TESTSERVER ? "GitHub Actions Test Server" : "IDE/Gradle" : "Normal"));
    }

    private static void setup(FMLCommonSetupEvent event) {
        MIDNIGHT.init();
        LOGGER.debug("Midnight initialized");
    }

    private static void loadComplete(FMLLoadCompleteEvent event) {
        MIDNIGHT.postInit();
        LOGGER.debug("Midnight post-initialized");
    }
}
