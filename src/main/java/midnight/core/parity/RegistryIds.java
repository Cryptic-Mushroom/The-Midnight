/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.core.parity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Easy way to get registry names from a {@link Registry}, which replaces {@code getRegistryName} on the Fabric side.
 */
public class RegistryIds {
    public static Identifier get(Block v) {
        return Registry.BLOCK.getId(v);
    }

    public static Identifier get(Item v) {
        return Registry.ITEM.getId(v);
    }
}
