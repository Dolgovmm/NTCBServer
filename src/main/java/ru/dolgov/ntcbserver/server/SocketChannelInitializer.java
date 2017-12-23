package ru.dolgov.ntcbserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import ru.dolgov.ntcbserver.messagehandler.MessageHandler;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("messageHandler", new MessageHandler());

    }
}
