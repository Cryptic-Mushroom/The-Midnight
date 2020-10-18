/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.gradle;

import org.objectweb.asm.commons.Remapper;

import java.util.HashMap;
import java.util.Map;

public class ShadeRemapper extends Remapper {
    private final HashMap<String, String> packageRenames = new HashMap<>();

    public void addPackageRename(String oldName, String newName) {
        packageRenames.put(oldName, newName);
    }

    @Override
    public String map(String internalName) {
        for (Map.Entry<String, String> entry : packageRenames.entrySet()) {
            if (internalName.startsWith(entry.getKey())) {
                String sub = internalName.substring(entry.getKey().length());
                if (sub.startsWith("/")) {
                    return entry.getValue() + sub;
                }
            }
        }
        return internalName;
    }
}
