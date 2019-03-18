package com.server.util.redis;

import com.server.config.*;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Redis {
    private static Logger logger = Logger.getLogger(Redis.class);
    private static ReentrantLock lock = new ReentrantLock();

    public static void publish(String channel,String message) throws InterruptedException {
        Jedis jedis = null;
        try{
            jedis = JedisPool.getJedis();
            jedis.publish(channel,message);
        } catch (InterruptedException e) {
           logger.debug("Redis---publish",e);
        }finally {
            JedisPool.returnJedis(jedis);
        }
    }
    public static void subscrib(JedisPubSub sub,String channel) throws InterruptedException {
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.subscribe(sub,channel);
        } catch (InterruptedException e) {
            logger.debug("Redis---subscrib",e);
            JedisPool.returnJedis(jedis);
        }
    }
    public static void put(String key,String v) throws InterruptedException {
        Jedis jedis = null;
        try{
            jedis = JedisPool.getJedis();
            jedis.set(key, v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
        }
    }
    public static String get(String key) throws InterruptedException {
        Jedis jedis= null;
        String result = null;
        try {
            jedis = JedisPool.getJedis();
            result = jedis.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
        }
        return result;
    }
    public static void setKeyTTL(String key,int time) throws InterruptedException {
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.expire(key, time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
        }
    }
    public static Set<String> getKeys() throws InterruptedException {
        lock.lock();
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = JedisPool.getJedis();
            set = jedis.keys("*");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
            lock.unlock();
        }
        return set;
    }
    public static long getLength(String key) throws InterruptedException {
        lock.lock();
        int i = 0;
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            i = Math.toIntExact(jedis.llen(key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
            lock.unlock();
        }
        return i;
    }
    public static List<String> getList(String key,long start,long end) throws InterruptedException {
        lock.lock();
        List<String> list=null;
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            list = jedis.lrange(key,start,end);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            JedisPool.returnJedis(jedis);
            lock.unlock();
        }
        return list;
    }
    public static void RandomSend(String message){
        try {
            publish(Roll.next(),message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void zsubscribe(JedisPubSub sub,String channnel){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis(ConfigInit.REDIS_IP,ConfigInit.REDIS_PORT);
               try {
                   jedis.subscribe(sub,channnel);
               }catch (Exception e){
                   jedis.disconnect();
               }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HeartBeat heartBeat = new HeartBeat();
                try {
                    heartBeat.Heartbeat(channnel,sub);
                } catch (InterruptedException e) {
                    logger.error("heartbeat is broken ---");
                }
            }
        }).start();
    }
}
