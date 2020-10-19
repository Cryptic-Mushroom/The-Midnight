/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.net;

import midnight.common.block.RockshroomBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class RockshroomAttackPacket implements MnPacket {
    private final BlockPos pos;

    public RockshroomAttackPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> RockshroomBlock.spawnSpores(Minecraft.getInstance().world, pos));
    }

    public static RockshroomAttackPacket read(PacketBuffer buf) {
        return new RockshroomAttackPacket(BlockPos.fromLong(buf.readLong()));
    }
}
