/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.core.util;

import net.minecraft.item.ToolMaterials;

public class ToolLevel {
    public static final int WOOD = ToolMaterials.WOOD.getMiningLevel();
    public static final int STONE = ToolMaterials.STONE.getMiningLevel();
    public static final int IRON = ToolMaterials.IRON.getMiningLevel();
    public static final int DIAMOND = ToolMaterials.DIAMOND.getMiningLevel();
    public static final int NETHERITE = ToolMaterials.NETHERITE.getMiningLevel();
}
