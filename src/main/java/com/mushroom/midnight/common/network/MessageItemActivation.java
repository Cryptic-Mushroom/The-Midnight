package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.mushroom.midnight.Midnight.LOGGER;

public class MessageItemActivation implements IMessage {
    private short num;

    public MessageItemActivation() {
    }

    public MessageItemActivation(short num) {
        this.num = num;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        num = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(num);
    }

    public static class Handler implements IMessageHandler<MessageItemActivation, IMessage> {
        @Override
        public IMessage onMessage(MessageItemActivation message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    switch(message.num) {
                        case 0:
                            Minecraft.getMinecraft().entityRenderer.displayItemActivation(new ItemStack(ModItems.RAW_DECEITFUL_SNAPPER));
                            break;
                        default:
                            LOGGER.warn("Unexpected number in packet for item activation");
                    }
                });
            }
            return null;
        }
    }
}
