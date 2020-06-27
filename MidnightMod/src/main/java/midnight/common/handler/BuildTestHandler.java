package midnight.common.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import midnight.MidnightInfo;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class BuildTestHandler {
    private static final Logger LOGGER = LogManager.getLogger("MidnightMod");

    private BuildTestHandler() {
    }

    @SubscribeEvent
    public static void serverStarted(FMLServerStartedEvent event) throws Exception {
        if (MidnightInfo.TEST_SERVER)
        {
            LOGGER.info("The Midnight server started successfully. The game will now crash.");

            throw new Exception();
        }
    }
}
