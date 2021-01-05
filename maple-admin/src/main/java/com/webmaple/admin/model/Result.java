package com.webmaple.admin.model;

/**
 * @author lyifee
 * on 2020/12/27
 */
public class Result {
    private String message;

    private Boolean success;

    private Object model;

    private Result(String message, boolean success, Object model) {
        this.message = message;
        this.success = success;
        this.model = model;
    }

    public static Result success() {
        return new Result(null, true, null);
    }

    public static Result fail() {
        return new Result(null, false, null);
    }

    public static Result success(String message) {
        return new Result(message, true, null);
    }

    public static Result fail(String message) {
        return new Result(message, false, null);
    }

    public static Result success(Object model) {
        return new Result(null, true, model);
    }

    public static Result fail(Object model) {
        return new Result(null, false, model);
    }

    public static Result success(String message, Object model) {
        return new Result(message, true, model);
    }

    public static Result fail(String message, Object model) {
        return new Result(message, false, model);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}

