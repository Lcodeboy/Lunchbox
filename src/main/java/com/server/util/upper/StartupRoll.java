package com.server.util.upper;

import com.server.mainserver.longconnection.LongConnServer;
import org.apache.log4j.Logger;

import java.util.List;

public class StartupRoll implements Runnable {
    private static Logger logger = Logger.getLogger(StartupRoll.class);

    private List<String> list;

    private String borg;
    public void setBorg(String borg){
        this.borg=borg;
    }
    public void setList(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (String s:list){
            new LongConnServer().sendMessage(borg,Factory.upperRollData(s));;
            logger.debug(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.debug(s,e);
            }
        }
        new LongConnServer().sendMessage(borg,Factory.END);
        logger.debug(borg+Factory.END);
    }
}
