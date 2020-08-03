package midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MidnightCraftingTableBlock extends CraftingTableBlock {
    private static final ITextComponent TITLE = new TranslationTextComponent("container.crafting");

    public MidnightCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedContainerProvider((containerId, playerInventory, player) -> {
            IWorldPosCallable worldPos = IWorldPosCallable.of(world, pos);
            return new WorkbenchContainer(containerId, playerInventory, worldPos) {
                @Override
                public boolean canInteractWith(PlayerEntity player) {
                    return isWithinUsableDistance(worldPos, player, MidnightCraftingTableBlock.this);
                }
            };
        }, TITLE);
    }
}
