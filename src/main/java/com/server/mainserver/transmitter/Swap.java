package com.server.mainserver.transmitter;

import org.apache.log4j.Logger;
import com.server.pojo.Value;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Swap {
    private static Logger logger=Logger.getLogger(Swap.class);
    private Lock lock;
    private Condition condition;
    private String message;

    public void setMessage(String uuid,String message){
        lock.lock();
        logger.debug("setMessage===arg:"+message);
        this.message = message;
        Value value = SwapHashmap.getValue(uuid);
        value.getCondition().signal();
        lock.unlock();
    }
    public String getMessage(String key){
        lock.lock();
        condition = lock.newCondition();
        String result = "";

        logger.debug("getMessage await===");
        Value value = new Value();
        value.setCondition(condition);
        value.setSwap(this);
        value.setTime(System.currentTimeMillis());
        SwapHashmap.add(key,value);
        try {
            condition.await();
            result = message;
        } catch (InterruptedException e) {
            logger.debug("getmessage await",e);
        }
        logger.debug("unlock");
        lock.unlock();
        return result;
    }
    private void add(){}
}
