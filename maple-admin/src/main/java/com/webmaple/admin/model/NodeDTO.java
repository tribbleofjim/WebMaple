package com.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/1/7
 */
public class NodeDTO {
    /**
     * worker or admin
     */
    private String type;

    /**
     * ip address of node
     */
    private String ip;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
