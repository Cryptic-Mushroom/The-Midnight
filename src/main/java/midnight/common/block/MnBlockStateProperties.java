/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 27
 */

package midnight.common.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;

/**
 * Holds all block state properties created and (possibly) in use by the Midnight.
 *
 * @author Shadew
 * @since 0.6.0
 */
public abstract class MnBlockStateProperties {
    /** {@code dense} - {@link BooleanProperty boolean} - {@code false true} */
    public static final BooleanProperty DENSE = BooleanProperty.create("dense");

    /** {@code facing} - {@link DirectionProperty Direction} - {@code up north east south west} */
    public static final DirectionProperty FACING_EXCEPT_DOWN = DirectionProperty.create("facing", d -> d != Direction.DOWN);

    /** {@code stage} - {@link IntegerProperty int} - {@code 0 1 2 3} */
    public static final IntegerProperty STAGE_0_3 = IntegerProperty.create("stage", 0, 3);

    /** {@code grown} - {@link BooleanProperty boolean} - {@code false true} */
    public static final BooleanProperty GROWN = BooleanProperty.create("grown");

    /** {@code end} - {@link BooleanProperty boolean} - {@code false true} */
    public static final BooleanProperty END = BooleanProperty.create("end");

    /** {@code root} - {@link BooleanProperty boolean} - {@code false true} */
    public static final BooleanProperty ROOT = BooleanProperty.create("root");

    /** {@code status} - {@link IntegerProperty int} - {@code 0 .. 23} */
    public static final IntegerProperty STATUS_0_23 = IntegerProperty.create("status", 0, 23);

    private MnBlockStateProperties() {
    }
}
