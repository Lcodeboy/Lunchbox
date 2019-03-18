package com.server;

import com.server.config.*;
import com.server.mainserver.httpserver.HttpServer;
import com.server.mainserver.longconnection.ConnListen;
import com.server.mainserver.longconnection.LongConnServer;
import com.server.util.redis.JedisPool;
import com.server.util.redis.Redis.*;
import com.server.util.upper.ThreadUtil;

public class Main {
    public static void main(String[] args) {

        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                HttpServer httpserver = new HttpServer(ConfigInit.HTTPPORT);
                httpserver.start();
            }
        },"httpserver");
        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                JedisPool.createpool();
            }
        },"start createpool");
//        JedisPool.createpool();
        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                LongConnServer.ConnListen();
                LongConnServer longConnServer = new LongConnServer();
                longConnServer.start(ConfigInit.LONGCONNECTION_PORT);
            }
        },"longconnector");

    }
}
