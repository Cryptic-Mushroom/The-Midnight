/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package midnight.core.plugin;

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
            // TODO
        } catch (ClassNotFoundException exc) {
            throw new Exception("Unable to find event subscriber class: " + classType.getClassName());
        }
    }
}
