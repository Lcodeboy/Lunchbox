package com.server.pojo.upper;

import com.server.config.ConfigInit;
import org.apache.log4j.Logger;
import com.server.util.redis.Redis;
import com.server.util.upper.JsonSplit;

public class Upper {
    private static Logger logger = Logger.getLogger(Upper.class);

    public void setStatus(String status) throws InterruptedException {
        JsonSplit jsonSplit = new JsonSplit(status);
        Machine machine = Change.toMachine(jsonSplit);
        String message = Command.setStatus(machine);
        logger.debug("setStatus:"+message);
        Redis.publish(ConfigInit.UPPERCHANNEL,message);
    }
    public void getStatus(String m){
        JsonSplit jsonSplit = new JsonSplit(m);
        Machine machine = Change.toMachine(jsonSplit);
        String message = Command.getStatus(machine);
        logger.debug("getStatus:"+message);
        try {
            Redis.publish(ConfigInit.UPPERCHANNEL,message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
