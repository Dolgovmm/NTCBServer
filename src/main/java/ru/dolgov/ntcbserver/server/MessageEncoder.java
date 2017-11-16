package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.dolgov.ntcbserver.messagehandler.Message;

import java.util.Arrays;

public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        System.out.println("encode msg");
        System.out.println(Arrays.toString(msg.toByteArray()));
        out.writeBytes(msg.toByteArray());
        ctx.writeAndFlush(out);
    }
}