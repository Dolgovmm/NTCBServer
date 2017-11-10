package ru.dolgov.ntcbserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private int port;
    private ChannelFuture channelFuture;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            System.out.println("server created");
            server.localAddress("localhost", port);
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new SocketChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("server configured");
            channelFuture = server.bind().sync();
            System.out.println("server bind");

            channelFuture.channel().closeFuture().sync();
            System.out.println("close future");
        } finally {
            workerGroup.shutdown();
            bossGroup.shutdown();
            System.out.println("shutdown");
        }
    }
}
