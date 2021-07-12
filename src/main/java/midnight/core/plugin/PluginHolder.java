/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.plugin;

import midnight.api.plugin.MidnightPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Holder of a plugin. This holds the {@link MidnightPlugin @MidnightPlugin} annotation instance, the plugin {@link
 * Class} and the instance once created.
 *
 * @author Shadew
 * @since 0.6.0
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
     * @return The plugin {@link Class}.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * @return The plugin annotation instance.
     */
    public MidnightPlugin getPlugin() {
        return plugin;
    }

    /**
     * @return The plugin instance.
     */
    public Object getInstance() {
        return instance;
    }
}
