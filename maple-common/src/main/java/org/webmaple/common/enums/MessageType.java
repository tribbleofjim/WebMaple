package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/5/3
 */
public enum MessageType {
    APPLY('0'), ACCUSE('1'), REST('2')
    ;
    private Character type;

    MessageType(Character type) {
        this.type = type;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }
}
