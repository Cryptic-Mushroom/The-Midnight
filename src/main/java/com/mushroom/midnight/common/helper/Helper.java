package com.mushroom.midnight.common.helper;

import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Helper {
    public static boolean isMidnightDimension(@Nullable World world) {
        return world != null && world.provider.getDimensionType() == ModDimensions.MIDNIGHT;
    }
}
