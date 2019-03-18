package com.server.util.redis;

import java.util.concurrent.locks.ReentrantLock;

public class Roll {
    private static String[] IP;
    private static int location = 0;
    private static ReentrantLock lock = new ReentrantLock();

    private static void init(String[] ip){
        IP=ip;
    }
    public static String next(){
        lock.lock();
        String var = "";
        int size = IP.length;
        if (location==size-1){
            var = IP[location];
            location = 0;
            return var;
        }
        var = IP[location];
        location++;
        lock.unlock();
        return var;
    }
}
