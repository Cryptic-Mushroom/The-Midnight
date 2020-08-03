package midnight.common.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import midnight.common.tileentity.MidnightChestTileEntity;
import midnight.common.tileentity.MnTileEntities;

import javax.annotation.Nullable;

public class MidnightChestBlock extends ChestBlock {

    public MidnightChestBlock(Properties properties) {
        super(properties, () -> {
            return MnTileEntities.MIDNIGHT_CHEST;
        });
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new MidnightChestTileEntity(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
