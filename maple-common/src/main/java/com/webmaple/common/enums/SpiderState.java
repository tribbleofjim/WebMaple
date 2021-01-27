package com.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/6
 */
public enum SpiderState {
    RUNNING("running"),
    STOP("stop"),
    TIMED("timed"),
    EXCEPTION("exception")
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
