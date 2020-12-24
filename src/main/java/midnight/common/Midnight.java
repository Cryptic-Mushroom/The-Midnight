package midnight.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public abstract class Midnight {


    // ====================================================
    //  INITIALIZER
    // ====================================================

    public void initialize() {

    }



    // ====================================================
    //  INSTANTIATION
    // ====================================================

    public static Midnight get() {
        return instance;
    }

    private static Midnight instance;

    public Midnight() {
        if(instance != null) {
            throw new IllegalStateException("Midnight initialized twice");
        }
        instance = this;
    }



    // ====================================================
    //  UTILS
    // ====================================================

    // TODO Port MnInfo
    public static Identifier id(String path) {
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new Identifier(path.substring(0, colon), path.substring(colon + 1));
        }
        return new Identifier("midnight", path);
    }

    public static String idStr(String path) {
        if (path.indexOf(':') >= 0) {
            return path;
        }
        return "midnight:" + path;
    }
}
