package ru.dolgov.ntcbserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("http-request-decoder", new HttpRequestDecoder());
        pipeline.addLast("http-response-encoder", new HttpResponseEncoder());
        pipeline.addLast("request-handler", new WebSocketServerProtocolHandler("/websocket"));
    }
}
