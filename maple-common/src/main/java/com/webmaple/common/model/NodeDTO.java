package com.webmaple.common.model;

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

    /**
     * name是一个节点的唯一标识
     * 因为用ip会出现复杂网络情况，比如机器使用了代理/连接的是动态网络等
     *
     * name of node
     */
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}