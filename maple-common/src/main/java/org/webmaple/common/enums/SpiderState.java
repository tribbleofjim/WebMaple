package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/6
 */
public enum SpiderState {
    RUNNING("执行中"),
    STOP("暂停中"),
    TIMED("定时爬虫"),
    EXCEPTION("异常")
    ;
    private String state;

    SpiderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
