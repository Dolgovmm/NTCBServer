package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import ru.dolgov.ntcbserver.messagehandler.MessageHandler;

import java.util.Arrays;

public class SimpleHandler extends ChannelInboundHandlerAdapter {

    private MessageHandler messageHandler;
    private byte[] outBytes;

    public SimpleHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            byte[] inBytes = new byte[buffer.readableBytes()];
            int readerIndex = buffer.readerIndex();
            buffer.getBytes(readerIndex, inBytes);
            outBytes = messageHandler.checkMessage(inBytes);
            System.out.println(Arrays.toString(outBytes));
            ByteBuf writeBuffer = ctx.alloc().buffer(inBytes.length);
            int writeIndex = writeBuffer.writerIndex();
            writeBuffer.setBytes(writeIndex, inBytes);
            final ChannelFuture channelFuture = ctx.writeAndFlush(writeBuffer);


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
