package com.webmaple.worker.job;

/**
 * @author lyifee
 * on 2021/1/12
 */
public enum JobMapDataKey {
    SPIDER_ID("spiderId"),
    EXTRA_INFO("extraInfo"),
    PROCESSOR("processor"),
    DOWNLOADER("downloader"),
    PIPELINE("pipeline"),
    URLS("urls"),
    UUID("uuid"),
    THREAD_NUM("threadNum"),
    MAINTAIN_URL_NUM("maintainUrlNum"),
    MAINTAIN_TIME("maintainTime")
    ;
    private String key;

    JobMapDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
