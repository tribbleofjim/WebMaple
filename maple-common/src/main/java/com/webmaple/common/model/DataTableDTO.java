package com.webmaple.common.model;

/**
 * layui数据网格返回对象
 *
 * @author lyifee
 * on 2021/1/6
 */
public class DataTableDTO {
    /**
     * 返回http响应码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据总量
     */
    private int count;

    /**
     * 返回数据
     */
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
