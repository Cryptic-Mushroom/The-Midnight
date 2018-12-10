package com.mushroom.midnight.common.network;

import com.mushroom.midnight.client.gui.GuiMidnightFurnace;
import com.mushroom.midnight.common.container.ContainerMidnightFurnace;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

import static com.mushroom.midnight.Midnight.LOGGER;

public class GuiHandler implements IGuiHandler {
    public static final int MIDNIGHT_FURNACE = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MIDNIGHT_FURNACE:
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEntityMidnightFurnace) {
                    return new ContainerMidnightFurnace(player.inventory, (TileEntityMidnightFurnace) tile);
                }
                break;
            default:
                LOGGER.warn("Invalid gui id, received : " + ID);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MIDNIGHT_FURNACE:
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEntityMidnightFurnace) {
                    return new GuiMidnightFurnace(player.inventory, (TileEntityMidnightFurnace) tile);
                }
                break;
            default:
                LOGGER.warn("Invalid gui id, received : " + ID);
        }
        return null;
    }
}
