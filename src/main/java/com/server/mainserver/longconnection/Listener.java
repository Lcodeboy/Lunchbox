package com.server.mainserver.longconnection;

import redis.clients.jedis.JedisPubSub;
import com.server.util.redis.HeartBeat;
import com.server.util.redis.RedisMessage;
import com.server.util.upper.ThreadUtil;

public class Listener extends JedisPubSub {
    private final String HEARTBEAT = "HEARTBEAT";
    private final String MESSAGE = "MESSAGE";

    @Override
    public void onMessage(String channel,String message){
        ThreadUtil.Run(new Runnable() {
            @Override
            public void run() {
                switch (RedisMessage.getHead(message)){
                    case HEARTBEAT:
                        HeartBeat.HEARTBEAT = RedisMessage.getBody(message);
                        new HeartBeat().countDown();
                        break;
                    case MESSAGE:
                        System.out.println(RedisMessage.getBody(message));
                        break;


                }
            }
        },"Listener JedisPubSub");
    }

}
