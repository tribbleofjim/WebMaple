package com.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/1/12
 */
public class JobDTO {
    private String ip;

    private String worker;

    private String spiderUUID;

    private String spiderSite;

    private String type;

    private int maintain;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getSpiderUUID() {
        return spiderUUID;
    }

    public void setSpiderUUID(String spiderUUID) {
        this.spiderUUID = spiderUUID;
    }

    public String getSpiderSite() {
        return spiderSite;
    }

    public void setSpiderSite(String spiderSite) {
        this.spiderSite = spiderSite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaintain() {
        return maintain;
    }

    public void setMaintain(int maintain) {
        this.maintain = maintain;
    }
}
