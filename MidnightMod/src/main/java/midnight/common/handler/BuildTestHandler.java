package midnight.common.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import midnight.MidnightInfo;
import midnight.MidnightMod;

import java.io.File;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class BuildTestHandler {
    private static final Logger LOGGER = LogManager.getLogger("MidnightMod");

    private BuildTestHandler() {
    }

    @SubscribeEvent
    public static void serverStarted(FMLServerStartedEvent event) throws Exception {
        File testServerProof = new File("./TESTSERVER");
        boolean isTestServer = testServerProof.exists();
        if (isTestServer)
        {
            throw new Exception("GitHub Actions server test successful. The game will now crash.");
        }
    }
}
