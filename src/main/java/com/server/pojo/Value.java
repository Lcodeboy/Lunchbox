package com.server.pojo;

import com.server.mainserver.transmitter.Swap;

import java.util.concurrent.locks.Condition;

public class Value {
    private long time;
    private Condition condition;
    private Swap swap;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Swap getSwap() {
        return swap;
    }

    public void setSwap(Swap swap) {
        this.swap = swap;
    }
}
