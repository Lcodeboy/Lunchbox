package com.server.mainserver.longconnection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ChannelWriter extends ChannelOutboundHandlerAdapter {
    static {
        PropertyConfigurator.configure("./Logger/log4j.propertites");

    }
    private static Logger logger = Logger.getLogger(ChannelWriter.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)  {
        String var = (String) msg;
        logger.info("message"+var);


        ByteBuf encoded = ctx.alloc().buffer(4*var.length());
        encoded.writeBytes(var.getBytes());
        System.out.println(encoded.readableBytes());
        ctx.write(encoded);
        ctx.flush();
        logger.debug("===channelwrite already=="+"\t"+var);
    }
}
