package com.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/2/2
 */
public enum SourceType {
    SERVER('0'),
    MYSQL('1'),
    MONGODB('2'),
    REDIS('3')
    ;
    private char type;

    SourceType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
