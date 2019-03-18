package com.server.mainserver.httpserver;

import com.server.config.ConfigInit;
import org.apache.log4j.Logger;
import com.server.pojo.upper.Upper;
import com.server.util.redis.Redis;

import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;

public class Analyse implements Runnable{
    private static Logger logger = Logger.getLogger(Analyse.class);

    private  static Logger record = Logger.getLogger("secondLogger");
    private String requestURI;
    private String message;
    private static String HEAD = "Json|";

    private  final  static  String PACKAGE = " com.server.pojo.upper.Upper";
    private final static String MESSAGE = "message";
    private final static ReentrantLock lock = new ReentrantLock();

    public Analyse(String uri,String message){
        this.requestURI = uri;
        this.message = message;
    }
    public Analyse(){}

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void DealSet(){
        lock.lock();
        try{
            switch (requestURI){
            case MESSAGE:
                record.info("ConfigInit:"+ConfigInit.DATACHANNEL+"/t|"+message+"\\\\"+requestURI);
                logger.info("==begin==\t"+ConfigInit.DATACHANNEL);
                if (ConfigInit.REDIS_PUB_MUL){
                    Redis.RandomSend(message);
                }else {
                    Redis.publish(ConfigInit.DATACHANNEL,HEAD+message);
                }
                logger.info("||||"+ConfigInit.REDIS_PUB_MUL+"\t|||"+ConfigInit.DATACHANNEL+"\t|||"+message);
                break;
            default:
                logger.info("defult\t|||"+requestURI+"\t||||"+message);
                Class c = Class.forName(PACKAGE);
                Upper upper = (Upper) c.newInstance();
                Method[] methods = Upper.class.getMethods();
                for (int i=0;i<methods.length;i++){
                    if (methods[i].getName().equals(requestURI)){
                        logger.debug(methods[i]+"|"+message);
                    }
                }
                logger.info("over defult|||");
                break;


            }
        }catch (Exception e){
            logger.error(message+"====="+requestURI,e);
        }finally {
            logger.info("Analyes is over");
            lock.unlock();
        }
    }

    public void run() {

        DealSet();
    }
}
