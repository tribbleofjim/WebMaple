package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/5/3
 */
public enum MessageType {
    APPLY('0', "申请"), ACCUSE('1', "举报"), OTHER('2', "其他")
    ;
    private Character type;
    private String name;

    MessageType(Character type, String name) {
        this.type = type;
        this.name = name;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
