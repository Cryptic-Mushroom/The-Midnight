package midnight.server;

import midnight.common.Midnight;

public class MidnightServer extends Midnight {

    public static MidnightServer get() {
        return (MidnightServer) Midnight.get();
    }
}
