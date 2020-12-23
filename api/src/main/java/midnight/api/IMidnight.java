/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.api;

import midnight.api.plugin.MidnightEventSubscriber;
import midnight.api.util.IMidnightObjects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * The main interface of the Midnight mod. Get all Midnight-related information and APIs from here. You may obtain an
 * instance of this class by calling {@link #get()}.
 *
 * @author Shadew
 * @since 0.6.0
 */
public interface IMidnight {
    /**
     * The {@link IEventBus} for Midnight-related events. Register to this event bus using {@link
     * MidnightEventSubscriber}.
     */
    IEventBus EVENT_BUS = BusBuilder.builder().startShutdown().build();

    /**
     * Returns the {@link Dist} the Midnight is running on.
     */
    Dist getRuntimeDist();

    /**
     * Returns the mod and build info of the Midnight.
     */
    IMidnightInfo getInfo();

    /**
     * Returns the object manager of the Midnight. This is the base access to all Midnight objects, giving access to
     * registered objects ({@link Block}, {@link Item}, {@link Fluid}), but also to unregistered objects ({@link
     * Material}, {@link Food}, {@link SoundType}).
     */
    IMidnightObjects getObjects();

    /**
     * Get the public {@link IMidnight} instance, if available. When not available, this method throws a {@link
     * MidnightNotInstalledException}.
     *
     * @return The {@link IMidnight} instance.
     *
     * @throws MidnightNotInstalledException When the midnight is not installed.
     * @throws RuntimeException              When the midnight could not be obtained for some other reason.
     */
    @Nonnull
    static IMidnight get() {
        try {
            Class<?> modClass = Class.forName("midnight.MidnightMod");
            Field midnightField = modClass.getField("MIDNIGHT");
            midnightField.setAccessible(true);
            Object midnight = midnightField.get(null);
            if (midnight == null) {
                throw new RuntimeException("Field 'MidnightMod.MIDNIGHT' is null! What implementation is used?");
            }
            return (IMidnight) midnight;
        } catch (ClassNotFoundException exc) {
            throw new MidnightNotInstalledException("The Midnight is not avaiable! Make sure you only interface with the Midnight from a plugin.");
        } catch (NoSuchFieldException exc) {
            throw new RuntimeException("Couldn't find field 'MidnightMod.MIDNIGHT'! What implementation is used?", exc);
        } catch (IllegalAccessException exc) {
            throw new RuntimeException("Couldn't access field 'MidnightMod.MIDNIGHT'! What implementation is used?", exc);
        } catch (ClassCastException exc) {
            throw new RuntimeException("Field 'MidnightMod.MIDNIGHT' is not an 'IMidnight'! What implementation is used?", exc);
        } catch (Throwable exc) {
            throw new RuntimeException("Unable to get field value of 'MidnightMod.MIDNIGHT'! What implementation is used?", exc);
        }
    }


    /**
     * Create a {@link ResourceLocation} with {@link IMidnightInfo#modid() midnight} as default namespace.
     * <ul>
     * <li>{@code "minecraft:path"} will yield {@code minecraft:path}</li>
     * <li>{@code "midnight:path"} will yield {@code midnight:path}</li>
     * <li>{@code "path"} will yield {@code midnight:path} (unlike the {@link ResourceLocation#ResourceLocation(String)
     * ResourceLocation} constructor will yield {@code minecraft:path})</li>
     * </ul>
     *
     * @param path The resource path.
     * @return The created {@link ResourceLocation} instance.
     */
    static ResourceLocation resLoc(String path) {
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new ResourceLocation(path.substring(0, colon), path.substring(colon + 1));
        }
        return new ResourceLocation(get().getInfo().modid(), path);
    }

    /**
     * Create a stringified {@link ResourceLocation} with {@link IMidnightInfo#modid() midnight} as default namespace.
     * See {@link #resLoc}.
     *
     * @param path The resource path.
     * @return The created resource id.
     */
    static String resStr(String path) {
        if (path.indexOf(':') >= 0) {
            return path;
        }
        return get().getInfo().modid() + ":" + path;
    }
}
