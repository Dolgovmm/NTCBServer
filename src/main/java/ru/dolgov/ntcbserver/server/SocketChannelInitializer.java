package ru.dolgov.ntcbserver.server;

import io.netty.channel.ChannelInitializer;
        import io.netty.channel.ChannelPipeline;
        import io.netty.channel.socket.SocketChannel;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("simpleHandler", new SimpleHandler());
    }
}