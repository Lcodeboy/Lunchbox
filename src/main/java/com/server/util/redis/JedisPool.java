package com.server.util.redis;

import com.server.config.ConfigInit;
import com.server.mainserver.httpserver.HttpServerInboundHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class JedisPool {
    static {
        PropertyConfigurator.configure("./Logger/log4j.propertites");
    }
    private static Logger logger = Logger.getLogger(HttpServerInboundHandler.class);
    private static ReentrantLock lock = new ReentrantLock();
    private static ArrayBlockingQueue<Jedis> pool = new ArrayBlockingQueue<Jedis>(30);

    public static void createpool(){
        for (int i = 0;i <30;i++){
            try {
                pool.put(new Jedis(ConfigInit.REDIS_IP,ConfigInit.REDIS_PORT));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static Jedis getJedis() throws InterruptedException {
        return pool.take();
    }
    public static void returnJedis(Jedis jedis) throws InterruptedException {
        pool.put(jedis);
    }
    public static Jedis isAlive(Jedis jedis){
        int time = 0;

        while (true){
            try {
                if (!(jedis.ping().equals("PONG"))) {
                    jedis = new Jedis(ConfigInit.REDIS_IP, ConfigInit.REDIS_PORT);
                }
                return jedis;
            }catch (Exception e){
                if (time == 10){
                    System.out.println("start up time out");
                    logger.error("time out:" + time,e);
                    System.exit(1);
                }
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("重新连接");
                time++;
                continue;
            }
        }
    }
}
