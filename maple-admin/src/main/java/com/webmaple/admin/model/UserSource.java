package com.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/2/2
 */
public class UserSource {
    private String phone;

    private String sourceName;

    private Character sourceType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Character getSourceType() {
        return sourceType;
    }

    public void setSourceType(Character sourceType) {
        this.sourceType = sourceType;
    }
}
