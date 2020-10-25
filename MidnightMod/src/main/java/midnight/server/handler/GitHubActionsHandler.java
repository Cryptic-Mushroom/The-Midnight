/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.server.handler;

import midnight.MnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Event handler responsible for crashing the dedicated server on GitHub Actions so that it is not in an infinite loop
 * of keeping the server open.
 *
 * @author Jonathing
 * @version 0.6.0
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = MnInfo.MODID)
public final class GitHubActionsHandler {
    private static final Logger LOGGER = LogManager.getLogger("Midnight/GitHubActionsHandler");

    private GitHubActionsHandler() {
    }

    @SubscribeEvent
    public static void serverStarted(FMLServerStartedEvent event) throws Exception {
        File testServerProof = new File("./TESTSERVER.txt");
        boolean isTestServer = testServerProof.exists();

        // Only fire if ./TESTSERVER.txt exists and the server is running via runTestServer.
        if (MnInfo.TESTSERVER) {
            if (isTestServer) {
                // Delete the old file and create a new one saying "TEST SUCCESS".
                testServerProof.delete();
                File testServerSuccess = new File("./TESTSERVER.txt");
                try {
                    FileWriter fileWriter = new FileWriter(testServerSuccess, false);
                    fileWriter.write("TEST SUCCESS");
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LOGGER.warn("GitHub Actions server test successful. The game will now crash.");
                // This Exception is intentional. Do not report it.
                throw new Exception("Crash intended for GitHub Actions. If you are trying to run this server normally, delete the TESTSERVER.txt file from your directory.");
            } else {
                LOGGER.fatal("UNABLE TO LOCATE TESTSERVER.txt! THE CI WILL REPORT THIS BUILD AS FAILED!");
                throw new NullPointerException("TESTSERVER.txt was not found in the root server directory.");
            }
        }

    }
}
