package com.mushroom.midnight.common.json.recipe;

import net.minecraft.util.IStringSerializable;

public interface IJsonRecipe extends IStringSerializable {
    String getType();
}
