package com.server.pojo.upper;

import org.apache.log4j.Logger;
import com.server.util.upper.Factory;
import com.server.util.upper.JsonSplit;

public class Change {
    private static Logger logger = Logger.getLogger(Change.class);
    public static Machine toMachine(JsonSplit split){
        Machine machine = new Machine();
        machine.setAdn(Factory.canelQuotation(split.getADM()));
        machine.setUuid(Factory.canelQuotation(split.getUUID()));
        machine.setBorg(Factory.canelQuotation(split.getBROG()));
        machine.setDevicestatus(Factory.canelQuotation(split.getDEVICESTATUS()));
        machine.setBid(Factory.canelQuotation(split.getBID()));



        return machine;
    }
}
