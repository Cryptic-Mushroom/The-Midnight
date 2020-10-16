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
