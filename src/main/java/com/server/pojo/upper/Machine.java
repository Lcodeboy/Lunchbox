package com.server.pojo.upper;

public class Machine {
    private String borg;
    private String uuid;
    private String adn;
    private String bid;
    private String devicestatus;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getDevicestatus() {
        return devicestatus;
    }

    public void setDevicestatus(String devicestatus) {
        this.devicestatus = devicestatus;
    }

    public String getBorg() {
        return borg;
    }

    public void setBorg(String borg) {
        this.borg = borg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAdn() {
        return adn;
    }

    public void setAdn(String adn) {
        this.adn = adn;
    }

}
