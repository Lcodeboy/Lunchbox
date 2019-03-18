package com.server.util.upper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService service = Executors.newCachedThreadPool();
    public void run(Runnable runnable){
        service.execute(runnable);
    }
}
