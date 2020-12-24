package midnight;

import midnight.client.MidnightClient;
import net.fabricmc.api.ClientModInitializer;

public class MidnightModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MidnightClient client = new MidnightClient();
        client.initialize();
    }
}
