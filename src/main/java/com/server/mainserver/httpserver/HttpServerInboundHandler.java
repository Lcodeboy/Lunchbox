package com.server.mainserver.httpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import com.server.mainserver.transmitter.Swap;
import org.apache.log4j.Logger;
import com.server.util.redis.Redis;
import com.server.util.upper.StartupRoll;
import com.server.util.upper.ThreadUtil;

import java.util.UUID;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(HttpServerInboundHandler.class);
    private final static String SET = "set";
    private final static String GET = "get";
    private final static String MESSAGE = "mes";
    private final static String REDISALREADY = "red";
    private final static String ADD = "add";
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        logger.debug("channelread is on ....");
        FullHttpRequest request = (FullHttpRequest) msg;
        String url = request.uri().substring(1, request.uri().length());
        logger.debug("url"+url);
        String url_head = url.substring(0,3);
        logger.debug("url_head"+url_head);
        String content = "";
        int a = request.content().readableBytes();
        logger.debug(a);
        if (a>0){
            byte[] bytes = new byte[a];
            request.content().readBytes(bytes);
            content = new String(bytes);
        }
        logger.debug(content);
        switch (url_head){
            case SET:
                logger.debug(SET);
                Analyse ana =new Analyse();
                UUID uuid_set = UUID.randomUUID();
                String tmp_set = content.substring(0,content.length()-1);
                String message1 = tmp_set+",uuid="+uuid_set+"}";
                ana.setRequestURI(url);
                ana.setMessage(url);
                ThreadUtil.Run(ana,SET);
                String ss = new Swap().getMessage(uuid_set.toString());
                logger.debug("response_set:ss"+ss);
                ctx.write(ss);
                ctx.write("ok");
                break;
            case GET:
                logger.debug(GET);
                Analyse analyse = new Analyse();
                UUID uuid = UUID.randomUUID();
                String tmp = content.substring(0,content.length()-1);
                String message =tmp+",uuid="+uuid+"}";
                analyse.setRequestURI(url);
                analyse.setMessage(message);

                logger.debug("uuid:"+uuid+"\ttmp:"+tmp+"\tmessage:"+message+"\turl:"+url+"\t");

                ThreadUtil.Run(analyse,GET);
                Swap swap = new Swap();
                String s= swap.getMessage(uuid.toString());
                logger.debug("response:"+s);
                ctx.write(s);
                break;
            case MESSAGE:
                logger.debug(MESSAGE);
                Analyse analyse1 = new Analyse();
                analyse1.setRequestURI(url);
                analyse1.setMessage(content);
                logger.debug("url:"+url+"\tcontent:"+content+"\t");
                ThreadUtil.Run(analyse1,MESSAGE);
                logger.debug("Threadutil analyse1 over");
                break;
            case REDISALREADY:
                logger.debug(REDISALREADY);
                StartupRoll roll = new StartupRoll();
                String[] key =url.split("=");
                roll.setBorg(key[1]);
                roll.setList(Redis.getList(key[1],0,Redis.getLength(key[1])));
                logger.debug(REDISALREADY+"key:"+key[0]+key[1]+"\t");
                ThreadUtil.Run(roll,REDISALREADY);
                logger.debug(REDISALREADY+"over"+key[0]+key[1]);
                String redisok = "already send uuper";
                ctx.write(redisok);
                break;
            case ADD:
                logger.debug(ADD);
                break;
             default:
                    logger.debug("corrected message");
                    String result = "ERROR";
                    ctx.write(result);
                    logger.debug("corrected defult error");

        }
    }
}
