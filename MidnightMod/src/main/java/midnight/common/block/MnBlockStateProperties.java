/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;

public final class MnBlockStateProperties {
    public static final BooleanProperty DENSE = BooleanProperty.create("dense");
    public static final DirectionProperty FACING_EXCEPT_DOWN = DirectionProperty.create("facing", d -> d != Direction.DOWN);

    private MnBlockStateProperties() {
    }
}
