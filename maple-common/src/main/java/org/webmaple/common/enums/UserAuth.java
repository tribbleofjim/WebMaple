package org.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/2/6
 */
public enum UserAuth {
    ADMIN('0'),
    USER('1')
    ;
    private char auth;

    UserAuth(char auth) {
        this.auth = auth;
    }

    public char getAuth() {
        return auth;
    }

    public void setAuth(char auth) {
        this.auth = auth;
    }
}
