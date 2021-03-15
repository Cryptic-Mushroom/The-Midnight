/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.net;

import midnight.common.block.VioleafBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * A packet sent when a player or other entity heals from nausea by walking through a violeaf. This packet notifies
 * clients to play a small particle effect of little dust popping out of the violeaf.
 * <p>
 * The packet has a 192-bits payload holding the packed coordinates of the exact particle effect, each 64 bits being one
 * coordinate formatted as a {@code double}.
 * </p>
 *
 * @author Shadew
 * @since 0.6.0
 */
public class VioleafHealPacket implements MnPacket {
    private final Vector3d vec;

    public VioleafHealPacket(Vector3d vec) {
        this.vec = vec;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(vec.x);
        buf.writeDouble(vec.y);
        buf.writeDouble(vec.z);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> VioleafBlock.spawnParticles(Minecraft.getInstance().level, vec));
    }

    public static VioleafHealPacket read(PacketBuffer buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new VioleafHealPacket(new Vector3d(x, y, z));
    }
}
