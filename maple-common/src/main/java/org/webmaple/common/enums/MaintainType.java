package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/12
 */
public enum MaintainType {
    TIME("time"),
    URL_NUM("urlNum")
    ;
    private String type;

    MaintainType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static MaintainType getType(String type) {
        for (MaintainType maintainType : MaintainType.values()) {
            if (maintainType.getType().equals(type)) {
                return maintainType;
            }
        }
        return null;
    }
}
