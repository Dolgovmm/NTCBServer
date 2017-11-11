package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import ru.dolgov.ntcbserver.messagehandler.MessageHandler;

public class SimpleHandler extends SimpleChannelInboundHandler<String> {

    private MessageHandler messageHandler;

    public SimpleHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            byte[] inBytes = new byte[buffer.readableBytes()];
            int readerIndex = buffer.readerIndex();
            buffer.getBytes(readerIndex, inBytes);
            byte[] outBytes = messageHandler.checkMessage(inBytes);
//            ctx.write(msg);
//            ctx.flush();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
