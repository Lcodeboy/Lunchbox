package com.server.client;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class testLog {
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        Logger log = Logger.getLogger(NClient.class.getName());
        System.out.println(
        Thread.currentThread().getContextClassLoader().getResource("").getPath());

    }
}
