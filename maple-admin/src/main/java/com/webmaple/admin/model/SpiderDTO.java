package com.webmaple.admin.model;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
public class SpiderDTO {
    private String ip;

    private List<String> urls;

    private String uuid;

    private String state;

    private int threadNum;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
