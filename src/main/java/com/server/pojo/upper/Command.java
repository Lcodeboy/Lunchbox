package com.server.pojo.upper;

import org.apache.log4j.Logger;

public class Command {
    private static String LOCALIP = "100001";
    private static String BEGIN = "#";
    private static String READ = "11";
    private static String NOSET = "ff";
    private static String GET_STATUS = "#01=";
    private static String SET_STATUS = "#101";
    private static String SET_IP = "#02="+LOCALIP;
    private static String BORG = "borg";
    private static String UUID = "#uuid=";
    private static Logger logger = Logger.getLogger(Command.class);




    private static String addZero(String var){
        if (var.length()==1){
            return "00" + var;
        }
        return "0" + var;
    }
    public static String setStatus(Machine machine){
        String borg = machine.getBorg();
        String adn = machine.getAdn();
        String uuid = machine.getUuid();
        String status = machine.getDevicestatus();
        String result = BORG+borg+"/"+BEGIN+UUID+uuid+GET_STATUS+NOSET+SET_STATUS+status+NOSET+SET_IP+addZero(adn)+NOSET;

        logger.debug(result);
        return result;
    }
    public static String getStatus(Machine machine){
        String borg = machine.getBorg();
        String uuid = machine.getUuid();
        String adn = machine.getAdn();
        String result = BORG+borg+"/"+BEGIN+UUID+GET_STATUS+READ+SET_STATUS+NOSET+SET_IP+addZero(adn)+NOSET;
        logger.debug(result);
        return result;
    }
}
