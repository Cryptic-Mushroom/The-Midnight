/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.net;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * A packet to be sent over the {@linkplain MnNetwork#CHANNEL Midnight packet channel}. The interface provides
 * serializing and handling the packet, but the constructor should be made itself and specified when registring.
 *
 * @author Shadew
 * @since 0.6.0
 */
public interface MnPacket {
    /**
     * Serializes (writes) the packet to the given buffer. A packet must deserialize the exact same data as that is
     * serialized here.
     *
     * @param buf The buffer to serialize to.
     */
    void write(PacketBuffer buf);

    /**
     * Handle the packet, i.e. do what the packet should do. The packet should ideally run the action on the main thread
     * and should therefore call {@link NetworkEvent.Context#enqueueWork(Runnable)} with the action to be performed.
     *
     * @param ctx The context to handle the packet in.
     */
    void handle(NetworkEvent.Context ctx);
}
