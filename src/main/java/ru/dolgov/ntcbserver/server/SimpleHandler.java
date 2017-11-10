package ru.dolgov.ntcbserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class SimpleHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String received = textWebSocketFrame.text();
        System.out.println(received);
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame(received));
    }
}
