package midnight.tests;

import midnight.api.plugin.MidnightPlugin;
import midnight.api.plugin.Side;

@MidnightPlugin(side = Side.CLIENT)
public class ClientPluginTest {
    public ClientPluginTest() {
        System.out.println("Client plugin loaded");
    }
}
