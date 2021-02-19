package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/27
 */
public enum JobState {
    RUNNING("执行中"),
    STOP("暂停中")
    ;
    private String state;

    JobState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
