package com.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/25
 */
public enum ComponentType {
    PROCESSOR("processor"),
    DOWNLOADER("downloader"),
    PIPELINE("pipeline")
    ;
    private String type;

    ComponentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
