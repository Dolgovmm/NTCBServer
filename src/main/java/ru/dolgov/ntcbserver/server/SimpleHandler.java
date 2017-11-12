package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import ru.dolgov.ntcbserver.messagehandler.MessageHandler;

import java.util.Arrays;

public class SimpleHandler extends SimpleChannelInboundHandler<Object> {

    private MessageHandler messageHandler;

    public SimpleHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            byte[] inBytes = new byte[buffer.readableBytes()];
            int readerIndex = buffer.readerIndex();
            buffer.getBytes(readerIndex, inBytes);
            byte[] outBytes = messageHandler.checkMessage(inBytes);
            System.out.println(Arrays.toString(outBytes));
            buffer = ctx.alloc().buffer(outBytes.length);
            int writeIndex = buffer.writerIndex();
            buffer.setBytes(writeIndex, outBytes);
            ctx.write(buffer);
            ctx.flush();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
