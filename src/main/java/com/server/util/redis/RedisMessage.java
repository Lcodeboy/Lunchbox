package com.server.util.redis;

public class RedisMessage {
    public static String getHead(String str){
        String[] s = str.split("\\|");
        return s[0];
    }
    public static String getBody(String str){
        String[] s = str.split("\\|");
        return s[1];
    }
}
