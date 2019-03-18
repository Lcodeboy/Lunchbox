package com.server.util.upper;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    static {
        PropertyConfigurator.configure("./Logger/log4j.properties");
    }
    private static Logger logger = Logger.getLogger("fourth");
    private static ExecutorService service = Executors.newCachedThreadPool();

    public static void Run(Runnable runnable,String message){
        service.execute(runnable);
    }


}
