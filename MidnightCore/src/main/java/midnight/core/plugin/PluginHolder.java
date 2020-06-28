/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package midnight.core.plugin;

import midnight.api.plugin.MidnightPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Holder of a plugin. This holds the {@link MidnightPlugin @MidnightPlugin} annotation instance, the plugin {@link
 * Class} and the instance once created.
 */
public class PluginHolder {
    private final MidnightPlugin plugin;
    private final Class<?> clazz;
    private Object instance;

    PluginHolder(Class<?> clazz) throws PluginException {
        this.clazz = clazz;
        this.plugin = clazz.getAnnotation(MidnightPlugin.class);

        if (plugin == null) {
            // This can't happen if used properly
            // as plugin holders are only created
            // for classes with the MidnightPlugin
            // annotation.

            throw new PluginException("Plugin class has no MidnightPlugin annotation");
        }
    }

    /**
     * Instantiates the plugin instance from class.
     *
     * @throws PluginException If instantiation fails.
     */
    public void makeInstance() throws PluginException {
        if (clazz.isInterface()) {
            throw new PluginException("Constructor class is an interface");
        }
        if (clazz.isEnum()) {
            throw new PluginException("Constructor class is an enum");
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new PluginException("Constructor class is abstract");
        }

        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException exc) {
            throw new PluginException("Constructor class has no public, nullary constructor");
        }

        Object inst;
        try {
            inst = constructor.newInstance();
        } catch (InstantiationException exc) {
            // Should not happen because of previous check
            throw new PluginException("Constructor class is abstract");
        } catch (IllegalAccessException exc) {
            throw new PluginException("Class or it's nullary constructor is not accessible");
        } catch (InvocationTargetException exc) {
            throw new PluginException("Nullary constructor threw an exception", exc.getTargetException());
        }

        instance = inst;
    }

    /**
     * Returns the plugin class.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Returns the plugin annotation instance.
     */
    public MidnightPlugin getPlugin() {
        return plugin;
    }

    /**
     * Returns the plugin instance.
     */
    public Object getInstance() {
        return instance;
    }
}
