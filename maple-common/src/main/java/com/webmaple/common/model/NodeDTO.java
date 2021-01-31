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
        node = node.replaceFirst("NodeDTO", "").replace("{", "").replace("}", "");
        String[] params = node.split(",");

        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setType(params[0].split("=")[1].replace("'", ""));
        nodeDTO.setIp(params[1].split("=")[1].replace("'", ""));
        nodeDTO.setName(params[2].split("=")[1].replace("'", ""));
        nodeDTO.setIdx(Integer.parseInt(params[3].split("=")[1]));
        nodeDTO.setPort(Integer.parseInt(params[4].split("=")[1]));
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
