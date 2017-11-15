package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.dolgov.ntcbserver.messagehandler.Message;
import ru.dolgov.ntcbserver.messagehandler.MessageFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    private MessageEncoder messageEncoder;

    public MessageDecoder(MessageEncoder messageEncoder) {
        this.messageEncoder = messageEncoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        byte[] bytes = new byte[readableBytes];
        in.readBytes(bytes, 0, readableBytes);
        Message message = MessageFactory.getMessage(bytes);
        message.fromByteArray(bytes);
        out.add(message);
        ByteBuf buf = ctx.alloc().buffer(message.toByteArray().length);
        messageEncoder.encode(ctx, message, buf);
    }
}
