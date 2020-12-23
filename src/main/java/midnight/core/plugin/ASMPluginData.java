/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.plugin;

import midnight.api.IMidnight;
import net.minecraftforge.api.distmarker.Dist;
import org.objectweb.asm.Type;

class ASMPluginData {
    private final Type classType;
    private final Dist dist;

    ASMPluginData(Type classType, Dist dist) {
        this.classType = classType;
        this.dist = dist;
    }

    boolean shouldLoad(Dist actualDist) {
        return dist == null || dist == actualDist;
    }

    PluginHolder makeHolder() throws PluginException {
        try {
            Class<?> cls = Class.forName(classType.getClassName());
            return new PluginHolder(cls);
        } catch (ClassNotFoundException exc) {
            throw new PluginException("Unable to find plugin class: " + classType.getClassName());
        }
    }

    void registerEventSubscriber() throws Exception {
        try {
            Class<?> cls = Class.forName(classType.getClassName());
            IMidnight.EVENT_BUS.register(cls);
        } catch (ClassNotFoundException exc) {
            throw new Exception("Unable to find event subscriber class: " + classType.getClassName());
        }
    }
}
