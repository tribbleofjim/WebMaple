package com.webmaple.admin.enums;

/**
 * @author lyifee
 * on 2021/1/6
 */
public enum SpiderState {
    RUNNING("running"),
    STOP("stop")
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
