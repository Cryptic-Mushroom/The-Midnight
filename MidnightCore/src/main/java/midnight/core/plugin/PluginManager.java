/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package midnight.core.plugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import midnight.api.plugin.IPluginManager;
import midnight.api.plugin.MidnightEventSubscriber;
import midnight.api.plugin.MidnightPlugin;
import midnight.api.plugin.Side;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Set;

public final class PluginManager implements IPluginManager {
    public static final PluginManager INSTANCE = new PluginManager();

    private static final Logger LOGGER = LogManager.getLogger("Midnight Plugin Manager");

    private static final Type PLUGIN_ANNOTATION = Type.getType(MidnightPlugin.class);
    private static final Type EVENT_SUB_ANNOTATION = Type.getType(MidnightEventSubscriber.class);
    private static final Type SIDE_TYPE = Type.getType(Side.class);

    private final Set<PluginHolder> plugins = Sets.newHashSet();
    private final Set<ASMPluginData> pluginASM = Sets.newHashSet();
    private final Set<ASMPluginData> eventSubASM = Sets.newHashSet();

    private final List<PluginException> pluginErrors = Lists.newArrayList();


    private PluginManager() {
    }

    /**
     * Loads all Midnight plugins (classes annotated with {@link MidnightPlugin @MidnightPlugin}) and all Midnight event
     * bus subscribers (classes annotated with {@link MidnightEventSubscriber @MidnightEventSubscriber}). This uses
     * Forge's {@link ModFileScanData} to find annotated classes.
     */
    public void loadPlugins() {
        LOGGER.info("Loading plugins...");

        // Search for ASM data
        List<ModFileScanData> scanData = ModList.get().getAllScanData();
        for (ModFileScanData data : scanData) {
            analyze(data);
        }

        // Subscribe all event subscribers
        for (ASMPluginData data : eventSubASM) {
            try {
                if (data.shouldLoad(FMLEnvironment.dist)) {
                    data.registerEventSubscriber();
                }
            } catch (Exception exc) {
                LOGGER.error("Unable to create MidnightEventSubscriber: " + exc.getMessage());
            }
        }

        // Load all necessary classes from ASM data
        for (ASMPluginData data : pluginASM) {
            try {
                if (data.shouldLoad(FMLEnvironment.dist)) {
                    plugins.add(data.makeHolder());
                }
            } catch (PluginException exc) {
                pluginErrors.add(exc);
            }
        }

        // Clear these as we don't need these memory references anymore (saves some memory)
        pluginASM.clear();
        eventSubASM.clear();

        // Instantiates all plugins queued for loading
        for (PluginHolder holder : plugins) {
            try {
                holder.makeInstance();
            } catch (PluginException exc) {
                pluginErrors.add(exc);
            }
        }

        // Log debug info
        LOGGER.info("Loaded {} plugins", plugins.size());
        LOGGER.debug("Loaded plugins:");
        for (PluginHolder holder : plugins) {
            LOGGER.debug(" - " + holder.getInstance());
        }

        // Log errors
        if (pluginErrors.size() > 0) {
            LOGGER.error("{} plugins failed loading", pluginErrors.size());

            for (PluginException err : pluginErrors) {
                if (err.getCause() == null) {
                    LOGGER.error(err.getMessage());
                } else {
                    LOGGER.error(err.getMessage());
                    LOGGER.error("Stacktrace: ", err.getCause());
                }
            }
        }
    }

    /**
     * Gets all plugin instances of a specific type, cast to that specific type.
     *
     * @param type The type class
     * @return A list of cast plugin instances.
     */
    @Override
    public <T> List<T> getAllOfType(Class<T> type) {
        List<T> list = Lists.newArrayList();

        for (PluginHolder holder : plugins) {
            if (type.isAssignableFrom(holder.getClazz())) {
                list.add(type.cast(holder.getInstance()));
            }
        }

        return list;
    }

    /**
     * Analyzes the {@link ModFileScanData} for {@link MidnightPlugin @MidnightyPlugin} annotations.
     */
    private void analyze(ModFileScanData data) {
        Set<ModFileScanData.AnnotationData> annotations = data.getAnnotations();

        for (ModFileScanData.AnnotationData annotationData : annotations) {

            if (annotationData.getTargetType() == ElementType.TYPE) {
                if (PLUGIN_ANNOTATION.equals(annotationData.getAnnotationType())) {
                    pluginASM.add(new ASMPluginData(
                            annotationData.getClassType(),
                            distFromAnnotationValue(annotationData.getAnnotationData().get("side"))
                    ));
                } else if (EVENT_SUB_ANNOTATION.equals(annotationData.getAnnotationType())) {
                    eventSubASM.add(new ASMPluginData(
                            annotationData.getClassType(),
                            distFromAnnotationValue(annotationData.getAnnotationData().get("side"))
                    ));
                }
            }

        }
    }

    /**
     * Computes the dist from the ASM value of {@link Dist}. Returns null for invalid or common side. Given array should
     * be a String array with two non-null strings.
     *
     * @param value The ASM value of {@link Dist}.
     * @return The side's {@link Dist}, or null for a common side.
     */
    private static Dist distFromAnnotationValue(Object value) {
        if (!(value instanceof ModAnnotation.EnumHolder)) {
            return null;
        }
        ModAnnotation.EnumHolder values = (ModAnnotation.EnumHolder) value;

        Type desc = Type.getType(values.getDesc());
        String name = values.getValue();
        if (SIDE_TYPE.equals(desc)) {
            switch (name) {
                case "CLIENT":
                    return Dist.CLIENT;
                case "SERVER":
                    return Dist.DEDICATED_SERVER;
            }
        }
        // Default switch case automatically lead to null return
        return null;
    }
}
