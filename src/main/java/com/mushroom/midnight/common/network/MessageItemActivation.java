package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageItemActivation implements IMessage {
    private ItemStack stack;

    public MessageItemActivation() {
    }

    public MessageItemActivation(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<MessageItemActivation, IMessage> {
        @Override
        public IMessage onMessage(MessageItemActivation message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    Minecraft.getMinecraft().entityRenderer.displayItemActivation(message.stack);
                });
            }
            return null;
        }
    }
}
