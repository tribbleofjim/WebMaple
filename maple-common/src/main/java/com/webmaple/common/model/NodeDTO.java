package com.webmaple.common.model;

import com.alibaba.fastjson.JSONObject;

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

    /**
     * name = worker + idx
     */
    private int idx;

    /**
     * 端口号，用于识别worker上不同spiderProcess
     */
    private int port;

    @Override
    public String toString() {
        return "NodeDTO{" +
                "type='" + type + '\'' +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", idx=" + idx +
                ", port=" + port +
                '}';
    }

    public static NodeDTO fromString(String node) {
        node = node.replaceFirst("NodeDTO", "");
        JSONObject jsonObject = JSONObject.parseObject(node);
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setName(jsonObject.getString("name"));
        nodeDTO.setIp(jsonObject.getString("ip"));
        nodeDTO.setPort(jsonObject.getInteger("port"));
        nodeDTO.setType(jsonObject.getString("type"));
        nodeDTO.setIdx(jsonObject.getInteger("idx"));
        return nodeDTO;
    }

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

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
