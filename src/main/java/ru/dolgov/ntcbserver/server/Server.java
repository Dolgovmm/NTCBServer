package ru.dolgov.ntcbserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Server {
    static final Logger logger = LoggerFactory.getLogger(Server.class);

    private String address;
    private int port;
    private ChannelFuture channelFuture;
    private List<String> messageList;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
        messageList = new LinkedList();
    }

    public void run() throws Exception {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.localAddress(address, port);
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new SocketChannelInitializer(messageList))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture = server.bind().sync();
            logger.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + " сервер запущен");
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public String getLogFromList() {
        String str;
        if (!messageList.isEmpty()) {
            str = messageList.get(0);
            messageList.remove(0);
            return str;
        }
        return null;
    }

    public void shutdown() {
        try {
            channelFuture.channel().close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
