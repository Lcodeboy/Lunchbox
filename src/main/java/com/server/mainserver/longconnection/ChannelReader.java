package com.server.mainserver.longconnection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.server.mainserver.httpserver.Message;
import com.server.mainserver.transmitter.SwapHashmap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.server.pojo.Value;

public class ChannelReader extends ChannelInboundHandlerAdapter {
    static {
        PropertyConfigurator.configure("./Logger/log4j.propertites");
    }
    private static Logger logger = Logger.getLogger(ChannelReader.class);
    private final String LOGIN = "LOGIN";
    private final static String MESSAGE = "MESSAGE";
    private final static String HEARTBEAT = "HEARTBEAT";

    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String resultSrt = new String (bytes);
        logger.info("resultString:"+resultSrt);

        String head = Message.getHead(resultSrt);

        switch (head){
            case LOGIN:
                logger.info(LOGIN+"\t"+"head:"+head);
                String body = Message.getBody(resultSrt);
                if (body !=null && !body.equals("")){
                    ChannelMap.add(body,ctx);
                    String str="";
                    ctx.write(str);
                    logger.debug("LOGIN WRITE OVER");
                }
                logger.info("body:"+body);
                break;
            case HEARTBEAT:
                logger.info("HEARTBEAT Head:"+head);
                ctx.write("HEA");
                break;
            case MESSAGE:
                logger.info(MESSAGE+"head:"+head);
                String res = Message.getBody(resultSrt);
                String var[] = res.split("#");
                Value value = SwapHashmap.getValue(var[0]);
                value.getSwap().setMessage(var[0],var[1]+var[2]);
                ctx.write("");
                logger.info("body"+res);
                break;
        }
    }
}
