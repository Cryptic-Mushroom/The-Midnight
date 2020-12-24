package midnight.client;

import midnight.common.Midnight;

public class MidnightClient extends Midnight {

    public static MidnightClient get() {
        return (MidnightClient) Midnight.get();
    }
}
