package org.webmaple.common.model;

/**
 * @author lyifee
 * on 2021/1/25
 */
public class SpiderProcessDTO {
    /**
     * SpiderProcess所在ip地址
     */
    private String ip;

    /**
     * SpiderProcess所处worker
     */
    private String worker;

    /**
     * SpiderProcess所处端口号
     */
    private int port;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
