package midnight;

import midnight.client.MidnightClient;
import midnight.server.MidnightServer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class MidnightModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        MidnightServer server = new MidnightServer();
        server.initialize();
    }
}
