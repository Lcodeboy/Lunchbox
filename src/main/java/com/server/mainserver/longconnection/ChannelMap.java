package com.server.mainserver.longconnection;

import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelMap {
    static {
        PropertyConfigurator.configure("./Logger/log4j.propertites");
    }
    private static Logger logger = Logger.getLogger(ChannelReader.class);

    private static ConcurrentHashMap<String,ChannelHandlerContext> MAP = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static void add(String clientid,ChannelHandlerContext client){
        logger.info(clientid+"already save");
        MAP.put(clientid,client);
        logger.debug("clientid:" + clientid+"\tclient:"+client+"\t:client:"+MAP.get(clientid));
    }
    public static ChannelHandlerContext getCliet(String clientid){
        logger.info(clientid+"already get");
        return MAP.get(clientid);
    }
    public static void remove(String clientid){
        MAP.remove(clientid);
        logger.info(clientid+"already remove");
    }
    public static int getLength(){
        return MAP.size();
    }
    public static Enumeration<String> getMap(){
        return MAP.keys();
    }
}
