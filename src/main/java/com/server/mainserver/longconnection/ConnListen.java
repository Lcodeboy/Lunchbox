package com.server.mainserver.longconnection;

import com.server.config.ConfigInit;
import org.apache.log4j.Logger;
import com.server.util.redis.JedisPool;
import com.server.util.redis.Redis;
import com.server.util.upper.ThreadUtil;

public class ConnListen {
    private static Logger logger = Logger.getLogger(ConnListen.class);


    public static void listen(){
        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                new Redis().zsubscribe(new Listener(),ConfigInit.UPPERCHANNEL);
                try {
                    JedisPool.getJedis().subscribe(new Listener(),ConfigInit.UPPERCHANNEL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"ConnListen");
    }
}
