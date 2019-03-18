package com.server.mainserver.httpserver;

public class Message {
    public static String getBody(String str){
        String[] temp = str.split("/");
        return temp[1];
    }
    public static String getHead(String  str){
        String[] temp = str.split("/");
        return temp[0];
    }
}
