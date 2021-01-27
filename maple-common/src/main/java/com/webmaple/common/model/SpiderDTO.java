package com.webmaple.common.model;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
public class SpiderDTO {
    /**
     * spider 所在 ip
     */
    private String ip;

    /**
     * spider起始URL列表
     */
    private List<String> urls;

    /**
     * spider的uuid，也是spider唯一标识
     */
    private String uuid;

    /**
     * spider当前状态，启动/暂停/异常
     */
    private String state;

    /**
     * spider线程数
     */
    private int threadNum;

    /**
     * spider processor 全类名
     */
    private String processor;

    /**
     * spider pipeline 全类名
     */
    private String pipeline;

    /**
     * spider downloader 全类名
     */
    private String downloader;

    @Override
    public String toString() {
        return "SpiderDTO{" +
                "ip='" + ip + '\'' +
                ", urls=" + urls +
                ", uuid='" + uuid + '\'' +
                ", state='" + state + '\'' +
                ", threadNum=" + threadNum +
                ", processor='" + processor + '\'' +
                ", pipeline='" + pipeline + '\'' +
                ", downloader='" + downloader + '\'' +
                '}';
    }

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

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public String getDownloader() {
        return downloader;
    }

    public void setDownloader(String downloader) {
        this.downloader = downloader;
    }
}
