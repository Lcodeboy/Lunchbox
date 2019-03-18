package com.server.mainserver.longconnection;

import com.server.config.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.server.util.redis.Redis;
import com.server.util.upper.ThreadUtil;

public class LongConnServer {
    static {
        PropertyConfigurator.configure("./Logger/log4j.propeertites");
    }
    private static Logger logger = Logger.getLogger(ChannelWriter.class);

    public LongConnServer(){

    }
    public static void ConnListen(){
        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                new Redis().zsubscribe(new Listener(),ConfigInit.UPPERCHANNEL);
            }
        },"ConnListen");
        logger.debug("----ConnListen----");
    }
    public void start(int port){
        logger.info("LongConnserver start"+port);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(workerGroup,bossGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        socketChannel.pipeline().addLast(new ChannelWriter());
                        socketChannel.pipeline().addLast(new ChannelReader());
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("LongConnServer....",e);
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("longconnserver over");
        }
    }
    public void sendMessage(String clientid,String message){
        ChannelHandlerContext ctx = ChannelMap.getCliet(clientid);
        ctx.write(message);

        logger.info("clientid:"+clientid+"|message:"+message+"|size"+ChannelMap.getLength());
    }
}
