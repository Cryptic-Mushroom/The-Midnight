/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors;

import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class ColorResourceLocation extends ResourceLocation {
    private final String color;

    protected ColorResourceLocation(String[] pathItems) {
        super(pathItems);
        color = pathItems[2].toLowerCase(Locale.ROOT);
    }

    public ColorResourceLocation(String path) {
        this(parsePathString(path));
    }

    public ColorResourceLocation(ResourceLocation location, String color) {
        this(new String[] {location.getNamespace(), location.getPath(), color});
    }

    public ColorResourceLocation(String location, String color) {
        this(parsePathString(location + '#' + color));
    }

    protected static String[] parsePathString(String path) {
        String[] out = {null, path, ""};
        int hash = path.indexOf('#');

        String p = path;
        if (hash >= 0) {
            out[2] = path.substring(hash + 1);
            if (hash > 1) {
                p = path.substring(0, hash);
            }
        }

        System.arraycopy(ResourceLocation.decompose(p, ':'), 0, out, 0, 2);
        return out;
    }

    public String getColor() {
        return this.color;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ColorResourceLocation && super.equals(obj)) {
            ColorResourceLocation crl = (ColorResourceLocation) obj;
            return color.equals(crl.color);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }

    public String toString() {
        return super.toString() + '#' + color;
    }
}
