package com.webmaple.common.enums;

/**
 * @author lyifee
 * on 2021/1/5
 */
public enum CommonErrorCode {
    NULL_PARAM("null_param")
    ;
    private String code;

    CommonErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
