package org.webmaple.worker.job.model;

/**
 * @author lyifee
 * on 2021/1/13
 */
public class SpiderInfo {
    private String processor;

    private String pipeline;

    private String urls;

    private String uuid;

    private String downloader;

    private int threadNum;

    private int maintainUrlNum;

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

    public String getDownloader() {
        return downloader;
    }

    public void setDownloader(String downloader) {
        this.downloader = downloader;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getMaintainUrlNum() {
        return maintainUrlNum;
    }

    public void setMaintainUrlNum(int maintainUrlNum) {
        this.maintainUrlNum = maintainUrlNum;
    }
}
