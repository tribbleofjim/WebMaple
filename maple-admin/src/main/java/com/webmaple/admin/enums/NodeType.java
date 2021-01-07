package com.webmaple.admin.enums;

/**
 * @author lyifee
 * on 2021/1/7
 */
public enum NodeType {
    WORKER("worker"),
    ADMIN("admin")
    ;
    private String type;

    NodeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
