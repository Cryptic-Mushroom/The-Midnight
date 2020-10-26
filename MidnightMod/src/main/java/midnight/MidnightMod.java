/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight;

import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.server.MidnightServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Bootstrap class of the Midnight. This is the first class of the Midnight that is being loaded by Forge. From here we
 * start interfacing with Minecraft.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
@Mod(MnInfo.MODID)
public class MidnightMod {
    private static final Logger LOGGER = LogManager.getLogger("Midnight Mod");

    /**
     * The general {@link Midnight} instance. Don't use - use {@link Midnight#get()} instead.
     */
    public static final Midnight MIDNIGHT = DistExecutor.safeRunForDist(() -> MidnightClient::new, () -> MidnightServer::new);

    public MidnightMod() {
        printVersion();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);

        MinecraftForge.EVENT_BUS.register(MIDNIGHT);
        FMLJavaModLoadingContext.get().getModEventBus().register(MIDNIGHT);

        MIDNIGHT.preInit();
        LOGGER.debug("Midnight pre-initialized");
    }

    private void printVersion() {
        LOGGER.info("Initializing The Midnight");
        LOGGER.info(" - Version: " + MnInfo.VERSION);
        LOGGER.info(" - Build Date: " + MnInfo.BUILD_DATE);
        LOGGER.info(" - Dist: " + FMLEnvironment.dist);

        if (MnInfo.IDE && !MnInfo.TESTSERVER) {
            LOGGER.info(" - Running in an IDE or via Gradle");
        } else if (MnInfo.TESTSERVER) {
            LOGGER.info(" - Running a GitHub Actions test server");
        }

        if (MnInfo.DATAGEN) {
            LOGGER.info(" - Running data generator");
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        MIDNIGHT.init();

        /*
         * TODO This is basically the mechanism we will use to inform players that the build of The Midnight
         *  is not signed. Since I don't know how to get the SHA1 or any certificate fingerprint despite my
         *  best efforts, this will be commented out until we get that ability.
         *
         * Essentially, what this does is
         * - If on client, a GUI will show on startup saying that some mods had warnings whens starting up.
         *   The text (which is currently WARNING MESSAGE HERE) will be shown on that screen.
         * - If on server, the server will show in the log that a mod has had warnings when starting up. However,
         *   since the warning is flooded by a bunch of other useless nonsense, we will probably have a warning/error
         *   message on the FMLServerStartingEvent anyway.
         *
         * - Jonathing
         */
//        if(!MidnightInfo.IDE && ModList.get().getModContainerById("midnight").isPresent())
//        {
//            ModLoader.get().addWarning(new ModLoadingWarning(ModList.get().getModContainerById("midnight").get().getModInfo(), ModLoadingStage.ERROR, "WARNING MESSAGE HERE", (Object) null));
//        }

        LOGGER.debug("Midnight initialized");
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        MIDNIGHT.postInit();
        LOGGER.debug("Midnight post-initialized");
    }
}
