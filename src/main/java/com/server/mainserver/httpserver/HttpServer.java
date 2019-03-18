package com.server.mainserver.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import org.apache.log4j.Logger;
public class HttpServer {
    private static Logger logger =Logger.getLogger(HttpServer.class);
    private int port;
    public void HttpServer(int port){
        this.port=port;
    }
    public void start(){
        logger.debug("http:$portstarup====");
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        EventLoopGroup bossGroup= new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(workerGroup,bossGroup)
                    .channel(NioSctpServerChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_BACKLOG,4096)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpResponseDecoder());
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());
                            socketChannel.pipeline().addLast(new HttpClientCodec());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(65535));
                            socketChannel.pipeline().addLast(new HttpServerInboundHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("port\t",e);
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("http server is over....");
        }

    }
}
