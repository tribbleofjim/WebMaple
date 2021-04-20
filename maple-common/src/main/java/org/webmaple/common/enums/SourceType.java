package org.webmaple.common.enums;

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

    public static String getTypeStr(char type) throws Exception {
        SourceType[] types = SourceType.values();
        for (SourceType sourceType : types) {
            if (sourceType.type == type) {
                return sourceType.name().toLowerCase();
            }
        }
        throw new Exception("invalid type : " + type);
    }
}
