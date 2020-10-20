/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
 */

package midnight.common.net;

import midnight.common.block.TendrilweedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * A packet sent when a tendrilweed grows. This packet notifies clients to play a small particle effect of little pollen
 * particles popping out.
 * <p>
 * The packet has a 64-bits payload holding the packed coordinates of the tendrilweed block that grew.
 * </p>
 *
 * @author Shadew
 * @since 0.6.0
 */
public class TendrilweedGrowPacket implements MnPacket {
    private final BlockPos pos;

    public TendrilweedGrowPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> TendrilweedBlock.spawnPollenCloud(Minecraft.getInstance().world, pos));
    }

    public static TendrilweedGrowPacket read(PacketBuffer buf) {
        return new TendrilweedGrowPacket(BlockPos.fromLong(buf.readLong()));
    }
}
