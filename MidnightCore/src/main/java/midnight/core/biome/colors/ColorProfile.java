/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import midnight.core.biome.colors.provider.ErrorColorProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ColorProfile {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final HashMap<ResourceLocation, ColorMap> COLOR_MAPS = new HashMap<>();

    private final IColorProvider provider;

    private AtomicLong seed = new AtomicLong();

    public ColorProfile(IColorProvider provider) {
        this.provider = provider;
        provider.initForSeed(0);
    }

    public int getColor(@Nullable IWorld world, @Nullable BlockPos pos) {
        if (pos == null) pos = BlockPos.ZERO;

        return provider.getColor(world, pos);
    }

    public int getItemColor() {
        return getColor(null, null);
    }

    public IColorProvider getProvider() {
        return provider;
    }

    public static ColorMap getColorMap(ResourceLocation id) {
        return COLOR_MAPS.computeIfAbsent(id, key -> {
            ColorMap map = new ColorMap(new ResourceLocation(
                key.getNamespace(),
                "textures/" + key.getPath() + ".png"
            ));
            map.reload(Minecraft.getInstance().getResourceManager());
            return map;
        });
    }

    public static ColorProfile parse(JsonObject object, ResourceLocation id, ColorProfileManager mgr) {
        ColorDeserializeContext ctx = new ColorDeserializeContext(mgr);
        IColorProvider color = ctx.silentDeserialize(object, "root");

        List<ColorDeserializeException> excs = ctx.getExcs();

        if (!excs.isEmpty()) {
            LOGGER.warn("Color profile '{}' has {} problems:", id, excs.size());
            for (ColorDeserializeException exc : ctx.getExcs()) {
                LOGGER.warn(" - " + exc.getMessage());
            }
        } else {
            LOGGER.info("Color profile '{}' loaded successfully", id);
        }

        return new ColorProfile(color);
    }

    public static ColorProfile load(InputStream stream, ResourceLocation id, ColorProfileManager mgr) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(new InputStreamReader(stream));
            return parse(el.getAsJsonObject(), id, mgr);
        } catch (Throwable exc) {
            LOGGER.warn("Color profile '{}' failed loading: {}", id, exc.getMessage());
            return new ColorProfile(ErrorColorProvider.INSTANCE);
        }
    }

    public static ColorProfile load(IResourceManager manager, ResourceLocation id, ColorProfileManager mgr) {
        try {
            IResource resource = manager.getResource(new ResourceLocation(
                id.getNamespace(),
                "colors/" + id.getPath() + ".json"
            ));
            return load(resource.getInputStream(), id, mgr);
        } catch (Throwable exc) {
            LOGGER.warn("Color profile '{}' failed loading: {}", id, exc.getMessage());
            return new ColorProfile(ErrorColorProvider.INSTANCE);
        }
    }
}
