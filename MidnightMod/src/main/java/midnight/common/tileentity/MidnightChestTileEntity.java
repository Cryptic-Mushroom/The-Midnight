package midnight.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import midnight.common.block.MnBlocks;

public class MidnightChestTileEntity extends ChestTileEntity {
    private Block block = Blocks.AIR;

    protected MidnightChestTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public MidnightChestTileEntity() {
        super(MnTileEntities.MIDNIGHT_CHEST);
    }

    public MidnightChestTileEntity(Block chest) {
        this();
        this.setChestModel(chest);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
                this.pos.add(-1, 0, -1),
                this.pos.add(2, 2, 2)
        );
    }

    public void setChestModel(Block block) {
        this.block = block;
    }

    public Block getChestModel() {
        return this.block != Blocks.AIR ? this.block : MnBlocks.SHADOWROOT_CHEST;
    }
}
