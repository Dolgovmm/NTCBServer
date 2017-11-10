package ru.dolgov.ntcbserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;

public class SimpleHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf in = (ByteBuf) msg;
//        StringBuilder sb = new StringBuilder();
//        try {
//            while (in.isReadable()) {
//                System.out.print((char) in.readByte());
//                System.out.flush();
//                sb.append((char) in.readByte());
//            }
//            ctx.write(msg);
//            ctx.flush();
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
        ctx.write(msg);
        ctx.flush();
    }
}
