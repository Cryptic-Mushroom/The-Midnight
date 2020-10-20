/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
 */

package midnight.common.net;

import midnight.common.Midnight;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Hold and handles the registry of the Midnight network channel. This channel is used to send messages between server
 * and client over the same TCP connection as Minecraft uses to send packets between server and client. We use Forge's
 * {@link SimpleChannel} to handle packet sending.
 *
 * @author Shadew
 * @since 0.6.0
 */
public final class MnNetwork {
    public static final String NET_PROTOCOL = "3"; // 2 and 1 were pre-rewrite, let's go for 3
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
                                                    .named(Midnight.resLoc("net"))
                                                    .networkProtocolVersion(() -> NET_PROTOCOL)
                                                    .clientAcceptedVersions(NET_PROTOCOL::equals)
                                                    .serverAcceptedVersions(NET_PROTOCOL::equals)
                                                    .simpleChannel();

    /**
     * Registers all packets that are being sent by the Midnight. This method is called from {@link Midnight#preInit()}.
     */
    public static void init() {
        register(0, NetworkDirection.PLAY_TO_CLIENT, RockshroomAttackPacket.class, RockshroomAttackPacket::read);
        register(1, NetworkDirection.PLAY_TO_CLIENT, VioleafHealPacket.class, VioleafHealPacket::read);
    }

    /**
     * Registers a packet to the network channel.
     * @param id   The packet ID. This should be unique for each packet that is registered.
     * @param dir  The direction (and network phase) the packet is sent into.
     * @param type The packet type class
     * @param read The read function, which much construct the packet from a given {@link PacketBuffer}.
     */
    private static <T extends MnPacket> void register(int id, NetworkDirection dir, Class<T> type, Function<PacketBuffer, T> read) {
        CHANNEL.messageBuilder(type, id, dir)
               .encoder(MnPacket::write)
               .decoder(read)
               .consumer((BiConsumer<T, Supplier<NetworkEvent.Context>>) (pkt, ctxSup) -> pkt.handle(ctxSup.get()))
               .add();
    }

    private MnNetwork() {
    }
}
