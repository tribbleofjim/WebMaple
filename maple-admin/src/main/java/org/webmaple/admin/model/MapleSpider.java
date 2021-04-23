package org.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/4/23
 */
public class MapleSpider {
    /**
     * spider 所在 worker
     */
    private String worker;

    /**
     * spider起始URL列表
     */
    private String urls;

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

    /**a
     * spider pipeline 全类名
     */
    private String pipeline;

    /**
     * spider downloader 全类名
     */
    private String downloader;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
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
