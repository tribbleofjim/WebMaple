package com.webmaple.admin.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lyifee
 * on 2021/1/6
 */
public class DataTableDTO {
    private int code;

    private String msg;

    private int count;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
