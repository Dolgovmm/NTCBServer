package ru.dolgov.ntcbserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.List;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    private MessageDecoder messageDecoder;
    private MessageEncoder messageEncoder;

    public SocketChannelInitializer(List<String> messageList) {
        this.messageEncoder = new MessageEncoder();
        this.messageDecoder = new MessageDecoder(this.messageEncoder, messageList);
    }

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(messageEncoder);
        pipeline.addLast(messageDecoder);
    }
}